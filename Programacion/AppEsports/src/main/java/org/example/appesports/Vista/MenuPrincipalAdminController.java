package org.example.appesports.Vista;

import javafx.stage.Stage;


public class MenuPrincipalAdminController {

    private Stage stage;
    private MenuInicioSesionController controller;

    public void init (Stage stage, MenuInicioSesionController menu){
        this.controller = menu;
        this.stage = stage;
    }
}
