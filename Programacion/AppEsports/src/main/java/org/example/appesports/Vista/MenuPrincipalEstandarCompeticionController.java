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

/** Clase MenuPrincipalEstandarCompeticionController que controla la vista del menú principal para competiciones estándar.
 * Esta clase se encarga de mostrar la información de los equipos registrados en la competición y gestionar la interacción del usuario con la interfaz.
 */
public class MenuPrincipalEstandarCompeticionController {
    /** Atributos de la clase MenuPrincipalEstandarCompeticionController:
     * - lbNombreBienvenida: Un Label que muestra el nombre de bienvenida al usuario.
     * - onCerrarSesion: Un HBox que representa el área de la interfaz donde el usuario puede hacer clic para cerrar sesión.
     * - vbEquipos: Un VBox que contiene las cartas de información de los equipos registrados en la competición, permitiendo mostrar detalles como el nombre del equipo y su fecha de fundación.
     */
    public Label lbNombreBienvenida;
    @FXML
    public HBox onCerrarSesion;

    @FXML
    public VBox vbEquipos;
    public VBox vbResultadosUltimaJornada;

    /** Atributos adicionales:
     * - stage: Un objeto de tipo Stage que representa la ventana actual del menú principal, utilizado para gestionar la navegación entre vistas.
     * - controller: Un objeto de tipo MenuInicioSesionController que se utiliza para mostrar la ventana del menú de inicio de sesión cuando el usuario decide cerrar sesión.
     */
    public Stage stage;
    public MenuInicioSesionController controller;

    /** Método init que inicializa la vista del menú principal con la información del usuario y los equipos registrados.
     * El método recibe como parámetros la ventana actual (stage), el controlador del menú de inicio de sesión (menu) y el nombre de usuario (username).
     * Al ejecutar este método, se establece el texto del Label de bienvenida con el nombre del usuario y se llama al método actualizarInfo() para mostrar la información de los equipos registrados en la competición.
     * @param stage
     * @param menu
     * @param username
     */
    public void init (Stage stage, MenuInicioSesionController menu, String username) throws Exception {
        this.controller = menu;
        this.stage = stage;
        
        lbNombreBienvenida.setText(username);
        actualizarInfo();
    }
    /** Método crearCartasInfoEquipo que crea una carta de información para un equipo específico.
     * @param equipo
     * El método recibe un objeto de tipo Equipo y genera una carta visual que muestra el nombre del equipo y su fecha de fundación.
     * La carta se estiliza con un fondo blanco, bordes redondeados y una sombra para mejorar su apariencia.
     * @return
     * El método devuelve un nodo (Node) que representa la carta de información del equipo, listo para ser agregado a la interfaz gráfica.
     */
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


    /** Funcion para reccorrer los equipos y crear la carta por cada equipo
     * El método actualizarInfo se encarga de actualizar la información mostrada en la interfaz gráfica con los datos de los equipos registrados en la competición.
     * El método obtiene la lista de equipos a través del EquipoController, limpia el contenedor de equipos (vbEquipos) y luego recorre la lista de equipos para crear y agregar una carta de información para cada equipo utilizando el método crearCartasInfoEquipo.
     */
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


    /** Método onCerrarSesion que maneja el evento de cerrar sesión.
     * El método se activa cuando el usuario hace clic en el elemento de la interfaz asociado al cierre de sesión (onCerrarSesion).
     * Al ejecutar este método, se muestra la ventana del menú de inicio de sesión (controller.show()) y se cierra la ventana actual del menú principal (stage.close()), permitiendo al usuario volver a la pantalla de inicio de sesión.
     */
    @FXML
    void onCerrarSesion(MouseEvent event) {
        controller.show();
        stage.close();

    }

    /** Método mostarMensaje que muestra un mensaje de alerta al usuario.
     * El método recibe tres parámetros: el título del mensaje (titulo), el contenido del mensaje (mensaje) y el tipo de alerta (alerta) que se desea mostrar.
     * @param titulo
     * @param mensaje
     * @param alerta
     */
    private void mostarMensaje(String titulo, String mensaje, Alert.AlertType alerta){
        Alert alert = new Alert(alerta);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.show();
    }


}
