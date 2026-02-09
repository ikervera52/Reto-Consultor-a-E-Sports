package Modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Equipo {
    private int idEquipo;
    private String nombre;
    private LocalDate fechaFundacion;
    private ArrayList<Jugador> jugadores;
    private Competicion competicion;

    public Equipo() {
    }

    public Equipo(int idEquipo, String nombre) {
        this.idEquipo = idEquipo;
        this.nombre = nombre;
        this.jugadores = new ArrayList<>();
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
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

    public void setJugador(Jugador jugador){
        if(this.jugadores == null){
            this.jugadores = new ArrayList<>();
            this.jugadores.add(jugador);
        }
        else {
            this.jugadores.add(jugador);
        }
        jugador.setEquipo(this);
    }

    public Competicion getCompeticion() {
        return competicion;
    }

    public void setCompeticion(Competicion competicion) {
        this.competicion = competicion;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        if (jugadores != null) {
            for(Jugador jugador : jugadores){
                sb.append(jugador.getNickname()).append(" ");
            }
        } else sb.append("Sin jugadores");

        return "Nombre: " + nombre + '\n' +
                "Fecha de fundación: " + fechaFundacion + "\n" +
                "Jugadores: " + sb  + "\n";
    }
}
