import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

public class MenuConfiguracion extends JFrame {
    Usuario usuario;
    private CollectionUsuarios collectionUsuarios;
    public MenuConfiguracion(Usuario usuario, File archivo) {
        // Configuración de la ventana principal
        setTitle("Menú Configuración");
        this.usuario=usuario;
        collectionUsuarios=new CollectionUsuarios();                             //Menu utilizado donde aqui es donde se configuran los usuarios POR CUENTA PROPIA
        collectionUsuarios.leerArchivo(archivo);
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.decode("#191414"));
        add(mainPanel, BorderLayout.CENTER);

        // Panel para el título y el logo
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.decode("#191414"));

        // Título
        JLabel titleLabel = new JLabel("Spotifake Configuración");
        titleLabel.setForeground(Color.decode("#1ED760"));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);

        // Logo
        ImageIcon logoIcon = new ImageIcon("Media/Spotifake.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon logoScaledIcon = new ImageIcon(logoImage);
        JLabel logoLabel = new JLabel(logoScaledIcon);
        titlePanel.add(logoLabel);

        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Panel para las opciones del menú
        JPanel menuPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        menuPanel.setBackground(Color.decode("#191414"));
        mainPanel.add(menuPanel, BorderLayout.CENTER);

        // Botones del menú
        JButton cambiarNombreBtn = crearBoton("Cambiar nombre de usuario");
        JButton cambiarPassBtn = crearBoton("Cambiar contraseña");
        JButton darBajaAltaBtn = crearBoton(usuario.isActivo() ? "Dar de baja usuario" : "Dar de alta usuario");

        menuPanel.add(cambiarNombreBtn);
        menuPanel.add(cambiarPassBtn);
        menuPanel.add(darBajaAltaBtn);
        setLocationRelativeTo(null);
        // Ajustar el título de la ventana
        setTitle("Spotifake");
        // Ajustar el logo para la ventana
        ImageIcon logoIcon2 = new ImageIcon("Media/Spotifake.png");
        Image logoImage2 = logoIcon2.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon logoScaledIcon3 = new ImageIcon(logoImage);

        // Establecer el logo como icono de la ventana
        setIconImage(logoScaledIcon3.getImage());

        // ActionListener para los botones
        cambiarNombreBtn.addActionListener(e -> {
            this.usuario=cambiarNombre(archivo,this.usuario);
        });

        cambiarPassBtn.addActionListener(e -> {
            this.usuario=cambiarContraseña(archivo,this.usuario);
        });

        darBajaAltaBtn.addActionListener(e -> {
            // Crear un campo de contraseña para la entrada segura
            JPasswordField passwordField = new JPasswordField(20);
            JLabel label = new JLabel("Introduce tu contraseña para confirmar:");
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(label);
            panel.add(passwordField);

            int option = JOptionPane.showConfirmDialog(MenuConfiguracion.this, panel, "Confirmar Contraseña", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                char[] passwordChars = passwordField.getPassword();
                String password = String.valueOf(passwordChars);
                if (!password.trim().isEmpty()) {
                    if (this.usuario.getContrasenia().equals(password)) {
                        if (this.usuario.isActivo()) {
                            // Si el usuario está activo, lo damos de baja
                            this.usuario.setActivo(false);
                            darBajaAltaBtn.setText("Dar de alta usuario");
                            JOptionPane.showMessageDialog(MenuConfiguracion.this, "Usuario dado de baja.", "Estado del Usuario", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            // Si el usuario está inactivo, lo damos de alta
                            this.usuario.setActivo(true);
                            darBajaAltaBtn.setText("Dar de baja usuario");
                            JOptionPane.showMessageDialog(MenuConfiguracion.this, "Usuario dado de alta.", "Estado del Usuario", JOptionPane.INFORMATION_MESSAGE);
                        }

                        // Actualizar el estado del usuario en el archivo
                        collectionUsuarios.leerYEscribirActivo(archivo, this.usuario);
                    } else {
                        JOptionPane.showMessageDialog(MenuConfiguracion.this, "Contraseña incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(MenuConfiguracion.this, "La contraseña no puede estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                // Limpiar el contenido del campo de contraseña después de usarlo
                Arrays.fill(passwordChars, ' ');
                passwordField.setText("");
            }
        });



        setVisible(true);
    }

    private Usuario cambiarNombre(File archivo, Usuario usuario) {
        while (true) {
            usuario=collectionUsuarios.devolverUsuario(usuario.getNickname());
            JPanel panel = new JPanel(new GridLayout(0, 1));
            JPasswordField passwordField = new JPasswordField(20);
            JTextField nuevoNombreField = new JTextField(20);

            panel.add(new JLabel("Introduce tu contraseña:"));
            panel.add(passwordField);
            panel.add(new JLabel("Introduce tu nuevo nombre de usuario:"));
            panel.add(nuevoNombreField);

            int result = JOptionPane.showConfirmDialog(this, panel, "Cambiar Nombre de Usuario", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String contraseña = new String(passwordField.getPassword());
                String nuevoNombre = nuevoNombreField.getText();

                if (!contraseña.isEmpty() && !nuevoNombre.isEmpty()) {
                    if (usuario.getContrasenia().equals(contraseña)) {
                        if (!collectionUsuarios.comprobarNickname(nuevoNombre)) {
                            ArrayList<Usuario> aEscribir = collectionUsuarios.cambiarTreeMap(nuevoNombre, usuario.getNickname(), "", true);
                            collectionUsuarios.reescribirArchivoLinea(archivo, aEscribir);
                            JOptionPane.showMessageDialog(this, "Nombre de usuario cambiado exitosamente.", "Cambio de Nombre", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        } else {
                            JOptionPane.showMessageDialog(this, "Ese nombre de usuario ya está en uso. Por favor, elige otro.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Contraseña incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Los campos no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                break;
            }
        }
        return usuario;

    }
    private Usuario cambiarContraseña(File archivo, Usuario usuario) {
        while (true) {
            JPanel panel = new JPanel(new GridLayout(0, 1));
            JPasswordField currentPasswordField = new JPasswordField(20);
            JPasswordField newPasswordField = new JPasswordField(20);
            JPasswordField confirmNewPasswordField = new JPasswordField(20);

            panel.add(new JLabel("Introduce tu contraseña actual:"));
            panel.add(currentPasswordField);
            panel.add(new JLabel("Introduce tu nueva contraseña:"));
            panel.add(newPasswordField);
            panel.add(new JLabel("Confirma tu nueva contraseña:"));
            panel.add(confirmNewPasswordField);

            int result = JOptionPane.showConfirmDialog(this, panel, "Cambiar Contraseña", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String contraseñaActual = new String(currentPasswordField.getPassword());
                String nuevaContraseña = new String(newPasswordField.getPassword());
                String confirmarContraseña = new String(confirmNewPasswordField.getPassword());

                if (!contraseñaActual.isEmpty() && !nuevaContraseña.isEmpty() && !confirmarContraseña.isEmpty()) {
                    if (usuario.getContrasenia().equals(contraseñaActual)) {
                        if (nuevaContraseña.equals(confirmarContraseña)) {
                            usuario.setContrasenia(nuevaContraseña);
                            ArrayList<Usuario> aEscribir = collectionUsuarios.cambiarTreeMap(usuario.getNickname(), usuario.getNickname(), nuevaContraseña, false);
                            collectionUsuarios.reescribirArchivoLinea(archivo, aEscribir);
                            JOptionPane.showMessageDialog(this, "Contraseña cambiada exitosamente.", "Cambio de Contraseña", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        } else {
                            JOptionPane.showMessageDialog(this, "Las nuevas contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Contraseña actual incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Los campos no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                break;
            }
        }
        return usuario;
    }

    // Función para crear botones con estilo consistente
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(Color.decode("#1ED760"));
        boton.setForeground(Color.decode("#191414"));
        boton.setPreferredSize(new Dimension(200, 30));
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setFocusPainted(false); // Eliminar el efecto de enfoque al hacer clic
        return boton;
    }



}
