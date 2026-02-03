public class Jugador {
    int ID_jugador;
    String nombre;
    String apellido;
    String nacionalidad;
    String nickname;
    String rol;
    String fecha_nacimiento;
    int sueldo;

    public Jugador(int ID_jugador, String nombre, String apellido, String nacionalidad, String nickname, String rol, String fecha_nacimiento, int sueldo) {
        this.ID_jugador = ID_jugador;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nacionalidad = nacionalidad;
        this.nickname = nickname;
        this.rol = rol;
        this.fecha_nacimiento = fecha_nacimiento;
        this.sueldo = sueldo;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public int getID_jugador() {
        return ID_jugador;
    }

    public void setID_jugador(int ID_jugador) {
        this.ID_jugador = ID_jugador;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getSueldo() {
        return sueldo;
    }

    public void setSueldo(int sueldo) {
        this.sueldo = sueldo;
    }
}


