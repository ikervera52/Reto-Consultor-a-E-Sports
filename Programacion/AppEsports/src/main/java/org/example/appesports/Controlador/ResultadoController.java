package org.example.appesports.Controlador;

import org.example.appesports.DAO.ResultadoDAO;
import org.example.appesports.Modelo.Resultado;

public class ResultadoController {

    public static void crearResultado(int idEquipo, int idEnfrentamiento) throws Exception{
        Resultado resultado = new Resultado(EquipoController.equipoPorId(idEquipo), EnfrentamientoController.buscarPorId(idEnfrentamiento));
        ResultadoDAO.crearResultado(resultado);
    }
}
