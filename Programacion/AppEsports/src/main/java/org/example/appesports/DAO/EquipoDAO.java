package org.example.appesports.DAO;

import oracle.jdbc.OracleType;
import org.example.appesports.Controlador.JugadorController;
import org.example.appesports.Modelo.Equipo;
import org.example.appesports.Modelo.EquipoInforme;
import org.example.appesports.Modelo.Jugador;
import org.example.appesports.Utilidades.ConexionBD;

import java.sql.*;
import java.util.ArrayList;

public class EquipoDAO {

    public static int contarEquipos() throws Exception{
       int cantidad = 0;

        Connection con = ConexionBD.getConexion();
        String sql = "SELECT COUNT(*) FROM equipos";

        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            cantidad = rs.getInt(1);
        }

        ConexionBD.closeConexion(con);


        return cantidad;
    }

    public static ArrayList<Equipo> verEquipos() throws Exception{
        ArrayList<Equipo> equipos = new ArrayList<>();
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "SELECT * FROM equipos";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();



            while (rs.next()){
                ArrayList<Jugador> jugadoresEquipo = JugadorController.verJugadoresPorEquipo();
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

    public static Equipo equipoPorNombre(String nombre) throws Exception{
        Equipo equipo = null;
            Connection con = ConexionBD.getConexion();
            String sql = "SELECt * FROM equipos WHERE nombre = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,nombre);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                equipo = new Equipo(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDate("fecha_fundacion").toLocalDate()
                );
            }else throw new Exception("No se ha encontrado el equipo con el nombre: " + nombre);

            ConexionBD.closeConexion(con);

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

    public static void editarEquipo(String nombreV, Equipo equipo){
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "UPDATE equipos SET nombre = ?, fecha_fundacion = ? WHERE nombre = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, equipo.getNombre());
            ps.setDate(2, java.sql.Date.valueOf(equipo.getFechaFundacion()));
            ps.setString(3, nombreV);
            ps.executeUpdate();
            ConexionBD.closeConexion(con);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void borrarEquipo(String nombre){
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "DELETE FROM equipos WHERE nombre = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.executeUpdate();
            ConexionBD.closeConexion(con);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<EquipoInforme> verEquiposInforme() throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "{call estadisticas_equipos(?)}";

        CallableStatement cs = con.prepareCall(sql);
        cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

        cs.execute();

        ResultSet rs = (ResultSet) cs.getObject(1);

        ArrayList<EquipoInforme> equipoInforme = new ArrayList<>();
        while (rs.next()){
            equipoInforme.add(new EquipoInforme(
                    rs.getString("nombre"),
                    rs.getDate("fecha_fundacion").toLocalDate(),
                    rs.getInt("cantidad_jugadores"),
                    rs.getInt("sueldo_maximo"),
                    rs.getInt("sueldo_minimo"),
                    rs.getInt("sueldo_medio")
            ));
        }

        return equipoInforme;
    }
}
