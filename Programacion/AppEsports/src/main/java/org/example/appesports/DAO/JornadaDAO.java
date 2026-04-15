package org.example.appesports.DAO;

import oracle.jdbc.proxy.annotation.Pre;
import org.example.appesports.Controlador.CompeticionController;
import org.example.appesports.Modelo.Jornada;
import org.example.appesports.Utilidades.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class JornadaDAO {

    public static void crearJornada(Jornada jornada) throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "INSERT INTO jornadas (num_jornada, fecha, id_competicion) VALUES (?,?,?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, jornada.getNumeroJornada());
        ps.setDate(2, java.sql.Date.valueOf(jornada.getFechaJornada()));
        ps.setInt(3, jornada.getCompeticion().getIdCompeticion());

        int e = ps.executeUpdate();

        if(e == 0) throw new Exception("Error al crear Jornada");

    }

    public static ArrayList<Jornada> listarJornadas() throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "SELECT * FROM jornadas";

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

        return jornadas;
    }

    public static Jornada jornadaPorId(int id) throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "SELECT * FROM jornadas WHERE id = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();



        Jornada jornada = null;
        if (rs.next()){
            jornada = new Jornada(
                    rs.getInt("id"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getInt("num_jornada")
            );
        }

        return jornada;

    }
}
