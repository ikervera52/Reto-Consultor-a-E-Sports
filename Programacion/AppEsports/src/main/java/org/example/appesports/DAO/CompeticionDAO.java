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
