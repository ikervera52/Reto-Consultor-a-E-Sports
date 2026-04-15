package org.example.appesports.DAO;

import org.example.appesports.Modelo.Competicion;
import org.example.appesports.Utilidades.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CompeticionDAO {

    public static Competicion buscarCompeticion() throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "SELECT * FROM competiciones";

        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        Competicion competicion = null;

        if (rs.next()){
            competicion = new Competicion(
                    rs.getInt("id"),
                    rs.getString("etapa")
            );
        } else throw new Exception("Error al buscar Competición");

        return competicion;
    }

    public static void cerrarCompeticion(Competicion competicion) throws Exception{
            Connection con = ConexionBD.getConexion();
            String sql = "UPDATE competiciones SET etapa = ?, tipo_puntuacion = ?, fecha_inicio = ?, fecha_fin = ? WHERE id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, competicion.getEtapa());
            ps.setString(2, competicion.getTipoPuntuacion());
            ps.setDate(3, java.sql.Date.valueOf(competicion.getFechaInicio()));
            ps.setDate(4, java.sql.Date.valueOf(competicion.getFechaFin()));
            ps.setInt(5, competicion.getIdCompeticion());


            int e = ps.executeUpdate();

            if (e == 0) throw new Exception();
    }
}
