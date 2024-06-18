public abstract class Persona {
    protected String nombre;
    protected String apellido;                 //De esta clase extienden los usuarios y el admin

    public Persona(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }
    public Persona(){}
}
