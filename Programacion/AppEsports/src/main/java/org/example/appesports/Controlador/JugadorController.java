package org.example.appesports.Controlador;

import org.example.appesports.DAO.JugadorDAO;

public class JugadorController {

    public static String contarJugadores(){
        int cantidad = JugadorDAO.contarJugadores();
        return String.valueOf(cantidad);
    }
}
