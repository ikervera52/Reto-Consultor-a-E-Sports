package org.example.appesports.Vista;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.appesports.Controlador.*;
import org.example.appesports.Modelo.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class MenuPrincipalEstandarCompeticionController {

    public Label lbNombreBienvenida;
    @FXML
    public HBox onCerrarSesion;

    @FXML
    public VBox vbEquipos;
    public VBox vbResultadosUltimaJornada;


    public Stage stage;
    public MenuInicioSesionController controller;

    public void init (Stage stage, MenuInicioSesionController menu, String username) throws Exception {
        this.controller = menu;
        this.stage = stage;
        
        lbNombreBienvenida.setText(username);
        actualizarInfo();
    }

    private Node crearCartasInfoEquipo(Equipo equipo) {
        VBox carta = new VBox();
        carta.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0)");
        carta.setPadding(new Insets(15));
        carta.setPrefWidth(300);


        Label nombre = new Label();
        nombre.setText(equipo.getNombre());
        nombre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        nombre.setTextFill(Color.web("#0089ed"));


        Label fecha = new Label();
        fecha.setText("Fecha de Fundación: " + equipo.getFechaFundacion().toString());

        carta.getChildren().addAll(nombre, fecha);
        return carta;
    }

    public Node CrearCartaResultado(Enfrentamiento enfrentamiento) throws Exception {
        ArrayList<Resultado> resultados = ResultadoController.verPorEnfrentamiento(enfrentamiento.getIdEnfrentamiento());

        VBox carta = new VBox();
        carta.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0)");
        carta.setPadding(new Insets(15));
        carta.setPrefWidth(300);
        carta.setAlignment(Pos.CENTER);

        Label equipos = new Label(resultados.get(0).getEquipo().getNombre() + " - " + resultados.get(1).getEquipo().getNombre());
        equipos.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        equipos.setTextFill(Color.web("#0089ed"));

        Label puntuaciones = new Label(resultados.getFirst().getResultado() + " - " + resultados.getLast().getResultado());
        puntuaciones.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        carta.getChildren().addAll(equipos, puntuaciones);

        return carta;
    }




    //Funcion para reccorrer los equipos y crear la carta por cada equipo
    public void actualizarInfo() throws Exception {
        try {
            vbEquipos.getChildren().clear();
            ArrayList<Equipo> equipos = EquipoController.listarEquipos();
            for (Equipo equipo : equipos) {
                vbEquipos.getChildren().add(crearCartasInfoEquipo(equipo));
            }

            vbResultadosUltimaJornada.getChildren().clear();
            ArrayList<Jornada> jornadas = JornadaController.listarJornadas();
            Jornada ultimaJornadaJugada = null;
            for (Jornada jornada : jornadas) {
                if (jornada.getFechaJornada().isBefore(LocalDate.now())) {
                    ultimaJornadaJugada = jornada;
                }
            }
            if (ultimaJornadaJugada == null) {
                Label sinResultados = new Label("No hay resultados para mostrar.");
                sinResultados.setStyle("-fx-font-size: 18px; -fx-text-fill: #555;");
                vbResultadosUltimaJornada.getChildren().add(sinResultados);
            }else {
                ArrayList<Enfrentamiento> enfrentamientosUltimaJornada = EnfrentamientoController.buscarPorJornada(ultimaJornadaJugada.getIdJornada());
                for (Enfrentamiento enfrentamiento : enfrentamientosUltimaJornada) {
                    vbResultadosUltimaJornada.getChildren().add(CrearCartaResultado(enfrentamiento));
                }
            }
        } catch (Exception e) {
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void onActualizarInfo(ActionEvent actionEvent) throws Exception {
        actualizarInfo();
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
