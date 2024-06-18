import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class RegistrarUsuarioWindow extends JFrame {
    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField emailField;
    private JTextField nicknameField;
    private JPasswordField passwordField;                         //Aqui es donde se registran los usuarios
    private JPasswordField verifyPasswordField;

    public RegistrarUsuarioWindow(CollectionUsuarios collectionUsuarios, File archivo) {
        setTitle("Registrar Usuario");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra esta ventana, pero no la aplicación principal
        setLayout(new BorderLayout());

        // Crear y configurar el panel principal
        JPanel registroPanel = new JPanel();
        registroPanel.setBackground(Color.decode("#191414"));
        registroPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        // Crear un panel para el título y el logo
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.decode("#191414"));

        JLabel titleLabel = new JLabel("Spotifake");
        titleLabel.setForeground(Color.decode("#1ED760"));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Mismo estilo que otros componentes
        titlePanel.add(titleLabel);

        // Etiqueta de logo
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("Media/Spotifake.png"); // Ruta al logo
        Image logoImage2 = logoIcon.getImage();
        Image logoImageScaled = logoImage2.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Ajustar el tamaño del logo aquí
        ImageIcon logoIconScaled = new ImageIcon(logoImageScaled);
        logoLabel.setIcon(logoIconScaled);
        titlePanel.add(logoLabel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        registroPanel.add(titlePanel, gbc);

        // Reset gridwidth
        gbc.gridwidth = 1;

        // Etiqueta y campo de texto para el nombre
        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setForeground(Color.decode("#1ED760"));
        gbc.gridx = 0;
        gbc.gridy = 1; // Cambiar la fila a 2 para dejar espacio para el título
        registroPanel.add(nombreLabel, gbc);
        nombreField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1; // Cambiar la fila a 2 para dejar espacio para el título
        registroPanel.add(nombreField, gbc);

        // Etiqueta y campo de texto para el apellido
        JLabel apellidoLabel = new JLabel("Apellido:");
        apellidoLabel.setForeground(Color.decode("#1ED760"));
        gbc.gridx = 0;
        gbc.gridy = 2;
        registroPanel.add(apellidoLabel, gbc);
        apellidoField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        registroPanel.add(apellidoField, gbc);

        // Etiqueta y campo de texto para el email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.decode("#1ED760"));
        gbc.gridx = 0;
        gbc.gridy = 3;
        registroPanel.add(emailLabel, gbc);
        emailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        registroPanel.add(emailField, gbc);

        // Etiqueta y campo de texto para el username
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.decode("#1ED760"));
        gbc.gridx = 0;
        gbc.gridy = 4;
        registroPanel.add(usernameLabel, gbc);
        nicknameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        registroPanel.add(nicknameField, gbc);

        // Etiqueta y campo de texto para la contraseña
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setForeground(Color.decode("#1ED760"));
        gbc.gridx = 0;
        gbc.gridy = 5;
        registroPanel.add(passwordLabel, gbc);
        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 5;
        registroPanel.add(passwordField, gbc);

        // Etiqueta y campo de texto para verificar la contraseña
        JLabel verifyPasswordLabel = new JLabel("Verificar Contraseña:");
        verifyPasswordLabel.setForeground(Color.decode("#1ED760"));
        gbc.gridx = 0;
        gbc.gridy = 6;
        registroPanel.add(verifyPasswordLabel, gbc);
        verifyPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 6;
        registroPanel.add(verifyPasswordField, gbc);
        // Ajustar el tamaño de la ventana
        setSize(550, 450);
        // Establecer el título de la ventana
        setTitle("Iniciar Sesión - Spotifake");

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

        // Botón de registro
        JButton registrarButton = new JButton("Registrar");
        registrarButton.setBackground(Color.decode("#1ED760"));
        registrarButton.setForeground(Color.decode("#191414"));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        registroPanel.add(registrarButton, gbc);
        // Agregar ActionListener al botón de registro
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               registrarUsuario(archivo);
            }
        });

        // Envolver el panel principal en un JScrollPane
        JScrollPane scrollPane = new JScrollPane(registroPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Mostrar la ventana
        setVisible(true);
    }
    private void registrarUsuario(File archivo)
    {
        CollectionUsuarios usuario=new CollectionUsuarios();
        usuario.leerArchivo(archivo);
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        String email = emailField.getText();
        String username = nicknameField.getText();
        String password = new String(passwordField.getPassword());
        String verifyPassword = new String(verifyPasswordField.getPassword());
        if (nombre.isEmpty() || password.isEmpty()||apellido.isEmpty()||email.isEmpty()||username.isEmpty()||verifyPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese todos sus datos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else if(usuario.comprobarNickname(username))
        {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese otro username, ese ya esta en uso ","Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (!password.equals(verifyPassword)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden","Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else if(!email.endsWith("@gmail.com") && !email.endsWith("@hotmail.com"))
        {
            JOptionPane.showMessageDialog(this, "Ingrese un mail valido (Gmail o Hotmail)","Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else {
            JOptionPane.showMessageDialog(this, "Registro de usuario exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            Usuario aEscribir=new Usuario(nombre,apellido,username,password,email);
            usuario.escribirArchivo(archivo,aEscribir);
        }
    }
}


