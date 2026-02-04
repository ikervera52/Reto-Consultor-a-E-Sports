package Modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Competicion {
    private int id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private tipoEtapa etapa;
    private ArrayList<Jornada> jornadas;

    enum tipoEtapa{
        INSCRIPCION, COMPETICION
    }

    public Competicion() {
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

    public tipoEtapa getEtapa() {
        return etapa;
    }

    public void setEtapa(tipoEtapa etapa) {
        this.etapa = etapa;
    }

    public ArrayList<Jornada> getJornadas() {
        return jornadas;
    }

    public void setJornadas(ArrayList<Jornada> jornadas) {
        this.jornadas = jornadas;
    }
}
