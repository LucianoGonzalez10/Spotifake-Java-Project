import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class CollectionUsuarios {
    private TreeMap<String, Usuario> usuarios;               //TreeMap con usuarios, donde por nickname se utiliza la busqueda y comprobacion

    public CollectionUsuarios() {
        usuarios = new TreeMap<>();
    }


    public boolean comprobarNickname(String nickname)
    {
        if(usuarios.containsKey(nickname))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Usuario devolverUsuario(String nickname)
    {
        return usuarios.get(nickname);
    }

    public void escribirArchivo(File archivo, Usuario usuario) {
        ObjectMapper objectMapper = new ObjectMapper();
        try (FileWriter fileWriter = new FileWriter(archivo, true)) {

            PrintWriter printWriter = new PrintWriter(fileWriter);
            String usuarioAEscribir = objectMapper.writeValueAsString(usuario);            //Escribo cada usuario al final del archivo
            printWriter.println(usuarioAEscribir);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void leerArchivo(File archivo) {
        Usuario usuario = new Usuario();
        ObjectMapper mapper = new ObjectMapper();
        if (archivo.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {                               //Leo el archivo linea por linea y lo subo a nuestro treemap
                    usuario = mapper.readValue(linea, Usuario.class);
                    usuarios.put(usuario.getNickname(), usuario);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("El archivo no existe");
        }
    }

    public TreeMap<String, Usuario> getUsuarios() {
        return usuarios;
    }

    //Utilizada en caso de que o se cambie el nombre o la contraseña
    public ArrayList<Usuario> cambiarTreeMap(String newNickame, String oldNickname, String newPassword, boolean cambiarNickname) {
        Usuario usuario = usuarios.get(oldNickname);
        usuarios.remove(oldNickname);
        if (cambiarNickname) {
            usuario.setNickname(newNickame);
        } else {
            usuario.setContrasenia(newPassword);
        }
        usuarios.put(usuario.getNickname(), usuario);
        return new ArrayList<>(usuarios.values());
    }
    //Para evitar errores a la hora de la baja logica y alta del usuario, se utiliza esta funcion para escribirlo en el archivo
    public void leerYEscribirActivo(File archivo, Usuario usuario)
    {
        leerArchivo(archivo);
        usuarios.remove(usuario.getNickname());
        usuarios.put(usuario.getNickname(),usuario);
        ArrayList<Usuario> aEscribir=new ArrayList<>(usuarios.values());
        ObjectMapper objectMapper = new ObjectMapper();
        try (FileWriter fileWriter = new FileWriter(archivo, false)) {

            PrintWriter printWriter = new PrintWriter(fileWriter);
            for (Usuario usuario2 : aEscribir) {
                System.out.println(usuario.toString());
                String usuarioAEscribir = objectMapper.writeValueAsString(usuario2);            //Escribo cada usuario al final del archivo
                printWriter.println(usuarioAEscribir);
            }
            printWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reescribirArchivoLinea(File archivo, ArrayList<Usuario> usuariosArchivo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try (FileWriter fileWriter = new FileWriter(archivo, false)) {

            PrintWriter printWriter = new PrintWriter(fileWriter);
            for (Usuario usuario : usuariosArchivo) {
                System.out.println(usuario.toString());
                String usuarioAEscribir = objectMapper.writeValueAsString(usuario);            //Escribo cada usuario al final del archivo
                printWriter.println(usuarioAEscribir);
            }
            printWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void solicitarContraseña(Usuario usuario) {
        boolean contraseñaCorrecta = false;
        Scanner teclado = new Scanner(System.in);
        while (!contraseñaCorrecta) {
            try {
                System.out.println("Ingrese su contraseña: ");
                String contraseña = teclado.next();
                if (!contraseña.equals(usuario.getContrasenia())) {
                    throw new ExcepcionDatos("Contraseña incorrecta");   //Funcion hecha para quitar problemas de buffer
                } else {
                    contraseñaCorrecta = true;
                }
            } catch (ExcepcionDatos e) {
                System.out.println(e.getMessage());
            }

        }
        teclado.close();

    }

}








