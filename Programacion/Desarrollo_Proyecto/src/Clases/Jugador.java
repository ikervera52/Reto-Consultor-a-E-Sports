package Clases;

import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;

public class Jugador {
    private Integer id;
    private String nombre;
    private String apellido;
    private String nacionalidad;
    private LocalDate fechaNacimiento;
    private String nickname;
    private String sueldo;
    private String[] rol = new String[]{"Entry Fragger", "Support", "AWPer", "IGL", "Lurker", "Riflers"};
    private ArrayList<Equipo> equipos = new ArrayList<>();

    public Jugador(Integer id, String nombre, String apellido, String nacionalidad, LocalDate fechaNacimiento, String nickname, String sueldo, String[] rol, ArrayList<Equipo> equipos) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
        this.nickname = nickname;
        this.sueldo = sueldo;
        this.rol = rol;
        this.equipos = equipos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSueldo() {
        return sueldo;
    }

    public void setSueldo(String sueldo) {
        this.sueldo = sueldo;
    }

    public String[] getRol() {
        return rol;
    }

    public void setRol(String[] rol) {
        this.rol = rol;
    }

    public ArrayList<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(ArrayList<Equipo> equipos) {
        this.equipos = equipos;
    }
}
