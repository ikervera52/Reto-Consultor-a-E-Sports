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
    private String tipo_puntuacion;

    public Competicion(int id_competicion, String fecha_inicio, String fecha_fin, Etapa etapa, String tipo_puntuacion) {
        this.id_competicion = id_competicion;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.etapa = etapa;
        this.tipo_puntuacion = tipo_puntuacion;
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

    public String getTipo_puntuacion() {
        return tipo_puntuacion;
    }

    public void setTipo_puntuacion(String tipo_puntuacion) {
        this.tipo_puntuacion = tipo_puntuacion;
    }
}
