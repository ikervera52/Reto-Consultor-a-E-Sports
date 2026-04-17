package org.example.appesports.Utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    public static Connection getConexion (){
        try {
            String url = "jdbc:oracle:thin:@//localhost:1521/XEPDB1";
            String username = "iker";
            String password = "iker";

            return DriverManager.getConnection(url,username,password);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void closeConexion(Connection con){
        try {
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
