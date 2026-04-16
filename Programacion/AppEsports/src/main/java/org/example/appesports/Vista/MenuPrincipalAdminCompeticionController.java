package org.example.appesports.Vista;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.appesports.ApiExterna.GrogAPI;
import org.example.appesports.Controlador.*;
import org.example.appesports.Modelo.*;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.example.appesports.Controlador.JornadaController.listarJornadas;

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
    public AnchorPane apCalculadorIA;
    public AnchorPane apMenuPrincipal;
    public Label laRespuestaIA;


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


@FXML
public void onTerminarCompeticion (MouseEvent MouseEvent){

    try {
        Optional<ButtonType> opcion = mostrarMensajeEsperar("Confirmación para Terminar la Competición");

        if (opcion.isPresent() && opcion.get() == ButtonType.OK){
            CompeticionController.terminarCompeticion();
        }
    }
    catch (Exception e){
        mostarMensaje("Error al terminar la Competición", e.getMessage(), Alert.AlertType.ERROR);
    }

}

private void actualizarMenuPrincipal() {
        try {

            Jornada ultimaJornada = listarJornadas().getLast();

            tfUltimaJornada.setText(String.valueOf(ultimaJornada.getNumeroJornada()));

            ArrayList<Enfrentamiento> enfrentamientos = EnfrentamientoController.buscarPorJornada(ultimaJornada.getIdJornada());

            Enfrentamiento ultimoEnfrentamiento = enfrentamientos.getLast();
            if (enfrentamientos.size() > 1) {
                enfrentamientos.remove(ultimoEnfrentamiento);
            }
            Enfrentamiento anateriorEnfrentamiento = enfrentamientos.getLast();

            ArrayList<Resultado> ultimosResultados = ResultadoController.verPorEnfrentamiento(ultimoEnfrentamiento.getIdEnfrentamiento());

            String ultimoResutaldo = ultimosResultados.get(0).getResultado() + " - " + ultimosResultados.get(1).getResultado();
            String ultimoEquipos = ultimosResultados.get(0).getEquipo().getNombre() + " - " + ultimosResultados.get(1).getEquipo().getNombre();

            String anteriorEquipos = ultimosResultados.get(0).getEquipo().getNombre() + " - " + ultimosResultados.get(1).getEquipo().getNombre();

            tfUltimoResultado.setText(ultimoEquipos);
            tfResultadoPrincipal.setText(ultimoResutaldo);

            tfSiguientePartido.setText(anteriorEquipos);
            tfHora.setText(anateriorEnfrentamiento.getHoraEnfrentamiento().toString());

        } catch (Exception e) {
            System.out.println("pe");
        }
    }
    @FXML
    public void onWinCalculator(MouseEvent MouseEvent){
        apCalculadorIA.setVisible(true);

    }

    public void onConsultarIA(ActionEvent actionEvent) {

        try {

            Jornada primeraJornada = JornadaController.listarJornadas().getFirst();
            Enfrentamiento primerPartido = EnfrentamientoController.buscarPorJornada(primeraJornada.getIdJornada()).getFirst();

            LocalDateTime momentoPartido = LocalDateTime.of(primeraJornada.getFechaJornada(), primerPartido.getHoraEnfrentamiento());

            /*if (momentoPartido.isAfter(LocalDateTime.now())) {
                throw new Exception("No se ha jugado ningún partido todavía, intentalo más tarde.");
            }*/

            StringBuilder prompt = new StringBuilder();
            ArrayList<Equipo> equipos = EquipoController.listarEquipos();

            Map<String, Integer> puntosPorEquipo = new HashMap<>();
            for (Equipo e : equipos) {
                puntosPorEquipo.put(e.getNombre(), 0);
            }

            for (Jornada jornada : JornadaController.listarJornadas()) {
                //if (jornada.getFechaJornada().isBefore(LocalDate.now())) {

                for (Enfrentamiento enf : EnfrentamientoController.buscarPorJornada(jornada.getIdJornada())) {
                    //if (enf.getHoraEnfrentamiento().isBefore(LocalTime.now())) {

                    for (Resultado puntuacion : ResultadoController.verPorEnfrentamiento(enf.getIdEnfrentamiento())) {
                        String nombreEq = puntuacion.getEquipo().getNombre();
                        int puntosActuales = puntosPorEquipo.get(nombreEq);

                        puntosPorEquipo.put(nombreEq, puntosActuales + puntuacion.getResultado());
                    }
                    //}
                }
                //}
            }

            puntosPorEquipo.forEach((nombre, puntos) -> {
                if (!prompt.isEmpty()) prompt.append(", ");
                prompt.append(nombre).append(" - ").append(puntos);
            });

            laRespuestaIA.setText(GrogAPI.preguntarALaIA(prompt.toString()));


        } catch (Exception e){
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private Optional<ButtonType> mostrarMensajeEsperar(String texto){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setContentText(texto);

        return alert.showAndWait();
    }

    private void mostarMensaje(String titulo, String mensaje, Alert.AlertType alerta){
        Alert alert = new Alert(alerta);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.show();
    }

    public void onVolverMenuPrincipal(ActionEvent actionEvent) {
        laRespuestaIA.setText(null);
        apCalculadorIA.setVisible(false);
    }
}
