package org.example.appesports.Utilidades;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/** Clase ValidarDatos que proporciona métodos estáticos para validar diferentes tipos de datos, como nombres de usuario, contraseñas, cadenas de texto y números.
 * Utiliza expresiones regulares para verificar que los datos cumplan con ciertos criterios de formato.
 */
public class ValidarDatos {

    /** Método para validar un nombre de usuario.
     * El método utiliza una expresión regular para asegurarse de que el nombre de usuario solo contenga letras mayúsculas, minúsculas y números.
     * @param username
     * @throws Exception
     */
    public static void validarUsername(String username) throws Exception{

        Matcher m = Pattern.compile("^[A-Za-z0-9]+$").matcher(username);
        if (!m.matches()){
            throw new Exception("Nombre de Usuario no valido");
        }
    }
    /** Método para validar una contraseña.
     * El método utiliza una expresión regular para asegurarse de que la contraseña solo contenga letras mayúsculas, minúsculas, números y guiones bajos.
     * @param contrasena
     * @throws Exception
     */
    public static void validarContrasena(String contrasena) throws Exception{

        Matcher m = Pattern.compile("^[A-Za-z0-9_]+$").matcher(contrasena);
        if (!m.matches()){
            throw new Exception("Contraseña no valida");
        }

    }
    /** Método para validar una cadena de texto.
     * El método utiliza una expresión regular para asegurarse de que la cadena comience con una letra mayúscula seguida de letras minúsculas o la letra "ñ".
     * @param nombre
     * @throws Exception
     */
    public static void validarString(String nombre) throws Exception{

        Matcher m = Pattern.compile("^[A-Z][a-zñ]+$").matcher(nombre);
        if (!m.matches()){
            throw new Exception("Datos no validos");
        }
    }
    /** Método para validar un número en formato de cadena.
     * El método utiliza una expresión regular para asegurarse de que la cadena solo contenga dígitos y puntos decimales, lo que es común en números de tipo double.
     * @param numero
     * @throws Exception
     */
    public static void validarDouble(String numero) throws Exception{

        Matcher m = Pattern.compile("^[0-9.]+$").matcher(numero);
        if (!m.matches()){
            throw new Exception("Sueldo no valido");
        }
    }

    /** Método para validar una fecha.
     * El método utiliza una expresión regular para asegurarse de que la fecha sea anterior a la fecha del sistema.
     * @param fecha
     * @throws Exception
     */

    public static void validarFecha(LocalDate fecha) throws Exception{

        if (fecha.isAfter(LocalDate.now())) throw new Exception("La fecha no puede ser posterior a hoy");
    }
}
