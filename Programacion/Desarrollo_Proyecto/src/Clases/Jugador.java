package Clases;

import java.time.LocalDate;

public class Jugador {

    private String nombre;
    private String apellido;
    private String nacionalidad;
    private LocalDate fechaNacimiento;
    private String nickname;
    private String rol;
    private double sueldo;

    public static String[] ROLES = {
            "Entry Fragger", "Support", "AWPer", "IGL", "Lurker", "Rifler"
    };

    public Jugador(String nombre, String apellido, String nacionalidad, LocalDate fechaNacimiento,
                   String nickname, String rol, double sueldo) {
        if (sueldo < 16576) {
            throw new IllegalArgumentException("Sueldo por debajo del mínimo permitido");
        }

        boolean rolValido = false;
        for (String r : ROLES) {
            if (r.equalsIgnoreCase(rol)) {
                rolValido = true;
                break;
            }
        }
        if (!rolValido) {
            System.out.println("Rol inválido. Se asignará 'Support' por defecto.");
            rol = "Support";
        }

        this.nombre = nombre;
        this.apellido = apellido;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
        this.nickname = nickname;
        this.rol = rol;
        this.sueldo = sueldo;
    }

    public String getNombre() { return nombre; }

    public String getApellido() { return apellido; }

    public String getNacionalidad() { return nacionalidad; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }

    public String getNickname() { return nickname; }

    public String getRol() { return rol; }

    public double getSueldo() { return sueldo; }


    public void setNombre(String nombre) { this.nombre = nombre; }

    public void setApellido(String apellido) { this.apellido = apellido; }

    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }

    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public void setRol(String rol) { this.rol = rol; }

    public void setSueldo(double sueldo) { this.sueldo = sueldo; }
}
