package Clases;

import java.time.LocalTime;
import java.util.Random;

public class Enfrentamiento {
    private Equipo local;
    private Equipo visitante;
    private LocalTime hora;
    private int rondasLocal;
    private int rondasVisitante;
    private boolean jugado;

    public Enfrentamiento(Equipo local, Equipo visitante, LocalTime hora) {
        this.local = local;
        this.visitante = visitante;
        this.hora = hora;
        this.jugado = false;
    }

    public void simularResultado() {
        if (jugado) return;
        Random rand = new Random();
        if (rand.nextDouble() < 0.10) {
            this.rondasLocal = 15;
            this.rondasVisitante = 15;
        } else {
            if (rand.nextBoolean()) {
                this.rondasLocal = 16;
                this.rondasVisitante = rand.nextInt(15);
            } else {
                this.rondasVisitante = 16;
                this.rondasLocal = rand.nextInt(15);
            }
        }
        this.jugado = true;
    }

    public Equipo getLocal() { return local; }
    public Equipo getVisitante() { return visitante; }
    public int getRondasLocal() { return rondasLocal; }
    public int getRondasVisitante() { return rondasVisitante; }

    @Override
    public String toString() {
        String res = jugado ? " [" + rondasLocal + "-" + rondasVisitante + "]" : " [Pendiente]";
        return local.getNombre() + " vs " + visitante.getNombre() + " (" + hora + ")" + res;
    }
}