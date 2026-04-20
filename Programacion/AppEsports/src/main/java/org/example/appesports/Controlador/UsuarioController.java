package org.example.appesports.Controlador;

import org.example.appesports.DAO.UsuarioDAO;
import org.example.appesports.Modelo.Admin;
import org.example.appesports.Modelo.Estandar;
import org.example.appesports.Modelo.Usuario;

/** Controlador para gestionar las operaciones relacionadas con los usuarios, como validación, creación, eliminación y edición de usuarios.
 */
public class UsuarioController {
    /** Valida las credenciales de un usuario y devuelve su tipo (admin o estandar) si son correctas, o null si no lo son.
     * @param username El nombre de usuario a validar.
     * @param contrasena La contraseña a validar.
     * @return "admin" si el usuario es un administrador, "estandar" si es un usuario estándar, o null si las credenciales son incorrectas.
     * @throws Exception Si ocurre un error durante la validación.
     */
    public static String validarUsuario(String username, String contrasena) throws Exception{
        Usuario usuario = UsuarioDAO.validarUsuario(username, contrasena);
        if (usuario instanceof Admin){
            return "admin";
         } else if (usuario instanceof Estandar){
            return "estandar";
        } else return null;
    }
    /** Agrega un nuevo usuario al sistema con el nombre de usuario, contraseña y tipo de usuario especificados.
     * @param username El nombre de usuario del nuevo usuario.
     * @param contrasena La contraseña del nuevo usuario.
     * @param tipoUsuario El tipo de usuario ("admin" o "estandar") del nuevo usuario.
     * @throws Exception Si ocurre un error durante la adición del usuario.
     */
    public static void anadirUsuario (String username, String contrasena, String tipoUsuario) throws Exception{
        if (tipoUsuario.equals("admin")){
            UsuarioDAO.anadirUsuario(new Admin (username, contrasena));
        } else
            UsuarioDAO.anadirUsuario(new Estandar(username, contrasena));
    }
    /** Elimina un usuario del sistema basado en su nombre de usuario.
     * @param username El nombre de usuario del usuario a eliminar.
     * @throws Exception Si ocurre un error durante la eliminación del usuario.
     */
    public static void borrarUsuario(String username) throws Exception{
        UsuarioDAO.borrarUsuario(username);
    }
    /** Busca un usuario en el sistema por su nombre de usuario y devuelve el objeto Usuario correspondiente.
     * @param username El nombre de usuario del usuario a buscar.
     * @return El objeto Usuario correspondiente al nombre de usuario proporcionado.
     * @throws Exception Si ocurre un error durante la búsqueda del usuario.
     */
    public static Usuario buscarPorNombreUsusario(String username) throws Exception{
        return UsuarioDAO.buscarPorNombreUsuario(username);
    }
    /** Edita la información de un usuario existente en el sistema, actualizando su nombre de usuario, contraseña y tipo de usuario según los parámetros proporcionados.
     * @param username El nombre de usuario del usuario a editar.
     * @param usernameNuevo El nuevo nombre de usuario para el usuario editado.
     * @param contrasena La nueva contraseña para el usuario editado.
     * @param tipoUsuario El nuevo tipo de usuario ("admin" o "estandar") para el usuario editado.
     * @throws Exception Si ocurre un error durante la edición del usuario.
     */
    public static void editarUsuario(String username, String usernameNuevo, String contrasena, String tipoUsuario) throws Exception{
        Usuario usuario;
        if (tipoUsuario.equals("admin")){
            usuario = new Admin(usernameNuevo, contrasena);
        } else usuario = new Estandar(usernameNuevo, contrasena);

        UsuarioDAO.editarUsuario(username, usuario);
    }
}
