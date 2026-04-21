package org.example.appesports.Vista;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.appesports.Controlador.CompeticionController;
import org.example.appesports.Controlador.UsuarioController;
import org.example.appesports.MainApplication;
import org.example.appesports.Utilidades.ValidarDatos;

/** Controlador para el menú de inicio de sesión.
 * Permite a los usuarios ingresar sus credenciales y acceder a las diferentes vistas según su tipo de usuario y la etapa de la competición.
 */
public class MenuInicioSesionController {
    /** Referencia al escenario (ventana) actual, utilizada para controlar la visibilidad y el cierre de la ventana de inicio de sesión.
     */
    private Stage stage;

    @FXML
    private Button bEntrar;

    @FXML
    private PasswordField pfContrasena;

    @FXML
    private TextField tfUsuario;
    /** Método que se ejecuta al hacer clic en el botón "Entrar".
     * Valida las credenciales ingresadas por el usuario y redirige a la vista correspondiente según el tipo de usuario y la etapa de la competición.
     * Si las credenciales son inválidas, muestra un mensaje de error.
     * @param event El evento de acción generado al hacer clic en el botón "Entrar".
     */
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
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("menu-principal-estandar-competicion.fxml"));
                Parent root = fxmlLoader.load();

                Scene scene = new Scene(root, 1200, 800);
                Stage stage = new Stage();
                stage.setTitle("AppEsports");
                stage.setScene(scene);

                MenuPrincipalEstandarCompeticionController controller = fxmlLoader.getController();

                controller.init(stage, this, username);
                stage.show();

                tfUsuario.clear();
                pfContrasena.clear();

                this.stage.close();

            }


        }catch (Exception e){
            mostarMensaje("Error" ,"el usuario o la contraseña no son validos", Alert.AlertType.ERROR);
        }
    }
    /** Método para mostrar un mensaje de alerta al usuario.
     * Crea una ventana emergente con el título, mensaje y tipo de alerta especificados.
     * @param titulo El título de la ventana de alerta.
     * @param mensaje El mensaje que se mostrará en la ventana de alerta.
     * @param alerta El tipo de alerta (por ejemplo, ERROR, INFORMATION, etc.) que determinará el icono y estilo de la ventana de alerta.
     */
    private void mostarMensaje(String titulo, String mensaje, Alert.AlertType alerta){
        Alert alert = new Alert(alerta);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.show();
    }
    /** Método que se ejecuta al hacer clic en el botón "Salir".
     * Cierra la aplicación de manera segura.
     */
    @FXML
    public void onSalir(){
        System.exit(0);
    }
    /** Método para establecer la etapa de la competición.
     * Este método puede ser utilizado para actualizar la etapa de la competición en el controlador, lo que podría afectar la lógica de redirección a diferentes vistas según la etapa actual.
     */
    public void setStage(Stage stage){
        this.stage = stage;
    }
    /** Método para mostrar la ventana de inicio de sesión.
     * Este método se puede llamar para hacer visible la ventana de inicio de sesión después de haberla configurado y preparado para su uso.
     */
    public void show(){
        stage.show();
    }

}
