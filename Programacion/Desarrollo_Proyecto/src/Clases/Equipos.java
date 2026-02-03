package Clases;

import java.util.ArrayList;

public class Equipos {
    private int id_equipo;
    private String nombre_equipo;
    private String fecha_fundacion;
    private int cantidad_miembros;
    private ArrayList<Jugadores> lista_jugadores;

    public Equipos(int id_equipo, String nombre_equipo, int cantidad_miembros, String fecha_fundacion, ArrayList<Jugadores> lista_jugadores) {
        this.id_equipo = id_equipo;
        this.nombre_equipo = nombre_equipo;
        this.cantidad_miembros = cantidad_miembros;
        this.fecha_fundacion = fecha_fundacion;
        this.lista_jugadores = lista_jugadores;
    }

    public int getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(int id_equipo) {
        this.id_equipo = id_equipo;
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

    public int getCantidad_miembros() {
        return cantidad_miembros;
    }

    public void setCantidad_miembros(int cantidad_miembros) {
        this.cantidad_miembros = cantidad_miembros;
    }

    public ArrayList<Jugadores> getLista_jugadores() {
        return lista_jugadores;
    }

    public void setLista_jugadores(ArrayList<Jugadores> lista_jugadores) {
        this.lista_jugadores = lista_jugadores;
    }
}
