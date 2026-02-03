package Clases;

import java.util.ArrayList;

public class Gestion {
    protected int id_gestion;
    protected String nombre_usuario;
    protected String contrasena;
    protected String tipo_usuario;

    public Gestion() {
    }

    public Gestion(int id_gestion, String tipo_usuario, String contrasena, String nombre_usuario) {
        this.id_gestion = id_gestion;
        this.tipo_usuario = tipo_usuario;
        this.contrasena = contrasena;
        this.nombre_usuario = nombre_usuario;
    }

    public int getId_gestion() {
        return id_gestion;
    }

    public void setId_gestion(int id_gestion) {
        this.id_gestion = id_gestion;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }
}
