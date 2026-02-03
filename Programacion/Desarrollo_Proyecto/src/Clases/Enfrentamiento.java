package Clases;

import java.time.LocalDateTime;

public class Enfrentamiento {

    private Equipo equipo1;
    private Equipo equipo2;
    private LocalDateTime fechaHora;
    private String resultado;

    public Enfrentamiento(Equipo equipo1, Equipo equipo2, LocalDateTime fechaHora) {
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.fechaHora = fechaHora;
        this.resultado = "";
    }

    // Getters
    public Equipo getEquipo1() { return equipo1; }

    public Equipo getEquipo2() { return equipo2; }

    public LocalDateTime getFechaHora() { return fechaHora; }

    public String getResultado() { return resultado; }

    // Setters
    public void setEquipo1(Equipo equipo1) { this.equipo1 = equipo1; }

    public void setEquipo2(Equipo equipo2) { this.equipo2 = equipo2; }

    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public void setResultado(String resultado) { this.resultado = resultado; }

    public String getEnfrentamientoInfo() {
        return equipo1.getNombre() + " vs " + equipo2.getNombre() + " | " + resultado;
    }
}
