package org.example.appesports.Utilidades;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidarDatos {

    public static void validarUsername(String username) throws Exception{

        Matcher m = Pattern.compile("^[A-Za-z0-9]+$").matcher(username);
        if (!m.matches()){
            throw new Exception("Nombre de Usuario no valido");
        }
    }

    public static void validarContrasena(String contrasena) throws Exception{

        Matcher m = Pattern.compile("^[A-Za-z0-9_]+$").matcher(contrasena);
        if (!m.matches()){
            throw new Exception("Contraseña no valida");
        }

    }

    public static void validarString(String nombre) throws Exception{

        Matcher m = Pattern.compile("^[A-Z][a-zñ]+$").matcher(nombre);
        if (!m.matches()){
            throw new Exception("Datos no validos");
        }
    }

    public static void validarDouble(String numero) throws Exception{

        Matcher m = Pattern.compile("^[0-9.]+$").matcher(numero);
        if (!m.matches()){
            throw new Exception("Sueldo no valido");
        }
    }



}
