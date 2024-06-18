import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.TreeSet;

public class CollectionsPlaylist implements Cargable<Playlist> {
    LinkedHashMap<String, Playlist> playlists;         //Aca estan guardadas las playlist trap, reggaeton, hip hop

    public CollectionsPlaylist() {
        this.playlists = new LinkedHashMap<>();
    }

    public void agregarPlaylist(Playlist playlist) {
        playlists.put(playlist.getNombre(), playlist);
    }
    //Esta es otra funcion sin usos, pero que de igual forma se la conserva ya que para comprobar resulta importante
    public void mostrarPlaylist(File f) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            playlists = mapper.readValue(f, new TypeReference<LinkedHashMap<String, Playlist>>() {});
        } catch (IOException e) {
            System.out.println("No pudieron bajarse las Playlist: " + e.getMessage());
        }
        finally{
            for(Playlist aux : playlists.values()){
                System.out.println(" Playlist: " + aux.getNombre());
        }
        }
        
    }
    public boolean removeSong(int id) {
        boolean songRemoved = false;
        for (Playlist playlist : playlists.values()) {
            TreeSet<Cancion> cancionesPlaylist = playlist.getCancionesPlaylist();
            Iterator<Cancion> iterator = cancionesPlaylist.iterator();
            while (iterator.hasNext()) {
                Cancion cancion = iterator.next();
                if (cancion.getId() == id) {
                    iterator.remove();
                    songRemoved = true;
                }
            }
        }
        if (!songRemoved) {
            System.out.println("No se encontró ninguna canción con ID " + id + " en ninguna playlist.");
        }
        return songRemoved;
    }

    public LinkedHashMap<String, Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(LinkedHashMap<String, Playlist> playlists) {
        this.playlists = playlists;
    }

    @Override
    public void cargarArchivo(File f) {
        ObjectMapper mapa = new ObjectMapper();
        mapa.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapa.writeValue(f, playlists);
        } catch (IOException e) {
            System.out.println("No pudieron cargarse las Playlist: " + e.getMessage());
        }
    }

    @Override
    public void bajarArchivo(File f) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            playlists = mapper.readValue(f, new TypeReference<LinkedHashMap<String, Playlist>>() {
            });
        } catch (IOException e) {
            System.out.println("No pudieron bajarse las Playlist: " + e.getMessage());
        }
    }

    @Override
    public void borrarObjeto(File f, Playlist playlist) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            // Cargar los datos del archivo JSON en el LinkedHashMap
            this.playlists = mapper.readValue(f, new TypeReference<LinkedHashMap<String, Playlist>>() {});
        
            // Eliminar la cancion del LinkedHashMap
            this.playlists.remove(playlist.getNombre());
        
            // Guardar los datos actualizados en el archivo JSON
            mapper.writeValue(f, this.playlists);
        
            System.out.println("Cancion eliminada correctamente del archivo JSON.");
         } 
    catch (IOException e) {
        System.out.println("Error al eliminar la cancion del archivo JSON: " + e.getMessage());
    }
}
} 

