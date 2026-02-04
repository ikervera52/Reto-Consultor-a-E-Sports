package Modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Jornada {
    private int id;
    private LocalDate fecha;
    private int NumJornada;
    private ArrayList<Enfrentamiento> enfrentamientos;
    private Competicion competicion;

    public Jornada(int id, LocalDate fecha, int numJornada, ArrayList<Enfrentamiento> enfrentamientos, Competicion competicion) {
        this.id = id;
        this.fecha = fecha;
        NumJornada = numJornada;
        this.enfrentamientos = enfrentamientos;
        this.competicion = competicion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getNumJornada() {
        return NumJornada;
    }

    public void setNumJornada(int numJornada) {
        NumJornada = numJornada;
    }

    public ArrayList<Enfrentamiento> getEnfrentamientos() {
        return enfrentamientos;
    }

    public void setEnfrentamientos(ArrayList<Enfrentamiento> enfrentamientos) {
        this.enfrentamientos = enfrentamientos;
    }

    public Competicion getCompeticion() {
        return competicion;
    }

    public void setCompeticion(Competicion competicion) {
        this.competicion = competicion;
    }
}
