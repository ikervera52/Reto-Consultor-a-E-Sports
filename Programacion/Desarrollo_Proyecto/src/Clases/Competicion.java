package Clases;

import java.time.LocalDate;
import java.util.ArrayList;

public class Competicion {

    private Integer id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    public static final String[] ETAPAS = {
            "Inscripcion",
            "Competicion"
    };

    private int etapa;

    private ArrayList<Jornada> jornadas;

    public Competicion(Integer id, LocalDate fechaInicio, LocalDate fechaFin, int etapa) {

        if (etapa < 0 || etapa >= ETAPAS.length) {
            throw new IllegalArgumentException("Etapa inválida");
        }

        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.etapa = etapa;
        this.jornadas = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public String getEtapa() {
        return ETAPAS[etapa];
    }

    public ArrayList<Jornada> getJornadas() {
        return jornadas;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setEtapa(int etapa) {
        if (etapa < 0 || etapa >= ETAPAS.length) {
            throw new IllegalArgumentException("Etapa inválida");
        }
        this.etapa = etapa;
    }

    public void agregarJornada(Jornada jornada) {
        if (jornada != null && !jornadas.contains(jornada)) {
            jornadas.add(jornada);
        }
    }

    public void eliminarJornada(Jornada jornada) {
        jornadas.remove(jornada);
    }
}
