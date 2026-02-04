package Modelo;

import java.time.LocalTime;

public class Enfrentamiento {
    private int id;
    private LocalTime hora;
    private String resultado;
    private Equipo[] equipos =new Equipo[2];
    private Jornada jornada;

    public Enfrentamiento(int id, LocalTime hora, Equipo[] equipos, Jornada jornada) {
        this.id = id;
        this.hora = hora;
        this.equipos = equipos;
        this.jornada = jornada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Equipo[] getEquipos() {
        return equipos;
    }

    public void setEquipos(Equipo[] equipos) {
        this.equipos = equipos;
    }

    public Jornada getJornada() {
        return jornada;
    }

    public void setJornada(Jornada jornada) {
        this.jornada = jornada;
    }
}
