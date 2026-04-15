package org.example.appesports.DAO;

import org.example.appesports.Modelo.Resultado;
import org.example.appesports.Utilidades.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ResultadoDAO {

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
}
