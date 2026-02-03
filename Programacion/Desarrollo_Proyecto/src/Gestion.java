public class Gestion {
    int ID_gestion;
    String usuarios;
    String tipo_usuario;
    int contrasena;

    public Gestion(int ID_gestion, String usuarios, String tipo_usuario, int contrasena) {
        this.ID_gestion = ID_gestion;
        this.usuarios = usuarios;
        this.tipo_usuario = tipo_usuario;
        this.contrasena = contrasena;
    }

    public String getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(String usuarios) {
        this.usuarios = usuarios;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public int getID_gestion() {
        return ID_gestion;
    }

    public void setID_gestion(int ID_gestion) {
        this.ID_gestion = ID_gestion;
    }

    public int getContrasena() {
        return contrasena;
    }

    public void setContrasena(int contrasena) {
        this.contrasena = contrasena;
    }
}
