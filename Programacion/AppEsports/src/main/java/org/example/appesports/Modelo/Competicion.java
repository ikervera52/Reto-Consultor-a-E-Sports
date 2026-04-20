package org.example.appesports.Modelo;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Clase que representa una competición en el sistema de gestión de esports.
 * Contiene información sobre la fecha de inicio y fin de la competición, la etapa en la que se encuentra y el tipo de puntuación que se utiliza en la competición.
 */
public class Competicion {
    private int idCompeticion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String etapa;
    private String tipoPuntuacion;

    public Competicion() {
    }

    public Competicion(String etapa) {
        this.etapa = etapa;
    }

    public Competicion(int idCompeticion, LocalDate fechaInicio, LocalDate fechaFin, String etapa, String tipoPuntuacion) {
        this.idCompeticion = idCompeticion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.etapa = etapa;
        this.tipoPuntuacion = tipoPuntuacion;
    }

    public int getIdCompeticion() {
        return idCompeticion;
    }

    public void setIdCompeticion(int idCompeticion) {
        this.idCompeticion = idCompeticion;
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
}
