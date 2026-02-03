public class Administrador extends Gestion{
    public Administrador(int ID_gestion, String usuarios, String tipo_usuario, int contrasena) {
        super(ID_gestion, usuarios, tipo_usuario, contrasena);
    }

public String realizar_CRUD_de_tablas() {
    return "Realizando operaciones CRUD en las tablas...";
}

public String cerrar_etapa_inscripcion() {
    return "Cerrando etapa de inscripción...";
}

public String generar_calendarios() {
    return "Generando calendarios de partidos...";
}

public String introducir_resultados() {
    return "Introduciendo resultados de los partidos...";
}

public String ver_informes() {
    return "Mostrando informes detallados...";
}
}
