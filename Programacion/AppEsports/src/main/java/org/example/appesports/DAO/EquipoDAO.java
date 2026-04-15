package org.example.appesports.DAO;

import org.example.appesports.Controlador.JugadorController;
import org.example.appesports.Modelo.Equipo;
import org.example.appesports.Modelo.Jugador;
import org.example.appesports.Utilidades.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EquipoDAO {

    public static int contarEquipos(){
       int cantidad = 0;
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "SELECT COUNT(*) FROM equipos";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                cantidad = rs.getInt(1);
            }

            ConexionBD.closeConexion(con);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cantidad;
    }

    public static ArrayList<Equipo> verEquipos(){
        ArrayList<Equipo> equipos = new ArrayList<>();
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "SELECT * FROM equipos";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();



            while (rs.next()){
                ArrayList<Jugador> jugadoresEquipo = JugadorController.verJugadoresPorEquipo(rs.getInt("id"));
                equipos.add(
                        new Equipo(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getDate("fecha_fundacion").toLocalDate(),
                                jugadoresEquipo
                        )
                );
            }
            ConexionBD.closeConexion(con);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return equipos;
    }

    public static Equipo equipoPorId(int id){
        Equipo equipo = null;
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "SELECt * FROM equipos WHERE id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                equipo = new Equipo(
                        rs.getInt("id"),
                        rs.getString("nombre")
                        );
            }

            ConexionBD.closeConexion(con);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return equipo;
    }

    public static void insertarEquipo(Equipo equipo){
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "INSERT INTO equipos (nombre, fecha_fundacion) VALUES (?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, equipo.getNombre());
            ps.setDate(2, java.sql.Date.valueOf(equipo.getFechaFundacion()));
            ps.executeUpdate();
            ConexionBD.closeConexion(con);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
