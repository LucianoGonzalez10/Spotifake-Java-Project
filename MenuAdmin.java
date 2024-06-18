import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.swing.*;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MenuAdmin extends JFrame {
    private static final String archivoCanciones = "zCanciones.json";               //Funciones de admin, hechas para eliminar, borrar, listar y modificar cosas
    private JList<Cancion> listaCanciones;
    private JList<Usuario> listaUsuarios;
    private DefaultListModel<Usuario> modeloListaUsuarios;
    private List<Usuario> usuarios;
    File archivo2;

    public MenuAdmin(File archivo) {
        listaCanciones = new JList<>();
        usuarios = new ArrayList<>();
        modeloListaUsuarios = new DefaultListModel<>();
        listaUsuarios = new JList<>(modeloListaUsuarios);
        this.archivo2=new File("zplaylists.json");

        // Configuración de la ventana
        setTitle("Menú Administrador");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear y configurar el panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.decode("#191414"));
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Crear un panel para el título y el logo
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.decode("#191414"));

        // Etiqueta de título
        JLabel titleLabel = new JLabel("Spotifake");
        titleLabel.setForeground(Color.decode("#1ED760"));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Mismo estilo que otros componentes
        titlePanel.add(titleLabel);

        // Etiqueta de logo
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("Media/Spotifake.png"); // Ruta al logo
        // Ajustar el tamaño del logo
        Image logoImage2 = logoIcon.getImage();
        Image logoImageScaled = logoImage2.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Ajustar el tamaño del logo aquí
        ImageIcon logoIconScaled = new ImageIcon(logoImageScaled);
        logoLabel.setIcon(logoIconScaled);
        titlePanel.add(logoLabel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titlePanel, gbc);

        // Reset gridwidth
        gbc.gridwidth = 1;

        // Ajustar el logo para la ventana
        ImageIcon logoIcon2 = new ImageIcon("Media/Spotifake.png");
        Image logoImage = logoIcon2.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon logoScaledIcon = new ImageIcon(logoImage);

        // Establecer el logo como icono de la ventana
        setIconImage(logoScaledIcon.getImage());

        // Ajustar el título de la ventana
        setTitle("Spotifake");

        // Botones
        JButton btnMostrarCanciones = new JButton("Mostrar Canciones");
        JButton btnMostrarUsuarios = new JButton("Mostrar Usuarios");
        JButton btnAgregarCancion = new JButton("Agregar Canción");
        JButton btnEliminarCancion = new JButton("Eliminar Canción");
        JButton btnModificarCancion = new JButton("Modificar Canción");

        JButton[] buttons = {btnMostrarCanciones, btnMostrarUsuarios, btnAgregarCancion, btnEliminarCancion, btnModificarCancion};

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setBackground(Color.decode("#1ED760"));
            buttons[i].setForeground(Color.decode("#191414"));
            buttons[i].setPreferredSize(new Dimension(200, 30));
            buttons[i].setFont(new Font("Arial", Font.BOLD, 14));
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.gridwidth = 2;
            mainPanel.add(buttons[i], gbc);
        }

        // Agregar acciones a los botones
        btnMostrarCanciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarCanciones();
            }
        });

        btnMostrarUsuarios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarUsuarios(archivo);
            }
        });

        btnAgregarCancion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarCancion(archivoCanciones);
            }
        });

        btnEliminarCancion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarCancion();
            }
        });

        btnModificarCancion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modificarCancion();
            }
        });

        // Agregar el panel principal a la ventana
        add(mainPanel, BorderLayout.CENTER);

        // Ajustar el tamaño de la ventana para que quepan todos los componentes
        pack();

        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null);

        // Mostrar la ventana
        setVisible(true);
    }

    // METODOS
    public void mostrarCanciones() {
        leerCancionesDesdeJson(archivoCanciones);

        JFrame frame = new JFrame("Lista de Canciones");
        frame.setTitle("Spotifake");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.decode("#191414"));
        mainPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Lista de Canciones");
        titleLabel.setForeground(Color.decode("#1ED760"));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        listaCanciones.setBackground(Color.decode("#191414"));
        listaCanciones.setForeground(Color.decode("#1ED760"));
        listaCanciones.setSelectionBackground(Color.decode("#1ED760"));
        listaCanciones.setSelectionForeground(Color.decode("#191414"));
        listaCanciones.setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(listaCanciones);
        scrollPane.getViewport().setBackground(Color.decode("#191414"));
        scrollPane.setBorder(null);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Agregar el logo de Spotifake como icono de la ventana
        ImageIcon logoIcon = new ImageIcon("Media/Spotifake.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon logoScaledIcon = new ImageIcon(logoImage);
        frame.setIconImage(logoScaledIcon.getImage());

        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        frame.setSize(800, 600); // Ajustar el tamaño según sea necesario
        frame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        frame.setVisible(true);
    }

    public void mostrarUsuarios(File archivo) {
        leerUsuariosDesdeJson(archivo);

        JFrame frame = new JFrame("Lista de Usuarios");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.decode("#191414"));
        mainPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Lista de Usuarios");
        titleLabel.setForeground(Color.decode("#1ED760"));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        listaUsuarios.setBackground(Color.decode("#191414"));
        listaUsuarios.setForeground(Color.decode("#1ED760"));
        listaUsuarios.setSelectionBackground(Color.decode("#1ED760"));
        listaUsuarios.setSelectionForeground(Color.decode("#191414"));
        listaUsuarios.setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(listaUsuarios);
        scrollPane.getViewport().setBackground(Color.decode("#191414"));
        scrollPane.setBorder(null);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Cargar y establecer el logo de Spotify como icono de la ventana
        ImageIcon logoIcon = new ImageIcon("Media/Spotifake.png"); // Reemplaza con la ruta correcta de tu archivo de imagen
        Image logoImage = logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon logoScaledIcon = new ImageIcon(logoImage);
        frame.setIconImage(logoScaledIcon.getImage());

        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Establecer el título de la ventana
        frame.setTitle("Spotifake");

        // Calcular el tamaño de la ventana en función del número de usuarios
        int numUsuarios = modeloListaUsuarios.getSize();
        int alturaElemento = listaUsuarios.getFixedCellHeight();
        if (alturaElemento == -1) {
            alturaElemento = 24; // Valor por defecto si no está establecido
        }
        int alturaTotal = numUsuarios * alturaElemento + 100; // Ajuste adicional para el título y bordes
        int alturaMaxima = 600; // Altura máxima permitida

        // Ajustar la altura y asegurarse de que no exceda la altura máxima
        if (alturaTotal > alturaMaxima) {
            alturaTotal = alturaMaxima;
        }

        // Establecer el tamaño de la ventana
        frame.setSize(1200, alturaTotal);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void leerUsuariosDesdeJson(File archivo) {
        ObjectMapper mapper = new ObjectMapper();
        if (archivo.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                modeloListaUsuarios.clear(); // Limpiar el modelo antes de agregar los usuarios
                while ((linea = br.readLine()) != null) {
                    Usuario usuario = mapper.readValue(linea, Usuario.class);
                    modeloListaUsuarios.addElement(usuario); // Agregar el usuario al modelo de la lista
                    System.out.println(usuario);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("El archivo no existe");
        }
    }


    public void agregarCancion(String nombreArchivo) {
        int id = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id de la canción:"));
        if (verificarIdCancion(id)) {
            JOptionPane.showMessageDialog(null, "El ID de canción ya existe.");
            return;
        } else {
            String nombre = JOptionPane.showInputDialog("Ingrese el nombre:");
            String genero = JOptionPane.showInputDialog("Ingrese el género:");
            String rutaCancion = JOptionPane.showInputDialog("Ingrese la URL de la canción:");
            String rutaPortada = JOptionPane.showInputDialog("Ingrese la URL de la portada:");

            Cancion nuevaCancion = new Cancion(id, nombre, genero, rutaCancion, rutaPortada);
            CollectionsCancion canciones2=new CollectionsCancion();
            canciones2.bajarArchivo(new File(archivoCanciones));
            canciones2.agregarCanciones(nuevaCancion);
            CollectionsPlaylist playlist=new CollectionsPlaylist();
            playlist.bajarArchivo(archivo2);
            for(Playlist aux: playlist.getPlaylists().values())
            {
                if(aux.getNombre().equalsIgnoreCase(nuevaCancion.getGenero()))
                {
                    aux.agregarCancion(nuevaCancion);
                }

            }
            canciones2.cargarArchivo(new File(archivoCanciones));
            playlist.cargarArchivo(archivo2);


        }
    }

    private void leerCancionesDesdeJson(String nombreArchivo) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            LinkedHashMap<Integer, Cancion> canciones = mapper.readValue(new File(nombreArchivo), new TypeReference<LinkedHashMap<Integer, Cancion>>() {});
            DefaultListModel<Cancion> modeloLista = new DefaultListModel<>();
            for (Cancion cancion : canciones.values()) {
                modeloLista.addElement(cancion);
            }
            listaCanciones.setModel(modeloLista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Cancion> leerCancionesDesdeJsonRetorna(String nombreArchivo) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File archivo = new File(nombreArchivo);
            if (archivo.exists()) {
                return mapper.readValue(archivo, new TypeReference<List<Cancion>>() {});
            } else {
                System.out.println("El archivo de canciones no existe.");
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public void eliminarCancion() {
        // Configurar el diálogo de entrada
        JTextField idField = new JTextField(5);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Ingrese el ID de la canción:"));
        panel.add(idField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Eliminar Canción", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            int idCancion;
            try {
                idCancion = Integer.parseInt(idField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ID inválido. Por favor ingrese un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            CollectionsCancion canciones=new CollectionsCancion();
            canciones.bajarArchivo(new File(archivoCanciones));

            boolean encontrado = canciones.removeSong(idCancion);
            if(encontrado)
            {
                canciones.cargarArchivo(new File(archivoCanciones));
                CollectionsPlaylist playlist=new CollectionsPlaylist();
                playlist.bajarArchivo(archivo2);
                playlist.removeSong(idCancion);
                playlist.cargarArchivo(archivo2);
            }


            if (!encontrado) {
                JOptionPane.showMessageDialog(null, "No se encontró ninguna canción con el ID especificado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
    public void modificarCancion() {
        eliminarCancion();
        agregarCancion(archivoCanciones);
    }
    private boolean verificarIdCancion(int id) {
        CollectionsCancion canciones=new CollectionsCancion();
        canciones.bajarArchivo(new File(archivoCanciones));
        if(canciones.retornarsiHayID(id))
        {
            return true;
        }
        else
        {
            return false;
        }

    }


}

