package org.example.appesports.DAO;

import org.example.appesports.Controlador.EnfrentamientoController;
import org.example.appesports.Controlador.EquipoController;
import org.example.appesports.Modelo.Resultado;
import org.example.appesports.Utilidades.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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

        ConexionBD.closeConexion(con);

        return resultados;
    }

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
