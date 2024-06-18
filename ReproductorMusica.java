import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReproductorMusica extends JFrame implements ActionListener, ChangeListener {
    private JButton playPauseButton;
    private JLabel timeLabel, coverLabel;
    private JSlider timeSlider;
    private Clip clip;
    private boolean isPlaying;
    private ArrayList<Cancion> listaCanciones;
    private int currentIndex;
    private CollectionsPlaylist playlists;

    // Ajustar el tamaño de los íconos a 20x20 píxeles
    private final ImageIcon playIcon = new ImageIcon(new ImageIcon(getClass().getResource("Media/play.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    private final ImageIcon pauseIcon = new ImageIcon(new ImageIcon(getClass().getResource("Media/16427.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));

    public ReproductorMusica(ArrayList<Cancion> listaCanciones, CollectionsPlaylist playlists) {
        this.listaCanciones = listaCanciones;
        this.playlists = playlists;
        this.currentIndex = 0;
        this.isPlaying = false;

        setTitle("Reproductor de Música");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);                 //Esto es el reproductor de musica, aqui se veran las portadas y se escucharan las canciones
        setLayout(new BorderLayout());

        // Panel para la imagen de la portada
        JPanel coverPanel = new JPanel(new GridBagLayout());
        coverLabel = new JLabel();
        ImageIcon icon = new ImageIcon(getClass().getResource(listaCanciones.get(currentIndex).getRutaPortada()));
        coverLabel.setIcon(icon);
        coverPanel.add(coverLabel, new GridBagConstraints());
        add(coverPanel, BorderLayout.CENTER);

        // Panel de control
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        playPauseButton = new JButton();
        playPauseButton.setIcon(playIcon);
        playPauseButton.setBackground(Color.GREEN); // Establecer el color de fondo en verde
        playPauseButton.setForeground(Color.BLACK);
        playPauseButton.addActionListener(this);
        controlPanel.add(playPauseButton, gbc);

        add(controlPanel, BorderLayout.SOUTH);

        // Etiqueta de tiempo
        timeLabel = new JLabel("Tiempo: 0:00", JLabel.CENTER);
        add(timeLabel, BorderLayout.NORTH);

        // Barra de reproducción
        timeSlider = new JSlider(0, 100, 0);
        timeSlider.setEnabled(false);
        timeSlider.addChangeListener(this);
        timeSlider.setBackground(Color.BLACK);
        timeSlider.setForeground(Color.GREEN);
        add(timeSlider, BorderLayout.WEST);

        // Listener para detener la música al cerrar la ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                detenerCancion();
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playPauseButton) {
            if (isPlaying) {
                pausarCancion();
            } else {
                reproducirCancion();
            }
        }
    }

    private void reproducirCancion() {
        try {
            if (clip == null || !clip.isOpen()) {
                clip = AudioSystem.getClip();
                String rutaCancion = limpiarEspacios(listaCanciones.get(currentIndex).getRutaCancion()); //Esto se utiliza para que no haya errores a la hora de pasar la ruta, ya que un espacio al principio o al final, romperia todo
                clip.open(AudioSystem.getAudioInputStream(new File(rutaCancion)));
            }
            clip.start();
            playPauseButton.setIcon(pauseIcon);
            actualizarTiempo(); // Actualizar el tiempo al iniciar la reproducción

            new Thread(() -> {
                while (clip.isActive()) {
                    long microSeconds = clip.getMicrosecondPosition();
                    long duration = clip.getMicrosecondLength();
                    int value = (int) (100 * microSeconds / duration);
                    SwingUtilities.invokeLater(() -> {
                        timeSlider.setValue(value);
                        actualizarTiempo();
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                isPlaying = false;
                SwingUtilities.invokeLater(() -> playPauseButton.setIcon(playIcon));
            }).start();

            isPlaying = true;
            timeSlider.setEnabled(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private String limpiarEspacios(String ruta) {
        // Definir un patrón que coincida con espacios en blanco al inicio de la cadena
        Pattern pattern = Pattern.compile("^\\s+");

        // Usar un Matcher para encontrar el espacio en blanco al principio
        Matcher matcher = pattern.matcher(ruta);

        // Reemplazar los espacios en blanco al principio con una cadena vacía
        return matcher.replaceAll("");
    }

    private void pausarCancion() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            isPlaying = false;
            playPauseButton.setIcon(playIcon);
        }
    }
    
    private void detenerCancion() {
        if (clip != null && clip.isOpen()) {
            clip.stop();
            clip.close();
            isPlaying = false;
            playPauseButton.setIcon(playIcon);
            timeLabel.setText("Tiempo: 0:00"); // Restablecer la etiqueta de tiempo a 0:00
        }
    }

    private void actualizarTiempo() {
        long microSeconds = clip.getMicrosecondPosition();
        long seconds = microSeconds / 1_000_000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        timeLabel.setText("Tiempo: " + minutes + ":" + String.format("%02d", seconds));
    }

    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == timeSlider && clip != null) {
            if (!timeSlider.getValueIsAdjusting()) {
                long duration = clip.getMicrosecondLength();
                long newPosition = (long) (duration * timeSlider.getValue() / 100.0);
                clip.setMicrosecondPosition(newPosition);
            }
        }
    }
}
