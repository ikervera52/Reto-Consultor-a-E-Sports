package org.example.appesports.Vista;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.appesports.ApiExterna.GrogAPI;
import org.example.appesports.Controlador.*;
import org.example.appesports.Modelo.*;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.example.appesports.Controlador.EnfrentamientoController;
import org.example.appesports.Controlador.JornadaController;
import org.example.appesports.Controlador.ResultadoController;
import org.example.appesports.DAO.JornadaDAO;
import org.example.appesports.Modelo.Enfrentamiento;
import org.example.appesports.Modelo.Jornada;
import org.example.appesports.Modelo.Resultado;

import java.awt.*;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** * Controlador para el menú principal del administrador de la competición.
 * Gestiona la interacción del usuario con la interfaz gráfica y la lógica de negocio relacionada con la competición.
 * Permite al administrador ver información sobre las jornadas, partidos, resultados, llenar puntuaciones y consultar un calculador de IA.
 */
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

    /** Método para cerrar sesión del administrador.
     * Muestra el menú de inicio de sesión y cierra la ventana actual del menú principal.
     * @param MouseEvent
     */
    @FXML
    public void onCerrarSesion(MouseEvent MouseEvent) {
        controller.show();
        stage.close();
    }

    /** Método para terminar la competición.
     * Muestra un mensaje de confirmación al administrador y, si se confirma, termina la competición, muestra el menú de inicio de sesión y cierra la ventana actual del menú principal.
     * @param MouseEvent
     */
@FXML
public void onTerminarCompeticion (MouseEvent MouseEvent){

    try {
        Optional<ButtonType> opcion = mostrarMensajeEsperar("Confirmación para Terminar la Competición");

        if (opcion.isPresent() && opcion.get() == ButtonType.OK){
            CompeticionController.terminarCompeticion();
            controller.show();
            stage.close();

        }
    }
    catch (Exception e){
        mostarMensaje("Error al terminar la Competición", e.getMessage(), Alert.AlertType.ERROR);
    }

}
    /** Método para actualizar la información mostrada en el menú principal.
     * Obtiene la información de las jornadas, partidos y resultados para mostrar al administrador la última jornada, el siguiente partido y el último resultado.
     * Maneja excepciones que puedan ocurrir durante la obtención de datos y actualización de la interfaz.
     */
private void actualizarMenuPrincipal() {
        try {
            LocalDate siguienteDomingo = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
            ArrayList<Jornada> jornadas = JornadaController.listarJornadas();
            ArrayList<Jornada> jornadasPasadas = new ArrayList<>();
            for (Jornada jornada : jornadas) {
                if (jornada.getFechaJornada().isBefore(siguienteDomingo)) {
                    jornadasPasadas.add(jornada);
                }
            }

            tfUltimaJornada.setText(String.valueOf(jornadasPasadas.getLast().getNumeroJornada()));
            lbFechaJornada.setText(String.valueOf(jornadasPasadas.getLast().getFechaJornada()));

            ArrayList<Enfrentamiento> enfrentamientosUltimaJornada = EnfrentamientoController.buscarPorJornada(jornadasPasadas.getLast().getIdJornada());
            Enfrentamiento siguienteEnfrentamiento = new Enfrentamiento();
            for (Enfrentamiento enfrentamiento: enfrentamientosUltimaJornada) {
                if ( jornadasPasadas.getLast().getFechaJornada().isAfter(LocalDate.now()) || (jornadasPasadas.getLast().getFechaJornada().isEqual(LocalDate.now()) && enfrentamiento.getHoraEnfrentamiento().isBefore(LocalTime.now()))) {
                    siguienteEnfrentamiento =  enfrentamiento;
                    break;
                }
            }
            ArrayList<Resultado> resultadosSiguienteEnfrentamiento = ResultadoController.verPorEnfrentamiento(siguienteEnfrentamiento.getIdEnfrentamiento());
            tfSiguientePartido.setText(resultadosSiguienteEnfrentamiento.getFirst().getEquipo().getNombre() + " - " +  resultadosSiguienteEnfrentamiento.getLast().getEquipo().getNombre());
            tfHora.setText(siguienteEnfrentamiento.getHoraEnfrentamiento().toString());

            Enfrentamiento anteriorEnfrentamiento = new Enfrentamiento();
            for (Enfrentamiento enfrentamiento: enfrentamientosUltimaJornada) {
                if (jornadasPasadas.getLast().getFechaJornada().isBefore(LocalDate.now()) || (jornadasPasadas.getLast().getFechaJornada().isEqual(LocalDate.now()) && enfrentamiento.getHoraEnfrentamiento().isBefore(LocalTime.now()))) {
                    anteriorEnfrentamiento =  enfrentamiento;
                }
            }

            ArrayList<Resultado> resultadosAnteriorEnfrentamiento = ResultadoController.verPorEnfrentamiento(anteriorEnfrentamiento.getIdEnfrentamiento());
            tfUltimoResultado.setText(resultadosAnteriorEnfrentamiento.getFirst().getEquipo().getNombre() + " - " +  resultadosAnteriorEnfrentamiento.getLast().getEquipo().getNombre());
            tfResultadoPrincipal.setText(resultadosAnteriorEnfrentamiento.getFirst().getResultado() + " - " + resultadosAnteriorEnfrentamiento.getLast().getResultado());

        }catch (Exception e){
            System.out.println("pe");
        }
    }
    /** Método para mostrar la interfaz de llenar puntuaciones.
     * Llama al método para rellenar las jornadas disponibles para llenar puntuaciones y muestra el panel correspondiente.
     * Maneja excepciones que puedan ocurrir durante la obtención de datos y actualización de la interfaz.
     * @param mouseEvent
     */
    public void onLlenarPuntuaciones(MouseEvent mouseEvent) {
        try {
            rellenarLlenarPuntuaciones();
            apLlenarPuntuaciones.setVisible(true);
        }catch (Exception e){
            System.out.println("pe");
        }
        apLlenarPuntuaciones.setVisible(true);
    }
    /** Método para rellenar las jornadas disponibles para llenar puntuaciones.
     * Obtiene la lista de jornadas y muestra solo aquellas que ya han pasado o que son el día actual, permitiendo al administrador llenar las puntuaciones de los enfrentamientos correspondientes.
     * Si no hay jornadas disponibles, muestra un mensaje indicando que no hay jornadas para llenar puntuaciones.
     * Maneja excepciones que puedan ocurrir durante la obtención de datos y actualización de la interfaz.
     * @throws Exception
     */
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
    /** Método para crear una carta de jornada.
     * Crea una interfaz gráfica para mostrar la información de una jornada específica, incluyendo los enfrentamientos y sus resultados.
     * Solo muestra los enfrentamientos que ya han pasado, permitiendo al administrador llenar las puntuaciones correspondientes.
     * Maneja excepciones que puedan ocurrir durante la obtención de datos y actualización de la interfaz.
     * @param jornada
     * @return Node
     * @throws Exception
     */
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
    /** Método para crear una carta de enfrentamiento.
     * Crea una interfaz gráfica para mostrar la información de un enfrentamiento específico, incluyendo los equipos participantes y sus puntuaciones.
     * Permite al administrador modificar las puntuaciones y guardarlas, actualizando la información en la base de datos.
     * Maneja excepciones que puedan ocurrir durante la obtención de datos y actualización de la interfaz.
     * @param enfrentamiento
     * @return Node
     * @throws Exception
     */
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

    /** Método para mostrar la interfaz del calculador de IA.
     * Muestra el panel correspondiente al calculador de IA, permitiendo al administrador consultar información relacionada con la competición utilizando inteligencia artificial.
     * Maneja excepciones que puedan ocurrir durante la obtención de datos y actualización de la interfaz.
     * @param MouseEvent
     */
    @FXML
    public void onWinCalculator(MouseEvent MouseEvent){
        apCalculadorIA.setVisible(true);

    }
    /** Método para consultar la IA con la información de los equipos y sus puntos.
     * Obtiene la información de los equipos y sus puntos acumulados, construye un prompt para la IA y muestra la respuesta obtenida en la interfaz.
     * Maneja excepciones que puedan ocurrir durante la obtención de datos y actualización de la interfaz.
     */
    public void onConsultarIA(ActionEvent actionEvent) {

        try {

            StringBuilder prompt = new StringBuilder();
            ArrayList<Equipo> equipos = EquipoController.listarEquipos();

            Map<String, Integer> puntosPorEquipo = new HashMap<>();
            for (Equipo e : equipos) {
                puntosPorEquipo.put(e.getNombre(), 0);
            }

            for (Jornada jornada : JornadaController.listarJornadas()) {

                for (Enfrentamiento enf : EnfrentamientoController.buscarPorJornada(jornada.getIdJornada())) {

                    for (Resultado puntuacion : ResultadoController.verPorEnfrentamiento(enf.getIdEnfrentamiento())) {
                        String nombreEq = puntuacion.getEquipo().getNombre();
                        int puntosActuales = puntosPorEquipo.get(nombreEq);

                        puntosPorEquipo.put(nombreEq, puntosActuales + puntuacion.getResultado());
                    }

                }

            }

            puntosPorEquipo.forEach((nombre, puntos) -> {
                if (!prompt.isEmpty()) prompt.append(", ");
                prompt.append(nombre).append(" - ").append(puntos);
            });

            String respuesta = GrogAPI.preguntarALaIA(prompt.toString());
            laRespuestaIA.setText(respuesta);


        } catch (Exception e){
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /** Método para mostrar un mensaje de confirmación al usuario.
     * Crea una alerta de tipo confirmación con el texto proporcionado y espera la respuesta del usuario, devolviendo la opción seleccionada.
     * @param texto
     * El mensaje que se mostrará en la alerta de confirmación.
     * @return Optional<ButtonType>
     * Devuelve la opción seleccionada por el usuario en la alerta de confirmación, envuelta en un Optional para manejar posibles casos de ausencia de selección.
     */
    private Optional<ButtonType> mostrarMensajeEsperar(String texto){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setContentText(texto);

        return alert.showAndWait();
    }
    /** Método para mostrar un mensaje al usuario.
     * Crea una alerta con el título, mensaje y tipo de alerta proporcionados, y la muestra al usuario.
     * @param titulo
     * El título que se mostrará en la alerta.
     * @param mensaje
     * El mensaje que se mostrará en la alerta.
     * @param alerta
     * El tipo de alerta que se mostrará (por ejemplo, información, error, etc.).
     */
    private void mostarMensaje(String titulo, String mensaje, Alert.AlertType alerta){
        Alert alert = new Alert(alerta);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.show();
    }
    /** Método para volver al menú principal desde el calculador de IA o la sección de llenar puntuaciones.
     * Limpia la respuesta mostrada por la IA y oculta los paneles correspondientes, mostrando nuevamente el menú principal.
     * Maneja excepciones que puedan ocurrir durante la actualización de la interfaz.
     */
    public void onVolverMenuPrincipal(ActionEvent actionEvent) {
        laRespuestaIA.setText(null);
        apCalculadorIA.setVisible(false);
        apLlenarPuntuaciones.setVisible(false);
    }
}
