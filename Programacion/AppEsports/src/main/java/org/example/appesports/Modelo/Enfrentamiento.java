package org.example.appesports.Modelo;

import java.time.LocalTime;
/**
 * Clase que representa un enfrentamiento entre equipos en el sistema de gestión de esports.
 * Contiene información sobre la hora del enfrentamiento, la jornada a la que pertenece y los equipos que participan en el enfrentamiento.
 */
public class Enfrentamiento {
    private int idEnfrentamiento;
    private LocalTime horaEnfrentamiento;
    private Jornada jornada;
    private Equipo[] equipos;

    public Enfrentamiento() {
    }

    public Enfrentamiento(int idEnfrentamiento, LocalTime horaEnfrentamiento, Jornada jornada) {
        this.idEnfrentamiento = idEnfrentamiento;
        this.horaEnfrentamiento = horaEnfrentamiento;
        this.jornada = jornada;
    }

    public Enfrentamiento(int idEnfrentamiento, LocalTime horaEnfrentamiento, String resultado, Jornada jornada, Equipo[] equipos) {
        this.idEnfrentamiento = idEnfrentamiento;
        this.horaEnfrentamiento = horaEnfrentamiento;
        this.jornada = jornada;
    }

    public int getIdEnfrentamiento() {
        return idEnfrentamiento;
    }

    public void setIdEnfrentamiento(int idEnfrentamiento) {
        this.idEnfrentamiento = idEnfrentamiento;
    }

    public LocalTime getHoraEnfrentamiento() {
        return horaEnfrentamiento;
    }

    public void setHoraEnfrentamiento(LocalTime horaEnfrentamiento) {
        this.horaEnfrentamiento = horaEnfrentamiento;
    }

    public Jornada getJornada() {
        return jornada;
    }

    public void setJornada(Jornada jornada) {
        this.jornada = jornada;
    }

    public Equipo[] getEquipos() {
        return equipos;
    }

    public void setEquipos(Equipo[] equipos) {
        this.equipos = equipos;
    }
}
