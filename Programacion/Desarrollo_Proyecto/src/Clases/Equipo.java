package Clases;

import java.time.LocalDate;
import java.util.ArrayList;

public class Equipo {
    private Integer id;
    private String nombre;
    private LocalDate fecha_fun;
    private ArrayList<Enfrentamineto> enfrentaminetos = new ArrayList<>();
    private ArrayList<Competicion> competiciones = new ArrayList<>();

    public Equipo(Integer id, String nombre, LocalDate fecha_fun, ArrayList<Enfrentamineto> enfrentaminetos, ArrayList<Competicion> competiciones) {
        this.id = id;
        this.nombre = nombre;
        this.fecha_fun = fecha_fun;
        this.enfrentaminetos = enfrentaminetos;
        this.competiciones = competiciones;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha_fun() {
        return fecha_fun;
    }

    public void setFecha_fun(LocalDate fecha_fun) {
        this.fecha_fun = fecha_fun;
    }

    public ArrayList<Enfrentamineto> getEnfrentaminetos() {
        return enfrentaminetos;
    }

    public void setEnfrentaminetos(ArrayList<Enfrentamineto> enfrentaminetos) {
        this.enfrentaminetos = enfrentaminetos;
    }

    public ArrayList<Competicion> getCompeticiones() {
        return competiciones;
    }

    public void setCompeticiones(ArrayList<Competicion> competiciones) {
        this.competiciones = competiciones;
    }
}
