package org.example.appesports.Vista;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.appesports.Controlador.UsuarioController;
import org.example.appesports.MainApplication;
import org.example.appesports.Utilidades.ValidarDatos;

import java.awt.*;

public class MenuInicioSesionController {

    private Stage stage;

    @FXML
    private Button bEntrar;

    @FXML
    private PasswordField pfContrasena;

    @FXML
    private TextField tfUsuario;

    @FXML
    void onEntrar(ActionEvent event) {

        String username = tfUsuario.getText();
        String contrasena = pfContrasena.getText();

        try {
            ValidarDatos.validarUsername(username);
            ValidarDatos.validarContrasena(contrasena);

            String tipoUsuario = UsuarioController.validarUsuario(username,contrasena);

            if (tipoUsuario.equals("admin")){
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("menu-principal-admin.fxml"));
                Parent root = fxmlLoader.load();

                Scene scene = new Scene(root, 1200, 800);
                Stage stage = new Stage();
                stage.setTitle("Menu");
                stage.setScene(scene);

                MenuPrincipalAdminController controller = fxmlLoader.getController();

                controller.init(stage, this, username);
                stage.show();

                tfUsuario.clear();
                pfContrasena.clear();

                this.stage.close();


            }else if (tipoUsuario.equals("estandar")){
                    String nose = null;
            }


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void show(){
        stage.show();
    }

}
