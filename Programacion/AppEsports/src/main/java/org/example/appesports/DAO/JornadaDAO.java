package org.example.appesports.DAO;

import org.example.appesports.Modelo.Jornada;
import org.example.appesports.Utilidades.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/** Clase JornadaDAO que se encarga de realizar las operaciones CRUD (Crear, Leer, Actualizar) en la base de datos para los objetos de tipo Jornada.
 * Esta clase utiliza la clase ConexionBD para establecer la conexión con la base de datos y ejecutar las consultas SQL necesarias.
 * Además, incluye métodos para crear una nueva jornada, listar todas las jornadas y obtener una jornada por su ID.
 */
public class JornadaDAO {
    /** Método para crear una nueva jornada en la base de datos. Recibe un objeto Jornada como parámetro y lo inserta en la tabla de jornadas.
     * Si el proceso de inserción falla, se lanza una excepción indicando que hubo un error al crear la jornada.
     * @param jornada El objeto Jornada que se desea crear en la base de datos.
     * @throws Exception Si ocurre un error al insertar la jornada en la base de datos.
     */
    public static void crearJornada(Jornada jornada) throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "INSERT INTO jornadas (num_jornada, fecha, id_competicion) VALUES (?,?,?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, jornada.getNumeroJornada());
        ps.setDate(2, java.sql.Date.valueOf(jornada.getFechaJornada()));
        ps.setInt(3, jornada.getCompeticion().getIdCompeticion());

        int e = ps.executeUpdate();

        if(e == 0) throw new Exception("Error al crear Jornada");

        ConexionBD.closeConexion(con);

    }
    /** Método para listar todas las jornadas almacenadas en la base de datos. Devuelve una lista de objetos Jornada ordenados por número de jornada.
     * Si ocurre un error al acceder a la base de datos, se lanza una excepción indicando que hubo un error al listar las jornadas.
     * @return Una lista de objetos Jornada ordenados por número de jornada.
     * @throws Exception Si ocurre un error al acceder a la base de datos o si no se encuentran jornadas en la base de datos.
     */
    public static ArrayList<Jornada> listarJornadas() throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "SELECT * FROM jornadas ORDER BY num_jornada ASC";

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        ArrayList<Jornada> jornadas = new ArrayList<>();
        while (rs.next()){
            jornadas.add( new Jornada(
                    rs.getInt("id"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getInt("num_jornada")
            ));
        }

        ConexionBD.closeConexion(con);

        return jornadas;
    }
    /** Método para obtener una jornada por su ID. Recibe el ID de la jornada como parámetro y devuelve un objeto Jornada asociado a ese ID.
     * Si ocurre un error al acceder a la base de datos o si no se encuentra una jornada con el ID especificado, se lanza una excepción indicando que hubo un error al obtener la jornada.
     * @param id El ID de la jornada que se desea obtener.
     * @return Un objeto Jornada asociado al ID especificado.
     * @throws Exception Si ocurre un error al acceder a la base de datos o si no se encuentra una jornada con el ID especificado.
     */
    public static Jornada jornadaPorId(int id) throws Exception {
        Connection con = ConexionBD.getConexion();
        String sql = "SELECT * FROM jornadas WHERE id = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();


        Jornada jornada = null;
        if (rs.next()) {
            jornada = new Jornada(
                    rs.getInt("id"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getInt("num_jornada")
            );
        }

        ConexionBD.closeConexion(con);

        return jornada;

    }
}
