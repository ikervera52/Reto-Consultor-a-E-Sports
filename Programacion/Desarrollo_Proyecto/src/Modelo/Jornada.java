package Modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Jornada {
    private int idJornada;
    private LocalDate fechaJornada;
    private int numeroJornada;
    private Competicion competicion;
    private ArrayList<Enfrentamiento> enfrentamientos;

    public Jornada() {
        this.enfrentamientos = new ArrayList<>();
    }

    public int getIdJornada() {
        return idJornada;
    }

    public void setIdJornada(int idJornada) {
        this.idJornada = idJornada;
    }

    public LocalDate getFechaJornada() {
        return fechaJornada;
    }

    public void setFechaJornada(LocalDate fechaJornada) {
        this.fechaJornada = fechaJornada;
    }

    public int getNumeroJornada() {
        return numeroJornada;
    }

    public void setNumeroJornada(int numeroJornada) {
        this.numeroJornada = numeroJornada;
    }

    public Competicion getCompeticion() {
        return competicion;
    }

    public void setCompeticion(Competicion competicion) {
        this.competicion = competicion;
    }

    public ArrayList<Enfrentamiento> getEnfrentamientos() {
        return enfrentamientos;
    }

    public void setEnfrentamientos(ArrayList<Enfrentamiento> enfrentamientos) {
        this.enfrentamientos = enfrentamientos;
    }
    public void setEnfrentamiento(Enfrentamiento enfrentamiento) {
        if (this.enfrentamientos == null) {
            this.enfrentamientos = new ArrayList<>();
            this.enfrentamientos.add(enfrentamiento);
        } else {
            this.enfrentamientos.add(enfrentamiento);
        }
        enfrentamiento.setJornada(this);
    }
}
