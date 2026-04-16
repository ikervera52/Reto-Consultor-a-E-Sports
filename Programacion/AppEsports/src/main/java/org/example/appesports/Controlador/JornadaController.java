package org.example.appesports.Controlador;

import org.example.appesports.DAO.JornadaDAO;
import org.example.appesports.Modelo.Jornada;

import java.time.LocalDate;
import java.util.ArrayList;

public class JornadaController {

    public static void crearJornada(LocalDate fecha, int numJornada) throws Exception {
        Jornada jornada = new Jornada(fecha, numJornada, CompeticionController.buscarCompeticion());

        JornadaDAO.crearJornada(jornada);
    }

    public static ArrayList<Jornada> listarJornadas() throws Exception{
        return JornadaDAO.listarJornadas();
    }

    public static Jornada jornadaPorId(int id) throws Exception{
        return JornadaDAO.jornadaPorId(id);
    }
}
