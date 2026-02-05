package Clases;

import java.util.ArrayList;

public class Equipos {
    private String nombre_equipo;
    private String fecha_fundacion;
    private ArrayList<Jugadores> lista_jugadores;

    public Equipos(String nombre_equipo, String fecha_fundacion, ArrayList<Jugadores> lista_jugadores) {
        this.nombre_equipo = nombre_equipo;
        this.fecha_fundacion = fecha_fundacion;
        this.lista_jugadores = lista_jugadores;
    }


    public String getNombre_equipo() {
        return nombre_equipo;
    }

    public void setNombre_equipo(String nombre_equipo) {
        this.nombre_equipo = nombre_equipo;
    }

    public String getFecha_fundacion() {
        return fecha_fundacion;
    }

    public void setFecha_fundacion(String fecha_fundacion) {
        this.fecha_fundacion = fecha_fundacion;
    }


    public ArrayList<Jugadores> getLista_jugadores() {
        return lista_jugadores;
    }

    public void setLista_jugadores(ArrayList<Jugadores> lista_jugadores) {
        this.lista_jugadores = lista_jugadores;
    }
}
