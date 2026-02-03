public class Enfrentamiento {
    int ID_enfrentamiento;
    int numero_jornada;
    String equipos;
    int hora_enfrentamiento;
    String resultado;

    public Enfrentamiento(int ID_enfrentamiento, int numero_jornada, String equipos, int hora_enfrentamiento, String resultado) {
        this.ID_enfrentamiento = ID_enfrentamiento;
        this.numero_jornada = numero_jornada;
        this.equipos = equipos;
        this.hora_enfrentamiento = hora_enfrentamiento;
        this.resultado = resultado;
    }

    public String getEquipos() {
        return equipos;
    }

    public void setEquipos(String equipos) {
        this.equipos = equipos;
    }

    public int getHora_enfrentamiento() {
        return hora_enfrentamiento;
    }

    public void setHora_enfrentamiento(int hora_enfrentamiento) {
        this.hora_enfrentamiento = hora_enfrentamiento;
    }

    public int getID_enfrentamiento() {
        return ID_enfrentamiento;
    }

    public void setID_enfrentamiento(int ID_enfrentamiento) {
        this.ID_enfrentamiento = ID_enfrentamiento;
    }

    public int getNumero_jornada() {
        return numero_jornada;
    }

    public void setNumero_jornada(int numero_jornada) {
        this.numero_jornada = numero_jornada;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
