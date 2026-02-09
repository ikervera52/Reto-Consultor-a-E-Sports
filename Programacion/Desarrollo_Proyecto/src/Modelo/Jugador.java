package Modelo;

public class Jugador {
    private int id;
    private String nickname;
    private String nombre;
    private String apellido;
    private String nacionalidad;
    private String rol;
    private double sueldo;
    private Equipo equipo;

    public Jugador() {
    }

    public Jugador(int id, String nickname, String nombre, String apellido, String nacionalidad, String rol) {
        this.id = id;
        this.nickname = nickname;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nacionalidad = nacionalidad;
        this.rol = rol;
    }

    public double getSueldo() {
        return sueldo;
    }

    public boolean setSueldo(double sueldo, double SMI) {
        if (sueldo > SMI) {
            this.sueldo = sueldo;
            return true;
        }
        else return false;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    @Override
    public String toString() {
        String nombreEquipo;
        if (this.equipo == null){
            nombreEquipo = "Sin equipo";
        } else nombreEquipo = this.equipo.getNombre();
        return "Nickname: " + nickname + '\n' +
                "Nombre: " + nombre + '\n' +
                "Apellido: " + apellido + '\n' +
                "Nacionalidad: " + nacionalidad + '\n' +
                "Rol: " + rol + '\n' +
                "Sueldo: " + sueldo + "\n" +
                "Equipo: " + nombreEquipo + "\n" ;
    }
}
