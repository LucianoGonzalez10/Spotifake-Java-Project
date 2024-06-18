import com.fasterxml.jackson.annotation.JsonBackReference;

public class Cancion implements Comparable<Cancion> {
    int id;
    String nombre;
    String genero;
    String rutaCancion;         //Clase canciones para la reproduccion
    String rutaPortada;

    @JsonBackReference
    Playlist playlist; //Para utilizar en caso de referencias circulares

    public Cancion(int id, String nombre, String genero, String rutaCancion, String rutaPortada) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.rutaCancion = rutaCancion;
        this.rutaPortada = rutaPortada;    }

    public Cancion() {
    }

    public int getId() {
        return id;
    }

    public String getGenero() {
        return genero;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRutaCancion() {
        return rutaCancion;
    }

   
    public String getRutaPortada() {
        return rutaPortada;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRutaCancion(String rutaCancion) {
        this.rutaCancion = rutaCancion;
    }



    public void setRutaPortada(String rutaPortada) {
        this.rutaPortada = rutaPortada;
    }

    @Override
    public String toString() {
        return " Cancion-" + id + " Nombre: " + nombre;
    }

    @Override
    public int compareTo(Cancion otraCancion) {
        // Define el criterio de comparación. Aquí se compara por el nombre de la canción.
        return this.nombre.compareTo(otraCancion.nombre);
    }
}
