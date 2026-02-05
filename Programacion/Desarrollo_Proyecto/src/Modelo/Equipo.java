package Modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Equipo {
    private int id;
    private String nombre;
    private LocalDate fechaFundacion;
    private ArrayList<Jugador> jugadores = new ArrayList<>();
    private ArrayList<Competicion> competiciones = new ArrayList<>();

    public Equipo() {

    }

    public Equipo(int id, String nombre, LocalDate fechaFundacion) {
        this.id = id;
        this.nombre = nombre;
        this.fechaFundacion = fechaFundacion;
    }

    public Equipo(int id, String nombre, LocalDate fechaFundacion, ArrayList<Competicion> competiciones) {
        this.id = id;
        this.nombre = nombre;
        this.fechaFundacion = fechaFundacion;
        this.competiciones = competiciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaFundacion() {
        return fechaFundacion;
    }

    public void setFechaFundacion(LocalDate fechaFundacion) {
        this.fechaFundacion = fechaFundacion;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public ArrayList<Competicion> getCompeticiones() {
        return competiciones;
    }

    public void setCompeticiones(ArrayList<Competicion> competiciones) {
        this.competiciones = competiciones;
    }
}
