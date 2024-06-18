import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class LoginWindow extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;


    public LoginWindow(File archivo,CollectionUsuarios collectionUsuarios) {


        // Configuración de la ventana
        setTitle("Iniciar Sesión");
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

        // Etiqueta de usuario
        JLabel usernameLabel = new JLabel("Usuario:");
        usernameLabel.setForeground(Color.decode("#1ED760"));
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(usernameLabel, gbc);

        // Campo de texto para el usuario
        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(usernameField, gbc);

        // Etiqueta de contraseña
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setForeground(Color.decode("#1ED760"));
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(passwordLabel, gbc);

        // Campo de texto para la contraseña
        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(passwordField, gbc);

        // Botón de inicio de sesión
        JButton loginButton = new JButton("Iniciar Sesión");
        loginButton.setBackground(Color.decode("#1ED760"));
        loginButton.setForeground(Color.decode("#191414"));
        loginButton.setPreferredSize(new Dimension(150, 30));
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(loginButton, gbc);
        // CheckBox para "Entrar como admin"
        JCheckBox adminCheckBox = new JCheckBox("Entrar como admin");
        adminCheckBox.setForeground(Color.decode("#1ED760"));
        adminCheckBox.setFont(new Font("Arial", Font.PLAIN, 12));
        adminCheckBox.setBackground(Color.decode("#191414"));
        gbc.gridx = 0; // Coloca el CheckBox en la primera columna
        gbc.gridy = 5; // Coloca el CheckBox debajo del botón de registrar usuario
        gbc.gridwidth = 2; // El CheckBox abarca dos columnas
        mainPanel.add(adminCheckBox, gbc);
        //Boton de registrar usuario
        JButton registrarUsuarioButton = new JButton("Registrar Usuario");
        registrarUsuarioButton.setBackground(Color.decode("#1ED760")); // Mismo color que el botón de inicio de sesión
        registrarUsuarioButton.setForeground(Color.decode("#191414")); // Mismo color de texto que el botón de inicio de sesión
        registrarUsuarioButton.setPreferredSize(new Dimension(150, 30)); // Mismo tamaño que el botón de inicio de sesión
        registrarUsuarioButton.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14)); // Misma fuente y tamaño de texto que el botón de inicio de sesión
        registrarUsuarioButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Borde vacío
        registrarUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear una nueva instancia de la ventana de registro de usuarios y hacerla visible
                RegistrarUsuarioWindow registroUsuarioWindow = new RegistrarUsuarioWindow(collectionUsuarios,archivo);
                registroUsuarioWindow.setVisible(true);
            }
        });
        gbc.gridx = 0; // Coloca el botón en la primera columna
        gbc.gridy = 4; // Coloca el botón debajo del botón de inicio de sesión
        gbc.gridwidth = 2; // El botón abarca dos columnas
        mainPanel.add(registrarUsuarioButton, gbc);


        // Agregar el panel principal a la ventana
        add(mainPanel, BorderLayout.CENTER);
        // Ajustar el tamaño de la ventana para que quepan todos los componentes
        pack();
        // Ajustar el logo para la ventana
        ImageIcon logoIcon2 = new ImageIcon("Media/Spotifake.png");
        Image logoImage = logoIcon2.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon logoScaledIcon = new ImageIcon(logoImage);

        // Establecer el logo como icono de la ventana
        setIconImage(logoScaledIcon.getImage());

        // Ajustar el título de la ventana
        setTitle("Spotifake");

        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null);

        // Mostrar la ventana
        setVisible(true);


        // Configurar el botón de inicio de sesión
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(adminCheckBox.isSelected())
                {
                    Administrador guille=new Administrador();
                    String username=usernameField.getText();
                    String password=passwordField.getText();
                    if(username.isEmpty() && password.isEmpty())
                    {
                        JOptionPane.showMessageDialog(LoginWindow.this, "Por favor, ingrese su usuario y contraseña", "Error", JOptionPane.ERROR_MESSAGE);

                    }
                    else if(!username.equals(guille.getUsername()))
                    {
                        JOptionPane.showMessageDialog(LoginWindow.this, "Usuario incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else if (username.equals(guille.getUsername()))
                    {
                        if(password.equals(guille.getPassword()))
                        {
                            MenuAdmin menuAdmin=new MenuAdmin(archivo);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(LoginWindow.this, "Contraseña incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                    else
                    {
                        JOptionPane.showMessageDialog(LoginWindow.this, "Datos incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
                else {
                    iniciarSesion(archivo);
                }

            }
        });

        // Mostrar la ventana
        setVisible(true);
    }
    
    
    private void iniciarSesion(File archivo) {
        CollectionsCancion canciones = new CollectionsCancion();
        CollectionsPlaylist playlists = new CollectionsPlaylist();
        
         // CREAR ARCHIVOS
        File f = new File("zCanciones.json");
        File f3 = new File("zplaylists.json");
        File f2 = new File("Usuarios.json");

        String username = usernameField.getText();
        String password = passwordField.getText();
    
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese su usuario y contraseña", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        // Lee el TreeMap de usuarios desde el archivo JSON
        CollectionUsuarios usuarios = new CollectionUsuarios();
        usuarios.leerArchivo(f2);
        playlists.bajarArchivo(f3);
        canciones.bajarArchivo(f);
        // Recorre el TreeMap de usuarios para encontrar el usuario correspondiente
        if (usuarios.comprobarNickname(username)) {
            Usuario usuario = usuarios.devolverUsuario(username);
            if (password.equals(usuario.getContrasenia())) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                String activo = usuario.getNickname();
                playlists.agregarPlaylist(usuario.getTusMeGusta());
                SwingUtilities.invokeLater(() -> new PaginaPrincipal(playlists, usuario));

            } else {
                JOptionPane.showMessageDialog(this, "Contraseña incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
         else {
            JOptionPane.showMessageDialog(this, "Usuario incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    

}
