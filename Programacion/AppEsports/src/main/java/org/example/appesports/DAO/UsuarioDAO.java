package org.example.appesports.DAO;

import org.example.appesports.Modelo.Admin;
import org.example.appesports.Modelo.Estandar;
import org.example.appesports.Modelo.Usuario;
import org.example.appesports.Utilidades.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public static Usuario validarUsuario(String username, String contrasena) throws Exception{
        Usuario usuario = null;
        try {
            Connection con = ConexionBD.getConexion();

            String sql = "SELECT * FROM usuarios WHERE nombre = ? AND contrasena = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,contrasena);
            ResultSet rs = ps.executeQuery();

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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }
}
