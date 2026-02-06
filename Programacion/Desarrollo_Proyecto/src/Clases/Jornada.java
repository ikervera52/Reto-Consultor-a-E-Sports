package Clases;

import java.time.LocalDate;
import java.util.ArrayList;

public class Jornada {
    private int id;
    private int numero;
    private LocalDate fecha;
    private ArrayList<Enfrentamiento> enfrentamientos;

    public Jornada(int id, int numero, LocalDate fecha) {
        this.id = id;
        this.numero = numero;
        this.fecha = fecha;
        this.enfrentamientos = new ArrayList<>();
    }

    public int getId() { return id; }

    public void agregarEnfrentamiento(Enfrentamiento e) {
        enfrentamientos.add(e);
    }

    public ArrayList<Enfrentamiento> getEnfrentamientos() {
        return enfrentamientos;
    }

    @Override
    public String toString() {
        return "JORNADA " + numero + " (ID:" + id + ") - " + fecha;
    }
}