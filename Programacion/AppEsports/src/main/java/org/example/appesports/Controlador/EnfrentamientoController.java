package org.example.appesports.Controlador;

import org.example.appesports.DAO.EnfrentamientoDAO;
import org.example.appesports.Modelo.Enfrentamiento;

import java.time.LocalTime;
import java.util.ArrayList;

public class EnfrentamientoController {

    public static void crearEnfrentamiento(LocalTime hora, int idJornada) throws Exception{
        EnfrentamientoDAO.crearEnfrentamiento(hora, idJornada);

    }

    public static ArrayList<Enfrentamiento> buscarPorJornada(int idJornada) throws Exception{
        return EnfrentamientoDAO.buscarPorJornada(idJornada);
    }

    public static Enfrentamiento buscarPorId(int id) throws Exception{
        return EnfrentamientoDAO.buscarPorId(id);
    }
}
