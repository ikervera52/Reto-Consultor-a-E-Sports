public class Jornada {
int ID_jornada;
String fecha;
int num_jornada;

public Jornada(int ID_jornada, String fecha, int num_jornada) {
    this.ID_jornada = ID_jornada;
    this.fecha = fecha;
    this.num_jornada = num_jornada;
}
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getID_jornada() {
        return ID_jornada;
    }

    public void setID_jornada(int ID_jornada) {
        this.ID_jornada = ID_jornada;
    }

    public int getNum_jornada() {
        return num_jornada;
    }

    public void setNum_jornada(int num_jornada) {
        this.num_jornada = num_jornada;
    }
}
