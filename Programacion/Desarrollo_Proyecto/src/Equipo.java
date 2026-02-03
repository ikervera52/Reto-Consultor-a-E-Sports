public class Equipo {
    int ID_equipo;
    String nombre;
    String fecha_fundacion;
    int jugadores;

    public Equipo(int ID_equipo, String nombre, String fecha_fundacion, int jugadores) {
        this.ID_equipo = ID_equipo;
        this.nombre = nombre;
        this.fecha_fundacion = fecha_fundacion;
        this.jugadores = jugadores;
    }

    public String getFecha_fundacion() {
        return fecha_fundacion;
    }

    public void setFecha_fundacion(String fecha_fundacion) {
        this.fecha_fundacion = fecha_fundacion;
    }

    public int getID_equipo() {
        return ID_equipo;
    }

    public void setID_equipo(int ID_equipo) {
        this.ID_equipo = ID_equipo;
    }

    public int getJugadores() {
        return jugadores;
    }

    public void setJugadores(int jugadores) {
        this.jugadores = jugadores;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
