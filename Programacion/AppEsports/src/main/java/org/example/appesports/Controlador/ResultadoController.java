package org.example.appesports.Controlador;

import org.example.appesports.DAO.ResultadoDAO;
import org.example.appesports.Modelo.Enfrentamiento;
import org.example.appesports.Modelo.Resultado;

import java.util.ArrayList;
/** Controlador para gestionar las operaciones relacionadas con los resultados de los enfrentamientos, como creación, visualización y actualización de resultados.
 */
public class ResultadoController {
    /** Crea un nuevo resultado para un enfrentamiento específico, asociando un equipo con el enfrentamiento correspondiente.
     * @param idEquipo El ID del equipo que participó en el enfrentamiento.
     * @param idEnfrentamiento El ID del enfrentamiento para el cual se está creando el resultado.
     * @throws Exception Si ocurre un error durante la creación del resultado.
     */
    public static void crearResultado(int idEquipo, int idEnfrentamiento) throws Exception{
        Resultado resultado = new Resultado(EquipoController.equipoPorId(idEquipo), EnfrentamientoController.buscarPorId(idEnfrentamiento));
        ResultadoDAO.crearResultado(resultado);
    }
    /** Recupera una lista de resultados asociados a un enfrentamiento específico, identificados por su ID.
     * @param idEnfrentamiento El ID del enfrentamiento para el cual se desean obtener los resultados.
     * @return Una lista de objetos Resultado correspondientes al enfrentamiento especificado.
     * @throws Exception Si ocurre un error durante la recuperación de los resultados.
     */
    public static ArrayList<Resultado> verPorEnfrentamiento(int idEnfrentamiento) throws Exception{
        return ResultadoDAO.verPorEnfrentamiento(idEnfrentamiento);
    }
    /** Actualiza la información de un resultado existente en el sistema, modificando los detalles del resultado según el objeto Resultado proporcionado.
     * @param resultado El objeto Resultado que contiene la información actualizada del resultado a modificar.
     * @throws Exception Si ocurre un error durante la actualización del resultado.
     */
    public static void actualizarResultado(Resultado resultado) throws Exception{
        ResultadoDAO.actualizarResultado(resultado);
    }
}
