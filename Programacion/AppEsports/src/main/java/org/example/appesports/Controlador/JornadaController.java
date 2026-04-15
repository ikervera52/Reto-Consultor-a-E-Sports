package org.example.appesports.Controlador;

import org.example.appesports.DAO.JornadaDAO;
import org.example.appesports.Modelo.Jornada;

import java.time.LocalDate;

public class JornadaController {

    public static void crearJornada(LocalDate fecha, int numJornada) throws Exception {
        Jornada jornada = new Jornada(fecha, numJornada, CompeticionController.buscarCompeticion());

        JornadaDAO.crearJornada(jornada);
    }
}
