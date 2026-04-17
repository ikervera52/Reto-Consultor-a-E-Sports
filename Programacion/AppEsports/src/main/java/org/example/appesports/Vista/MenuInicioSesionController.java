package org.example.appesports.Vista;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.appesports.Controlador.CompeticionController;
import org.example.appesports.Controlador.UsuarioController;
import org.example.appesports.MainApplication;
import org.example.appesports.Utilidades.ValidarDatos;


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

            String etapaCompeticion = CompeticionController.verEstadoCompeticion();

            if (tipoUsuario.equals("admin") && etapaCompeticion.equals("inscripcion")){
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("menu-principal-admin.fxml"));
                Parent root = fxmlLoader.load();

                Scene scene = new Scene(root, 1200, 800);
                Stage stage = new Stage();
                stage.setTitle("AppEsports");
                stage.setScene(scene);

                MenuPrincipalAdminController controller = fxmlLoader.getController();

                controller.init(stage, this, username);
                stage.show();

                tfUsuario.clear();
                pfContrasena.clear();

                this.stage.close();


            } else if(tipoUsuario.equals("admin") && etapaCompeticion.equals("competicion")){

                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("menu-principal-admin-competicion.fxml"));
                Parent root = fxmlLoader.load();

                Scene scene = new Scene(root, 1200, 800);
                Stage stage = new Stage();
                stage.setTitle("AppEsports");
                stage.setScene(scene);

                MenuPrincipalAdminCompeticionController controller = fxmlLoader.getController();

                controller.init(stage, this, username);
                stage.show();

                tfUsuario.clear();
                pfContrasena.clear();

                this.stage.close();


            }else if (tipoUsuario.equals("estandar") && etapaCompeticion.equals("inscripcion")){
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("menu-principal-estandar.fxml"));
                Parent root = fxmlLoader.load();

                Scene scene = new Scene(root, 1200, 800);
                Stage stage = new Stage();
                stage.setTitle("AppEsports");
                stage.setScene(scene);

                MenuPrincipalEstandarController controller = fxmlLoader.getController();

                controller.init(stage, this);
                stage.show();

                tfUsuario.clear();
                pfContrasena.clear();

                this.stage.close();

            } else if(tipoUsuario.equals("estandar") && etapaCompeticion.equals("competicion")){

            }


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void onSalir(){
        System.exit(0);
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void show(){
        stage.show();
    }

}
