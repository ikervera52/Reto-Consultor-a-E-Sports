package org.example.appesports.Modelo;

/**
 * Clase que representa el resultado de un enfrentamiento entre equipos en el sistema de gestión de esports.
 * Contiene información sobre el equipo, el enfrentamiento y el resultado obtenido.
 */
public class Resultado {

    private Equipo equipo;
    private Enfrentamiento enfrentamiento;
    private int resultado;

    public Resultado() {
    }

    public Resultado(Equipo equipo, Enfrentamiento enfrentamiento) {
        this.equipo = equipo;
        this.enfrentamiento = enfrentamiento;
    }

    public Resultado(Equipo equipo, Enfrentamiento enfrentamiento, int puntuacion) {
        this.equipo = equipo;
        this.enfrentamiento = enfrentamiento;
        this.resultado = puntuacion;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Enfrentamiento getEnfrentamiento() {
        return enfrentamiento;
    }

    public void setEnfrentamiento(Enfrentamiento enfrentamiento) {
        this.enfrentamiento = enfrentamiento;
    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }
}
