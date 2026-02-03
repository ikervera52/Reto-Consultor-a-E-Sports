public class Competicion {
    int ID_competicion;
    String etapa;
    String fecha_inicio;
    String fecha_fin;

    public Competicion(int ID_competicion, String etapa, String fecha_inicio, String fecha_fin) {
        this.ID_competicion = ID_competicion;
        this.etapa = etapa;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
    }

    public int getID_competicion() {
        return ID_competicion;
    }

    public void setID_competicion(int ID_competicion) {
        this.ID_competicion = ID_competicion;
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

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }
}
