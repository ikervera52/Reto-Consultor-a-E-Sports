package org.example.appesports.DAO;

import org.example.appesports.Modelo.Competicion;
import org.example.appesports.Utilidades.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CompeticionDAO {

    public static Competicion buscarCompeticion() throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "SELECT * FROM competición";

        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        Competicion competicion = null;

        if (rs.next()){
            competicion = new Competicion(
                    rs.getInt("id"),
                    rs.getString("tipo_etapa")
            );
        } else throw new Exception("Error al buscar Competición");

        return competicion;
    }

    public static void cerrarCompeticion(Competicion competicion) throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "UPDATE competicion SET etapa = ? WHERE = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, competicion.getEtapa().toString());
        ps.setInt(2, competicion.getIdCompeticion());

        int e = ps.executeUpdate();

        if (e == 0) throw new Exception("Error al cerrar la Competición");
    }
}
