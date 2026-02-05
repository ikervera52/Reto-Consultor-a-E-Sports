package Clases;

import java.util.ArrayList;

public class Gestion {

    public enum TipoUsuario {
        ADMINISTRADOR,
        USUARIO
    }




    protected int id_gestion;
    protected String nombre_usuario;
    protected String contrasena;
    protected TipoUsuario tipo_usuario;

    public Gestion() {
    }

    public Gestion(int id_gestion, String nombre_usuario, String contrasena, TipoUsuario tipo_usuario) {
        this.id_gestion = id_gestion;
        this.nombre_usuario = nombre_usuario;
        this.contrasena = contrasena;
        this.tipo_usuario = tipo_usuario;
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

    public TipoUsuario getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(TipoUsuario tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }
}
