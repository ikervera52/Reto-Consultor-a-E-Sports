package org.example.appesports.Controlador;

import org.example.appesports.DAO.CompeticionDAO;
import org.example.appesports.Modelo.Competicion;

import java.time.LocalDate;

public class CompeticionController {

    public static Competicion buscarCompeticion() throws Exception{
        return CompeticionDAO.buscarCompeticion();

    }

    public static void cerrarCompeticion(LocalDate fechaInicio, LocalDate fechaFin, String tipoPuntuacion) throws Exception{
        Competicion competicion = buscarCompeticion();
        competicion.setEtapa("competicion");
        competicion.setFechaInicio(fechaInicio);
        competicion.setFechaFin(fechaFin);
        competicion.setTipoPuntuacion(tipoPuntuacion);

        CompeticionDAO.cerrarCompeticion(competicion);
    }

    public static String verEstadoCompeticion() throws Exception{
        Competicion competicion = buscarCompeticion();
        return competicion.getEtapa();
    }

    public static void terminarCompeticion() throws Exception{
        Competicion competicion = buscarCompeticion();
        competicion.setEtapa("inscripcion");
        CompeticionDAO.cerrarCompeticion(competicion);

    }

}
