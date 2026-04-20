package org.example.appesports.DAO;

import oracle.jdbc.proxy.annotation.Pre;
import org.example.appesports.Controlador.EquipoController;
import org.example.appesports.Modelo.Equipo;
import org.example.appesports.Modelo.Jugador;
import org.example.appesports.Utilidades.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
/** Clase JugadorDAO que se encarga de realizar las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) en la base de datos para los objetos de tipo Jugador.
 * Esta clase utiliza la clase ConexionBD para establecer la conexión con la base de datos y ejecutar las consultas SQL necesarias.
 * Además, incluye métodos para contar el número de jugadores, ver los jugadores por equipo, añadir un nuevo jugador, borrar un jugador existente, buscar un jugador por su nickname y editar un jugador.
 * También cuenta con un método para obtener una lista de todos los jugadores almacenados en la base de datos.
 */
public class JugadorDAO {
    /** Método para contar el número de jugadores en la base de datos. Devuelve un entero que representa la cantidad total de jugadores registrados.
     * Si ocurre un error al acceder a la base de datos, se lanza una excepción indicando que hubo un error al contar los jugadores.
     * @return Un entero que representa la cantidad total de jugadores registrados en la base de datos.
     * @throws Exception Si ocurre un error al acceder a la base de datos.
     */
    public static int contarJugadores(){
        int cantidad = 0;
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "SELECT COUNT(*) FROM jugadores";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                cantidad = rs.getInt(1);
            }
            ConexionBD.closeConexion(con);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cantidad;
    }
    /** Método para ver los jugadores por equipo. Recibe el nombre del equipo como parámetro y devuelve una lista de objetos Jugador asociados a ese equipo.
     * Si ocurre un error al acceder a la base de datos, se lanza una excepción indicando que hubo un error al obtener los jugadores por equipo.
     * @param nombreEquipo El nombre del equipo para el cual se desean obtener los jugadores.
     * @return Una lista de objetos Jugador asociados al equipo especificado.
     * @throws Exception Si ocurre un error al acceder a la base de datos o si no se encuentran jugadores para el equipo especificado.
     */
    public static ArrayList<Jugador> verJugadoresPorEquipo(String nombreEquipo) throws Exception{

        Connection con = ConexionBD.getConexion();
        String sql = "{call jugadores_por_equipos(?,?)}";

        CallableStatement cs = con.prepareCall(sql);
        cs.setString(1, nombreEquipo);
        cs.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);

        cs.execute();

        ResultSet rs = (ResultSet) cs.getObject(2);

        ArrayList<Jugador> jugadores = new ArrayList<>();
        while (rs.next()){
            jugadores.add(new Jugador(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getDate(5).toLocalDate(),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getDouble(8),
                    EquipoController.equipoPorId(rs.getInt(9))
            ));
        }
        ConexionBD.closeConexion(con);

        ConexionBD.closeConexion(con);

        return jugadores;
    }
    /** Método para añadir un nuevo jugador a la base de datos. Recibe un objeto Jugador como parámetro y agrega un nuevo registro en la tabla de jugadores con los datos proporcionados.
     * Si el proceso de inserción falla, se lanza una excepción indicando que hubo un error al ingresar el jugador.
     * @param jugador El objeto Jugador que se desea agregar a la base de datos.
     * @throws Exception Si ocurre un error al acceder a la base de datos o si no se puede ingresar el jugador.
     */
    public static void anadirJugador(Jugador jugador) throws Exception{
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "INSERT INTO jugadores (nombre, apellido, nacionalidad, fecha_nacimiento," +
                    "                            nickname, rol, sueldo, id_equipo) VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, jugador.getNombre());
            ps.setString(2, jugador.getApellido());
            ps.setString(3, jugador.getNacionalidad());
            ps.setDate(4, java.sql.Date.valueOf(jugador.getFechaNacimiento()));
            ps.setString(5, jugador.getNickname());
            ps.setString(6, jugador.getRol());
            ps.setDouble(7, jugador.getSueldo());
            ps.setInt(8, jugador.getEquipo().getIdEquipo());

            int e = ps.executeUpdate();

            if (e == 0){
                throw new Exception("Error al ingresar el jugador");
            }

            ConexionBD.closeConexion(con);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /** Método para borrar un jugador de la base de datos. Recibe el nickname del jugador como parámetro y elimina el registro correspondiente en la tabla de jugadores.
     * Si el proceso de eliminación falla, se lanza una excepción indicando que hubo un error al eliminar el jugador.
     * @param nickname El nickname del jugador que se desea eliminar.
     * @throws Exception Si ocurre un error al acceder a la base de datos o si no se puede eliminar el jugador.
     */
    public static void borrarJugador(String nickname)throws Exception{

        Connection con = ConexionBD.getConexion();
        String sql = "DELETE FROM jugadores WHERE nickname = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nickname);

        int e = ps.executeUpdate();

        if (e == 0){
            throw new Exception("No se ha podido eliminar el jugador");
        }

        ConexionBD.closeConexion(con);

    }
    /** Método para buscar un jugador por su nickname. Recibe el nickname del jugador como parámetro y devuelve un objeto Jugador con los datos correspondientes si se encuentra en la base de datos.
     * Si ocurre un error al acceder a la base de datos o si no se encuentra el jugador, se lanza una excepción indicando que hubo un error al buscar el jugador.
     * @param nickname El nickname del jugador que se desea buscar.
     * @return Un objeto Jugador con los datos correspondientes al jugador encontrado en la base de datos.
     * @throws Exception Si ocurre un error al acceder a la base de datos o si no se encuentra el jugador.
     */
    public static Jugador buscarPorNickname(String nickname) throws Exception{

        Jugador jugador = null;
        Connection con = ConexionBD.getConexion();
        String sql = "SELECT * FROM jugadores WHERE nickname = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nickname);

        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            jugador = new Jugador(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("nacionalidad"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getString("nickname"),
                    rs.getString("rol"),
                    rs.getDouble("sueldo"),
                    EquipoController.equipoPorId(rs.getInt("id_equipo")));

        } else throw new Exception("Error al buscar el Jugador");

        ConexionBD.closeConexion(con);

        return jugador;
    }
    /** Método para editar un jugador existente en la base de datos. Recibe el nickname del jugador que se desea editar y un objeto Jugador con los nuevos datos.
     * Actualiza el registro correspondiente en la tabla de jugadores con los nuevos datos proporcionados.
     * Si el proceso de actualización falla, se lanza una excepción indicando que hubo un error al editar el jugador.
     * @param nickname El nickname del jugador que se desea editar.
     * @param jugador Un objeto Jugador con los nuevos datos del jugador.
     * @throws Exception Si ocurre un error al acceder a la base de datos o si no se puede editar el jugador.
     */
    public static void editarJugador(String nickname, Jugador jugador) throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "UPDATE jugadores SET nombre = ?, apellido = ?, nacionalidad = ?, fecha_nacimiento = ?, nickname = ?," +
                "rol = ?, sueldo = ?, id_equipo = ? WHERE nickname = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, jugador.getNombre());
        ps.setString(2, jugador.getApellido());
        ps.setString(3,jugador.getNacionalidad());
        ps.setDate(4, java.sql.Date.valueOf(jugador.getFechaNacimiento()));
        ps.setString(5, jugador.getNickname());
        ps.setString(6, jugador.getRol());
        ps.setDouble(7, jugador.getSueldo());
        ps.setInt(8, jugador.getEquipo().getIdEquipo());
        ps.setString(9, nickname);

        int e = ps.executeUpdate();

        if (e == 0){
            throw new Exception("Error al actualizar el Jugador");
        }

        ConexionBD.closeConexion(con);
    }
    /** Método para obtener una lista de todos los jugadores almacenados en la base de datos. Devuelve un ArrayList de objetos Jugador con los datos correspondientes a cada jugador registrado.
     * Si ocurre un error al acceder a la base de datos o si no se encuentran jugadores, se lanza una excepción indicando que hubo un error al obtener los jugadores.
     * @return Un ArrayList de objetos Jugador con los datos correspondientes a cada jugador registrado en la base de datos.
     * @throws Exception Si ocurre un error al acceder a la base de datos o si no se encuentran jugadores.
     */
    public static ArrayList<Jugador> verJugadores() throws Exception{
        ArrayList<Jugador> jugadores = new ArrayList<>();
        String sql = "SELECT * FROM jugadores";
        Connection con = ConexionBD.getConexion();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Jugador jugador = new Jugador(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("nacionalidad"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getString("nickname"),
                    rs.getString("rol"),
                    rs.getDouble("sueldo"),
                    EquipoController.equipoPorId(rs.getInt("id_equipo"))
            );
            jugadores.add(jugador);
        }

        if (jugadores.size()==0){
            throw new Exception("No se han encontrado jugadores");
        }

        return jugadores;
    }
}
