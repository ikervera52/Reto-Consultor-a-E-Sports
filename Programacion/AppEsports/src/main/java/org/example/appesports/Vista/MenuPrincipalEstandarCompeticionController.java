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

/** Clase MenuPrincipalEstandarCompeticionController que controla la vista del menú principal para competiciones estándar.
 * Esta clase se encarga de mostrar la información de los equipos registrados en la competición y gestionar la interacción del usuario con la interfaz.
 */
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

    /** Funcion para reccorrer los equipos y crear la carta por cada equipo
     * El método actualizarInfo se encarga de actualizar la información mostrada en la interfaz gráfica con los datos de los equipos registrados en la competición.
     * El método obtiene la lista de equipos a través del EquipoController, limpia el contenedor de equipos (vbEquipos) y luego recorre la lista de equipos para crear y agregar una carta de información para cada equipo utilizando el método crearCartasInfoEquipo.
     */
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
