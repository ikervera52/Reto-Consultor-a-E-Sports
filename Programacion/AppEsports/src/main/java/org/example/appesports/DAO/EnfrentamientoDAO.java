package org.example.appesports.DAO;

import oracle.jdbc.proxy.annotation.Pre;
import org.example.appesports.Controlador.JornadaController;
import org.example.appesports.Modelo.Enfrentamiento;
import org.example.appesports.Utilidades.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.util.ArrayList;

public class EnfrentamientoDAO {

    public static void crearEnfrentamiento(LocalTime hora, int idJornada) throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "INSERT INTO enfrentamientos (hora_enfrentamiento, id_jornada) VALUES (?,?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setTime(1, java.sql.Time.valueOf(hora));
        ps.setInt(2, idJornada);

        int e = ps.executeUpdate();

        if (e == 0) throw new Exception("Error al crear el Enfrentamiento");

        ConexionBD.closeConexion(con);

    }

    public static ArrayList<Enfrentamiento> buscarPorJornada(int idJornada) throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "SELECT * FROM enfrentamientos";

        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        ArrayList<Enfrentamiento> enfrentamientos = new ArrayList<>();
        while (rs.next()){
            enfrentamientos.add(new Enfrentamiento(
                    rs.getInt("id"),
                    rs.getTime("hora_enfrentamiento").toLocalTime(),
                    JornadaController.jornadaPorId(rs.getInt("id_jornada"))
            ));
        }

        ConexionBD.closeConexion(con);

        return enfrentamientos;
    }

    public static Enfrentamiento buscarPorId(int id) throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "SELECT * FROM enfrentamientos WHERE id = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        Enfrentamiento enfrentamiento = null;
        if (rs.next()){
            enfrentamiento = new Enfrentamiento(
                    rs.getInt("id"),
                    rs.getTime("hora_enfrentamiento").toLocalTime(),
                    JornadaController.jornadaPorId(rs.getInt("id_jornada"))
            );
        }

        ConexionBD.closeConexion(con);

        return enfrentamiento;

    }
}
