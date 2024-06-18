
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonManagedReference;


public class Playlist {
    String nombre;
    TreeSet<Cancion> cancionesPlaylist;                //Clase creada para la creacion de playlist con canciones dentro
    private String portada;
    int id;
    
    public Playlist(String nombre, String portada) {
        this.nombre = nombre;
        this.portada = portada;
        this.cancionesPlaylist = new TreeSet<>();
    }

    Playlist(){

    }

    @JsonManagedReference
    public TreeSet<Cancion> getCancionesPlaylist() {
        return cancionesPlaylist;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setCancionesPlaylist(TreeSet<Cancion> cancionesPlaylist) {
        this.cancionesPlaylist = cancionesPlaylist;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void agregarCancion(Cancion cancion){
        cancionesPlaylist.add(cancion);
    }
    
    public void eliminarCancion(Cancion cancion){
        try{
            if(cancionesPlaylist.remove(cancion)){
                System.out.println("La cancion se elimino correctamente");
            }
            else{
                System.out.println("La cancion no se encuentra en la Playlist");
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }    
    }
    
    @Override
    public String toString() {
        return " Playlist: " + nombre;
    }
}
