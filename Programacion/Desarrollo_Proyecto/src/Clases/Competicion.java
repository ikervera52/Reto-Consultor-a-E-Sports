package Clases;

public class Competicion {


    public enum Etapa {
        INSCRITO,
        COMPETICION
    }

    private int id_competicion;
    private String fecha_inicio;
    private String fecha_fin;
    private Etapa etapa;

    public Competicion(int id_competicion, Etapa etapa, String fecha_fin, String fecha_inicio) {
        this.id_competicion = id_competicion;
        this.etapa = etapa;
        this.fecha_fin = fecha_fin;
        this.fecha_inicio = fecha_inicio;
    }

    public int getId_competicion() {
        return id_competicion;
    }

    public void setId_competicion(int id_competicion) {
        this.id_competicion = id_competicion;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public Etapa getEtapa() {
        return etapa;
    }

    public void setEtapa(Etapa etapa) {
        this.etapa = etapa;
    }
}
