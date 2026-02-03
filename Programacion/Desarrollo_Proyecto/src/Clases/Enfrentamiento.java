package Clases;

import java.util.ArrayList;

public class Enfrentamiento {
    private int id_enfrentamiento;
    private int num_jornada;
    private ArrayList<Equipos> equipos_involucrados;
    private int hora_enfrentamiento;
    private String Resultado;

    public Enfrentamiento(int id_enfrentamiento, int num_jornada, ArrayList<Equipos> equipos_involucrados, int hora_enfrentamiento, String resultado) {
        this.id_enfrentamiento = id_enfrentamiento;
        this.num_jornada = num_jornada;
        this.equipos_involucrados = equipos_involucrados;
        this.hora_enfrentamiento = hora_enfrentamiento;
        Resultado = resultado;
    }

    public int getId_enfrentamiento() {
        return id_enfrentamiento;
    }

    public void setId_enfrentamiento(int id_enfrentamiento) {
        this.id_enfrentamiento = id_enfrentamiento;
    }

    public int getNum_jornada() {
        return num_jornada;
    }

    public void setNum_jornada(int num_jornada) {
        this.num_jornada = num_jornada;
    }

    public ArrayList<Equipos> getEquipos_involucrados() {
        return equipos_involucrados;
    }

    public void setEquipos_involucrados(ArrayList<Equipos> equipos_involucrados) {
        this.equipos_involucrados = equipos_involucrados;
    }

    public int getHora_enfrentamiento() {
        return hora_enfrentamiento;
    }

    public void setHora_enfrentamiento(int hora_enfrentamiento) {
        this.hora_enfrentamiento = hora_enfrentamiento;
    }

    public String getResultado() {
        return Resultado;
    }

    public void setResultado(String resultado) {
        Resultado = resultado;
    }
}
