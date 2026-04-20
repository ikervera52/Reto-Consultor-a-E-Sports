package org.example.appesports.Vista;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/** * Controlador para el menú principal estándar de la aplicación.
 * Permite al usuario cerrar sesión y regresar al menú de inicio de sesión.
 */

public class MenuPrincipalEstandarController {

    public Stage stage;
    public MenuInicioSesionController controller;

    public void init (Stage stage, MenuInicioSesionController menu){
        this.controller = menu;
        this.stage = stage;
    }

    /** Método que se ejecuta cuando el usuario hace clic en la opción de cerrar sesión.
     * Este método muestra el menú de inicio de sesión y cierra la ventana actual del menú principal estándar.
     * @param MouseEvent
     */
    @FXML
    public void onCerrarSesion(MouseEvent MouseEvent) {
        controller.show();
        stage.close();
    }


}
