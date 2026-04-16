package org.example.appesports.Vista;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.appesports.Controlador.EnfrentamientoController;
import org.example.appesports.Controlador.JornadaController;
import org.example.appesports.Controlador.ResultadoController;
import org.example.appesports.Controlador.UsuarioController;
import org.example.appesports.Modelo.Enfrentamiento;
import org.example.appesports.Modelo.Jornada;
import org.example.appesports.Modelo.Resultado;

import java.awt.*;
import java.util.ArrayList;

public class MenuPrincipalAdminCompeticionController {



    public Stage stage;
    public MenuInicioSesionController controller;
    public String username;

    @FXML
    public Label lbNombreBienvenida;
    public Label tfUltimaJornada;
    public Label tfSiguientePartido;
    public Label tfHora;
    public Label tfUltimoResultado;
    public Label tfResultadoPrincipal;


    public void init (Stage stage, MenuInicioSesionController menu, String username){
        this.controller = menu;
        this.stage = stage;
        this.username = username;

        lbNombreBienvenida.setText(username);

        actualizarMenuPrincipal();
    }

    @FXML
    public void onCerrarSesion(MouseEvent MouseEvent) {
        controller.show();
        stage.close();


    }

    private void actualizarMenuPrincipal(){
        try {

            Jornada ultimaJornada = JornadaController.listarJornadas().getLast();

            tfUltimaJornada.setText(String.valueOf(ultimaJornada.getNumeroJornada()));

            ArrayList<Enfrentamiento> enfrentamientos = EnfrentamientoController.buscarPorJornada(ultimaJornada.getIdJornada());

            Enfrentamiento ultimoEnfrentamiento = enfrentamientos.getLast();
            if (enfrentamientos.size() > 1){
                enfrentamientos.remove(ultimoEnfrentamiento);
            }
            Enfrentamiento anateriorEnfrentamiento = enfrentamientos.getLast();

            ArrayList<Resultado> ultimosResultados = ResultadoController.verPorEnfrentamiento(ultimoEnfrentamiento.getIdEnfrentamiento());

            String ultimoResutaldo = ultimosResultados.get(0).getResultado() + " - " + ultimosResultados.get(1).getResultado();
            String ultimoEquipos = ultimosResultados.get(0).getEquipo().getNombre() + " - " + ultimosResultados.get(1).getEquipo().getNombre();

            String anteriorEquipos = ultimosResultados.get(0).getEquipo().getNombre() + " - " + ultimosResultados.get(1).getEquipo().getNombre();

            tfUltimoResultado.setText(ultimoEquipos);
            tfResultadoPrincipal.setText(ultimoResutaldo);

            tfSiguientePartido.setText(anteriorEquipos);
            tfHora.setText(anateriorEnfrentamiento.getHoraEnfrentamiento().toString());

        }catch (Exception e){
            System.out.println("pe");
        }
    }
}
