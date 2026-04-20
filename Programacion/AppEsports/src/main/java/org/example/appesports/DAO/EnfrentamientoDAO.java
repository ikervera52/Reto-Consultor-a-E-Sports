package org.example.appesports.DAO;

import org.example.appesports.Controlador.JornadaController;
import org.example.appesports.Modelo.Enfrentamiento;
import org.example.appesports.Utilidades.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.util.ArrayList;

/** Clase EnfrentamientoDAO que se encarga de realizar las operaciones CRUD (Crear, Leer) en la base de datos para los objetos de tipo Enfrentamiento.
 * Esta clase utiliza la clase ConexionBD para establecer la conexión con la base de datos y ejecutar las consultas SQL necesarias.
 * Además, incluye métodos para crear un nuevo enfrentamiento, buscar enfrentamientos por jornada y buscar un enfrentamiento por su ID.
 */
public class EnfrentamientoDAO {
    /** Método para crear un nuevo enfrentamiento en la base de datos. Recibe la hora del enfrentamiento y el ID de la jornada como parámetros, e inserta un nuevo registro en la tabla de enfrentamientos.
     * Si el proceso de inserción falla, se lanza una excepción indicando que hubo un error al crear el enfrentamiento.
     * @param hora La hora del enfrentamiento a crear.
     * @param idJornada El ID de la jornada a la que pertenece el enfrentamiento.
     * @throws Exception Si ocurre un error al insertar el enfrentamiento en la base de datos.
     */
    public static void crearEnfrentamiento(LocalTime hora, int idJornada) throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "INSERT INTO enfrentamientos (hora_enfrentamiento, id_jornada) VALUES (?,?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setTime(1, java.sql.Time.valueOf(hora));
        ps.setInt(2, idJornada);

        int e = ps.executeUpdate();

        if (e == 0) throw new Exception("Error al crear el Enfrentamiento");

        ConexionBD.closeConexion(con);

    }
    /** Método para buscar enfrentamientos por jornada. Recibe el ID de la jornada como parámetro y devuelve una lista de objetos Enfrentamiento asociados a esa jornada, ordenados por hora de enfrentamiento.
     * Si ocurre un error al acceder a la base de datos, se lanza una excepción indicando que hubo un error al obtener los enfrentamientos.
     * @param idJornada El ID de la jornada para la cual se desean obtener los enfrentamientos.
     * @return Una lista de objetos Enfrentamiento asociados a la jornada especificada, ordenados por hora de enfrentamiento.
     * @throws Exception Si ocurre un error al acceder a la base de datos o si no se encuentran enfrentamientos para la jornada especificada.
     */
    public static ArrayList<Enfrentamiento> buscarPorJornada(int idJornada) throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "SELECT * FROM enfrentamientos WHERE id_jornada = ? ORDER BY hora_enfrentamiento ASC";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idJornada);

        ResultSet rs = ps.executeQuery();

        ArrayList<Enfrentamiento> enfrentamientos = new ArrayList<>();
        while (rs.next()){
            enfrentamientos.add(new Enfrentamiento(
                    rs.getInt("id"),
                    rs.getTime("hora_enfrentamiento").toLocalTime(),
                    JornadaController.jornadaPorId(rs.getInt("id_jornada"))
            ));
        }

        ConexionBD.closeConexion(con);

        return enfrentamientos;
    }
    /** Método para buscar un enfrentamiento por su ID. Recibe el ID del enfrentamiento como parámetro y devuelve un objeto Enfrentamiento asociado a ese ID.
     * Si ocurre un error al acceder a la base de datos o si no se encuentra un enfrentamiento con el ID especificado, se lanza una excepción indicando que hubo un error al obtener el enfrentamiento.
     * @param id El ID del enfrentamiento que se desea obtener.
     * @return Un objeto Enfrentamiento asociado al ID especificado.
     * @throws Exception Si ocurre un error al acceder a la base de datos o si no se encuentra un enfrentamiento con el ID especificado.
     */
    public static Enfrentamiento buscarPorId(int id) throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "SELECT * FROM enfrentamientos WHERE id = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        Enfrentamiento enfrentamiento = null;
        if (rs.next()){
            enfrentamiento = new Enfrentamiento(
                    rs.getInt("id"),
                    rs.getTime("hora_enfrentamiento").toLocalTime(),
                    JornadaController.jornadaPorId(rs.getInt("id_jornada"))
            );
        }

        ConexionBD.closeConexion(con);

        return enfrentamiento;

    }
}
