package org.example.appesports.DAO;

import org.example.appesports.Utilidades.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JugadorDAO {

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


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cantidad;
    }
}
