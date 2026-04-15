package org.example.appesports.Controlador;

import org.example.appesports.DAO.UsuarioDAO;
import org.example.appesports.Modelo.Admin;
import org.example.appesports.Modelo.Estandar;
import org.example.appesports.Modelo.Usuario;

public class UsuarioController {

    public static String validarUsuario(String username, String contrasena) throws Exception{
        Usuario usuario = UsuarioDAO.validarUsuario(username, contrasena);
        if (usuario instanceof Admin){
            return "admin";
         } else if (usuario instanceof Estandar){
            return "estandar";
        } else return null;
    }

    public static void anadirUsuario (String username, String contrasena, String tipoUsuario) throws Exception{
        if (tipoUsuario.equals("admin")){
            UsuarioDAO.anadirUsuario(new Admin (username, contrasena));
        } else
            UsuarioDAO.anadirUsuario(new Estandar(username, contrasena));
    }

    public static void borrarUsuario(String username) throws Exception{
        UsuarioDAO.borrarUsuario(username);
    }

    public static Usuario buscarPorNombreUsusario(String username) throws Exception{
        return UsuarioDAO.buscarPorNombreUsuario(username);
    }

    public static void editarUsuario(String username, String usernameNuevo, String contrasena, String tipoUsuario) throws Exception{
        Usuario usuario;
        if (tipoUsuario.equals("admin")){
            usuario = new Admin(usernameNuevo, contrasena);
        } else usuario = new Estandar(usernameNuevo, contrasena);

        UsuarioDAO.editarUsuario(username, usuario);
    }
}
