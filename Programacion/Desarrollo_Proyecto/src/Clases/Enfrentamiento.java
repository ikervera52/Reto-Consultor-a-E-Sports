package Clases;

import java.util.ArrayList;

public class Enfrentamiento {
    private int id_enfrentamiento;
    private ArrayList<Equipos> equipos_involucrados;
    private int hora_enfrentamiento;


        public Enfrentamiento(int id_enfrentamiento, ArrayList<Equipos> equipos_involucrados, int hora_enfrentamiento) {
            this.id_enfrentamiento = id_enfrentamiento;
            this.equipos_involucrados = equipos_involucrados;
            this.hora_enfrentamiento = hora_enfrentamiento;
        }

    public int getId_enfrentamiento() {
        return id_enfrentamiento;
    }

    public void setId_enfrentamiento(int id_enfrentamiento) {
        this.id_enfrentamiento = id_enfrentamiento;
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
}
