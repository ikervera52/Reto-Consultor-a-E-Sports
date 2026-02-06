package Modelo;

public class Resultado {
    private Equipo equipo;
    private Enfrentamiento enfrentamiento;
    private int puntuacion;

    public Resultado() {
    }

    public Resultado(Equipo equipo, Enfrentamiento enfrentamiento, int puntuacion) {
        this.equipo = equipo;
        this.enfrentamiento = enfrentamiento;
        this.puntuacion = puntuacion;
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

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
}
