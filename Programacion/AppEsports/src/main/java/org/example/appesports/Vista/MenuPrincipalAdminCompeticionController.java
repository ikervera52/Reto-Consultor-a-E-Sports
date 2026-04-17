package org.example.appesports.Vista;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.appesports.Controlador.EnfrentamientoController;
import org.example.appesports.Controlador.JornadaController;
import org.example.appesports.Controlador.ResultadoController;
import org.example.appesports.Controlador.UsuarioController;
import org.example.appesports.DAO.JornadaDAO;
import org.example.appesports.Modelo.Enfrentamiento;
import org.example.appesports.Modelo.Jornada;
import org.example.appesports.Modelo.Resultado;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

public class MenuPrincipalAdminCompeticionController {



    public Stage stage;
    public MenuInicioSesionController controller;
    public String username;

    @FXML
    public Label lbNombreBienvenida;
    public Label tfUltimaJornada;
    public Label tfSiguientePartido;
    public Label tfHora;
    public Label tfUltimoResultado;
    public Label tfResultadoPrincipal;
    public Label lbFechaJornada;

    @FXML
    public AnchorPane apLlenarPuntuaciones;
    public VBox vboxContenedorJornadas;



    public void init (Stage stage, MenuInicioSesionController menu, String username){
        this.controller = menu;
        this.stage = stage;
        this.username = username;

        lbNombreBienvenida.setText(username);

        actualizarMenuPrincipal();
    }

    @FXML
    public void onCerrarSesion(MouseEvent MouseEvent) {
        controller.show();
        stage.close();


    }

    private void actualizarMenuPrincipal(){
        try {

            ArrayList<Jornada> jornadas = JornadaDAO.listarJornadas();
            ArrayList<Jornada> jornadasPasadas = new ArrayList<>();
            LocalDate domingo = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

            for (Jornada jornada : jornadas) {
                if (jornada.getFechaJornada().isBefore(domingo)) {
                    jornadasPasadas.add(jornada);
                }
             }
            Jornada ultimaJornada = jornadasPasadas.getLast();

            tfUltimaJornada.setText(String.valueOf(ultimaJornada.getNumeroJornada()));
            lbFechaJornada.setText(String.valueOf(ultimaJornada.getFechaJornada()));

            ArrayList<Enfrentamiento> enfrentamientos = EnfrentamientoController.buscarPorJornada(ultimaJornada.getIdJornada());
            Enfrentamiento ultimoEnfrentamiento = null;
            for (Enfrentamiento enfrentamiento : enfrentamientos) {
                if (enfrentamiento.getHoraEnfrentamiento().isAfter(LocalTime.now())) {
                    ultimoEnfrentamiento = enfrentamiento;
                    break;
                }
            }

            ArrayList<Enfrentamiento> enfrentamientosPasados = new ArrayList<>();
            for (Enfrentamiento enfrentamiento : enfrentamientos) {
                if (enfrentamiento.getHoraEnfrentamiento().isBefore(LocalTime.now()) && (ultimaJornada.getFechaJornada().isBefore(LocalDate.now()) || ultimaJornada.getFechaJornada().isEqual(LocalDate.now()))) {
                    enfrentamientosPasados.add(enfrentamiento);
                }
            }

            ArrayList<Resultado> anteriorResultados = new ArrayList<>();
            if (!enfrentamientosPasados.isEmpty()) {
                anteriorResultados = ResultadoController.verPorEnfrentamiento(enfrentamientosPasados.getLast().getIdEnfrentamiento());
            }
            ArrayList<Resultado> proximosResultados = ResultadoController.verPorEnfrentamiento(ultimoEnfrentamiento.getIdEnfrentamiento());

            String proximoEquipos = proximosResultados.getFirst().getEquipo().getNombre() + " - " + proximosResultados.getLast().getEquipo().getNombre();
            tfSiguientePartido.setText(proximoEquipos);
            tfHora.setText(String.valueOf(ultimoEnfrentamiento.getHoraEnfrentamiento()));

            String anteriorEquipos = anteriorResultados.getFirst().getEquipo().getNombre() + " - " + anteriorResultados.getLast().getEquipo().getNombre();
            tfUltimoResultado.setText(anteriorEquipos);

            String anteriorResultado = anteriorResultados.getFirst().getResultado() + " - " + anteriorResultados.getLast().getResultado();
            tfResultadoPrincipal.setText(anteriorResultado);



        }catch (Exception e){
            System.out.println("pe");
        }
    }

    public void onVolverMenuPrincipal(ActionEvent actionEvent) {
        apLlenarPuntuaciones.setVisible(false);
    }

    public void onLlenarPuntuaciones(MouseEvent mouseEvent) {
        try {
            rellenarLlenarPuntuaciones();
            apLlenarPuntuaciones.setVisible(true);
        }catch (Exception e){
            System.out.println("pe");
        }
        apLlenarPuntuaciones.setVisible(true);
    }

    public void rellenarLlenarPuntuaciones() throws Exception {
        vboxContenedorJornadas.getChildren().clear();
        ArrayList<Jornada> jornadas = JornadaController.listarJornadas();
        for (Jornada jornada : jornadas) {
            if (jornada.getFechaJornada().isBefore(LocalDate.now()) || jornada.getFechaJornada().isEqual(LocalDate.now())) {
                vboxContenedorJornadas.getChildren().add(crearCartaJornada(jornada));
            }
        }
        if(vboxContenedorJornadas.getChildren().isEmpty()){
            Label sinJornadas = new Label("No hay jornadas disponibles para llenar puntuaciones.");
            sinJornadas.setStyle("-fx-font-size: 18px; -fx-text-fill: #555;");
            vboxContenedorJornadas.getChildren().add(sinJornadas);
        }
    }

    public Node crearCartaJornada(Jornada jornada) throws Exception {
        VBox cartaJornada = new VBox(15);
        cartaJornada.setAlignment(Pos.CENTER);
        cartaJornada.setPadding(new Insets(15));
        cartaJornada.setStyle("-fx-background-color: WHITE; -fx-background-radius: 20; -fx-effect:  dropshadow(three-pass-box, rgba(0, 0, 0, 0.2), 25, 0, 0, 12);");

        Label numeroJornada = new Label("Jornada " + jornada.getNumeroJornada());
        numeroJornada.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        cartaJornada.getChildren().add(numeroJornada);

        ArrayList<Enfrentamiento> enfrentamientos = EnfrentamientoController.buscarPorJornada(jornada.getIdJornada());
        for (int i = 0; i < enfrentamientos.size(); i += 2) {
            if (enfrentamientos.get(i).getHoraEnfrentamiento().isBefore(LocalTime.now())) {


                HBox fila = new HBox(30);
                fila.setAlignment(Pos.CENTER);

                Node enfrentamiento1 = crearCartaEnfrentamiento(enfrentamientos.get(i));
                fila.getChildren().add(enfrentamiento1);

                if (i + 1 < enfrentamientos.size()) {
                    Node enfrentamiento2 = crearCartaEnfrentamiento(enfrentamientos.get(i + 1));
                    fila.getChildren().add(enfrentamiento2);
                } else {
                    Region espacioVacio = new Region();
                    espacioVacio.setPrefWidth(400);
                    fila.getChildren().add(espacioVacio);
                }

                cartaJornada.getChildren().add(fila);
            }
        }
        return cartaJornada;
    }

    public Node crearCartaEnfrentamiento(Enfrentamiento enfrentamiento) throws Exception {
        ArrayList<Resultado> resultados = ResultadoController.verPorEnfrentamiento(enfrentamiento.getIdEnfrentamiento());
        HBox cartaEnfrentamiento = new HBox();
        cartaEnfrentamiento.setPadding(new Insets(10));
        cartaEnfrentamiento.setPrefWidth(400);
        cartaEnfrentamiento.setStyle("-fx-border-radius: 10; -fx-background-color: #005699; -fx-background-radius: 15; -fx-border-width: 8; -fx-border-color: #0086ED;");

        VBox cartaInterior = new VBox();
        cartaInterior.setAlignment(Pos.CENTER);

        Label equipos = new Label(resultados.get(0).getEquipo().getNombre() + " - " + resultados.get(1).getEquipo().getNombre());
        equipos.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: WHITE;");

        HBox puntuaciones = new HBox();
        puntuaciones.setAlignment(Pos.CENTER);

        TextField tfPuntuacionEquipo1 = new TextField();
        tfPuntuacionEquipo1.setAlignment(Pos.CENTER);
        tfPuntuacionEquipo1.setText(String.valueOf(resultados.getFirst().getResultado()));
        tfPuntuacionEquipo1.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: WHITE; -fx-font-size: 36px; -fx-font-weight: bold;");
        tfPuntuacionEquipo1.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        }));

        TextField tfPuntuacionEquipo2 = new TextField();
        tfPuntuacionEquipo2.setAlignment(Pos.CENTER);
        tfPuntuacionEquipo2.setText(String.valueOf(resultados.getLast().getResultado()));
        tfPuntuacionEquipo2.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: WHITE; -fx-font-size: 36px; -fx-font-weight: bold;");
        tfPuntuacionEquipo2.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        }));

        Label guion = new Label("-");
        guion.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: WHITE;");

        puntuaciones.getChildren().addAll(tfPuntuacionEquipo1, guion, tfPuntuacionEquipo2);

        Button btnGuardar = new Button("Guardar");
        btnGuardar.setStyle("-fx-background-color: #0086ED; -fx-text-fill: WHITE; -fx-font-size: 16px; -fx-font-weight: bold;");
        btnGuardar.setOnAction(actionEvent -> {
            if (!tfPuntuacionEquipo1.getText().isEmpty() && !tfPuntuacionEquipo2.getText().isEmpty()) {
                try {
                    resultados.getFirst().setResultado(Integer.parseInt(tfPuntuacionEquipo1.getText()));
                    resultados.getLast().setResultado(Integer.parseInt(tfPuntuacionEquipo2.getText()));
                    ResultadoController.actualizarResultado(resultados.getFirst());
                    ResultadoController.actualizarResultado(resultados.getLast());
                     actualizarMenuPrincipal();
                } catch (Exception e) {
                    System.out.println("Error al guardar resultados: " + e.getMessage());
                }
            }
        });

        cartaInterior.getChildren().addAll(equipos, puntuaciones, btnGuardar);
        cartaEnfrentamiento.getChildren().add(cartaInterior);

        return cartaEnfrentamiento;
    }
}
