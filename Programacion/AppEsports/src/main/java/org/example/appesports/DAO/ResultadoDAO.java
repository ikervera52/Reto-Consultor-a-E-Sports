package org.example.appesports.DAO;

import oracle.jdbc.proxy.annotation.Pre;
import org.example.appesports.Controlador.EnfrentamientoController;
import org.example.appesports.Controlador.EquipoController;
import org.example.appesports.Modelo.Resultado;
import org.example.appesports.Utilidades.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/** Clase ResultadoDAO que se encarga de realizar las operaciones CRUD (Crear, Leer, Actualizar) en la base de datos para los objetos de tipo Resultado.
 * Esta clase utiliza la clase ConexionBD para establecer la conexión con la base de datos y ejecutar las consultas SQL necesarias.
 * Además, incluye métodos para crear un nuevo resultado, ver los resultados por enfrentamiento y actualizar un resultado existente.
 */
public class ResultadoDAO {
    /** Método para crear un nuevo resultado en la base de datos. Recibe un objeto Resultado como parámetro y lo inserta en la tabla de resultados.
     * Si el proceso de inserción falla, se lanza una excepción indicando que hubo un error al crear el resultado.
     * @param resultado El objeto Resultado que se desea crear en la base de datos.
     * @throws Exception Si ocurre un error al insertar el resultado en la base de datos.
     */
    public static void crearResultado(Resultado resultado) throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "INSERT INTO resultados (id_equipo, id_enfrentamiento) VALUES (?,?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,resultado.getEquipo().getIdEquipo());
        ps.setInt(2, resultado.getEnfrentamiento().getIdEnfrentamiento());

        int e = ps.executeUpdate();

        if (e == 0) throw new Exception("Error al crear el Resultado");

        ConexionBD.closeConexion(con);

    }
    /** Método para ver los resultados por enfrentamiento. Recibe el ID del enfrentamiento como parámetro y devuelve una lista de objetos Resultado asociados a ese enfrentamiento.
     * Si ocurre un error al acceder a la base de datos, se lanza una excepción indicando que hubo un error al obtener los resultados.
     * @param idEnfrentamiento El ID del enfrentamiento para el cual se desean obtener los resultados.
     * @return Una lista de objetos Resultado asociados al enfrentamiento especificado.
     * @throws Exception Si ocurre un error al acceder a la base de datos o si no se encuentran resultados para el enfrentamiento especificado.
     */
    public static ArrayList<Resultado> verPorEnfrentamiento(int idEnfrentamiento) throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "SELECT * FROM resultados WHERE id_enfrentamiento = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idEnfrentamiento);

        ResultSet rs = ps.executeQuery();

        ArrayList<Resultado> resultados = new ArrayList<>();
        while (rs.next()){
            resultados.add( new Resultado(
                    EquipoController.equipoPorId(rs.getInt("id_equipo")),
                    EnfrentamientoController.buscarPorId(rs.getInt("id_enfrentamiento")),
                    rs.getInt("puntuacion")
                    )
            );

        }

        return resultados;
    }
    /** Método para actualizar un resultado existente en la base de datos. Recibe un objeto Resultado como parámetro y actualiza la puntuación asociada a ese resultado en la tabla de resultados.
     * Si ocurre un error al acceder a la base de datos, se lanza una excepción indicando que hubo un error al actualizar el resultado.
     * @param resultado El objeto Resultado que se desea actualizar en la base de datos.
     * @throws Exception Si ocurre un error al acceder a la base de datos o si no se encuentra el resultado especificado para actualizar.
     */
    public static void actualizarResultado(Resultado resultado) throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "UPDATE resultados SET puntuacion = ? WHERE id_enfrentamiento = ? AND id_equipo = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,resultado.getResultado());
        ps.setInt(2, resultado.getEnfrentamiento().getIdEnfrentamiento());
        ps.setInt(3, resultado.getEquipo().getIdEquipo());
        ps.executeUpdate();
        ConexionBD.closeConexion(con);
    }
}
