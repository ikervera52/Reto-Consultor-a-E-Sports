package org.example.appesports.Controlador;

import org.example.appesports.DAO.JugadorDAO;
import org.example.appesports.Modelo.Jugador;

import java.time.LocalDate;
import java.util.ArrayList;

/** Controlador para gestionar las operaciones relacionadas con los jugadores, como contar jugadores, ver jugadores por equipo, agregar, eliminar, buscar y editar jugadores.
 */
public class JugadorController {

    /** Cuenta el número total de jugadores registrados en el sistema y devuelve esa cantidad como una cadena.
     * @return El número total de jugadores registrados en el sistema, representado como una cadena.
     */
    public static String contarJugadores(){
        int cantidad = JugadorDAO.contarJugadores();
        return String.valueOf(cantidad);
    }

    /** Recupera una lista de jugadores asociados a un equipo específico, identificado por su nombre.
     * @param nombreEquipo
     * @return
     * @throws Exception
     */
    public static ArrayList<Jugador> verJugadoresPorEquipo(String nombreEquipo) throws Exception{
        return JugadorDAO.verJugadoresPorEquipo(nombreEquipo);
    }

    /** Agrega un nuevo jugador al sistema con la información proporcionada, incluyendo su nombre, apellido, nacionalidad, fecha de nacimiento, nickname, rol, sueldo y el equipo al que pertenece.
     * @param nombre
     * @param apellido
     * @param nacionalidad
     * @param fechaNacimiento
     * @param nickname
     * @param rol
     * @param sueldo
     * @param nombreEquipo
     * @throws Exception
     */
    public static void anadirJugador(String nombre, String apellido, String nacionalidad, LocalDate fechaNacimiento,
                                     String nickname, String rol, double sueldo, String nombreEquipo) throws Exception{
        JugadorDAO.anadirJugador(new Jugador(nombre, apellido, nacionalidad, fechaNacimiento, nickname,
                                             rol, sueldo, EquipoController.equipoPorNombre(nombreEquipo)));

    }

    /** Elimina un jugador del sistema basado en su nickname.
     * @param nickname El nickname del jugador a eliminar.
     * @throws Exception Si ocurre un error durante la eliminación del jugador.
     */
    public static void borrarJugador(String nickname) throws Exception {
        JugadorDAO.borrarJugador(nickname);
    }
    /** Busca un jugador en el sistema por su nickname y devuelve el objeto Jugador correspondiente.
     * @param nickname El nickname del jugador a buscar.
     * @return El objeto Jugador correspondiente al nickname proporcionado.
     * @throws Exception Si ocurre un error durante la búsqueda del jugador.
     */
    public static Jugador buscarPorNickname(String nickname) throws Exception{
        return JugadorDAO.buscarPorNickname(nickname);
    }
    /** Edita la información de un jugador existente en el sistema, actualizando su nombre, apellido, nacionalidad, fecha de nacimiento, nickname, rol, sueldo y equipo según los parámetros proporcionados.
     * @param nickname El nickname del jugador a editar.
     * @param nombre El nuevo nombre para el jugador editado.
     * @param apellido El nuevo apellido para el jugador editado.
     * @param nacionalidad La nueva nacionalidad para el jugador editado.
     * @param fechaNacimiento La nueva fecha de nacimiento para el jugador editado.
     * @param nuevoNickname El nuevo nickname para el jugador editado.
     * @param rol El nuevo rol para el jugador editado.
     * @param sueldo El nuevo sueldo para el jugador editado.
     * @param nombreEquipo El nuevo nombre del equipo al que pertenece el jugador editado.
     * @throws Exception Si ocurre un error durante la edición del jugador.
     */
    public static void editarJugador(String nickname, String nombre, String apellido, String nacionalidad,
                                     LocalDate fechaNacimiento, String nuevoNickname, String rol,
                                     double sueldo, String nombreEquipo) throws Exception {
        JugadorDAO.editarJugador(nickname,
                new Jugador( nombre, apellido, nacionalidad, fechaNacimiento, nuevoNickname, rol, sueldo,
                        EquipoController.equipoPorNombre(nombreEquipo)));

    }
    /** Recupera una lista de todos los jugadores registrados en el sistema.
     * @return Una lista de objetos Jugador correspondientes a todos los jugadores registrados en el sistema.
     * @throws Exception Si ocurre un error durante la recuperación de los jugadores.
     */
    public static ArrayList<Jugador> verJugadores() throws Exception {
        return JugadorDAO.verJugadores();
    }
}
