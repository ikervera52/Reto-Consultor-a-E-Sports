package org.example.appesports.Controlador;

import org.example.appesports.DAO.EquipoDAO;
import org.example.appesports.Modelo.Equipo;

import java.util.ArrayList;

public class EquipoController {

    public static String contarEquipos(){
        int cantidad = EquipoDAO.contarEquipos();
        return String.valueOf(cantidad);
    }

    public static ArrayList<String> rellenarComboEquipo(){
        ArrayList<String> nombreEquipo = new ArrayList<>();
        for (Equipo equipo : EquipoDAO.verEquipos()){
            nombreEquipo.add(equipo.getNombre());
        }
        return nombreEquipo;

    }

    public static Equipo equipoPorId(int id){
        return EquipoDAO.equipoPorId(id);
    }

    public static Equipo equipoPorNombre(String nombre){
        return EquipoDAO.equipoPorNombre(nombre);
    }
}
