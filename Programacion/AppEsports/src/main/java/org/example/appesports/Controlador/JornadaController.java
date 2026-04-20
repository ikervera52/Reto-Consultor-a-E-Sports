package org.example.appesports.Controlador;

import org.example.appesports.DAO.JornadaDAO;
import org.example.appesports.Modelo.Jornada;

import java.time.LocalDate;
import java.util.ArrayList;

/** Controlador para gestionar las operaciones relacionadas con las jornadas de competición, como creación, listado y búsqueda de jornadas por ID.
 */
public class JornadaController {

    /** Crea una nueva jornada de competición con la fecha, número de jornada y competición asociada, y la guarda en el sistema.
     * @param fecha La fecha de la jornada a crear.
     * @param numJornada El número de la jornada a crear.
     * @throws Exception Si ocurre un error durante la creación de la jornada.
     */
    public static void crearJornada(LocalDate fecha, int numJornada) throws Exception {
        Jornada jornada = new Jornada(fecha, numJornada, CompeticionController.buscarCompeticion());

        JornadaDAO.crearJornada(jornada);
    }
    /** Recupera una lista de todas las jornadas de competición registradas en el sistema.
     * @return Una lista de objetos Jornada que representan todas las jornadas de competición disponibles en el sistema.
     * @throws Exception Si ocurre un error durante la recuperación de las jornadas.
     */
    public static ArrayList<Jornada> listarJornadas() throws Exception{
        return JornadaDAO.listarJornadas();
    }
    /** Busca una jornada de competición en el sistema por su ID y devuelve el objeto Jornada correspondiente.
     * @param id El ID de la jornada a buscar.
     * @return El objeto Jornada correspondiente al ID proporcionado.
     * @throws Exception Si ocurre un error durante la búsqueda de la jornada.
     */
    public static Jornada jornadaPorId(int id) throws Exception{
        return JornadaDAO.jornadaPorId(id);
    }
}
