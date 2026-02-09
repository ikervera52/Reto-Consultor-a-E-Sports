package Modelo;

import java.time.LocalDate;

public class Jugador {
    private int id;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String nacionalidad;
    private String nickname;
    private String rol;
    private double sueldo;
    private Equipo equipo;



    public Jugador() {
    }

    public Jugador(int id, String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, String nickname, String rol, double sueldo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.nickname = nickname;
        this.rol = rol;
        this.sueldo = sueldo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return "Jugador: "+this.nickname
                +"\n  - Id: "+this.id
                +"\n  - Nombre: "+this.nombre
                +"\n  - Apellido: "+this.apellido
                +"\n  - Fecha de nacimiento: "+this.fechaNacimiento
                +"\n  - Nacionalidad: "+this.nacionalidad
                +"\n  - Rol: "+this.rol
                +"\n  - Sueldo: "+this.sueldo
                +"\n  - Equipo: "+this.equipo.getNombre();
    }
}
