package org.example.appesports.DAO;

import oracle.jdbc.proxy.annotation.Pre;
import org.example.appesports.Modelo.Jornada;
import org.example.appesports.Utilidades.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class JornadaDAO {

    public static void crearJornada(Jornada jornada) throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "INSERT INTO jornadas (num_jornada, fecha_jornada, id_competicion) VALUES (?,?,?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, jornada.getNumeroJornada());
        ps.setDate(2, java.sql.Date.valueOf(jornada.getFechaJornada()));
        ps.setInt(3, jornada.getCompeticion().getIdCompeticion());

        int e = ps.executeUpdate();

        if(e == 0) throw new Exception("Error al crear Jornada");

    }
}
