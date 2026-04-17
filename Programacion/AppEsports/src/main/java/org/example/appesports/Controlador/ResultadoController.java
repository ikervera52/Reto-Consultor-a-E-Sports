package org.example.appesports.Controlador;

import org.example.appesports.DAO.ResultadoDAO;
import org.example.appesports.Modelo.Enfrentamiento;
import org.example.appesports.Modelo.Resultado;

import java.util.ArrayList;

public class ResultadoController {

    public static void crearResultado(int idEquipo, int idEnfrentamiento) throws Exception{
        Resultado resultado = new Resultado(EquipoController.equipoPorId(idEquipo), EnfrentamientoController.buscarPorId(idEnfrentamiento));
        ResultadoDAO.crearResultado(resultado);
    }

    public static ArrayList<Resultado> verPorEnfrentamiento(int idEnfrentamiento) throws Exception{
        return ResultadoDAO.verPorEnfrentamiento(idEnfrentamiento);
    }

    public static void actualizarResultado(Resultado resultado) throws Exception{
        ResultadoDAO.actualizarResultado(resultado);
    }
}
