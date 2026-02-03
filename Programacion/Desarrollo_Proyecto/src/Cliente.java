public class Cliente extends Gestion{
    public Cliente(int ID_gestion, String usuarios, String tipo_usuario, int contrasena) {
        super(ID_gestion, usuarios, tipo_usuario, contrasena);
    }

    public String ver_informe_equipos() {
        return "Mostrando informe de equipos...";
    }

    public String ver_informe_resultados() {
        return "Mostrando informe de resultados...";
    }
}
