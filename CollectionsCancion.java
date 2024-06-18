import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class CollectionsCancion implements Cargable <Cancion>{
    LinkedHashMap<Integer, Cancion> cancionesTotal;                      //Estructura que sera subida al archivo, con las cancioens dentro

    CollectionsCancion(){
        this.cancionesTotal = new LinkedHashMap<>();
    }
    
    public void agregarCanciones(Cancion cancion){
        cancionesTotal.put(cancion.getId(), cancion);
    }

    public boolean retornarsiHayID(int id)
    {
        if(cancionesTotal.containsKey(id))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean removeSong(int id)
    {
        if(cancionesTotal.containsKey(id))
        {
            cancionesTotal.remove(id);
            return true;
        }
        else
        {
            return false;
        }

    }

    @Override
    public void cargarArchivo(File f){
        ObjectMapper mapa = new ObjectMapper();
        mapa.enable(SerializationFeature.INDENT_OUTPUT);
        try{
            mapa.writeValue(f, cancionesTotal);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public LinkedHashMap<Integer, Cancion> getCancionesTotal() {
        return cancionesTotal;
    }

    @Override
    public void bajarArchivo(File f){
        ObjectMapper mapper = new ObjectMapper();
        try {
            cancionesTotal = mapper.readValue(f, new TypeReference<LinkedHashMap<Integer, Cancion>>() {});
            System.out.println("Datos cargados correctamente desde el archivo JSON.");
        } catch (IOException e) {
            System.out.println("Error al cargar los datos desde el archivo JSON: " + e.getMessage());
        }
    }

    @Override
    public void borrarObjeto(File f, Cancion cancion) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            // Cargar los datos del archivo JSON en el LinkedHashMap
            cancionesTotal = mapper.readValue(f, new TypeReference<LinkedHashMap<Integer, Cancion>>() {});
        
            // Eliminar la cancion del LinkedHashMap
            cancionesTotal.remove(cancion.getId());
        
            // Guardar los datos actualizados en el archivo JSON
            mapper.writeValue(f, cancionesTotal);
        
            System.out.println("Cancion eliminada correctamente del archivo JSON.");
         } 
    catch (IOException e) {
        System.out.println("Error al eliminar la cancion del archivo JSON: " + e.getMessage());
    }
}

//Si bien no es utilizada, es un listado para el backend y la comprobacion correcta de canciones
public void mostrarCanciones(File f) {
    ObjectMapper mapper = new ObjectMapper();
    try {
        cancionesTotal = mapper.readValue(f, new TypeReference<LinkedHashMap<Integer, Cancion>>() {});
    } catch (IOException e) {
        System.out.println("No pudieron bajarse las canciones: " + e.getMessage());
    }
    finally{
        for(Cancion aux : cancionesTotal.values()){
            System.out.println(" Canciones: " + aux.getNombre());
        }
    }
    }
}
