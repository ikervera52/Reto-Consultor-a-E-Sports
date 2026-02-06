package Clases;

import Exepciones.CantidadJugadoresNoValida;
import java.time.LocalDate;
import java.util.ArrayList;

public class Equipo {
    private int id;
    private String nombre;
    private LocalDate fechaFundacion;
    private ArrayList<Jugador> jugadores;
    private int victorias;
    private int derrotas;
    private int empates;
    private int puntos;

    public Equipo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.fechaFundacion = LocalDate.now();
        this.jugadores = new ArrayList<>();
        this.victorias = 0;
        this.derrotas = 0;
        this.empates = 0;
        this.puntos = 0;
    }

    public int getId() { return id; }

    public void agregarJugador(Jugador jugador) throws CantidadJugadoresNoValida {
        if (this.jugadores.size() >= 6) throw new CantidadJugadoresNoValida("Equipo lleno.");
        this.jugadores.add(jugador);
    }

    public void validarMinimoJugadores() throws CantidadJugadoresNoValida {
        if (this.jugadores.size() < 2) throw new CantidadJugadoresNoValida("Faltan jugadores.");
    }

    public void sumarVictoria() {
        this.victorias++;
        this.puntos += 3;
    }

    public void sumarEmpate() {
        this.empates++;
        this.puntos += 1;
    }

    public void sumarDerrota() {
        this.derrotas++;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public LocalDate getFechaFundacion() { return fechaFundacion; }
    public void setFechaFundacion(LocalDate fecha) { this.fechaFundacion = fecha; }
    public ArrayList<Jugador> getJugadores() { return jugadores; }
    public int getPuntos() { return puntos; }
    public int getVictorias() { return victorias; }
    public int getDerrotas() { return derrotas; }
    public int getEmpates() { return empates; }

    @Override
    public String toString() {
        return "Equipo " + id + ": " + nombre + " (" + jugadores.size() + " jug.)";
    }
}