package org.example.appesports.Vista;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.appesports.Controlador.EquipoController;
import org.example.appesports.Controlador.JugadorController;
import org.example.appesports.Controlador.UsuarioController;
import org.example.appesports.Modelo.Admin;
import org.example.appesports.Modelo.Equipo;
import org.example.appesports.Modelo.Jugador;
import org.example.appesports.Modelo.Usuario;
import org.example.appesports.Controlador.*;
import javafx.scene.layout.AnchorPane;
import org.example.appesports.Modelo.*;
import org.example.appesports.Utilidades.ValidarDatos;
import org.example.appesports.DAO.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Optional;
import java.util.*;

/**
 * Controlador para el menú principal del administrador.
 * Gestiona la interacción del usuario con la interfaz gráfica y coordina las acciones relacionadas con la gestión de jugadores, usuarios, equipos e informes.
 */
public class MenuPrincipalAdminController {
    /** Variable para almacenar el nombre de usuario del administrador que ha iniciado sesión, utilizada para mostrar un mensaje de bienvenida personalizado en la interfaz gráfica.
     */
    private String username;
    /** Variables para mostrar los contadores de jugadores y equipos en el menú principal
     * Estas etiquetas se actualizan dinámicamente para reflejar el número total de jugadores y equipos registrados en la base de datos, proporcionando al administrador una visión general rápida del estado actual de la competición.
     */
    @FXML
    public Label lbCantEquipos;
    /** Variable para mostrar el contador de jugadores en el menú principal
     * Esta etiqueta se actualiza dinámicamente para reflejar el número total de jugadores registrados en la base de datos, proporcionando al administrador una visión general rápida del estado actual de la competición.
     */
    @FXML
    public Label lbCantJugadores;
    /** Variable para mostrar el mensaje de bienvenida con el nombre de usuario del administrador en el menú principal
     * Esta etiqueta se actualiza dinámicamente para mostrar un mensaje de bienvenida personalizado que incluye el nombre de usuario del administrador que ha iniciado sesión, creando una experiencia más personalizada y amigable para el usuario.
     */
    @FXML
    public Label lbNombreBienvenida;

        @FXML
        public Button bVolverMenuPrincipal;
    /** Variable para el botón de volver al menú principal desde el apartado de gestión de jugadores
     * Este botón permite al administrador regresar al menú principal de gestión de jugadores después de haber accedido a las opciones de añadir, eliminar o editar jugadores, proporcionando una navegación fácil y fluida dentro de la sección de gestión de jugadores.
     */
    @FXML
    public Button bVolverGestionarJugadores;
    /** Variable para el botón de volver al menú principal desde el apartado de gestión de usuarios
     * Este botón permite al administrador regresar al menú principal de gestión de usuarios después de haber accedido a las opciones de añadir, eliminar o editar usuarios, proporcionando una navegación fácil y fluida dentro de la sección de gestión de usuarios.
     */
    @FXML
    public AnchorPane apGestionarUsuariosPrincipal;
    /** Variable para el AnchorPane principal de gestión de jugadores
     * Este AnchorPane contiene las opciones principales para gestionar jugadores, como añadir, eliminar y editar jugadores. Al hacer clic en la opción de gestionar jugadores, este AnchorPane se vuelve visible y muestra las opciones relacionadas con la gestión de jugadores, mientras que los demás AnchorPane se ocultan para mostrar solo el apartado de gestión de jugadores.
     */
    @FXML
    public AnchorPane apGestionarJugadoresPrincipal;
    /** Variable para el AnchorPane de añadir jugador
     * Este AnchorPane contiene los campos de texto, combo box y date picker necesarios para introducir los datos de un nuevo jugador. Al hacer clic en la opción de añadir jugador, este AnchorPane se vuelve visible y muestra el formulario para añadir un nuevo jugador, mientras que los demás AnchorPane se ocultan para mostrar solo el apartado de añadir jugador.
     */
    @FXML
    public AnchorPane apGestionarJugadoresAnadir;
    /** Variable para el AnchorPane de eliminar jugador
     * Este AnchorPane contiene el campo de texto necesario para introducir el nickname del jugador que se desea eliminar. Al hacer clic en la opción de eliminar jugador, este AnchorPane se vuelve visible y muestra el formulario para eliminar un jugador, mientras que los demás AnchorPane se ocultan para mostrar solo el apartado de eliminar jugador.
     */
    @FXML
    public AnchorPane apMenuPrincipal;
    /** Datos para añadir Jugador
     * Estos campos de texto, combo box y date picker se utilizan para recoger los datos necesarios para añadir un nuevo jugador a la base de datos. El administrador introduce el nombre, apellido, nacionalidad, fecha de nacimiento, nickname, rol, sueldo y equipo del nuevo jugador en estos campos, y luego se valida la información antes de llamar al método correspondiente en el controlador JugadorController para insertar el nuevo jugador en la base de datos.
     */
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

    /** Datos para eliminar Jugador
     * Este campo de texto se utiliza para recoger el nickname del jugador que se desea eliminar de la base de datos. El administrador introduce el nickname del jugador en este campo, y luego se valida la información antes de llamar al método correspondiente en el controlador JugadorController para eliminar el jugador de la base de datos.
     */
    @FXML
    public AnchorPane apGestionarJugadoresBorrar;

    @FXML
    public TextField tfNicknameBorrar;

    @FXML
    public TextField tfNicknameBuscar;

    /** Datos para editar Jugador
     * Estos campos de texto, combo box y date picker se utilizan para recoger los datos necesarios para editar un jugador existente en la base de datos. El administrador introduce el nickname del jugador que desea editar en el campo de búsqueda, y luego se valida la información antes de llamar al método correspondiente en el controlador JugadorController para obtener los datos del jugador desde la base de datos. Si el jugador existe, se rellenan los campos del formulario con los datos del jugador para que el administrador pueda editarlos. Después de realizar las modificaciones, se valida la información nuevamente antes de llamar al método correspondiente en el controlador JugadorController para actualizar los datos del jugador en la base de datos.
     */
    @FXML
    public TextField tfNombreJugadorEditar;

    @FXML
    public TextField tfApellidoJugadorEditar;

    @FXML
    public TextField tfNacionalidadEditar;

    @FXML
    public DatePicker dpFechaNacimientoEditar;

    @FXML
    public TextField tfNicknameEditar;

    @FXML
    public ComboBox<String> cbRolEditar;

    @FXML
    public TextField tfSueldoEditar;

    @FXML
    public ComboBox<String> cbSeleccionEquipoParaJugadorEditar;

    @FXML
    public Button bEditar;

    @FXML
    public AnchorPane apGestionarJugadoresEditar;

    @FXML
    public AnchorPane apGestionarUsuariosAnadir;

    @FXML
    public AnchorPane apGestionarUsuariosBorrar;

    @FXML
    public AnchorPane apGestionarUsuariosEditar;

        @FXML
        public AnchorPane apCerrarCompeticion;

    /** Variables para añadir usuarios
     * Estos campos de texto, combo box y date picker se utilizan para recoger los datos necesarios para añadir un nuevo usuario a la base de datos. El administrador introduce el nombre de usuario, contraseña y tipo de usuario (admin o estandar) en estos campos, y luego se valida la información antes de llamar al método correspondiente en el controlador UsuarioController para insertar el nuevo usuario en la base de datos.
     */
    @FXML
        public TextField tfNombreUsuario;

    @FXML
    public PasswordField pfContrasena;

    @FXML
    public ComboBox<String> cbTipoUsuario;

    /** Variables para eliminar usuarios
     * Este campo de texto se utiliza para recoger el nombre de usuario del usuario que se desea eliminar de la base de datos. El administrador introduce el nombre de usuario en este campo, y luego se valida la información antes de llamar al método correspondiente en el controlador UsuarioController para eliminar el usuario de la base de datos.
     */
    @FXML
    public TextField tfNombreUsuarioBorrar;

    /** Variables para editar usuarios
     * Estos campos de texto, combo box y date picker se utilizan para recoger los datos necesarios para editar un usuario existente en la base de datos. El administrador introduce el nombre de usuario del usuario que desea editar en el campo de búsqueda, y luego se valida la información antes de llamar al método correspondiente en el controlador UsuarioController para obtener los datos del usuario desde la base de datos. Si el usuario existe, se rellenan los campos del formulario con los datos del usuario para que el administrador pueda editarlos. Después de realizar las modificaciones, se valida la información nuevamente antes de llamar al método correspondiente en el controlador UsuarioController para actualizar los datos del usuario en la base de datos.
     */
    @FXML
    public TextField tfNombreUsuarioBuscar;

    @FXML
    public TextField tfNombreUsuarioEditar;

    @FXML
    public PasswordField pfContrasenaEditar;

    @FXML
    public ComboBox<String> cbTipoUsuarioEditar;

    @FXML
    public Button bEditarUsuario;

    /** Variables para gestion equipos
     * Estos campos de texto, combo box y date picker se utilizan para recoger los datos necesarios para añadir, eliminar y editar equipos en la base de datos. El administrador introduce el nombre del equipo, fecha de fundación y otros datos relevantes en estos campos, y luego se valida la información antes de llamar a los métodos correspondientes en el controlador EquipoController para insertar, eliminar o actualizar los datos del equipo en la base de datos.
     */
    @FXML
    public AnchorPane apGestionarEquiposPrincipal;

    /** Variables para anadir equipo
     * Estos campos de texto, combo box y date picker se utilizan para recoger los datos necesarios para añadir un nuevo equipo a la base de datos. El administrador introduce el nombre del equipo y la fecha de fundación en estos campos, y luego se valida la información antes de llamar al método correspondiente en el controlador EquipoController para insertar el nuevo equipo en la base de datos.
     */
    @FXML
    public AnchorPane apGestionarEquiposAnadir;

    @FXML
    public TextField tfNombreEquipo;

    @FXML
    public DatePicker dpFechaFundacion;

    /** Variables para borrar equipos
     * Este campo de texto se utiliza para recoger el nombre del equipo que se desea eliminar de la base de datos. El administrador introduce el nombre del equipo en este campo, y luego se valida la información antes de llamar al método correspondiente en el controlador EquipoController para eliminar el equipo de la base de datos.
     */
    @FXML
    public AnchorPane apGestionarEquiposBorrar;

    @FXML
    public TextField tfNombreEquipoBorrar;

    /** Variables para editar equipos
     * Estos campos de texto, combo box y date picker se utilizan para recoger los datos necesarios para editar un equipo existente en la base de datos. El administrador introduce el nombre del equipo que desea editar en el campo de búsqueda, y luego se valida la información antes de llamar al método correspondiente en el controlador EquipoController para obtener los datos del equipo desde la base de datos. Si el equipo existe, se rellenan los campos del formulario con los datos del equipo para que el administrador pueda editarlos. Después de realizar las modificaciones, se valida la información nuevamente antes de llamar al método correspondiente en el controlador EquipoController para actualizar los datos del equipo en la base de datos.
     */
    @FXML
    public AnchorPane apGestionarEquiposEditar;

    @FXML
    public TextField tfNombreEquipoBuscar;

    @FXML
    public TextField tfNombreEquipoEditar;

    @FXML
    public DatePicker dpFechaFundacionEditar;

    @FXML
    public Button bEditarEquipo;
        @FXML
        public AnchorPane apVerInformes;

        @FXML
        public AnchorPane spVerJugadores;

        @FXML
        public VBox vboxContenedorJugadores;

        @FXML
        public ScrollPane spVerEquipos;

        @FXML
        public TextField tfBuscarJugadorPorEquipo;

        @FXML
        public VBox vboxContenedorEquipos;

        public TextField tfTipoPuntuacion;

    /** Variables para mostrar usuarios y roles en una Lista
     * Esta ListView se utiliza para mostrar la lista de usuarios registrados en la base de datos junto con sus roles (admin o estandar) en el apartado de gestionar usuarios. La información se obtiene desde el controlador UsuarioController y se muestra de manera clara y organizada para que el administrador pueda tener una visión general de los usuarios registrados y sus roles dentro del sistema.
     */
    @FXML
        public ListView<Usuario> listUsuarios;


    public Stage stage;
    public MenuInicioSesionController controller;

    public void init(Stage stage, MenuInicioSesionController menu, String username) {
        this.controller = menu;
        this.stage = stage;
        this.username = username;

        actualizarDatosPanelPrincipal();
    }
    /**
     * Función para mostrar la lista de usuarios y sus roles en el apartado de gestionar usuarios.
     * Obtiene la lista de usuarios desde el controlador UsuarioController y la muestra en un ListView.
     * Cada elemento del ListView muestra el nombre de usuario y su tipo (admin o estandar).
     */
    @FXML
    public void onVolverMenuPrincipal(ActionEvent event) {

        actualizarDatosPanelPrincipal();
        apGestionarJugadoresPrincipal.setVisible(false);
        apGestionarUsuariosPrincipal.setVisible(false);
        apGestionarEquiposPrincipal.setVisible(false);
        apVerInformes.setVisible(false);
        apCerrarCompeticion.setVisible(false);
        tfTipoPuntuacion.clear();
    }

    /** Función que abre el menu principal de gestion de Jugadores
     * Hace visible el AnchorPane de gestión de jugadores y oculta los demás AnchorPane para mostrar solo el apartado de gestión de jugadores.
     * @param MouseEvent
     */
    @FXML
    public void onGestionarJugadores(MouseEvent MouseEvent) {

        apGestionarJugadoresPrincipal.setVisible(true);
        apGestionarJugadoresAnadir.setVisible(false);
        apGestionarJugadoresEditar.setVisible(false);
        apGestionarJugadoresBorrar.setVisible(false);

        apGestionarEquiposPrincipal.setVisible(false);
        apGestionarEquiposAnadir.setVisible(false);

        apGestionarUsuariosPrincipal.setVisible(false);
        apGestionarUsuariosAnadir.setVisible(false);
        apGestionarUsuariosEditar.setVisible(false);
        apGestionarUsuariosBorrar.setVisible(false);

        apVerInformes.setVisible(false);
        spVerJugadores.setVisible(false);
        spVerEquipos.setVisible(false);
        tfBuscarJugadorPorEquipo.clear();

        apCerrarCompeticion.setVisible(false);
        tfTipoPuntuacion.clear();

        apGestionarEquiposEditar.setVisible(false);
        apGestionarEquiposBorrar.setVisible(false);
        apGestionarEquiposAnadir.setVisible(false);

        vaciarOpcionesEquipo();
        vaciarOpcionesJugador();
        vaciarOpcionesUsuario();

    }

    /** Función que abre la ventana de añadir jugador
     * Hace visible el AnchorPane de añadir jugador y oculta los demás AnchorPane para mostrar solo el apartado de añadir jugador.
     * @param mouseEvent
     */
    @FXML
    public void onAnadirJugador(MouseEvent mouseEvent) {
        apGestionarJugadoresAnadir.setVisible(true);

        llenarComboBoxJugador();

    }

    /** Función que añade un jugador a la base de datos
     * Recoge los datos introducidos por el usuario en los campos de texto y combo box, los valida utilizando la clase ValidarDatos y luego llama al método anadirJugador del controlador JugadorController para insertar el nuevo jugador en la base de datos.
     * @param event
     */
    @FXML
    public void onAnadirDatosJugador(ActionEvent event) {

        try {
            ValidarDatos.validarString(tfNombreJugador.getText());
            ValidarDatos.validarString(tfApellidoJugador.getText());
            ValidarDatos.validarString(tfNacionalidad.getText());
            ValidarDatos.validarFecha(dpFechaNacimiento.getValue());
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

            JugadorController.anadirJugador(nombre, apellido, nacionalidad, fechaNacimiento, nickname, rol, sueldo, nombreEquipo);

            // AQUI TIENE QUE MOSTRARSE UNA VENTANA QUE PONGA QUE SE CONFIRMA QUE SE HA AÑADIDO
            mostarMensaje("Confirmación", "El jugador se ha guardado con éxito", Alert.AlertType.INFORMATION);

            vaciarOpcionesJugador();
            llenarComboBoxJugador();

        } catch (Exception e) {
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /** Función que abre la ventana de eliminar Jugador
     * Hace visible el AnchorPane de eliminar jugador y oculta los demás AnchorPane para mostrar solo el apartado de eliminar jugador.
     * @param mouseEvent
     */
    @FXML
    public void onEliminarJugador(MouseEvent mouseEvent) {

        apGestionarJugadoresBorrar.setVisible(true);
    }

    /** Función que elimina el jugador de la base de datos
     * Recoge el nickname introducido por el usuario en el campo de texto, lo valida utilizando la clase ValidarDatos y luego llama al método borrarJugador del controlador JugadorController para eliminar el jugador de la base de datos.
     * @param event
     */
    @FXML
    public void onBorrarDatosJugador(ActionEvent event) {
        try {

            // MENSAJE DE CONFIMRACION
            Optional<ButtonType> result = mostarMensajeConfirmacion("Confirmación", "Confirmación de borrado");

            if (result.isPresent() && result.get() == ButtonType.OK) {
                JugadorController.borrarJugador(tfNicknameBorrar.getText());
                mostarMensaje("Confirmación", "El jugador se ha borrado con éxito", Alert.AlertType.INFORMATION);
            }

            vaciarOpcionesJugador();

        } catch (Exception e) {
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    /** Función que abre la ventana de editar Jugador
     * Hace visible el AnchorPane de editar jugador y oculta los demás AnchorPane para mostrar solo el apartado de editar jugador.
     * @param mouseEvent
     */
    @FXML
    public void onEditarJugador(MouseEvent mouseEvent) {
        tfNombreJugadorEditar.setDisable(true);
        tfApellidoJugadorEditar.setDisable(true);
        tfNacionalidadEditar.setDisable(true);
        dpFechaNacimientoEditar.setDisable(true);
        tfNicknameEditar.setDisable(true);
        cbRolEditar.setDisable(true);
        tfSueldoEditar.setDisable(true);
        cbSeleccionEquipoParaJugadorEditar.setDisable(true);
        bEditar.setDisable(true);

        apGestionarJugadoresEditar.setVisible(true);
    }

    /** Función que busca al jugador por el nickname para poder poner sus datos en el formulario
     * Recoge el nickname introducido por el usuario en el campo de texto, lo valida utilizando la clase ValidarDatos y luego llama al método buscarPorNickname del controlador JugadorController para obtener los datos del jugador desde la base de datos. Si el jugador existe, rellena los campos del formulario con los datos del jugador para que el usuario pueda editarlos.
     * @param event
     */
    @FXML
    public void onBuscarNickname(ActionEvent event) {

        try {
            Jugador jugador = JugadorController.buscarPorNickname(tfNicknameBuscar.getText());

            // Rellenar el ComboBox de ROL
            llenarComboBoxJugador();

            tfNombreJugadorEditar.setDisable(false);
            tfApellidoJugadorEditar.setDisable(false);
            tfNacionalidadEditar.setDisable(false);
            dpFechaNacimientoEditar.setDisable(false);
            tfNicknameEditar.setDisable(false);
            cbRolEditar.setDisable(false);
            tfSueldoEditar.setDisable(false);
            cbSeleccionEquipoParaJugadorEditar.setDisable(false);
            bEditar.setDisable(false);


            tfNombreJugadorEditar.setText(jugador.getNombre());
            tfApellidoJugadorEditar.setText(jugador.getApellido());
            tfNacionalidadEditar.setText(jugador.getNacionalidad());
            dpFechaNacimientoEditar.setValue(jugador.getFechaNacimiento());
            tfNicknameEditar.setText(jugador.getNickname());
            cbRolEditar.getSelectionModel().select(jugador.getRol());
            tfSueldoEditar.setText(String.valueOf(jugador.getSueldo()));
            if (jugador.getEquipo() != null) {
                cbSeleccionEquipoParaJugadorEditar.getSelectionModel().select(jugador.getEquipo().getNombre());
            }

        } catch (Exception e) {
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /** Función que edita los datos del jugador en la base de datos
     * Recoge los datos introducidos por el usuario en los campos de texto y combo box, los valida utilizando la clase ValidarDatos y luego llama al método editarJugador del controlador JugadorController para actualizar los datos del jugador en la base de datos.
     */
    @FXML
    public void onEditarDatosJugador() {
        try {

            ValidarDatos.validarUsername(tfNicknameEditar.getText());
            ValidarDatos.validarString(tfNombreJugadorEditar.getText());
            ValidarDatos.validarString(tfApellidoJugadorEditar.getText());
            ValidarDatos.validarString(tfNacionalidad.getText());
            ValidarDatos.validarFecha(dpFechaNacimientoEditar.getValue());
            ValidarDatos.validarUsername(tfNicknameEditar.getText());
            ValidarDatos.validarDouble(tfSueldoEditar.getText());

            String nickname = tfNicknameBuscar.getText();
            String nombre = tfNombreJugadorEditar.getText();
            String apellido = tfApellidoJugadorEditar.getText();
            String nacionalidad = tfNacionalidadEditar.getText();
            LocalDate fechaNacimiento = dpFechaNacimientoEditar.getValue();
            String nuevoNickname = tfNicknameEditar.getText();
            String rol = cbRolEditar.getValue();
            double sueldo = Double.parseDouble(tfSueldoEditar.getText());
            String nombreEquipo = cbSeleccionEquipoParaJugadorEditar.getValue();


            /** MENSAJE DE SI QUIERE ACEPTAR LOS CAMBIOS O NO
             * Si el usuario acepta, se llama al método editarJugador del controlador JugadorController para actualizar los datos del jugador en la base de datos y se muestra un mensaje de confirmación. Si el usuario no acepta, no se realizan cambios en la base de datos.
             */
            Optional<ButtonType> result = mostarMensajeConfirmacion("Confirmación", "Confirmación de edición");

            if (result.isPresent() && result.get() == ButtonType.OK) {
                JugadorController.editarJugador(nickname, nombre, apellido, nacionalidad, fechaNacimiento, nuevoNickname, rol, sueldo, nombreEquipo);
                mostarMensaje("Confirmación", "El jugador se ha editado con éxito", Alert.AlertType.INFORMATION);
            }

            vaciarOpcionesJugador();

        } catch (Exception e) {
            tfNombreJugadorEditar.setDisable(true);
            tfApellidoJugadorEditar.setDisable(true);
            tfNacionalidadEditar.setDisable(true);
            dpFechaNacimientoEditar.setDisable(true);
            tfNicknameEditar.setDisable(true);
            cbRolEditar.setDisable(true);
            tfSueldoEditar.setDisable(true);
            cbSeleccionEquipoParaJugadorEditar.setDisable(true);
            bEditar.setDisable(true);

            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /** Función para llenar los ComboBox de ROL y EQUIPO en los formularios de añadir y editar jugador
     * Obtiene la lista de equipos desde el controlador EquipoController y la muestra en el ComboBox de selección de equipo. También rellena el ComboBox de ROL con las opciones disponibles.
     */
    private void llenarComboBoxJugador(){
        try {
            /** Rellenar el apartado de ROL
             *
             */
            cbRol.getItems().addAll("Suport", "AWPer", "IGL", "Lurker", "Rifler", "Entry-flager");

            /** Rellenar el apartado de EQUIPOS
             *
             */
            cbSeleccionEquipoParaJugador.getItems().clear();
            ArrayList<String> nombreEquipos = EquipoController.rellenarComboEquipo();
            for (String nombre : nombreEquipos){
                cbSeleccionEquipoParaJugador.getItems().add(nombre);
            }

            cbRolEditar.getItems().addAll("Suport", "AWPer", "IGL", "Lurker", "Rifler", "Entry-flager");

            /**Rellenar el ComboBox de EQUIPOS
             *
             */
            cbSeleccionEquipoParaJugadorEditar.getItems().clear();
            ArrayList<String> nombreEquiposEditar = EquipoController.rellenarComboEquipo();
            for (String nombre : nombreEquiposEditar){
                cbSeleccionEquipoParaJugadorEditar.getItems().add(nombre);
            }

        }catch (Exception e){
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }


    }

    /** Funciones para apartado de GESTIONAR USUARIOS
     * Función que abre el menu principal de gestion de Usuarios
     * @param MouseEvent
     */
    @FXML
    public void onGestionarUsuarios(MouseEvent MouseEvent){
        mostrarUsuariosYRoles();
      
        apGestionarJugadoresPrincipal.setVisible(false);
        apGestionarJugadoresAnadir.setVisible(false);
        apGestionarJugadoresEditar.setVisible(false);
        apGestionarJugadoresBorrar.setVisible(false);

        apGestionarEquiposPrincipal.setVisible(false);
        apGestionarEquiposAnadir.setVisible(false);

        apGestionarUsuariosPrincipal.setVisible(true);
        apGestionarUsuariosAnadir.setVisible(false);
        apGestionarUsuariosEditar.setVisible(false);
        apGestionarUsuariosBorrar.setVisible(false);

        apVerInformes.setVisible(false);
        spVerJugadores.setVisible(false);
        spVerEquipos.setVisible(false);
        tfBuscarJugadorPorEquipo.clear();

        apCerrarCompeticion.setVisible(false);
        tfTipoPuntuacion.clear();


        apGestionarJugadoresAnadir.setVisible(false);
        apGestionarJugadoresBorrar.setVisible(false);
        apGestionarJugadoresEditar.setVisible(false);
        apGestionarEquiposEditar.setVisible(false);
        apGestionarEquiposBorrar.setVisible(false);
        apGestionarEquiposAnadir.setVisible(false);

        vaciarOpcionesEquipo();
        vaciarOpcionesJugador();
        vaciarOpcionesUsuario();

    }

    /** Función para mostrar la lista de usuarios y sus roles en el apartado de gestionar usuarios
     * Obtiene la lista de usuarios desde el controlador UsuarioController y la muestra en un ListView. Cada elemento del ListView muestra el nombre de usuario y su tipo (admin o estandar).
     */
    @FXML
    public void onAnadirUsuario(MouseEvent MouseEvent) {
        //Activar la ventana
        apGestionarUsuariosAnadir.setVisible(true);

        cbTipoUsuario.getItems().addAll("admin", "estandar");

    }

    /** Función que añade un usuario a la base de datos
     * Recoge los datos introducidos por el usuario en los campos de texto y combo box, los valida utilizando la clase ValidarDatos y luego llama al método anadirUsuario del controlador UsuarioController para insertar el nuevo usuario en la base de datos.
     * @param event
     */
    @FXML
    public void onAnadirDatosUsuario(ActionEvent event) {
        try {

            ValidarDatos.validarUsername(tfNombreUsuario.getText());
            ValidarDatos.validarContrasena(pfContrasena.getText());

            String username = tfNombreUsuario.getText();
            String contrasena = pfContrasena.getText();
            String tipoUsuario = cbTipoUsuario.getValue();

            UsuarioController.anadirUsuario(username, contrasena, tipoUsuario);

            // Mensaje de confimración de que se ha añadido
            mostarMensaje("Confirmación", "El usuario se ha guardado con éxito", Alert.AlertType.INFORMATION);

            vaciarOpcionesUsuario();
            cbTipoUsuario.getItems().addAll("admin", "estandar");


        } catch (Exception e) {
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    /** Función que abre la ventana de eliminar usuario
     * Hace visible el AnchorPane de eliminar usuario y oculta los demás AnchorPane para mostrar solo el apartado de eliminar usuario.
     * @param MouseEvent
     */
    @FXML
    public void onEliminarUsuario(MouseEvent MouseEvent) {
        apGestionarUsuariosBorrar.setVisible(true);
    }

    /** Función que elimina el usuario de la base de datos
     * Recoge el nombre de usuario introducido por el usuario en el campo de texto, lo valida utilizando la clase ValidarDatos y luego llama al método borrarUsuario del controlador UsuarioController para eliminar el usuario de la base de datos.
     */
    @FXML
    public void onBorrarDatosUsuario() {
        try {

            ValidarDatos.validarUsername(tfNombreUsuarioBorrar.getText());

            String username = tfNombreUsuarioBorrar.getText();

            // Panel para que confirme si quiere borrar o no
            Optional<ButtonType> result = mostarMensajeConfirmacion("Confirmación", "Confirmación de borrado");

            if (result.isPresent() && result.get() == ButtonType.OK) {
                UsuarioController.borrarUsuario(username);
                mostarMensaje("Confirmación", "El usuario se ha borrado con éxito", Alert.AlertType.INFORMATION);

            }
            // Si quiere que se haga esto


            vaciarOpcionesUsuario();

        } catch (Exception e) {
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    /** Función que abre la ventana de editar usuario
     * Hace visible el AnchorPane de editar usuario y oculta los demás AnchorPane para mostrar solo el apartado de editar usuario.
     */
    @FXML
    public void onEditarUsuario() {
        tfNombreUsuarioEditar.setDisable(true);
        pfContrasenaEditar.setDisable(true);
        cbTipoUsuarioEditar.setDisable(true);
        bEditarUsuario.setDisable(true);

        apGestionarUsuariosEditar.setVisible(true);

        cbTipoUsuarioEditar.getItems().addAll("admin", "estandar");
    }
    /** Función que busca al usuario por el nombre de usuario para poder poner sus datos en el formulario
     * Recoge el nombre de usuario introducido por el usuario en el campo de texto, lo valida utilizando la clase ValidarDatos y luego llama al método buscarPorNombreUsusario del controlador UsuarioController para obtener los datos del usuario desde la base de datos. Si el usuario existe, rellena los campos del formulario con los datos del usuario para que el usuario pueda editarlos.
     */
    @FXML
    public void onBuscarNombreUsuario() {
        try {

            Usuario usuario = UsuarioController.buscarPorNombreUsusario(tfNombreUsuarioBuscar.getText());

            tfNombreUsuarioEditar.setText(usuario.getNombreUsuario());
            pfContrasenaEditar.setText(usuario.getContrasena());
            if (usuario instanceof Admin) {
                cbTipoUsuarioEditar.getSelectionModel().select("admin");
            } else cbTipoUsuarioEditar.getSelectionModel().select("estandar");

            tfNombreUsuarioEditar.setDisable(false);
            pfContrasenaEditar.setDisable(false);
            cbTipoUsuarioEditar.setDisable(false);
            bEditarUsuario.setDisable(false);

        } catch (Exception e) {
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    /** Función que edita los datos del usuario en la base de datos
     * Recoge los datos introducidos por el usuario en los campos de texto y combo box, los valida utilizando la clase ValidarDatos y luego llama al método editarUsuario del controlador UsuarioController para actualizar los datos del usuario en la base de datos.
     */
    @FXML
    public void onEditarDatosUsuario() {
        try {
            ValidarDatos.validarUsername(tfNombreUsuarioEditar.getText());
            ValidarDatos.validarContrasena(pfContrasenaEditar.getText());

            String username = tfNombreUsuarioBuscar.getText();
            String usernameNuevo = tfNombreUsuarioEditar.getText();
            String contrasena = pfContrasenaEditar.getText();
            String tipoUsuario = cbTipoUsuarioEditar.getValue();

            // Menu para pedir al usuario si quiere realizar los cambios
            Optional<ButtonType> result = mostarMensajeConfirmacion("Confirmación", "Confirmación de edición");

            if (result.isPresent() && result.get() == ButtonType.OK) {
                UsuarioController.editarUsuario(username, usernameNuevo, contrasena, tipoUsuario);
                mostarMensaje("Confirmación", "El usuario se ha editado con éxito", Alert.AlertType.INFORMATION);

            }
            // Si si que pase esto

            vaciarOpcionesUsuario();

            tfNombreUsuarioEditar.setDisable(true);
            pfContrasenaEditar.setDisable(true);
            cbTipoUsuarioEditar.setDisable(true);
            bEditarUsuario.setDisable(true);


        } catch (Exception e) {
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    /** Función que muestra la lista de usuarios y sus roles en el apartado de gestionar usuarios
     * Obtiene la lista de usuarios desde el controlador UsuarioController y la muestra en un ListView. Cada elemento del ListView muestra el nombre de usuario y su tipo (admin o estandar).
     */
    @FXML
    public void onVolverGestionarUsuarios() {
        apGestionarUsuariosAnadir.setVisible(false);
        apGestionarUsuariosBorrar.setVisible(false);
        apGestionarUsuariosEditar.setVisible(false);

        vaciarOpcionesUsuario();
    }

    /** Función que vuelve al menu principal de gestion de jugadores desde añadir, eliminar y editar
     *
     * @param event
     */
    @FXML
    public void onVolverGestionarJugadores(ActionEvent event) {
        apGestionarJugadoresAnadir.setVisible(false);
        apGestionarJugadoresBorrar.setVisible(false);
        apGestionarJugadoresEditar.setVisible(false);

        vaciarOpcionesJugador();
    }

    /** Función que vacia los huecos donde el usuario introduce los datos en Gestión Jugadores
     * Limpia los campos de texto, combo box y date picker utilizados para añadir, eliminar y editar jugadores para que no queden datos residuales al volver al menú principal de gestión de jugadores o al abrir otro apartado.
     */
    private void vaciarOpcionesJugador() {
        tfNombreJugador.clear();
        tfApellidoJugador.clear();
        tfNacionalidad.clear();
        dpFechaNacimiento.setValue(null);
        tfNickname.clear();
        cbRol.getItems().clear();
        tfSueldo.clear();
        cbSeleccionEquipoParaJugador.getItems().clear();

        tfNicknameBorrar.clear();

        tfNicknameBuscar.clear();
        tfNombreJugadorEditar.clear();
        tfApellidoJugadorEditar.clear();
        tfNacionalidadEditar.clear();
        dpFechaNacimientoEditar.setValue(null);
        tfNicknameEditar.clear();
        cbRolEditar.getItems().clear();
        tfSueldoEditar.clear();
        cbSeleccionEquipoParaJugadorEditar.getItems().clear();


    }

    /** Función que vacia los huecos en Gestion Usuarios
     * Limpia los campos de texto, combo box y date picker utilizados para añadir, eliminar y editar usuarios para que no queden datos residuales al volver al menú principal de gestión de usuarios o al abrir otro apartado.
     */
    private void vaciarOpcionesUsuario() {
        tfNombreUsuario.clear();
        pfContrasena.clear();
        cbTipoUsuario.getItems().clear();

        tfNombreUsuarioBorrar.clear();

        tfNombreUsuarioBuscar.clear();
        tfNombreUsuarioEditar.clear();
        pfContrasenaEditar.clear();
        cbTipoUsuarioEditar.getItems().clear();
    }


    /** Función para volver al Inicio de Sesión
     * Muestra la ventana de inicio de sesión y cierra la ventana del menú principal del administrador para que el usuario pueda iniciar sesión con otro usuario o cerrar la aplicación.
     * @param MouseEvent
     */
    @FXML
    public void onCerrarSesion(MouseEvent MouseEvent) {
        controller.show();
        stage.close();
    }

    /** Función que actualiza los contadores de jugadores y equipos del menu principal
     * Llama a los métodos contarJugadores y contarEquipos de los controladores JugadorController y EquipoController para obtener el número total de jugadores y equipos registrados en la base de datos, respectivamente, y actualiza las etiquetas correspondientes en la interfaz gráfica para mostrar estos valores al usuario.
     */
    private void actualizarDatosPanelPrincipal(){
        try {
            lbNombreBienvenida.setText(username);
            lbCantJugadores.setText(JugadorController.contarJugadores());
            lbCantEquipos.setText(EquipoController.contarEquipos());
        }catch (Exception e){
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /** Funciones para apartado GESTION EQUIPOS
     * Función que abre el menu principal de gestion de Equipos
     * @param MouseEvent
     */
    public void onGestionarEquipos(MouseEvent MouseEvent) {
        apGestionarJugadoresPrincipal.setVisible(false);
        apGestionarJugadoresAnadir.setVisible(false);
        apGestionarJugadoresEditar.setVisible(false);
        apGestionarJugadoresBorrar.setVisible(false);

        apGestionarEquiposPrincipal.setVisible(true);
        apGestionarEquiposAnadir.setVisible(false);

        apGestionarUsuariosPrincipal.setVisible(false);
        apGestionarUsuariosAnadir.setVisible(false);
        apGestionarUsuariosEditar.setVisible(false);
        apGestionarUsuariosBorrar.setVisible(false);
        apVerInformes.setVisible(false);
        spVerJugadores.setVisible(false);
        spVerEquipos.setVisible(false);
        tfBuscarJugadorPorEquipo.clear();

        apCerrarCompeticion.setVisible(false);
        tfTipoPuntuacion.clear();


    }
    /** Función que abre el panel añadir equipo
     * Hace visible el AnchorPane de añadir equipo y oculta los demás AnchorPane para mostrar solo el apartado de añadir equipo.
     * @param mouseEvent
     */
    public void onAnadirEquipo(MouseEvent mouseEvent) {
        apGestionarEquiposAnadir.setVisible(true);
        apGestionarEquiposEditar.setVisible(false);
        apGestionarEquiposBorrar.setVisible(false);
    }

    /** Funcion boton volver al apartado GESTIONAR EQUIPOS
     * Hace invisible los AnchorPane de añadir, editar y eliminar equipo para volver al menú principal de gestión de equipos.
     * @param actionEvent
     */
    public void onVolverGestionarEquipos(ActionEvent actionEvent) {
        apGestionarEquiposAnadir.setVisible(false);
        apGestionarEquiposBorrar.setVisible(false);
        apGestionarEquiposEditar.setVisible(false);

        vaciarOpcionesEquipo();
        vaciarOpcionesJugador();
        vaciarOpcionesUsuario();
    }

    /** Funcion anadir equipo al pulsar boton
     * Recoge los datos introducidos por el usuario en los campos de texto y date picker, los valida utilizando la clase ValidarDatos y luego llama al método insertarEquipo del controlador EquipoController para insertar el nuevo equipo en la base de datos.
     * @param actionEvent
     */
    public void onAnadirDatosEquipo(ActionEvent actionEvent) {
        try {
            ValidarDatos.validarUsername(tfNombreEquipo.getText());
            ValidarDatos.validarFecha(dpFechaFundacion.getValue());

            String nombre = tfNombreEquipo.getText();
            LocalDate fechaFundacion = dpFechaFundacion.getValue();

            EquipoController.insertarEquipo(nombre, fechaFundacion);

            vaciarOpcionesEquipo();

            mostarMensaje("Confimración", "El equipo se ha añadido con éxito.", Alert.AlertType.INFORMATION);


        } catch (Exception e) {
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    /** Función para mostrar mensajes de confirmación, error o información al usuario
     * Recibe un título, un mensaje y un tipo de alerta (información, error o confirmación) y muestra una ventana emergente con el mensaje correspondiente al usuario.
     * @param titulo
     * @param mensaje
     * @param alerta
     */
    public void mostarMensaje(String titulo, String mensaje, Alert.AlertType alerta) {

        Alert alert = new Alert(alerta);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.show();

    }
    /** Función para mostrar un mensaje de confirmación al usuario
     * Recibe un título y un mensaje, muestra una ventana emergente con el mensaje de confirmación y devuelve la opción seleccionada por el usuario (Aceptar o Cancelar) para que la función que llama a este método pueda tomar una decisión en función de la respuesta del usuario.
     * @param titulo
     * @param mensaje
     * @return Optional<ButtonType>
     */
    public Optional<ButtonType> mostarMensajeConfirmacion(String titulo, String mensaje) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);

        return alert.showAndWait();
    }

    /** Funcion abrir panel Eliminar Equipo
     * Hace visible el AnchorPane de eliminar equipo y oculta los demás AnchorPane para mostrar solo el apartado de eliminar equipo.
     * @param mouseEvent
     */
    public void onEliminarEquipo (MouseEvent mouseEvent){
            apGestionarEquiposBorrar.setVisible(true);
        }

    /** Funcion borrar equipo al pulsar boton
     * Recoge el nombre del equipo introducido por el usuario en el campo de texto, lo valida utilizando la clase ValidarDatos y luego llama al método borrarEquipo del controlador EquipoController para eliminar el equipo de la base de datos.
     * @param actionEvent
     */
    public void onBorrarDatosEquipo (ActionEvent actionEvent){
            try {

                ValidarDatos.validarUsername(tfNombreEquipoBorrar.getText());

                String nombre = tfNombreEquipoBorrar.getText();

                // Panel para que confirme si quiere borrar o no
                // Si quiere que se haga esto
                EquipoController.borrarEquipo(nombre);

                vaciarOpcionesEquipo();

                mostarMensaje("Confirmación", "El equipo se ha borrado con éxito.", Alert.AlertType.INFORMATION);


            } catch (Exception e) {
                mostarMensaje("Confimración", e.getMessage(), Alert.AlertType.ERROR);            }
        }

    /** Funcion abrir panel Editar Equipo
     * Hace visible el AnchorPane de editar equipo y oculta los demás AnchorPane para mostrar solo el apartado de editar equipo. Además, desactiva los campos de texto y date picker para que el usuario no pueda editarlos hasta que busque un equipo existente.
     * @param mouseEvent
     */
    public void onEditarEquipo (MouseEvent mouseEvent){


        tfNombreEquipoEditar.setDisable(true);
        dpFechaFundacionEditar.setDisable(true);
        bEditarEquipo.setDisable(true);

        apGestionarEquiposEditar.setVisible(true);
    }

    /** Funcion buscar el equipo a editar
     * Recoge el nombre del equipo introducido por el usuario en el campo de texto, lo valida utilizando la clase ValidarDatos y luego llama al método equipoPorNombre del controlador EquipoController para obtener los datos del equipo desde la base de datos. Si el equipo existe, rellena los campos del formulario con los datos del equipo para que el usuario pueda editarlos y activa los campos de texto y date picker para que el usuario pueda modificarlos.
     * @param actionEvent
     */
    public void onBuscarNombreEquipo (ActionEvent actionEvent){
            try {
                Equipo equipo = EquipoController.equipoPorNombre(tfNombreEquipoBuscar.getText());

                tfNombreEquipoEditar.setDisable(false);
                dpFechaFundacionEditar.setDisable(false);
                bEditarEquipo.setDisable(false);

                tfNombreEquipoEditar.setText(equipo.getNombre());
                dpFechaFundacionEditar.setValue(equipo.getFechaFundacion());

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    /** Funcion editar equipo al pulsar boton
     * Recoge los datos introducidos por el usuario en los campos de texto y date picker, los valida utilizando la clase ValidarDatos y luego llama al método editarEquipo del controlador EquipoController para actualizar los datos del equipo en la base de datos.
     * @param actionEvent
     */
    public void onEditarDatosEquipo (ActionEvent actionEvent){
            try {
                ValidarDatos.validarUsername(tfNombreEquipoEditar.getText());
                ValidarDatos.validarFecha(dpFechaNacimientoEditar.getValue());

                String nombre = tfNombreEquipoEditar.getText();
                LocalDate fechaFundacion = dpFechaFundacionEditar.getValue();

                EquipoController.editarEquipo(tfNombreEquipoBuscar.getText(), nombre, fechaFundacion);

                mostarMensaje("Confimración", "El equipo se ha editado con éxito.", Alert.AlertType.INFORMATION);

                vaciarOpcionesEquipo();

            } catch (Exception e) {
                mostarMensaje("Confimración", e.getMessage(), Alert.AlertType.ERROR);
            }
        }

    /** Funcion vaciar todos los apartados de gestionar equipos
     * Limpia los campos de texto y date picker utilizados para añadir, eliminar y editar equipos para que no queden datos residuales al volver al menú principal de gestión de equipos o al abrir otro apartado.
     */
    public void vaciarOpcionesEquipo(){
        tfNombreEquipo.clear();
        dpFechaFundacion.setValue(null);
        tfNombreEquipoBorrar.clear();
        tfNombreEquipoBuscar.clear();
        tfNombreEquipoEditar.clear();
        dpFechaFundacionEditar.setValue(null);
    }

    /** Funcion para mostrar el panel ver informes
     * Hace visible el AnchorPane de ver informes y oculta los demás AnchorPane para mostrar solo el apartado de ver informes. Además, limpia el campo de texto utilizado para buscar jugadores por equipo en el panel de ver informes para que no queden datos residuales al abrir el panel.
     * @param actionEvent
     */
    public void onVerInformes(MouseEvent actionEvent) {
        apVerInformes.setVisible(true);
        spVerEquipos.setVisible(false);
        spVerJugadores.setVisible(false);

        apCerrarCompeticion.setVisible(false);
        tfTipoPuntuacion.clear();
    }

    /** Funcion para mostrar el panel con los jugadores al pulsar el boton ver jugadores
     * Hace visible el ScrollPane que contiene la lista de jugadores para mostrarla al usuario.
     * @param mouseEvent
     */
    public void onVerJugadores(MouseEvent mouseEvent) {
        spVerJugadores.setVisible(true);
    }

    /** Funcion para crear la vbox de cada jugador a mostrar
     * Recibe un objeto Jugador y crea una VBox con los datos del jugador formateados para mostrarlo en el panel de ver informes. La VBox incluye el nickname, nombre, apellido, fecha de nacimiento, rol, sueldo y equipo del jugador, y se le aplica un estilo visual para que se vea como una carta.
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
    /** Funcion para buscar jugadores por equipo al pulsar el boton
     * Recoge el nombre del equipo introducido por el usuario en el campo de texto, lo valida utilizando la clase ValidarDatos y luego llama al método verJugadoresPorEquipo del controlador JugadorController para obtener la lista de jugadores que pertenecen a ese equipo desde la base de datos. Si existen jugadores para ese equipo, llama a la función rellenarVerJugadores para mostrar la lista de jugadores en el panel de ver informes. Si no existe ningún equipo con ese nombre o no hay jugadores para ese equipo, muestra un mensaje de error al usuario.
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
     * Recibe una lista de jugadores y recorre la lista para crear una fila (HBox) cada dos jugadores, dentro de cada fila se añaden las VBoxes creadas por la función crearCartasJugador para mostrar los datos de cada jugador. Si el número de jugadores es impar, se añade un espacio vacío al final de la última fila para que la presentación visual sea más equilibrada. Si no hay jugadores para mostrar, se muestra un mensaje indicando que no hay jugadores disponibles.
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
            mostarMensaje("Confimración", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /** Funcion para volver al panel de ver informes
     * Hace invisible los ScrollPane que contienen las listas de jugadores y equipos para volver al menú principal de ver informes. Además, limpia el campo de texto utilizado para buscar jugadores por equipo para que no queden datos residuales al volver al menú principal de ver informes.
     * @param actionEvent
     */
    public void onVolverVerInformes(ActionEvent actionEvent) {
        spVerJugadores.setVisible(false);
        spVerEquipos.setVisible(false);
        tfBuscarJugadorPorEquipo.clear();
    }

    /** Funcion para mostrar el panel de ver equipos al pulsar el boton
     * Hace visible el ScrollPane que contiene la lista de equipos para mostrarla al usuario.
     * @param mouseEvent
     */
    public void onVerEquipos(MouseEvent mouseEvent) {
        rellenarVerEquipos();
        spVerEquipos.setVisible(true);
    }

    /** Funcion para crear la carta por cada equipo
     * Recibe un objeto EquipoInforme (que contiene los datos del equipo y estadísticas como el número de jugadores, sueldo máximo, sueldo mínimo y sueldo medio) y crea una VBox con los datos del equipo formateados para mostrarlo en el panel de ver informes. La VBox incluye el nombre del equipo, fecha de fundación, número de jugadores, sueldo máximo, sueldo mínimo y sueldo medio, y se le aplica un estilo visual para que se vea como una carta.
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
     * Recibe una lista de equipos y recorre la lista para crear una fila (HBox) cada dos equipos, dentro de cada fila se añaden las VBoxes creadas por la función crearCartasEquipo para mostrar los datos de cada equipo. Si el número de equipos es impar, se añade un espacio vacío al final de la última fila para que la presentación visual sea más equilibrada. Si no hay equipos para mostrar, se muestra un mensaje indicando que no hay equipos disponibles.
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

    /** Funcion para cerrar la competición
     * Hace visible el AnchorPane de cerrar competición y oculta los demás AnchorPane para mostrar solo el apartado de cerrar competición. Además, limpia el campo de texto utilizado para introducir el tipo de puntuación para que no queden datos residuales al abrir el panel.
     * @param MouseEvent
     */
    @FXML
    public void onCerrarCompeticion(MouseEvent MouseEvent){

        apGestionarJugadoresPrincipal.setVisible(false);
        apGestionarJugadoresAnadir.setVisible(false);
        apGestionarJugadoresEditar.setVisible(false);
        apGestionarJugadoresBorrar.setVisible(false);

        apGestionarEquiposPrincipal.setVisible(false);
        apGestionarEquiposAnadir.setVisible(false);

        apGestionarUsuariosPrincipal.setVisible(false);
        apGestionarUsuariosAnadir.setVisible(false);
        apGestionarUsuariosEditar.setVisible(false);
        apGestionarUsuariosBorrar.setVisible(false);

        apCerrarCompeticion.setVisible(true);
    }

    /** Funcion para cerrar la competición al pulsar el boton de cerrar competición
     * Recoge el tipo de puntuación introducido por el usuario en el campo de texto
     * Valida el tipo de puntuación utilizando la clase ValidarDatos y luego llama al método cerrarCompeticion del controlador CompeticionController para cerrar la competición en la base de datos. Después, calcula las fechas de inicio y fin de la competición en función del número de equipos registrados, llama a las funciones crearJornadas y crearEnfrentamientos para generar las jornadas y enfrentamientos de la competición, muestra un mensaje de confirmación al usuario y vuelve al menú principal del administrador.
     * @param actionEvent
     */
    @FXML
    public void onCerrarCompeticionAceptada(ActionEvent actionEvent){

        Optional<ButtonType> result =  mostarMensajeConfirmacion("Confirmación", "Confirmación para cerrar la Competición");

        if (result.isPresent() && result.get() == ButtonType.OK){
            try {

                ValidarDatos.validarString(tfTipoPuntuacion.getText());

                String tipoPuntuacion = tfTipoPuntuacion.getText();

                LocalDate fechaInicio = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

                int numeroJornadas = Integer.parseInt(EquipoController.contarEquipos()) - 1;

                LocalDate fechaFin = fechaInicio.plusDays(7 * (long) numeroJornadas);

                System.out.println("ahora");

                CompeticionController.cerrarCompeticion(fechaInicio, fechaFin, tipoPuntuacion);

                System.out.println("despues");

                crearJornadas(fechaInicio, numeroJornadas);

                crearEnfrentamientos(numeroJornadas);

                controller.show();
                stage.close();
                mostarMensaje("Confirmación", "La competición se ha cerrado con éxito", Alert.AlertType.INFORMATION);

                //AQUI

            }catch (Exception e){
                mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }
    /** Funcion para crear las jornadas de la competición
     * Recibe la fecha de inicio de la competición y el número de jornadas a crear, y llama al método crearJornada del controlador JornadaController para crear cada jornada en la base de datos con la fecha correspondiente (cada jornada se programa una semana después de la anterior).
     * @param fechaInicio
     * @param numeroEtapas
     * @throws Exception
     */
    public void crearJornadas(LocalDate fechaInicio, int numeroEtapas) throws Exception{

        for (int i = 1; i < numeroEtapas + 1; i++) {

            JornadaController.crearJornada(fechaInicio, i );

            fechaInicio = fechaInicio.plusDays(7);

        }
    }

    /** Funcion para crear los enfrentamientos de la competición
     * Recibe el número de jornadas creadas y para cada jornada llama al método crearEnfrentamiento del controlador EnfrentamientoController para crear los enfrentamientos correspondientes a esa jornada en la base de datos. Cada enfrentamiento se programa con una hora de inicio que comienza a las 14:00 para el primer enfrentamiento de la jornada y se va incrementando en una hora para cada enfrentamiento siguiente. Después de crear los enfrentamientos, llama a la función distribuirEquiposPorEnfrentamientos para asignar los equipos a cada enfrentamiento de forma equitativa.
     * @param numeroEtapas
     * @throws Exception
     */
    public void crearEnfrentamientos(int numeroEtapas) throws Exception{

        ArrayList<Jornada> jornadas = JornadaController.listarJornadas();
        int numeroEnfrentamientos = (numeroEtapas + 1) / 2;

        for (Jornada jornada : jornadas) {

            LocalTime horaEnfrentamiento = LocalTime.of(14, 0);

            for (int j = 0; j < numeroEnfrentamientos; j++) {

                EnfrentamientoController.crearEnfrentamiento(horaEnfrentamiento, jornada.getIdJornada());
                horaEnfrentamiento = horaEnfrentamiento.plusHours(1);

            }
        }
        distribuirEquiposPorEnfrentamientos(jornadas);
    }
    /** Funcion para distribuir los equipos por enfrentamientos
     * Recibe la lista de jornadas creadas y para cada jornada obtiene la lista de enfrentamientos correspondientes a esa jornada desde la base de datos. Luego, asigna los equipos a cada enfrentamiento de forma equitativa utilizando una estrategia de distribución que asigna el primer equipo al primer enfrentamiento, el segundo equipo al segundo enfrentamiento, y así sucesivamente, hasta que se asignan todos los equipos. Después de asignar los equipos a los enfrentamientos, llama al método crearResultado del controlador ResultadoController para crear un resultado inicial para cada equipo en cada enfrentamiento.
     * @param jornadas
     * @throws Exception
     */
    public void distribuirEquiposPorEnfrentamientos(ArrayList<Jornada> jornadas) throws Exception{

        ArrayList<Equipo> equipos = EquipoController.listarEquipos();


        for (Jornada jornada : jornadas){

            ArrayList<Enfrentamiento> enfrentamientos = EnfrentamientoController.buscarPorJornada(jornada.getIdJornada());
            int i = 0;
            for (Enfrentamiento enfrentamiento : enfrentamientos){

                ResultadoController.crearResultado(equipos.get(i).getIdEquipo(), enfrentamiento.getIdEnfrentamiento());
                ResultadoController.crearResultado(equipos.get(equipos.size() - i - 1).getIdEquipo(), enfrentamiento.getIdEnfrentamiento());

                i++;
            }

            Equipo equipoCambiar = equipos.get(1);
            equipos.remove(equipoCambiar);
            equipos.add(equipoCambiar);
        }
    }


    /** Esta función muestra los usuarios y sus roles en la tabla de gestionar usuarios
     * Obtiene la lista de usuarios desde el controlador UsuarioController y la muestra en un ListView. Cada elemento del ListView muestra el nombre de usuario y su tipo (admin o estandar). Si ocurre algún error al obtener los usuarios o mostrar la información, se muestra un mensaje de error al usuario.
     */

    public void mostrarUsuariosYRoles(){
        try {
            ObservableList<Usuario> usuarios = UsuarioDAO.obtenerUsuarios();

            listUsuarios.setItems(usuarios);
            listUsuarios.setCellFactory(param -> new ListCell<Usuario>() {
                @Override
                protected void updateItem(Usuario usuario, boolean empty) {
                    super.updateItem(usuario, empty);
                    if (empty || usuario == null) {
                        setText(null);
                    } else {
                        String tipo = (usuario instanceof Admin) ? "admin" : "estandar";
                        setText(usuario.getNombreUsuario() + " - " + tipo);
                    }
                }
            });




        } catch (Exception e) {
            System.out.println();
            mostarMensaje("Confimración", "Error al mostrar los usuarios y roles en la tabla", Alert.AlertType.ERROR);
        }
    }

}
