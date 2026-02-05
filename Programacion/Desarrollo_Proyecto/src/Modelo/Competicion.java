package Modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Competicion {
    private int idCompeticion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private TipoEtapa etapa;
    private ArrayList<Jornada> jornadas;
    private ArrayList<Equipo> equipos;
    private String tipoPuntuacion;

    public enum TipoEtapa {
        COMPETICION, INSCRIPCION
    }

    public Competicion(int id, TipoEtapa etapa ) {
        this.idCompeticion = id;
        this.etapa = etapa;
        this.jornadas = new ArrayList<>();
        this.equipos = new ArrayList<>();
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

    public TipoEtapa getEtapa() {
        return etapa;
    }

    public void setEtapa(TipoEtapa etapa) {
        this.etapa = etapa;
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

    public void setJornada(Jornada jornada){
        if(jornadas == null){
            jornadas = new ArrayList<>();
            jornadas.add(jornada);
        }
        else{
            jornadas.add(jornada);
        }
        jornada.setCompeticion(this);
    }

    public void setEquipos(ArrayList<Equipo> equipos) {
        this.equipos = equipos;
    }

    public String getTipoPuntuacion() {
        return tipoPuntuacion;
    }

    public void setTipoPuntuacion(String tipoPuntuacion) {
        this.tipoPuntuacion = tipoPuntuacion;
    }
}
