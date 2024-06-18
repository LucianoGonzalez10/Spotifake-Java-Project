import java.io.File;

public class index {
    public static void main(String[] args) {
        CollectionUsuarios collectionUsuarios=new CollectionUsuarios();
        File archivo=new File("usuarios.json");
        collectionUsuarios.leerArchivo(archivo);
        LoginWindow login=new LoginWindow(archivo,collectionUsuarios);
    }
}
