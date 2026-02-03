package Clases;

import java.time.LocalDate;
import java.util.ArrayList;

public class Equipo {

    private String nombre;
    private LocalDate fechaFundacion;
    private ArrayList<Jugador> jugadores = new ArrayList<>();

    public Equipo(String nombre, LocalDate fechaFundacion) {
        this.nombre = nombre;
        this.fechaFundacion = fechaFundacion;
    }

    public String getNombre() { return nombre; }

    public LocalDate getFechaFundacion() { return fechaFundacion; }

    public ArrayList<Jugador> getJugadores() { return jugadores; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public void setFechaFundacion(LocalDate fechaFundacion) { this.fechaFundacion = fechaFundacion; }

    public void setJugadores(ArrayList<Jugador> jugadores) { this.jugadores = jugadores; }

    public boolean agregarJugador(Jugador jugador) {
        if (jugadores.size() >= 6) {
            System.out.println("No se puede añadir más jugadores, equipo completo.");
            return false;
        }
        jugadores.add(jugador);
        return true;
    }
}
