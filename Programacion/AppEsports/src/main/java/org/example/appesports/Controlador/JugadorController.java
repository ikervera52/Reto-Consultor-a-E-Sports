package org.example.appesports.Controlador;

import org.example.appesports.DAO.JugadorDAO;
import org.example.appesports.Modelo.Jugador;

import java.util.ArrayList;

public class JugadorController {

    public static String contarJugadores(){
        int cantidad = JugadorDAO.contarJugadores();
        return String.valueOf(cantidad);
    }

    public static ArrayList<Jugador> verJugadoresPorEquipo(int id){
        return JugadorDAO.verJugadoresPorEquipo(id);
    }
}
