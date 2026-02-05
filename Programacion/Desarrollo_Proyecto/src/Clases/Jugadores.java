package Clases;

import java.util.ArrayList;

public class Jugadores {
    private int id_jugador;
    private String nombre;
    private String apellido;
    private String nacionalidad;
    private String fecha_nacimiento;
    private String nickname;

    public enum Rol {
        SUPPORT,
        AWPER,
        ENTRY_FRAGGER,
        LURKER,
        RIFLER,
        IGL
    }

    private Rol rol;
    private int sueldo;
    private ArrayList<Equipos> equipo_asociado;

    public Jugadores(String nombre, String apellido, String nacionalidad, String fechaNacimiento, String nickname, int rol, double sueldo) {
    }

    public Jugadores(int id_jugador, String nombre, String apellido, String nacionalidad, String fecha_nacimiento, String nickname, Rol rol, int sueldo) {
        this.id_jugador = id_jugador;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nacionalidad = nacionalidad;
        this.fecha_nacimiento = fecha_nacimiento;
        this.nickname = nickname;
        this.rol = rol;
        this.sueldo = sueldo;
    }

    public int getId_jugador() {
        return id_jugador;
    }

    public void setId_jugador(int id_jugador) {
        this.id_jugador = id_jugador;
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

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public int getSueldo() {
        return sueldo;
    }

    public void setSueldo(int sueldo) {
        this.sueldo = sueldo;
    }

    public ArrayList<Equipos> getEquipo_asociado() {
        return equipo_asociado;
    }

    public void setEquipo_asociado(ArrayList<Equipos> equipo_asociado) {
        this.equipo_asociado = equipo_asociado;
    }
}
