package org.example.appesports.DAO;

import oracle.jdbc.proxy.annotation.Pre;
import org.example.appesports.Controlador.EquipoController;
import org.example.appesports.Modelo.Equipo;
import org.example.appesports.Modelo.Jugador;
import org.example.appesports.Utilidades.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public static ArrayList<Jugador> verJugadoresPorEquipo(int idEquipo){
        ArrayList<Jugador> jugadores = new ArrayList<>();
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "SELECT * FROM jugadores WHERE id_equipo = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idEquipo);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                jugadores.add(new Jugador(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("nacionalidad"),
                        rs.getDate("fecha_nacimiento").toLocalDate(),
                        rs.getString("nickname"),
                        rs.getString("rol"),
                        rs.getDouble("sueldo"),
                        EquipoController.equipoPorId(idEquipo)
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return jugadores;
    }
}
