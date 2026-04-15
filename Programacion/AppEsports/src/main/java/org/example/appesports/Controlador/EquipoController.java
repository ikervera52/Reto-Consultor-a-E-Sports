package org.example.appesports.Controlador;

import javafx.collections.ObservableList;
import org.example.appesports.DAO.EquipoDAO;
import org.example.appesports.Modelo.Equipo;

import java.time.LocalDate;
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

    public static void insertarEquipo(String nombre, LocalDate fechaFundacion){
        Equipo equipo = new Equipo(nombre, fechaFundacion);
        EquipoDAO.insertarEquipo(equipo);
    }
}
