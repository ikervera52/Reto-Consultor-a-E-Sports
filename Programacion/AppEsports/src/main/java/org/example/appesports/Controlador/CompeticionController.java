package org.example.appesports.Controlador;

import org.example.appesports.DAO.CompeticionDAO;
import org.example.appesports.Modelo.Competicion;

import java.time.LocalDate;

/** Controlador para gestionar las operaciones relacionadas con la competición, como buscar la competición actual, cerrar la competición, ver el estado de la competición y terminar la competición.
 */
public class CompeticionController {

    /** Busca la competición actual en el sistema y devuelve el objeto Competicion correspondiente.
     * @return El objeto Competicion que representa la competición actual en el sistema.
     * @throws Exception Si ocurre un error durante la búsqueda de la competición.
     */
    public static Competicion buscarCompeticion() throws Exception{
        return CompeticionDAO.buscarCompeticion();

    }

    /** Cierra la competición actual estableciendo su etapa a "competicion", y actualizando las fechas de inicio, fin y el tipo de puntuación, luego guarda los cambios en el sistema
     * @param fechaInicio
     * @param fechaFin
     * @param tipoPuntuacion
     * @throws Exception
     */
    public static void cerrarCompeticion(LocalDate fechaInicio, LocalDate fechaFin, String tipoPuntuacion) throws Exception{
        Competicion competicion = buscarCompeticion();
        competicion.setEtapa("competicion");
        competicion.setFechaInicio(fechaInicio);
        competicion.setFechaFin(fechaFin);
        competicion.setTipoPuntuacion(tipoPuntuacion);

        CompeticionDAO.cerrarCompeticion(competicion);
    }

    /** Devuelve el estado actual de la competición, que se representa por la etapa en la que se encuentra.
     * @return El estado actual de la competición, representado como una cadena que indica la etapa actual de la competición.
     * @throws Exception Si ocurre un error durante la recuperación del estado de la competición.
     */
    public static String verEstadoCompeticion() throws Exception{
        Competicion competicion = buscarCompeticion();
        return competicion.getEtapa();
    }

    /** Termina la competición actual estableciendo su etapa a "inscripcion" y guardando los cambios en el sistema.
     * @throws Exception Si ocurre un error durante el proceso de terminar la competición.
     */
    public static void terminarCompeticion() throws Exception{
        Competicion competicion = buscarCompeticion();
        competicion.setEtapa("inscripcion");
        CompeticionDAO.cerrarCompeticion(competicion);

    }

}
