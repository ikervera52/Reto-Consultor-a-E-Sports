package org.example.appesports.Modelo;

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
