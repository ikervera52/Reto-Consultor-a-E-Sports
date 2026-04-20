package org.example.appesports.Utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/** Clase ConexionBD que proporciona métodos para establecer y cerrar conexiones a una base de datos Oracle.
 * Esta clase utiliza JDBC para conectarse a la base de datos y manejar las conexiones de manera segura.
 */
public class ConexionBD {
    /** Método para obtener una conexión a la base de datos.
     * Este método establece una conexión utilizando los parámetros de URL, nombre de usuario y contraseña especificados.
     * Si la conexión es exitosa, devuelve un objeto Connection; en caso de error, imprime el mensaje de error y devuelve null.
     * @return Connection La conexión establecida a la base de datos o null si ocurre un error.
     */
    public static Connection getConexion (){
        try {
            String url = "jdbc:oracle:thin:@//172.20.225.114:1521/ORCLPDB1";
            String username = "eqdaw02";
            String password = "eqdaw02";

            return DriverManager.getConnection(url,username,password);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    /** Método para cerrar una conexión a la base de datos.
     * Este método toma un objeto Connection como parámetro y cierra la conexión de manera segura, manejando cualquier excepción que pueda ocurrir durante el proceso.
     * @param con La conexión a la base de datos que se desea cerrar.
     */
    public static void closeConexion(Connection con){
        try {
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
