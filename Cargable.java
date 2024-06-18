import java.io.File;

public interface Cargable <T>{              //Interfaz utilizada para la carga de archivos, baja y borrar objetos en caso de utilizarlo como backender
    void cargarArchivo(File f);
    void bajarArchivo(File f);
    void borrarObjeto(File f , T objeto);
}
