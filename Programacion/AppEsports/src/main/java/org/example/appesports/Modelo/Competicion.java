package org.example.appesports.Modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Competicion {
    private int idCompeticion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String etapa;
    private String tipoPuntuacion;

    public Competicion() {
    }

    public Competicion(int id, String etapa ) {
        this.idCompeticion = id;
        this.etapa = etapa;
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
