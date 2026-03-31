package org.example.appesports.Vista;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MenuInicioSesionController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Hola!");
    }
}
