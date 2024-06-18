import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class PaginaPlaylist extends JFrame implements ActionListener {
    private Playlist playlist;
    private JPanel cancionesPanel;
    private Playlist meGustasPlaylist;
    private ImageIcon heartIcon;
    private ImageIcon filledHeartIcon;
    private CollectionsPlaylist playlists;
    private CollectionUsuarios usuarios;
    private Usuario activo;

    File f = new File("zCanciones.json");
    File f3 = new File("zplaylists.json");
    File f2 = new File("Usuarios.json");

    public PaginaPlaylist(Playlist playlist, Playlist meGustasPlaylist, CollectionsPlaylist playlists, Usuario activo) {
        this.playlist = playlist;
        this.meGustasPlaylist = meGustasPlaylist;                      //Esta pagina es la cual muestra las canciones de las playlist
        this.playlists = playlists;
        this.activo = activo;
        usuarios = new CollectionUsuarios();

        setTitle(playlist.getNombre());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //Cerrar ventana sin cerrar aplicacion
        setLayout(new BorderLayout());
        //Añadir icono
        ImageIcon icono = new ImageIcon("Media/Spotifake.png");
        setIconImage(icono.getImage());
        cancionesPanel = new JPanel(new GridLayout(0, 1)); // Layout para las canciones
        cancionesPanel.setBackground(new Color(0x191414)); // Cambio de color de fondo

        // Ajustar el tamaño de los iconos
        heartIcon = cargarIcono("Media/filled-heart-icon.png", 20, 20);
        filledHeartIcon = cargarIcono("Media/heart-icon.png", 20, 20);
        usuarios.leerArchivo(f2);
        ArrayList<Usuario> usuariosaux = new ArrayList<>(usuarios.getUsuarios().values());
        for (Cancion cancion : playlist.getCancionesPlaylist()) {
            JPanel cancionPanel = new JPanel(new BorderLayout());
            cancionPanel.setBackground(new Color(0x191414)); // Cambio de color de fondo
            JButton cancionButton = new JButton(cancion.getNombre());
            cancionButton.setForeground(new Color(0x1db954)); // Cambio de color del texto
            cancionButton.setBackground(new Color(0x191414)); // Cambio de color de fondo
            cancionButton.addActionListener(this);
            cancionPanel.add(cancionButton, BorderLayout.CENTER);

            JButton likeButton = new JButton(meGustasPlaylist.getCancionesPlaylist().contains(cancion) ? filledHeartIcon : heartIcon);
            likeButton.setActionCommand(cancion.getNombre());
            likeButton.setBackground(new Color(0x191414)); // Cambio de color de fondo
            likeButton.addActionListener(e -> {
                if (meGustasPlaylist.getCancionesPlaylist().contains(cancion)) {
                    meGustasPlaylist.eliminarCancion(cancion);
                    likeButton.setIcon(heartIcon);
                    activo.setTusMeGusta(meGustasPlaylist);
                    int i = 0;
                    for (Usuario aux : usuariosaux) {
                        if (aux.getNickname().equals(activo.getNickname())) {
                            break;
                        }
                        i++;
                    }
                    usuariosaux.set(i, activo);
                    usuarios.reescribirArchivoLinea(f2, usuariosaux);
                } else {
                    meGustasPlaylist.agregarCancion(cancion);
                    likeButton.setIcon(filledHeartIcon);
                    activo.setTusMeGusta(meGustasPlaylist);
                    int i = 0;
                    for (Usuario aux : usuariosaux) {
                        if (aux.getNickname().equals(activo.getNickname())) {
                            break;
                        }
                        i++;
                    }
                    usuariosaux.set(i, activo);
                    usuarios.reescribirArchivoLinea(f2, usuariosaux);
                }
            });
            cancionPanel.add(likeButton, BorderLayout.EAST);

            cancionesPanel.add(cancionPanel);
        }

        add(new JScrollPane(cancionesPanel), BorderLayout.CENTER);
        getContentPane().setBackground(new Color(0x191414)); // Cambio de color de fondo del contenedor principal

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private ImageIcon cargarIcono(String ruta, int width, int height) {
        ImageIcon icono = new ImageIcon(ruta);
        if (icono.getImageLoadStatus() == MediaTracker.COMPLETE) {
            Image imagen = icono.getImage();
            Image imagenEscalada = imagen.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(imagenEscalada);
        } else {
            System.out.println("Error al cargar la imagen: " + ruta);
            return null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        String cancionName = clickedButton.getText();

        for (Cancion cancion : playlist.getCancionesPlaylist()) {
            if (cancion.getNombre().equals(cancionName)) {
                ArrayList<Cancion> canciones = new ArrayList<>();
                canciones.add(cancion);
                new ReproductorMusica(new ArrayList<>(canciones), playlists); // Reproducir la canción seleccionada
                break;
            }
        }
    }
}