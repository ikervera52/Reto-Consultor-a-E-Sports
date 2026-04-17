package org.example.appesports.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import oracle.jdbc.proxy.annotation.Pre;
import org.example.appesports.Modelo.Admin;
import org.example.appesports.Modelo.Estandar;
import org.example.appesports.Modelo.Usuario;
import org.example.appesports.Utilidades.ConexionBD;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public static Usuario validarUsuario(String username, String contrasena) throws Exception{
        Usuario usuario;

        Connection con = ConexionBD.getConexion();

        String sql = "SELECT * FROM usuarios WHERE nombre = ? AND contrasena = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,username);
        ps.setString(2,contrasena);
        ResultSet rs = ps.executeQuery();

        usuario = crearUsuario(rs);

        ConexionBD.closeConexion(con);

        return usuario;
    }

    public static void anadirUsuario(Usuario usuario) throws Exception{

        Connection con = ConexionBD.getConexion();
        String sql = "INSERT INTO usuarios (nombre, contrasena, tipo_usuario) VALUES (?,?,?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, usuario.getNombreUsuario());
        ps.setString(2, usuario.getContrasena());

        if (usuario instanceof Admin){
            ps.setString(3, "admin");
        } else
            ps.setString(3, "estandar");

        int e = ps.executeUpdate();

        if (e == 0)
            throw new Exception("Error al añadir Usuario");

        ConexionBD.closeConexion(con);

    }

    public static void borrarUsuario(String username) throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "DELETE FROM usuarios WHERE nombre = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, username);
        int e = ps.executeUpdate();

        if (e == 0) throw new Exception("Error al eliminar el Usuario");

        ConexionBD.closeConexion(con);
    }

    public static Usuario buscarPorNombreUsuario(String username) throws Exception{

        Usuario usuario = null;

        Connection con = ConexionBD.getConexion();

        String sql = "SELECT * FROM usuarios WHERE nombre = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,username);
        ResultSet rs = ps.executeQuery();

        usuario = crearUsuario(rs);

        ConexionBD.closeConexion(con);

        return usuario;
    }

    public static void editarUsuario(String username, Usuario usuario) throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "UPDATE usuarios SET nombre = ?, contrasena = ?, tipo_usuario = ? WHERE nombre = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, usuario.getNombreUsuario());
        ps.setString(2, usuario.getContrasena());
        if (usuario instanceof Admin){
            ps.setString(3, "admin");
        } else
            ps.setString(3, "estandar");

        ps.setString(4, username);

        int e = ps.executeUpdate();

        if (e == 0) throw new Exception("Error al editar el Usuario");

        ConexionBD.closeConexion(con);
    }

    public static Usuario crearUsuario (ResultSet rs)throws Exception{
        Usuario usuario = null;

        if (rs.next()){
            String tipo = rs.getString("tipo_usuario");

            if (tipo.equals("estandar")){
                usuario = new Estandar(rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("contrasena")
                );

            } else if (tipo.equals("admin")){
                usuario =new Admin (rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("contrasena"));
            }
        } else throw new Exception("El usuario no existe");

        return usuario;
    }

    public static ObservableList<Usuario> obtenerUsuarios() throws Exception{
        ObservableList<Usuario> usuarios = FXCollections.observableArrayList();

        Connection con = ConexionBD.getConexion();
        String sql = "SELECT * FROM usuarios";

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            String tipo = rs.getString("tipo_usuario");

            if (tipo.equals("estandar")){
                usuarios.add(new Estandar(rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("contrasena")
                ));

            } else if (tipo.equals("admin")){
                usuarios.add(new Admin (rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("contrasena")));
            }
        }

        ConexionBD.closeConexion(con);

        return usuarios;
     }


}
