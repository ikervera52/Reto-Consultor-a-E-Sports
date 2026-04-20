package org.example.appesports.DAO;

import org.example.appesports.Modelo.Competicion;
import org.example.appesports.Utilidades.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/** Clase CompeticionDAO que se encarga de realizar las operaciones CRUD (Crear, Leer, Actualizar) en la base de datos para los objetos de tipo Competicion.
 * Esta clase utiliza la clase ConexionBD para establecer la conexión con la base de datos y ejecutar las consultas SQL necesarias.
 * Además, incluye métodos para buscar una competición existente y cerrar una competición actualizando su estado en la base de datos.
 */
public class CompeticionDAO {

    /** Método para buscar una competición existente en la base de datos. Devuelve un objeto Competicion con los datos de la competición encontrada.
     * Si no se encuentra ninguna competición, se lanza una excepción indicando que hubo un error al buscar la competición.
     * @return Un objeto Competicion con los datos de la competición encontrada.
     * @throws Exception Si ocurre un error al acceder a la base de datos o si no se encuentra ninguna competición.
     */
    public static Competicion buscarCompeticion() throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "SELECT * FROM competiciones";

        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        Competicion competicion;


        if (rs.next()){

            if(rs.getDate("fecha_inicio") == null && rs.getString("etapa").equals("inscripcion")){
                competicion = new Competicion(
                        rs.getString("etapa")
                );
            } else {
            competicion = new Competicion(
                    rs.getInt("id"),
                    rs.getDate("fecha_inicio").toLocalDate(),
                    rs.getDate("fecha_fin").toLocalDate(),
                    rs.getString("etapa"),
                    rs.getString("tipo_puntuacion"));
            }
        } else throw new Exception("Error al buscar Competición");

        ConexionBD.closeConexion(con);

        return competicion;
    }

    /** Método para cerrar una competición existente en la base de datos. Recibe un objeto Competicion como parámetro y actualiza su estado en la tabla de competiciones.
     * Si el proceso de actualización falla, se lanza una excepción indicando que hubo un error al cerrar la competición.
     * @param competicion El objeto Competicion que se desea cerrar en la base de datos.
     * @throws Exception Si ocurre un error al actualizar la competición en la base de datos.
     */
    public static void cerrarCompeticion(Competicion competicion) throws Exception{
            Connection con = ConexionBD.getConexion();
            String sql = "UPDATE competiciones SET etapa = ?, tipo_puntuacion = ?, fecha_inicio = ?, fecha_fin = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, competicion.getEtapa());
            ps.setString(2, competicion.getTipoPuntuacion());
            ps.setDate(3, java.sql.Date.valueOf(competicion.getFechaInicio()));
            ps.setDate(4, java.sql.Date.valueOf(competicion.getFechaFin()));

            int e = ps.executeUpdate();

            if (e == 0) throw new Exception();

            ConexionBD.closeConexion(con);
    }
}
