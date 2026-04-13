package org.example.appesports.Vista;

import javafx.stage.Stage;


public class MenuPrincipalAdminController {

    public Stage stage;
    public MenuInicioSesionController controller;

    public void init (Stage stage, MenuInicioSesionController menu){
        this.controller = menu;
        this.stage = stage;
    }
}
