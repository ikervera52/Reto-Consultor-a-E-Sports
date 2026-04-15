package org.example.appesports.Vista;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.appesports.Controlador.EquipoController;
import org.example.appesports.Controlador.JugadorController;
import javafx.scene.layout.AnchorPane;
import org.example.appesports.Modelo.Equipo;
import org.example.appesports.Utilidades.ValidarDatos;

import java.time.LocalDate;
import java.util.ArrayList;


public class MenuPrincipalAdminController {

        private String username;

        @FXML
        public Label lbCantEquipos;

        @FXML
        public Label lbCantJugadores;

        @FXML
        public Label lbNombreBienvenida;

        @FXML
        public Button bGestionarUsuarios;

        @FXML
        public Button bGestionarEquipos;

        @FXML
        public Button bGestionarJugadores;

        @FXML
        public Button bVolverMenuPrincipal;

        @FXML
        public Button bVolverGestionarJugadores;

        @FXML
        public AnchorPane apGestionarUsuariosPrincipal;

        @FXML
        public AnchorPane apGestionarJugadoresPrincipal;

        @FXML
        public AnchorPane apGestionarJugadoresAnadir;

        @FXML
        public AnchorPane apMenuPrincipal;

        // Datos para añadir Jugador
        @FXML
        public TextField tfNombreJugador;

        @FXML
        public TextField tfApellidoJugador;

        @FXML
        public TextField tfNacionalidad;

        @FXML
        public DatePicker dpFechaNacimiento;

        @FXML
        public TextField tfNickname;

        @FXML
        public ComboBox<String> cbRol;

        @FXML
        public TextField tfSueldo;

        @FXML
        public ComboBox<String> cbSeleccionEquipoParaJugador;

        @FXML
        public AnchorPane apGestionarEquiposPrincipal;

        @FXML
        public AnchorPane apGestionarEquiposAnadir;

        @FXML
        public TextField tfNombreEquipo;

        @FXML
        public DatePicker dpFechaFundacion;


    public Stage stage;
    public MenuInicioSesionController controller;

    public void init (Stage stage, MenuInicioSesionController menu, String username){
        this.controller = menu;
        this.stage = stage;
        this.username = username;

        actualizarDatosPanelPrincipal();
    }

    @FXML
    public void onGestionarJugadores(ActionEvent event) {

        apGestionarJugadoresPrincipal.setVisible(true);
        apGestionarJugadoresAnadir.setVisible(false);
        apGestionarEquiposPrincipal.setVisible(false);
        apGestionarEquiposAnadir.setVisible(false);

        bGestionarJugadores.setTextFill(Color.WHITE);
        bGestionarJugadores.setStyle("-fx-background-color: #0086ed; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        bGestionarUsuarios.setTextFill(Color.BLACK);
        bGestionarUsuarios.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        bGestionarEquipos.setTextFill(Color.BLACK);
        bGestionarEquipos.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");

    }

    @FXML
    public void onAnadirJugador(MouseEvent mouseEvent) {
        apGestionarJugadoresAnadir.setVisible(true);

        // Rellenar el apartado de ROL
        cbRol.getItems().addAll("Suport", "AWPer", "IGL", "Lurker", "Rifler", "Entry-flager");

        //Rellenar el apartado de EQUIPOS
        cbSeleccionEquipoParaJugador.getItems().clear();
        ArrayList<String> nombreEquipos = EquipoController.rellenarComboEquipo();
        for (String nombre : nombreEquipos){
            cbSeleccionEquipoParaJugador.getItems().add(nombre);
        }


    }

    @FXML
    public void onAnadirDatosJugador(ActionEvent event){


        try {
            ValidarDatos.validarString(tfNombreJugador.getText());
            ValidarDatos.validarString(tfApellidoJugador.getText());
            ValidarDatos.validarString(tfNacionalidad.getText());
            ValidarDatos.validarUsername(tfNickname.getText());
            ValidarDatos.validarDouble(tfSueldo.getText());

            String nombre = tfNombreJugador.getText();
            String apellido = tfApellidoJugador.getText();
            String nacionalidad = tfNacionalidad.getText();
            LocalDate fechaNacimiento = dpFechaNacimiento.getValue();
            String nickname = tfNickname.getText();
            String rol = cbRol.getValue();
            double sueldo = Double.parseDouble(tfSueldo.getText());
            String nombreEquipo = cbSeleccionEquipoParaJugador.getValue();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onBorrarDatosJugador(ActionEvent event){

    }

    @FXML
    public void onVolverMenuPrincipal(ActionEvent event){

        actualizarDatosPanelPrincipal();
        apGestionarJugadoresPrincipal.setVisible(false);
        apGestionarEquiposPrincipal.setVisible(false);

        bGestionarJugadores.setTextFill(Color.BLACK);
        bGestionarJugadores.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        bGestionarUsuarios.setTextFill(Color.BLACK);
        bGestionarUsuarios.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        bGestionarEquipos.setTextFill(Color.BLACK);
        bGestionarEquipos.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
    }

    @FXML
    public void onVolverGestionarJugadores(ActionEvent event){
        apGestionarJugadoresAnadir.setVisible(false);
    }

    @FXML
    public void onGestionarUsuarios(ActionEvent event){
        bGestionarUsuarios.setTextFill(Color.WHITE);
        bGestionarUsuarios.setStyle("-fx-background-color: #0086ed; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        bGestionarJugadores.setTextFill(Color.BLACK);
        bGestionarJugadores.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        bGestionarEquipos.setTextFill(Color.BLACK);
        bGestionarEquipos.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
    }

    @FXML
    public void onCerrarSesion(javafx.event.ActionEvent actionEvent) {
        controller.show();
        stage.close();
    }

    private void actualizarDatosPanelPrincipal(){
        lbNombreBienvenida.setText(username);
        lbCantJugadores.setText(JugadorController.contarJugadores());
        lbCantEquipos.setText(EquipoController.contarEquipos());
    }

    public void onGestionarEquipos(ActionEvent actionEvent) {
        apGestionarEquiposPrincipal.setVisible(true);
        apGestionarJugadoresAnadir.setVisible(false);
        apGestionarJugadoresPrincipal.setVisible(false);

        bGestionarEquipos.setTextFill(Color.WHITE);
        bGestionarEquipos.setStyle("-fx-background-color: #0086ed; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        bGestionarUsuarios.setTextFill(Color.BLACK);
        bGestionarUsuarios.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        bGestionarJugadores.setTextFill(Color.BLACK);
        bGestionarJugadores.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");

    }

    public void onAnadirEquipo(MouseEvent mouseEvent) {
        apGestionarEquiposAnadir.setVisible(true);

    }

    public void onVolverGestionarEquipos(ActionEvent actionEvent) {
        apGestionarEquiposAnadir.setVisible(false);
    }

    public void onAnadirDatosEquipo(ActionEvent actionEvent) {
        try {
            ValidarDatos.validarString(tfNombreEquipo.getText());

            String nombre = tfNombreEquipo.getText();
            LocalDate fechaFundacion = dpFechaFundacion.getValue();

            EquipoController.insertarEquipo(nombre, fechaFundacion);

            tfNombreEquipo.clear();
            dpFechaFundacion.setValue(null);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
