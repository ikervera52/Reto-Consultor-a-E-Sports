package org.example.appesports.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.appesports.Modelo.Admin;
import org.example.appesports.Modelo.Estandar;
import org.example.appesports.Modelo.Usuario;
import org.example.appesports.Utilidades.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/** Clase UsuarioDAO que se encarga de realizar las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) en la base de datos para los objetos de tipo Usuario.
 * Esta clase utiliza la clase ConexionBD para establecer la conexión con la base de datos y ejecutar las consultas SQL necesarias.
 * Además, incluye métodos para validar usuarios, añadir nuevos usuarios, borrar usuarios existentes, buscar usuarios por su nombre de usuario y editar usuarios.
 * También cuenta con un método para obtener una lista observable de todos los usuarios almacenados en la base de datos.
 */
public class UsuarioDAO {
    /** Método para validar un usuario en la base de datos. Recibe el nombre de usuario y la contraseña como parámetros, y devuelve un objeto Usuario si las credenciales son correctas.
     * Si las credenciales no son válidas, se lanza una excepción indicando que el usuario no existe.
     * @param username El nombre de usuario a validar.
     * @param contrasena La contraseña del usuario a validar.
     * @return Un objeto Usuario si las credenciales son correctas.
     * @throws Exception Si las credenciales no son válidas o si ocurre un error al acceder a la base de datos.
     */
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
    /** Método para añadir un nuevo usuario a la base de datos. Recibe un objeto Usuario como parámetro y lo inserta en la tabla de usuarios.
     * Si el proceso de inserción falla, se lanza una excepción indicando que hubo un error al añadir el usuario.
     * @param usuario El objeto Usuario que se desea añadir a la base de datos.
     * @throws Exception Si ocurre un error al insertar el usuario en la base de datos.
     */
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
    /** Método para borrar un usuario de la base de datos. Recibe el nombre de usuario como parámetro y elimina el registro correspondiente en la tabla de usuarios.
     * Si el proceso de eliminación falla, se lanza una excepción indicando que hubo un error al eliminar el usuario.
     * @param username El nombre de usuario del usuario que se desea eliminar.
     * @throws Exception Si ocurre un error al eliminar el usuario de la base de datos.
     */
    public static void borrarUsuario(String username) throws Exception{
        Connection con = ConexionBD.getConexion();
        String sql = "DELETE FROM usuarios WHERE nombre = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, username);
        int e = ps.executeUpdate();

        if (e == 0) throw new Exception("Error al eliminar el Usuario");

        ConexionBD.closeConexion(con);
    }
    /** Método para buscar un usuario en la base de datos por su nombre de usuario. Recibe el nombre de usuario como parámetro y devuelve un objeto Usuario si se encuentra un registro coincidente.
     * Si no se encuentra ningún usuario con el nombre de usuario proporcionado, se lanza una excepción indicando que el usuario no existe.
     * @param username El nombre de usuario del usuario que se desea buscar.
     * @return Un objeto Usuario si se encuentra un registro coincidente.
     * @throws Exception Si no se encuentra ningún usuario con el nombre de usuario proporcionado o si ocurre un error al acceder a la base de datos.
     */
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
    /** Método para editar un usuario existente en la base de datos. Recibe el nombre de usuario del usuario que se desea editar y un objeto Usuario con los nuevos datos.
     * Actualiza el registro correspondiente en la tabla de usuarios con los nuevos datos proporcionados.
     * Si el proceso de actualización falla, se lanza una excepción indicando que hubo un error al editar el usuario.
     * @param username El nombre de usuario del usuario que se desea editar.
     * @param usuario Un objeto Usuario con los nuevos datos del usuario.
     * @throws Exception Si ocurre un error al actualizar el usuario en la base de datos.
     */
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
    /** Método privado para crear un objeto Usuario a partir de un ResultSet obtenido de una consulta SQL. Este método se utiliza internamente para convertir los resultados de las consultas en objetos Usuario.
     * Recibe un ResultSet como parámetro y devuelve un objeto Usuario si se encuentra un registro válido en el ResultSet. Si no se encuentra ningún registro, se lanza una excepción indicando que el usuario no existe.
     * @param rs El ResultSet obtenido de una consulta SQL que contiene los datos del usuario.
     * @return Un objeto Usuario si se encuentra un registro válido en el ResultSet.
     * @throws Exception Si no se encuentra ningún registro en el ResultSet o si ocurre un error al procesar los datos.
     */
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

    /** Metodo para obtener todos los usuarios de la base de datos
     * Este método se encarga de recuperar todos los registros de la tabla de usuarios en la base de datos y convertirlos en una lista observable de objetos Usuario.
     * @return
     * @throws Exception
     */
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
