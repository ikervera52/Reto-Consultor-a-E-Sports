package org.example.appesports.DAO;

import org.example.appesports.Controlador.JugadorController;
import org.example.appesports.Modelo.Equipo;
import org.example.appesports.Modelo.EquipoInforme;
import org.example.appesports.Modelo.Jugador;
import org.example.appesports.Utilidades.ConexionBD;

import java.sql.*;
import java.util.ArrayList;

/** Clase EquipoDAO que se encarga de realizar las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) en la base de datos para los objetos de tipo Equipo.
 * Esta clase utiliza la clase ConexionBD para establecer la conexión con la base de datos y ejecutar las consultas SQL necesarias.
 * Además, incluye métodos para contar el número de equipos, ver todos los equipos, obtener un equipo por su ID o por su nombre, insertar un nuevo equipo, editar un equipo existente y borrar un equipo.
 * También cuenta con un método para obtener un informe detallado de los equipos utilizando un procedimiento almacenado en la base de datos.
 */
public class EquipoDAO {

    /** Método para contar el número de equipos almacenados en la base de datos. Devuelve un entero que representa la cantidad total de equipos.
     * Si ocurre un error al acceder a la base de datos, se lanza una excepción indicando que hubo un error al contar los equipos.
     * @return Un entero que representa la cantidad total de equipos almacenados en la base de datos.
     * @throws Exception Si ocurre un error al acceder a la base de datos o si no se pueden contar los equipos.
     */
    public static int contarEquipos() throws Exception{
       int cantidad = 0;

        Connection con = ConexionBD.getConexion();
        String sql = "SELECT COUNT(*) FROM equipos";

        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            cantidad = rs.getInt(1);
        }

        ConexionBD.closeConexion(con);


        return cantidad;
    }

    /** Método para ver todos los equipos almacenados en la base de datos. Devuelve una lista de objetos Equipo que representan todos los equipos registrados.
     * Si ocurre un error al acceder a la base de datos, se lanza una excepción indicando que hubo un error al obtener los equipos.
     * @return Una lista de objetos Equipo que representan todos los equipos almacenados en la base de datos.
     * @throws Exception Si ocurre un error al acceder a la base de datos o si no se pueden obtener los equipos.
     */
    public static ArrayList<Equipo> verEquipos() throws Exception{
        ArrayList<Equipo> equipos = new ArrayList<>();

        Connection con = ConexionBD.getConexion();
        String sql = "SELECT * FROM equipos";

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();



        while (rs.next()){
            ArrayList<Jugador> jugadoresEquipo = JugadorController.verJugadoresPorEquipo(rs.getString("nombre"));
            equipos.add(
                    new Equipo(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getDate("fecha_fundacion").toLocalDate(),
                            jugadoresEquipo
                    )
            );
        }

        ConexionBD.closeConexion(con);

        return equipos;
    }
    /** Método para obtener un equipo por su ID. Recibe el ID del equipo como parámetro y devuelve un objeto Equipo asociado a ese ID.
     * Si ocurre un error al acceder a la base de datos o si no se encuentra un equipo con el ID especificado, se lanza una excepción indicando que hubo un error al obtener el equipo.
     * @param id El ID del equipo que se desea obtener.
     * @return Un objeto Equipo asociado al ID especificado.
     * @throws Exception Si ocurre un error al acceder a la base de datos o si no se encuentra un equipo con el ID especificado.
     */
    public static Equipo equipoPorId(int id){
        Equipo equipo = null;
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "SELECt * FROM equipos WHERE id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                equipo = new Equipo(
                        rs.getInt("id"),
                        rs.getString("nombre")
                        );
            }

            ConexionBD.closeConexion(con);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return equipo;
    }

    /** Método para obtener un equipo por su nombre. Recibe el nombre del equipo como parámetro y devuelve un objeto Equipo asociado a ese nombre.
     * Si ocurre un error al acceder a la base de datos o si no se encuentra un equipo con el nombre especificado, se lanza una excepción indicando que hubo un error al obtener el equipo.
     * @param nombre El nombre del equipo que se desea obtener.
     * @return Un objeto Equipo asociado al nombre especificado.
     * @throws Exception Si ocurre un error al acceder a la base de datos o si no se encuentra un equipo con el nombre especificado.
     */
    public static Equipo equipoPorNombre(String nombre) throws Exception{
        Equipo equipo = null;
            Connection con = ConexionBD.getConexion();
            String sql = "SELECt * FROM equipos WHERE nombre = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,nombre);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                equipo = new Equipo(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDate("fecha_fundacion").toLocalDate()
                );
            }else throw new Exception("No se ha encontrado el equipo con el nombre: " + nombre);

            ConexionBD.closeConexion(con);

        return equipo;
    }
    /** Método para insertar un nuevo equipo en la base de datos. Recibe un objeto Equipo como parámetro y agrega un nuevo registro en la tabla de equipos con los datos proporcionados.
     * Si el proceso de inserción falla, se lanza una excepción indicando que hubo un error al insertar el equipo.
     * @param equipo El objeto Equipo que se desea insertar en la base de datos.
     * @throws Exception Si ocurre un error al insertar el equipo en la base de datos.
     */
    public static void insertarEquipo(Equipo equipo){
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "INSERT INTO equipos (nombre, fecha_fundacion) VALUES (?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, equipo.getNombre());
            ps.setDate(2, java.sql.Date.valueOf(equipo.getFechaFundacion()));
            ps.executeUpdate();
            ConexionBD.closeConexion(con);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    /** Método para editar un equipo existente en la base de datos. Recibe el nombre del equipo que se desea editar y un objeto Equipo con los nuevos datos. Actualiza el registro correspondiente en la tabla de equipos con los nuevos valores.
     * Si ocurre un error al acceder a la base de datos o si no se encuentra un equipo con el nombre especificado, se lanza una excepción indicando que hubo un error al editar el equipo.
     * @param nombreV El nombre del equipo que se desea editar.
     * @param equipo El objeto Equipo que contiene los nuevos datos para actualizar el equipo en la base de datos.
     * @throws Exception Si ocurre un error al acceder a la base de datos o si no se encuentra un equipo con el nombre especificado para editar.
     */
    public static void editarEquipo(String nombreV, Equipo equipo){
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "UPDATE equipos SET nombre = ?, fecha_fundacion = ? WHERE nombre = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, equipo.getNombre());
            ps.setDate(2, java.sql.Date.valueOf(equipo.getFechaFundacion()));
            ps.setString(3, nombreV);
            ps.executeUpdate();
            ConexionBD.closeConexion(con);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    /** Método para borrar un equipo de la base de datos. Recibe el nombre del equipo que se desea eliminar como parámetro y elimina el registro correspondiente en la tabla de equipos.
     * Si ocurre un error al acceder a la base de datos o si no se encuentra un equipo con el nombre especificado, se lanza una excepción indicando que hubo un error al borrar el equipo.
     * @param nombre El nombre del equipo que se desea eliminar de la base de datos.
     * @throws Exception Si ocurre un error al acceder a la base de datos o si no se encuentra un equipo con el nombre especificado para eliminar.
     */
    public static void borrarEquipo(String nombre) throws Exception{

        Connection con = ConexionBD.getConexion();
        String sql = "DELETE FROM equipos WHERE nombre = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nombre);
        int e = ps.executeUpdate();

        if (e == 0) throw new Exception("No existe ningún equipo con ese Nombre.");

        ConexionBD.closeConexion(con);

    }
    /** Método para obtener un informe detallado de los equipos utilizando un procedimiento almacenado en la base de datos. Devuelve una lista de objetos EquipoInforme que contienen información detallada sobre cada equipo, como su nombre, fecha de fundación, cantidad de jugadores y estadísticas salariales.
     * Si ocurre un error al acceder a la base de datos o si no se pueden obtener los datos del informe, se lanza una excepción indicando que hubo un error al obtener el informe de equipos.
     * @return Una lista de objetos EquipoInforme que contienen información detallada sobre cada equipo.
     * @throws Exception Si ocurre un error al acceder a la base de datos o si no se pueden obtener los datos del informe de equipos.
     */
    public static ArrayList<EquipoInforme> verEquiposInforme() throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "{call estadisticas_equipos(?)}";

        CallableStatement cs = con.prepareCall(sql);
        cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

        cs.execute();

        ResultSet rs = (ResultSet) cs.getObject(1);

        ArrayList<EquipoInforme> equipoInforme = new ArrayList<>();
        while (rs.next()){
            equipoInforme.add(new EquipoInforme(
                    rs.getString("nombre"),
                    rs.getDate("fecha_fundacion").toLocalDate(),
                    rs.getInt("cantidad_jugadores"),
                    rs.getInt("sueldo_maximo"),
                    rs.getInt("sueldo_minimo"),
                    rs.getInt("sueldo_medio")
            ));
        }

        ConexionBD.closeConexion(con);

        return equipoInforme;
    }
}
