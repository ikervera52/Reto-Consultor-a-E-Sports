package Exepciones;

public class CantidadJugadoresNoValida extends Exception {

    public CantidadJugadoresNoValida() {
        super();
    }
    public CantidadJugadoresNoValida(String mensaje) {
        super(mensaje);
    }
}