package org.example.appesports.Vista;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import org.example.appesports.Controlador.EquipoController;
import org.example.appesports.Controlador.JugadorController;

import java.awt.event.ActionEvent;


public class MenuPrincipalAdminController {

        @FXML
        public Label lbCantEquipos;

        @FXML
        public Label lbCantJugadores;

        @FXML
        public Label lbNombreBienvenida;


    public Stage stage;
    public MenuInicioSesionController controller;

    public void init (Stage stage, MenuInicioSesionController menu, String username){
        this.controller = menu;
        this.stage = stage;

        lbNombreBienvenida.setText(username);
        lbCantJugadores.setText(JugadorController.contarJugadores());
        lbCantEquipos.setText(EquipoController.contarEquipos());
    }

    @FXML
    public void onCerrarSesion(javafx.event.ActionEvent actionEvent) {
        controller.show();
        stage.close();


    }
}
