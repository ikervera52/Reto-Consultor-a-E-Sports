package org.example.appesports.Controlador;

import org.example.appesports.DAO.JugadorDAO;
import org.example.appesports.Modelo.Jugador;

import java.time.LocalDate;
import java.util.ArrayList;

public class JugadorController {

    public static String contarJugadores(){
        int cantidad = JugadorDAO.contarJugadores();
        return String.valueOf(cantidad);
    }

    public static ArrayList<Jugador> verJugadoresPorEquipo(int id){
        return JugadorDAO.verJugadoresPorEquipo(id);
    }

    public static void anadirJugador(String nombre, String apellido, String nacionalidad, LocalDate fechaNacimiento,
                                     String nickname, String rol, double sueldo, String nombreEquipo) throws Exception{
        JugadorDAO.anadirJugador(new Jugador(nombre, apellido, nacionalidad, fechaNacimiento, nickname,
                                             rol, sueldo, EquipoController.equipoPorNombre(nombreEquipo)));

    }

    public static void borrarJugador(String nickname) throws Exception {
        JugadorDAO.borrarJugador(nickname);
    }

    public static Jugador buscarPorNickname(String nickname) throws Exception{
        return JugadorDAO.buscarPorNickname(nickname);
    }

    public static void editarJugador(String nickname, String nombre, String apellido, String nacionalidad,
                                     LocalDate fechaNacimiento, String nuevoNickname, String rol,
                                     double sueldo, String nombreEquipo) throws Exception {
        JugadorDAO.editarJugador(nickname,
                new Jugador( nombre, apellido, nacionalidad, fechaNacimiento, nuevoNickname, rol, sueldo,
                        EquipoController.equipoPorNombre(nombreEquipo)));

    }

    public static ArrayList<Jugador> verJugadores() throws Exception {
        return JugadorDAO.verJugadores();
    }
}
