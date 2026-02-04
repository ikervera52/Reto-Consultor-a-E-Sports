package Clases;

public class Perfil {
    private Integer id;
    private String nombre;
    private String contraseña;

    public Perfil(Integer id, String nombre, String contraseña) {
        this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;
    }

    public String getNombre() { return nombre; }
    public String getContraseña() { return contraseña; }
}