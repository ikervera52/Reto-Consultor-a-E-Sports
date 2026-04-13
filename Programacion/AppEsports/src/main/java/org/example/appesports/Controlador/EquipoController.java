package org.example.appesports.Controlador;

import org.example.appesports.DAO.EquipoDAO;

public class EquipoController {

    public static String contarEquipos(){
        int cantidad = EquipoDAO.contarEquipos();
        return String.valueOf(cantidad);
    }
}
