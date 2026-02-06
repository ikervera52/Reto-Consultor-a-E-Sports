package Clases;

import java.time.LocalDate;
import java.util.ArrayList;

public class Competicion {
    private Integer id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public String[] nombresEtapas = { "Inscripcion", "Competicion" };

    private int etapa;

    private ArrayList<Jornada> jornadas;
    private ArrayList<Equipo> equiposParticipantes;

    public Competicion(Integer id, LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.etapa = 0;
        this.jornadas = new ArrayList<>();
        this.equiposParticipantes = new ArrayList<>();
    }

    public Integer getId() { return id; }

    public void agregarJornada(Jornada jornada) {
        jornadas.add(jornada);
    }

    public ArrayList<Jornada> getJornadas() { return jornadas; }

    public ArrayList<Equipo> getEquipos() { return equiposParticipantes; }
    public void setEquipos(ArrayList<Equipo> equipos) { this.equiposParticipantes = equipos; }

    public String getNombreEtapa() { return nombresEtapas[etapa]; }

    public int getEtapaIndex() { return etapa; }

    public LocalDate getFechaInicio() { return fechaInicio; }

    public void setEtapa(int nuevaEtapa) {
        if (nuevaEtapa < 0 || nuevaEtapa > 1) {
            System.out.println("Etapa no válida");
        } else {
            this.etapa = nuevaEtapa;
        }
    }
}