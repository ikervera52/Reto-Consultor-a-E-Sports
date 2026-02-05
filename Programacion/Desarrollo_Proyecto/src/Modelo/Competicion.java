package Modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Competicion {
    private int id;
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String etapa;
    private String tipoPuntuacion;
    private ArrayList<Jornada> jornadas = new ArrayList<>();
    private ArrayList<Equipo> equipos = new ArrayList<>();

    public Competicion() {
    }

    public Competicion(int id, String nombre, String etapa, String tipoPuntuacion, ArrayList<Equipo> equipos) {
        this.id = id;
        this.nombre = nombre;
        this.etapa = etapa;
        this.tipoPuntuacion = tipoPuntuacion;
        this.equipos = equipos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    public String getTipoPuntuacion() {
        return tipoPuntuacion;
    }

    public void setTipoPuntuacion(String tipoPuntuacion) {
        this.tipoPuntuacion = tipoPuntuacion;
    }

    public ArrayList<Jornada> getJornadas() {
        return jornadas;
    }

    public void setJornadas(ArrayList<Jornada> jornadas) {
        this.jornadas = jornadas;
    }

    public ArrayList<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(ArrayList<Equipo> equipos) {
        this.equipos = equipos;
    }
}
