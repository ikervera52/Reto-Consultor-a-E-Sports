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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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


    /** Atributos del controlador, incluyendo la etapa, el controlador del menú de inicio de sesión y el nombre de usuario del administrador.
     * También incluye elementos de la interfaz gráfica como etiquetas, paneles y contenedores para mostrar información y permitir la interacción del usuario.
     */
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
    /** Elementos de la interfaz gráfica para la sección de llenar puntuaciones, incluyendo un panel principal y un contenedor para mostrar las jornadas disponibles para llenar puntuaciones.
     */
    @FXML
    public AnchorPane apLlenarPuntuaciones;
    public VBox vboxContenedorJornadas;

    @FXML
    public AnchorPane apVerInformes;
    public AnchorPane spVerJugadores;
    public ScrollPane spVerEquipos;
    public AnchorPane spVerJornadas;
    public TextField tfBuscarJugadorPorEquipo;
    public VBox vboxContenedorJugadores;
    public VBox vboxContenedorEquipos;
    public VBox vboxContenedorJornadasVer;


    /** Método de inicialización del controlador.
     * Configura la etapa, el controlador del menú de inicio de sesión y el nombre de usuario del administrador, y actualiza la información mostrada en el menú principal.
     * @param stage
     * La etapa (ventana) en la que se muestra el menú principal.
     * @param menu
     * El controlador del menú de inicio de sesión, utilizado para mostrar el menú de inicio de sesión al cerrar sesión.
     * @param username
     * El nombre de usuario del administrador, utilizado para mostrar un mensaje de bienvenida y personalizar la experiencia del usuario.
     */
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
            Enfrentamiento siguienteEnfrentamiento = null;
            for (Enfrentamiento enfrentamiento: enfrentamientosUltimaJornada) {
                if ( jornadasPasadas.getLast().getFechaJornada().isAfter(LocalDate.now()) || (jornadasPasadas.getLast().getFechaJornada().isEqual(LocalDate.now()) && enfrentamiento.getHoraEnfrentamiento().isAfter(LocalTime.now()))) {
                    siguienteEnfrentamiento =  enfrentamiento;
                    break;
                }
            }
            if (siguienteEnfrentamiento == null && jornadasPasadas.size() != jornadas.size()) {
                jornadasPasadas.add(jornadas.get(jornadasPasadas.size()));
                ArrayList<Enfrentamiento> enfrentamientosSiguienteJornada = EnfrentamientoController.buscarPorJornada(jornadasPasadas.getLast().getIdJornada());
                siguienteEnfrentamiento = enfrentamientosSiguienteJornada.getFirst();
            }

            if (siguienteEnfrentamiento == null) {
                tfSiguientePartido.setText("No hay enfrentamientos programados");
                tfSiguientePartido.setFont(new javafx.scene.text.Font(18));
                tfHora.setVisible(false);
            }else {
                ArrayList<Resultado> resultadosSiguienteEnfrentamiento = ResultadoController.verPorEnfrentamiento(siguienteEnfrentamiento.getIdEnfrentamiento());
                tfSiguientePartido.setText(resultadosSiguienteEnfrentamiento.getFirst().getEquipo().getNombre() + " - " + resultadosSiguienteEnfrentamiento.getLast().getEquipo().getNombre());
                tfHora.setText(siguienteEnfrentamiento.getHoraEnfrentamiento().toString());
            }

            Enfrentamiento anteriorEnfrentamiento = null;
            for (Enfrentamiento enfrentamiento: enfrentamientosUltimaJornada) {
                if (jornadasPasadas.getLast().getFechaJornada().isBefore(LocalDate.now()) || (jornadasPasadas.getLast().getFechaJornada().isEqual(LocalDate.now()) && enfrentamiento.getHoraEnfrentamiento().isBefore(LocalTime.now()))) {
                    anteriorEnfrentamiento =  enfrentamiento;
                }
            }
            if (anteriorEnfrentamiento == null && jornadasPasadas.size() > 1) {
                ArrayList<Enfrentamiento> enfrentamientosPenultimaJornada = EnfrentamientoController.buscarPorJornada(jornadasPasadas.get(jornadasPasadas.size() - 2).getIdJornada());
                for (Enfrentamiento enfrentamiento: enfrentamientosPenultimaJornada) {
                    anteriorEnfrentamiento =  enfrentamiento;
                }
            }
            if (anteriorEnfrentamiento == null) {
                tfUltimoResultado.setText("No hay resultados disponibles");
                tfResultadoPrincipal.setVisible(false);
            }else {
                ArrayList<Resultado> resultadosAnteriorEnfrentamiento = ResultadoController.verPorEnfrentamiento(anteriorEnfrentamiento.getIdEnfrentamiento());
                tfUltimoResultado.setText(resultadosAnteriorEnfrentamiento.getFirst().getEquipo().getNombre() + " - " +  resultadosAnteriorEnfrentamiento.getLast().getEquipo().getNombre());
                tfResultadoPrincipal.setText(resultadosAnteriorEnfrentamiento.getFirst().getResultado() + " - " + resultadosAnteriorEnfrentamiento.getLast().getResultado());
            }

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
            apCalculadorIA.setVisible(false);
            apVerInformes.setVisible(false);
            spVerEquipos.setVisible(false);
            spVerJugadores.setVisible(false);
            spVerJornadas.setVisible(false);
        }catch (Exception e){
            System.out.println("pe");
        }
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

        Label numeroJornada = new Label("Jornada " + jornada.getNumeroJornada() + " - " + jornada.getFechaJornada());
        numeroJornada.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        cartaJornada.getChildren().add(numeroJornada);

        ArrayList<Enfrentamiento> enfrentamientos = EnfrentamientoController.buscarPorJornada(jornada.getIdJornada());
        for (int i = 0; i < enfrentamientos.size(); i += 2) {
            if ((enfrentamientos.get(i).getHoraEnfrentamiento().isBefore(LocalTime.now()) && jornada.getFechaJornada().isEqual(LocalDate.now())) || jornada.getFechaJornada().isBefore(LocalDate.now())) {


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
                     mostarMensaje("Confirmación", "Puntuación guardada con éxito", Alert.AlertType.INFORMATION);
                } catch (Exception e) {
                    mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
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
        apLlenarPuntuaciones.setVisible(false);
        apVerInformes.setVisible(false);
        spVerEquipos.setVisible(false);
        spVerJugadores.setVisible(false);
        spVerJornadas.setVisible(false);
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
    /** Método para mostrar la interfaz de ver informes.
     * Muestra el panel correspondiente a los informes, permitiendo al administrador consultar información detallada sobre los equipos, jugadores y jornadas de la competición.
     * Maneja excepciones que puedan ocurrir durante la obtención de datos y actualización de la interfaz.
     * @param MouseEvent
     */
    public void onVerInformes(MouseEvent MouseEvent) {
        apVerInformes.setVisible(true);
        apCalculadorIA.setVisible(false);
        apLlenarPuntuaciones.setVisible(false);
        spVerJornadas.setVisible(false);
        spVerEquipos.setVisible(false);
        spVerJugadores.setVisible(false);
    }

    /** Funcion para mostrar el panel con los jugadores al pulsar el boton ver jugadores
     * Muestra el panel correspondiente a los jugadores, permitiendo al administrador consultar información detallada sobre los jugadores de la competición, incluyendo su nombre, apellido, fecha de nacimiento, rol, sueldo y equipo al que pertenecen.
     * @param mouseEvent
     */
    public void onVerJugadores(MouseEvent mouseEvent) {
        spVerJugadores.setVisible(true);
    }

    /** Funcion para crear la vbox de cada jugador a mostrar
     * Crea una interfaz gráfica para mostrar la información de un jugador específico, incluyendo su nombre, apellido, fecha de nacimiento, rol, sueldo y equipo al que pertenecen.
     * @param jugador
     * @return
     */
    public Node crearCartasJugador(Jugador jugador) {
        VBox carta = new VBox();
        carta.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0)");
        carta.setPadding(new Insets(15));
        carta.setPrefWidth(400);

        Label nickname = new Label();
        nickname.setText(jugador.getNickname());
        nickname.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0089ed");

        Label nombre = new Label();
        nombre.setText("Nombre: " + jugador.getNombre());
        nombre.setStyle("-fx-text-fill: black;");

        Label apellido = new Label();
        apellido.setText("Apellido: " + jugador.getApellido());
        apellido.setStyle("-fx-text-fill: black;");

        Label fecha = new Label();
        fecha.setText("Fecha de nacimiento: " + jugador.getFechaNacimiento().toString());
        fecha.setStyle("-fx-text-fill: black;");

        Label rol = new Label();
        rol.setText("Rol: " + jugador.getRol());
        rol.setStyle("-fx-text-fill: black;");

        Label sueldo = new Label();
        sueldo.setText("Sueldo: " + jugador.getSueldo() + "€");
        sueldo.setStyle("-fx-text-fill: black;");

        Label equipo = new Label();
        equipo.setText("Equipo: " + (jugador.getEquipo() != null ? jugador.getEquipo().getNombre() : "Sin equipo"));
        equipo.setStyle("-fx-text-fill: black;");

        carta.getChildren().addAll(nickname, nombre, apellido, fecha, rol, sueldo, equipo);
        return carta;
    }
    /** Funcion para buscar jugadores por equipo al pulsar el boton de buscar
     * Permite al administrador buscar jugadores específicos por el nombre del equipo al que pertenecen, mostrando solo aquellos jugadores que pertenecen al equipo especificado.
     * Si no se encuentra ningún equipo con el nombre proporcionado, muestra un mensaje de error indicando que no existe ningún equipo con ese nombre.
     * Maneja excepciones que puedan ocurrir durante la obtención de datos y actualización de la interfaz.
     */
    public void onBuscarJugadorPorEquipo(){
        try {

            ArrayList<Jugador> jugadores = JugadorController.verJugadoresPorEquipo(tfBuscarJugadorPorEquipo.getText());

            rellenarVerJugadores(jugadores);


        }catch (Exception e){
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /** Funcion para recorrer los jugadores y ir creando la vbox por cada jugador
     * Recorre la lista de jugadores obtenida por el método de búsqueda y crea una carta para cada jugador utilizando el método crearCartasJugador, mostrando la información de cada jugador en la interfaz.
     * @param jugadores
     */
    public void rellenarVerJugadores(ArrayList<Jugador> jugadores){
        try {
            vboxContenedorJugadores.getChildren().clear();
            for (int i = 0; i < jugadores.size(); i += 2) {

                HBox fila = new HBox(30);
                fila.setStyle("-fx-background-color: transparent;");
                fila.setAlignment(Pos.CENTER);

                Node vistaJugador1 = crearCartasJugador(jugadores.get(i));
                fila.getChildren().add(vistaJugador1);

                if (i + 1 < jugadores.size()) {
                    Node vistaJugador2 = crearCartasJugador(jugadores.get(i + 1));
                    fila.getChildren().add(vistaJugador2);
                }else {
                    Region espacioVacio = new Region();
                    espacioVacio.setPrefWidth(400);
                    fila.getChildren().add(espacioVacio);
                }

                vboxContenedorJugadores.getChildren().add(fila);
            }

            if (jugadores.isEmpty()) {
                Label sinJugadores = new Label("No hay jugadores para ver.");
                sinJugadores.setStyle("-fx-font-size: 18px; -fx-text-fill: #555;");
                vboxContenedorJugadores.getChildren().add(sinJugadores);
            }

        }catch (Exception e){
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /** Funcion para volver al panel de ver informes
     * Permite al administrador volver al panel principal de informes, ocultando los paneles específicos de jugadores, equipos y jornadas, y limpiando el campo de búsqueda de jugadores por equipo para mostrar la información general de los informes.
     * @param actionEvent
     */
    public void onVolverVerInformes(ActionEvent actionEvent) {
        spVerJugadores.setVisible(false);
        spVerEquipos.setVisible(false);
        spVerJornadas.setVisible(false);
        tfBuscarJugadorPorEquipo.clear();
    }

    /** Funcion para mostrar el panel de ver equipos al pulsar el boton
     * Permite al administrador mostrar el panel específico para ver los equipos de la competición, mostrando información detallada sobre cada equipo, incluyendo su nombre, fecha de fundación, número de jugadores, sueldo máximo, sueldo mínimo y sueldo medio.
     * @param mouseEvent
     */
    public void onVerEquipos(MouseEvent mouseEvent) {
        rellenarVerEquipos();
        spVerEquipos.setVisible(true);
    }

    /** Funcion para crear la carta por cada equipo
     * Crea una interfaz gráfica para mostrar la información de un equipo específico, incluyendo su nombre, fecha de fundación, número de jugadores, sueldo máximo, sueldo mínimo y sueldo medio.
     * @param equipo
     * @return
     */
    public Node crearCartasEquipo(EquipoInforme equipo) {
        VBox carta = new VBox();
        carta.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0)");
        carta.setPadding(new Insets(15));
        carta.setPrefWidth(400);


        Label nombre = new Label();
        nombre.setText(equipo.getNombre());
        nombre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        nombre.setTextFill(Color.web("#0089ed"));


        Label fecha = new Label();
        fecha.setText("Fecha de Fundación: " + equipo.getFechaFundacion().toString());

        Label numeroJugadores = new Label();
        numeroJugadores.setText("Numero de Jugadores: " + equipo.getCantJugadores());

        Label sueldoMax = new Label();
        sueldoMax.setText("Sueldo Maximo: " + equipo.getMaxSalario() + "€");

        Label sueldoMin = new Label();
        sueldoMin.setText("Sueldo Minimo: " + equipo.getMinSalario() + "€");

        Label sueldoMed = new Label();
        sueldoMed.setText("Sueldo Medio: " + equipo.getAvgSalario() + "€");


        carta.getChildren().addAll(nombre, fecha, numeroJugadores, sueldoMax, sueldoMin, sueldoMed);
        return carta;
    }

    /** Funcion para reccorrer los equipos y crear la carta por cada equipo
     * Recorre la lista de equipos obtenida y crea una carta para cada equipo utilizando el método crearCartasEquipo, mostrando la información de cada equipo en la interfaz.
     */
    public void rellenarVerEquipos() {
        try {
            vboxContenedorEquipos.getChildren().clear();
            ArrayList<EquipoInforme> equipos = EquipoController.listarEquiposInforme();
            for (int i = 0; i < equipos.size(); i += 2) {

                HBox fila = new HBox(30);

                Node vistaJugador1 = crearCartasEquipo(equipos.get(i));
                fila.getChildren().add(vistaJugador1);

                if (i + 1 < equipos.size()) {
                    Node vistaJugador2 = crearCartasEquipo(equipos.get(i + 1));
                    fila.getChildren().add(vistaJugador2);
                } else {
                    Region espacioVacio = new Region();
                    espacioVacio.setPrefWidth(400);
                    fila.getChildren().add(espacioVacio);
                }

                vboxContenedorEquipos.getChildren().add(fila);
            }

            if (equipos.isEmpty()) {
                Label sinEquipos = new Label("No hay equipos para ver.");
                sinEquipos.setStyle("-fx-font-size: 18px; -fx-text-fill: #555;");
                vboxContenedorEquipos.getChildren().add(sinEquipos);
            }

        } catch (Exception e) {
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    /** Funcion para mostrar el panel de ver jornadas al pulsar el boton
     * Permite al administrador mostrar el panel específico para ver las jornadas de la competición, mostrando información detallada sobre cada jornada, incluyendo su número, fecha y los enfrentamientos correspondientes con sus resultados.
     * @param mouseEvent
     * @throws Exception
     */
    public void onVerJornadas(MouseEvent mouseEvent) throws Exception {
        rellenarVerJornadas();
        spVerJornadas.setVisible(true);
    }
    /** Funcion para crear la carta por cada jornada
     * Crea una interfaz gráfica para mostrar la información de una jornada específica, incluyendo su número, fecha y los enfrentamientos correspondientes con sus resultados.
     * @return
     * @throws Exception
     */
    public void rellenarVerJornadas() throws Exception {
        vboxContenedorJornadasVer.getChildren().clear();
        ArrayList<Jornada> jornadas = JornadaController.listarJornadas();
        for (Jornada jornada : jornadas) {
            vboxContenedorJornadasVer.getChildren().add(crearCartaJornada2(jornada));
        }
    }
    /** Función para crear la carta de una jornada con sus enfrentamientos y resultados
     * Crea una interfaz gráfica para mostrar la información de una jornada específica, incluyendo su número, fecha y los enfrentamientos correspondientes con sus resultados.
     * @param jornada
     * @return Node
     * @throws Exception
     */
    public Node crearCartaJornada2(Jornada jornada) throws Exception {
        VBox cartaJornada = new VBox(15);
        cartaJornada.setAlignment(Pos.CENTER);
        cartaJornada.setPadding(new Insets(15));
        cartaJornada.setStyle("-fx-background-color: WHITE; -fx-background-radius: 20; -fx-effect:  dropshadow(three-pass-box, rgba(0, 0, 0, 0.2), 25, 0, 0, 12);");

        Label numeroJornada = new Label("Jornada " + jornada.getNumeroJornada() + " - " +  jornada.getFechaJornada());
        numeroJornada.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        cartaJornada.getChildren().add(numeroJornada);

        ArrayList<Enfrentamiento> enfrentamientos = EnfrentamientoController.buscarPorJornada(jornada.getIdJornada());
        for (int i = 0; i < enfrentamientos.size(); i += 2) {
                HBox fila = new HBox(30);
                fila.setAlignment(Pos.CENTER);

                Node enfrentamiento1 = crearCartaEnfrentamiento2(enfrentamientos.get(i));
                fila.getChildren().add(enfrentamiento1);

                if (i + 1 < enfrentamientos.size()) {
                    Node enfrentamiento2 = crearCartaEnfrentamiento2(enfrentamientos.get(i + 1));
                    fila.getChildren().add(enfrentamiento2);
                } else {
                    Region espacioVacio = new Region();
                    espacioVacio.setPrefWidth(400);
                    fila.getChildren().add(espacioVacio);
                }

                cartaJornada.getChildren().add(fila);
        }
        return cartaJornada;
    }
    /** Función para crear la carta de un enfrentamiento con sus resultados
     * Crea una interfaz gráfica para mostrar la información de un enfrentamiento específico, incluyendo los equipos participantes y sus puntuaciones.
     * @param enfrentamiento
     * @return Node
     * @throws Exception
     */
    public Node crearCartaEnfrentamiento2(Enfrentamiento enfrentamiento) throws Exception {
        ArrayList<Resultado> resultados = ResultadoController.verPorEnfrentamiento(enfrentamiento.getIdEnfrentamiento());
        HBox cartaEnfrentamiento = new HBox();
        cartaEnfrentamiento.setPadding(new Insets(10));
        cartaEnfrentamiento.setPrefWidth(400);
        cartaEnfrentamiento.setStyle("-fx-border-radius: 10; -fx-background-color: #005699; -fx-background-radius: 15; -fx-border-width: 8; -fx-border-color: #0086ED;");

        VBox cartaInterior = new VBox();
        cartaInterior.setAlignment(Pos.CENTER);

        Label equipos = new Label(resultados.get(0).getEquipo().getNombre() + " - " + resultados.get(1).getEquipo().getNombre());
        equipos.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: WHITE;");
        equipos.setAlignment(Pos.CENTER);
        equipos.setPrefWidth(400);

        HBox puntuaciones = new HBox();
        puntuaciones.setAlignment(Pos.CENTER);

        Label tfPuntuacionEquipo1 = new Label();
        tfPuntuacionEquipo1.setAlignment(Pos.CENTER);
        tfPuntuacionEquipo1.setText(String.valueOf(resultados.getFirst().getResultado()));
        tfPuntuacionEquipo1.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: WHITE; -fx-font-size: 36px; -fx-font-weight: bold;");

        Label tfPuntuacionEquipo2 = new Label();
        tfPuntuacionEquipo2.setAlignment(Pos.CENTER);
        tfPuntuacionEquipo2.setText(String.valueOf(resultados.getLast().getResultado()));
        tfPuntuacionEquipo2.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: WHITE; -fx-font-size: 36px; -fx-font-weight: bold;");


        Label guion = new Label("-");
        guion.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: WHITE;");

        puntuaciones.getChildren().addAll(tfPuntuacionEquipo1, guion, tfPuntuacionEquipo2);
        puntuaciones.setAlignment(Pos.CENTER);

        Label hora = new Label(enfrentamiento.getHoraEnfrentamiento().toString());
        hora.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: WHITE;");
        hora.setAlignment(Pos.CENTER);


        cartaInterior.getChildren().addAll(equipos, puntuaciones, hora);
        cartaEnfrentamiento.getChildren().add(cartaInterior);

        return cartaEnfrentamiento;
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
        apVerInformes.setVisible(false);
    }


}
