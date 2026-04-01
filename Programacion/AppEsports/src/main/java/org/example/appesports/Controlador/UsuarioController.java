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
}
