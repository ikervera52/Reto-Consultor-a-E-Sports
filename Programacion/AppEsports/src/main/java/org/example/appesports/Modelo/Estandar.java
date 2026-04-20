package org.example.appesports.Modelo;

/** Clase que representa un usuario estándar en el sistema de gestión de esports.
 * Hereda de la clase Usuario y no tiene permisos especiales para gestionar competiciones, equipos o jugadores.
 */
public class Estandar extends Usuario {
    public Estandar() {
    }

    public Estandar(int idUsuario, String nombreUsuario, String contrasena) {
        super(idUsuario, nombreUsuario, contrasena);
    }

    public Estandar(String nombreUsuario, String contrasena) {
        super(nombreUsuario, contrasena);
    }
}
