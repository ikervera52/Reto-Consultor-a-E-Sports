package org.example.appesports.Modelo;

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
