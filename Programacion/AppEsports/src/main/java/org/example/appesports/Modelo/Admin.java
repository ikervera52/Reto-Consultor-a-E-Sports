package org.example.appesports.Modelo;
/** Clase que representa un usuario administrador en el sistema de gestión de esports.
 * Hereda de la clase Usuario y tiene permisos especiales para gestionar competiciones, equipos y jugadores.
 */
public class Admin extends Usuario {

    public Admin() {
    }

    public Admin(int idUsuario, String nombreUsuario, String contrasena) {
        super(idUsuario, nombreUsuario, contrasena);
    }

    public Admin(String nombreUsuario, String contrasena) {
        super(nombreUsuario, contrasena);
    }
}
