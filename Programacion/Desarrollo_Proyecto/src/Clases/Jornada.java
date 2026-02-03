package Clases;

import java.util.ArrayList;

public class Jornada {
    private int id_jornada;
    private String fecha_jornada;
    private int numero_jornada;
    private ArrayList<Competicion> competiciones;

    public Jornada(int id_jornada, String fecha_jornada, int numero_jornada, ArrayList<Competicion> competiciones) {
        this.id_jornada = id_jornada;
        this.fecha_jornada = fecha_jornada;
        this.numero_jornada = numero_jornada;
        this.competiciones = competiciones;
    }

    public int getId_jornada() {
        return id_jornada;
    }

    public void setId_jornada(int id_jornada) {
        this.id_jornada = id_jornada;
    }

    public String getFecha_jornada() {
        return fecha_jornada;
    }

    public void setFecha_jornada(String fecha_jornada) {
        this.fecha_jornada = fecha_jornada;
    }

    public int getNumero_jornada() {
        return numero_jornada;
    }

    public void setNumero_jornada(int numero_jornada) {
        this.numero_jornada = numero_jornada;
    }

    public ArrayList<Competicion> getCompeticiones() {
        return competiciones;
    }

    public void setCompeticiones(ArrayList<Competicion> competiciones) {
        this.competiciones = competiciones;
    }
}
