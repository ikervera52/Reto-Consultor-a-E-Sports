package org.example.appesports.Controlador;

import org.example.appesports.DAO.EquipoDAO;
import org.example.appesports.Modelo.Equipo;
import org.example.appesports.Modelo.EquipoInforme;

import java.time.LocalDate;
import java.util.ArrayList;

/** Controlador para gestionar las operaciones relacionadas con los equipos, como contar equipos, rellenar combo de equipos, buscar por ID o nombre, insertar, editar, borrar y listar equipos.
 */
public class EquipoController {

    /** Cuenta el número total de equipos registrados en el sistema y devuelve esa cantidad como una cadena.
     * @return El número total de equipos registrados en el sistema, representado como una cadena.
     * @throws Exception Si ocurre un error durante la cuenta de los equipos.
     */
    public static String contarEquipos() throws Exception{
        int cantidad = EquipoDAO.contarEquipos();
        return String.valueOf(cantidad);
    }

    /** Recupera una lista de nombres de equipos registrados en el sistema, que se puede utilizar para rellenar un combo box o lista desplegable.
     * @return Una lista de cadenas que representan los nombres de los equipos registrados en el sistema.
     * @throws Exception Si ocurre un error durante la recuperación de los nombres de los equipos.
     */
    public static ArrayList<String> rellenarComboEquipo() throws Exception{
        ArrayList<String> nombreEquipo = new ArrayList<>();
        for (Equipo equipo : EquipoDAO.verEquipos()){
            nombreEquipo.add(equipo.getNombre());
        }
        return nombreEquipo;

    }

    /** Busca un equipo en el sistema por su ID y devuelve el objeto Equipo correspondiente.
     * @param id El ID del equipo a buscar.
     * @return El objeto Equipo correspondiente al ID proporcionado.
     * @throws Exception Si ocurre un error durante la búsqueda del equipo.
     */
    public static Equipo equipoPorId(int id){
        return EquipoDAO.equipoPorId(id);
    }

    /** Crea un nuevo equipo con el nombre y la fecha de fundación proporcionados, y lo guarda en el sistema.
     * @param nombre El nombre del equipo a crear.
     * @param fechaFundacion La fecha de fundación del equipo a crear.
     * @throws Exception Si ocurre un error durante la creación del equipo.
     */
    public static void insertarEquipo(String nombre, LocalDate fechaFundacion){
        Equipo equipo = new Equipo(nombre, fechaFundacion);
        EquipoDAO.insertarEquipo(equipo);
    }

    /** Busca un equipo en el sistema por su nombre y devuelve el objeto Equipo correspondiente.
     * @param nombre El nombre del equipo a buscar.
     * @return El objeto Equipo correspondiente al nombre proporcionado.
     * @throws Exception Si ocurre un error durante la búsqueda del equipo.
     */
    public static Equipo equipoPorNombre(String nombre) throws Exception {
        return EquipoDAO.equipoPorNombre(nombre);
    }

    /** Edita la información de un equipo existente en el sistema, actualizando su nombre y fecha de fundación según los parámetros proporcionados.
     * @param nombreV El nombre actual del equipo que se desea editar.
     * @param nombre El nuevo nombre para el equipo editado.
     * @param fechaFundacion La nueva fecha de fundación para el equipo editado.
     * @throws Exception Si ocurre un error durante la edición del equipo.
     */
    public static void editarEquipo(String nombreV, String nombre, LocalDate fechaFundacion){
        Equipo equipo = new Equipo(nombre, fechaFundacion);

        EquipoDAO.editarEquipo(nombreV, equipo);
    }

    /** Elimina un equipo del sistema basado en su nombre.
     * @param nombre El nombre del equipo a eliminar.
     * @throws Exception Si ocurre un error durante la eliminación del equipo.
     */
    public static void borrarEquipo(String nombre) throws Exception{
        EquipoDAO.borrarEquipo(nombre);
    }

    /** Recupera una lista de todos los equipos registrados en el sistema.
     * @return Una lista de objetos Equipo que representan todos los equipos disponibles en el sistema.
     * @throws Exception Si ocurre un error durante la recuperación de los equipos.
     */
    public static ArrayList<Equipo> listarEquipos() throws Exception{
        return EquipoDAO.verEquipos();
    }

    /** Recupera una lista de objetos EquipoInforme que contienen información detallada sobre los equipos, como su nombre, fecha de fundación y el número de jugadores asociados a cada equipo.
     * @return Una lista de objetos EquipoInforme que representan la información detallada de los equipos registrados en el sistema.
     * @throws Exception Si ocurre un error durante la recuperación de la información de los equipos.
     */
    public static ArrayList<EquipoInforme> listarEquiposInforme() throws Exception{
        return EquipoDAO.verEquiposInforme();
    }
}
