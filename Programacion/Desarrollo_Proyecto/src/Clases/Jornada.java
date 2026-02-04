package Clases;

import java.time.LocalDate;
import java.util.ArrayList;

public class Jornada {
    private int numero;
    private LocalDate fecha;
    private ArrayList<Enfrentamiento> enfrentamientos;

    public Jornada(int numero, LocalDate fecha) {
        this.numero = numero;
        this.fecha = fecha;
        this.enfrentamientos = new ArrayList<>();
    }

    public void agregarEnfrentamiento(Enfrentamiento e) {
        enfrentamientos.add(e);
    }

    public ArrayList<Enfrentamiento> getEnfrentamientos() {
        return enfrentamientos;
    }

    @Override
    public String toString() {
        return "JORNADA " + numero + " (" + fecha + ")";
    }
}