public class Usuario extends Persona{
    private String nickname; //Identificador
    private Playlist tusMeGusta;              //Usuarios a registrarse
    private String fotoPortada;
    private String contrasenia;
    private boolean activo;
    private String mail;

    public Usuario(String nombre, String apellido, String nickname,String contrasenia,String mail) {
        super(nombre, apellido);
        this.nickname = nickname;
        this.contrasenia=contrasenia;
        activo=true;
        this.mail=mail;
        this.tusMeGusta = new Playlist("Tus me Gusta", "Media/MediaMG.png");
    }

    public String getContrasenia() {
        return contrasenia;
    }
    
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Usuario() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Playlist getTusMeGusta() {
        return tusMeGusta;
    }

    public void setTusMeGusta(Playlist tusMeGusta) {
        this.tusMeGusta = tusMeGusta;
    }

    public String getFotoPortada() {
        return fotoPortada;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setFotoPortada(String fotoPortada) {
        this.fotoPortada = fotoPortada;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nickname='" + nickname + '\'' +
                ", tusMeGusta=" + tusMeGusta +
                ", fotoPortada='" + fotoPortada + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", activo=" + activo +
                ", mail='" + mail + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                '}';
    }
}
