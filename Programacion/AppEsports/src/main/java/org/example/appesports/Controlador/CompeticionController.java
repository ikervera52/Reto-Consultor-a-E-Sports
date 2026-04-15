package org.example.appesports.Controlador;

import org.example.appesports.DAO.CompeticionDAO;
import org.example.appesports.Modelo.Competicion;

public class CompeticionController {

    public static Competicion buscarCompeticion() throws Exception{
        return CompeticionDAO.buscarCompeticion();

    }

    public static void cerrarCompeticion() throws Exception{
        Competicion competicion = buscarCompeticion();
        competicion.setEtapa("competicion");
        CompeticionDAO.cerrarCompeticion(competicion);
    }

}
