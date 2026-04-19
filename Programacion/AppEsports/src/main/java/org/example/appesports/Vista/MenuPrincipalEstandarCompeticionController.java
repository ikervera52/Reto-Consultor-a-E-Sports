package org.example.appesports.Vista;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.appesports.Controlador.EquipoController;
import org.example.appesports.Controlador.JugadorController;
import org.example.appesports.Modelo.Equipo;
import org.example.appesports.Modelo.EquipoInforme;

import java.util.ArrayList;

public class MenuPrincipalEstandarCompeticionController {

    public Label lbNombreBienvenida;
    @FXML
    private HBox onCerrarSesion;

    @FXML
    private VBox vbEquipos;


    public Stage stage;
    public MenuInicioSesionController controller;

    public void init (Stage stage, MenuInicioSesionController menu, String username){
        this.controller = menu;
        this.stage = stage;
        
        lbNombreBienvenida.setText(username);
        actualizarInfo();
    }

    private Node crearCartasInfoEquipo(Equipo equipo) {
        VBox carta = new VBox();
        carta.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0)");
        carta.setPadding(new Insets(15));
        carta.setPrefWidth(320);


        Label nombre = new Label();
        nombre.setText(equipo.getNombre());
        nombre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        nombre.setTextFill(Color.web("#0089ed"));


        Label fecha = new Label();
        fecha.setText("Fecha de Fundación: " + equipo.getFechaFundacion().toString());

        carta.getChildren().addAll(nombre, fecha);
        return carta;
    }

    //Funcion para reccorrer los equipos y crear la carta por cada equipo
    public void actualizarInfo() {
        try {
            vbEquipos.getChildren().clear();
            ArrayList<Equipo> equipos = EquipoController.listarEquipos();
            for (int i = 0; i < equipos.size(); i ++) {

                HBox fila = new HBox(30);

                Node vistaJugador1 = crearCartasInfoEquipo(equipos.get(i));
                fila.getChildren().add(vistaJugador1);

                /*if (i + 1 < equipos.size()) {
                    Node vistaJugador2 = crearCartasEquipo(equipos.get(i + 1));
                    fila.getChildren().add(vistaJugador2);
                } else {
                    Region espacioVacio = new Region();
                    espacioVacio.setPrefWidth(400);
                    fila.getChildren().add(espacioVacio);
                }*/

                vbEquipos.getChildren().add(fila);
            }

            if (equipos.isEmpty()) {
                Label sinEquipos = new Label("No hay equipos para ver.");
                sinEquipos.setStyle("-fx-font-size: 18px; -fx-text-fill: #555;");
                vbEquipos.getChildren().add(sinEquipos);
            }

        } catch (Exception e) {
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }



    @FXML
    void onCerrarSesion(MouseEvent event) {
        controller.show();
        stage.close();

    }

    private void mostarMensaje(String titulo, String mensaje, Alert.AlertType alerta){
        Alert alert = new Alert(alerta);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.show();
    }

}
