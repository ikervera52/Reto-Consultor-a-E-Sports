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

            ConexionBD.closeConexion(con);


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

            ConexionBD.closeConexion(con);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return jugadores;
    }

    public static void anadirJugador(Jugador jugador) throws Exception{
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "INSERT INTO jugadores (nombre, apellido, nacionalidad, fecha_nacimiento," +
                    "                            nickname, rol, sueldo, id_equipo) VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, jugador.getNombre());
            ps.setString(2, jugador.getApellido());
            ps.setString(3, jugador.getNacionalidad());
            ps.setDate(4, java.sql.Date.valueOf(jugador.getFechaNacimiento()));
            ps.setString(5, jugador.getNickname());
            ps.setString(6, jugador.getRol());
            ps.setDouble(7, jugador.getSueldo());
            ps.setInt(8, jugador.getEquipo().getIdEquipo());

            int e = ps.executeUpdate();

            if (e == 0){
                throw new Exception("Error al ingresar el jugador");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void borrarJugador(String nickname)throws Exception{

        Connection con = ConexionBD.getConexion();
        String sql = "DELETE FROM jugadores WHERE nickname = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nickname);

        int e = ps.executeUpdate();

        if (e == 0){
            throw new Exception("No se ha podido eliminar el jugador");
        }

    }

    public static Jugador buscarPorNickname(String nickname) throws Exception{

        Jugador jugador = null;
        Connection con = ConexionBD.getConexion();
        String sql = "SELECT * FROM jugadores WHERE nickname = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nickname);

        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            jugador = new Jugador(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("nacionalidad"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getString("nickname"),
                    rs.getString("rol"),
                    rs.getDouble("sueldo"),
                    EquipoController.equipoPorId(rs.getInt("id_equipo")));

        } else throw new Exception("Error al buscar el Jugador");

        return jugador;
    }

    public static void editarJugador(String nickname, Jugador jugador) throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "UPDATE jugadores SET nombre = ?, apellido = ?, nacionalidad = ?, fecha_nacimiento = ?, nickname = ?," +
                "rol = ?, sueldo = ?, id_equipo = ? WHERE nickname = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, jugador.getNombre());
        ps.setString(2, jugador.getApellido());
        ps.setString(3,jugador.getNacionalidad());
        ps.setDate(4, java.sql.Date.valueOf(jugador.getFechaNacimiento()));
        ps.setString(5, jugador.getNickname());
        ps.setDouble(6, jugador.getSueldo());
        ps.setInt(7, jugador.getEquipo().getIdEquipo());

        int e = ps.executeUpdate();

        if (e == 0){
            throw new Exception("Error al actualizar el Jugador");
        }
    }
}
