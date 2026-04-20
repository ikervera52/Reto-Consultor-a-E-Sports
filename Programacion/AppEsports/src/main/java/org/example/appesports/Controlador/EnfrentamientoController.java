package org.example.appesports.Controlador;

import org.example.appesports.DAO.EnfrentamientoDAO;
import org.example.appesports.Modelo.Enfrentamiento;

import java.time.LocalTime;
import java.util.ArrayList;

/** Controlador para gestionar las operaciones relacionadas con los enfrentamientos, como creación, búsqueda por jornada y búsqueda por ID.
 */
public class EnfrentamientoController {

    /** Crea un nuevo enfrentamiento con la hora y la jornada asociada, y lo guarda en el sistema.
     * @param hora La hora del enfrentamiento a crear.
     * @param idJornada El ID de la jornada a la que pertenece el enfrentamiento.
     * @throws Exception Si ocurre un error durante la creación del enfrentamiento.
     */
    public static void crearEnfrentamiento(LocalTime hora, int idJornada) throws Exception{
        EnfrentamientoDAO.crearEnfrentamiento(hora, idJornada);

    }

    /** Recupera una lista de enfrentamientos asociados a una jornada específica, identificados por su ID.
     * @param idJornada El ID de la jornada para la cual se desean obtener los enfrentamientos.
     * @return Una lista de objetos Enfrentamiento correspondientes a la jornada especificada.
     * @throws Exception Si ocurre un error durante la recuperación de los enfrentamientos.
     */
    public static ArrayList<Enfrentamiento> buscarPorJornada(int idJornada) throws Exception{
        return EnfrentamientoDAO.buscarPorJornada(idJornada);
    }

    /** Busca un enfrentamiento en el sistema por su ID y devuelve el objeto Enfrentamiento correspondiente.
     * @param id El ID del enfrentamiento a buscar.
     * @return El objeto Enfrentamiento correspondiente al ID proporcionado.
     * @throws Exception Si ocurre un error durante la búsqueda del enfrentamiento.
     */
    public static Enfrentamiento buscarPorId(int id) throws Exception{
        return EnfrentamientoDAO.buscarPorId(id);
    }
}
