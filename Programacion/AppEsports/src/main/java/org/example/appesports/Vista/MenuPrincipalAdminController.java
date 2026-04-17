package org.example.appesports.Vista;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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


public class MenuPrincipalAdminController {

    private String username;

    @FXML
    public Label lbCantEquipos;

    @FXML
    public Label lbCantJugadores;

    @FXML
    public Label lbNombreBienvenida;

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

    // Datos para eliminar Jugador
    @FXML
    public AnchorPane apGestionarJugadoresBorrar;

    @FXML
    public TextField tfNicknameBorrar;

    @FXML
    public TextField tfNicknameBuscar;

    // Datos para editar Jugador
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

        // Variables para añadir usuarios
        @FXML
        public TextField tfNombreUsuario;

    @FXML
    public PasswordField pfContrasena;

    @FXML
    public ComboBox<String> cbTipoUsuario;

    // Variables para eliminar usuarios
    @FXML
    public TextField tfNombreUsuarioBorrar;

    // Variables para editar usuarios
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

    //Variables para gestion equipos
    @FXML
    public AnchorPane apGestionarEquiposPrincipal;

    //Variables para anadir equipo
    @FXML
    public AnchorPane apGestionarEquiposAnadir;

    @FXML
    public TextField tfNombreEquipo;

    @FXML
    public DatePicker dpFechaFundacion;

    //Variables para borrar equipos
    @FXML
    public AnchorPane apGestionarEquiposBorrar;

    @FXML
    public TextField tfNombreEquipoBorrar;

    //Variables para editar equipos
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
        public ScrollPane spVerJugadores;

        @FXML
        public VBox vboxContenedorJugadores;

        @FXML
        public ScrollPane spVerEquipos;

        @FXML
        public VBox vboxContenedorEquipos;

        public TextField tfTipoPuntuacion;

        // Variables para mostrar usuarios y roles en una Lista
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

    // Funciones para apartado de GESTIONAR JUGADORES
    // Función para volver al menu principal desde cualquiera de los menus secundarios
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

    // Función que abre el menu principal de gestion de Jugadores
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

        apCerrarCompeticion.setVisible(false);
        tfTipoPuntuacion.clear();

        apGestionarEquiposEditar.setVisible(false);
        apGestionarEquiposBorrar.setVisible(false);
        apGestionarEquiposAnadir.setVisible(false);

        vaciarOpcionesEquipo();
        vaciarOpcionesJugador();
        vaciarOpcionesUsuario();

    }

    // Función que abre la ventana de añadir jugador
    @FXML
    public void onAnadirJugador(MouseEvent mouseEvent) {
        apGestionarJugadoresAnadir.setVisible(true);

        llenarComboBoxJugador();

    }

    // Función que añade un jugador a la base de datos
    @FXML
    public void onAnadirDatosJugador(ActionEvent event) {

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

            JugadorController.anadirJugador(nombre, apellido, nacionalidad, fechaNacimiento, nickname, rol, sueldo, nombreEquipo);

            // AQUI TIENE QUE MOSTRARSE UNA VENTANA QUE PONGA QUE SE CONFIRMA QUE SE HA AÑADIDO
            mostarMensaje("Confirmación", "El jugador se ha guardado con éxito", Alert.AlertType.INFORMATION);

            vaciarOpcionesJugador();
            llenarComboBoxJugador();

        } catch (Exception e) {
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Función que abre la ventana de eliminar Jugador
    @FXML
    public void onEliminarJugador(MouseEvent mouseEvent) {

        apGestionarJugadoresBorrar.setVisible(true);
    }

    // Función que elimina el jugador de la base de datos
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


    // Función que abre la ventana de editar Jugador
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

    // Función que busca al jugador por el nickname para poder poner sus datos en el formulario
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

    // Función que edita los datos del jugador en la base de datos
    @FXML
    public void onEditarDatosJugador() {
        try {
            String nickname = tfNicknameBuscar.getText();
            String nombre = tfNombreJugadorEditar.getText();
            String apellido = tfApellidoJugadorEditar.getText();
            String nacionalidad = tfNacionalidadEditar.getText();
            LocalDate fechaNacimiento = dpFechaNacimientoEditar.getValue();
            String nuevoNickname = tfNicknameEditar.getText();
            String rol = cbRolEditar.getValue();
            double sueldo = Double.parseDouble(tfSueldoEditar.getText());
            String nombreEquipo = cbSeleccionEquipoParaJugadorEditar.getValue();


            // MENSAJE DE SI QUIERE ACEPTAR LOS CAMBIOS O NO
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

    private void llenarComboBoxJugador(){
        // Rellenar el apartado de ROL
        cbRol.getItems().addAll("Suport", "AWPer", "IGL", "Lurker", "Rifler", "Entry-flager");

        //Rellenar el apartado de EQUIPOS
        cbSeleccionEquipoParaJugador.getItems().clear();
        ArrayList<String> nombreEquipos = EquipoController.rellenarComboEquipo();
        for (String nombre : nombreEquipos){
            cbSeleccionEquipoParaJugador.getItems().add(nombre);
        }

        cbRolEditar.getItems().addAll("Suport", "AWPer", "IGL", "Lurker", "Rifler", "Entry-flager");

        //Rellenar el ComboBox de EQUIPOS
        cbSeleccionEquipoParaJugadorEditar.getItems().clear();
        ArrayList<String> nombreEquiposEditar = EquipoController.rellenarComboEquipo();
        for (String nombre : nombreEquiposEditar){
            cbSeleccionEquipoParaJugadorEditar.getItems().add(nombre);
        }


    }

    // Funciones para apartado de GESTIONAR USUARIOS
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

    @FXML
    public void onAnadirUsuario(MouseEvent MouseEvent) {
        //Activar la ventana
        apGestionarUsuariosAnadir.setVisible(true);

        cbTipoUsuario.getItems().addAll("admin", "estandar");

    }

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

    @FXML
    public void onEliminarUsuario(MouseEvent MouseEvent) {
        apGestionarUsuariosBorrar.setVisible(true);
    }

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

    @FXML
    public void onEditarUsuario() {
        tfNombreUsuarioEditar.setDisable(true);
        pfContrasenaEditar.setDisable(true);
        cbTipoUsuarioEditar.setDisable(true);
        bEditarUsuario.setDisable(true);

        apGestionarUsuariosEditar.setVisible(true);

        cbTipoUsuarioEditar.getItems().addAll("admin", "estandar");
    }

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

    @FXML
    public void onVolverGestionarUsuarios() {
        apGestionarUsuariosAnadir.setVisible(false);
        apGestionarUsuariosBorrar.setVisible(false);
        apGestionarUsuariosEditar.setVisible(false);

        vaciarOpcionesUsuario();
    }

    // Función que vuelve al menu principal de gestion de jugadores desde añadir, eliminar y editar
    @FXML
    public void onVolverGestionarJugadores(ActionEvent event) {
        apGestionarJugadoresAnadir.setVisible(false);
        apGestionarJugadoresBorrar.setVisible(false);
        apGestionarJugadoresEditar.setVisible(false);

        vaciarOpcionesJugador();
    }

    // Función que vacia los huecos donde el usuario introduce los datos en Gestión Jugadores
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

    // Función que vacia los huecos en Gestion Usuarios
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


    // Función para volver al Inicio de Sesión
    @FXML
    public void onCerrarSesion(MouseEvent MouseEvent) {
        controller.show();
        stage.close();
    }

    // Función que actualiza los contadores de jugadores y equipos del menu principal
    private void actualizarDatosPanelPrincipal() {
        lbNombreBienvenida.setText(username);
        lbCantJugadores.setText(JugadorController.contarJugadores());
        lbCantEquipos.setText(EquipoController.contarEquipos());
    }

    //Funciones para apartado GESTION EQUIPOS
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

        apCerrarCompeticion.setVisible(false);
        tfTipoPuntuacion.clear();


    }

    public void onAnadirEquipo(MouseEvent mouseEvent) {
        apGestionarEquiposAnadir.setVisible(true);
        apGestionarEquiposEditar.setVisible(false);
        apGestionarEquiposBorrar.setVisible(false);
    }

    //Funcion boton volver al apartado GESTIONAR EQUIPOS
    public void onVolverGestionarEquipos(ActionEvent actionEvent) {
        apGestionarEquiposAnadir.setVisible(false);
        apGestionarEquiposBorrar.setVisible(false);
        apGestionarEquiposEditar.setVisible(false);

        vaciarOpcionesEquipo();
        vaciarOpcionesJugador();
        vaciarOpcionesUsuario();
    }

    //Funcion anadir equipo al pulsar boton
    public void onAnadirDatosEquipo(ActionEvent actionEvent) {
        try {
            ValidarDatos.validarUsername(tfNombreEquipo.getText());

            String nombre = tfNombreEquipo.getText();
            LocalDate fechaFundacion = dpFechaFundacion.getValue();

            EquipoController.insertarEquipo(nombre, fechaFundacion);

            vaciarOpcionesEquipo();


        } catch (Exception e) {
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void mostarMensaje(String titulo, String mensaje, Alert.AlertType alerta) {

        Alert alert = new Alert(alerta);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.show();

    }

    public Optional<ButtonType> mostarMensajeConfirmacion(String titulo, String mensaje) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);

        return alert.showAndWait();
    }

    //Funcion abrir panel Eliminar Equipo
    public void onEliminarEquipo(MouseEvent mouseEvent) {
        apGestionarEquiposBorrar.setVisible(true);
    }
        //Funcion abrir panel Eliminar Equipo
        public void onEliminarEquipo (MouseEvent mouseEvent){
            apGestionarEquiposBorrar.setVisible(true);
        }

        //Funcion borrar equipo al pulsar boton
        public void onBorrarDatosEquipo (ActionEvent actionEvent){
            try {

                ValidarDatos.validarString(tfNombreEquipoBorrar.getText());

                String nombre = tfNombreEquipoBorrar.getText();

                // Panel para que confirme si quiere borrar o no
                // Si quiere que se haga esto
                EquipoController.borrarEquipo(nombre);

                vaciarOpcionesEquipo();

            } catch (Exception e) {
                System.out.println("Error al borrar Equipo");
            }
        }

        //Funcion abrir panel Editar Equipo
        public void onEditarEquipo (MouseEvent mouseEvent){

            tfNombreEquipoEditar.setDisable(true);
            dpFechaFundacionEditar.setDisable(true);
            bEditarEquipo.setDisable(true);

            apGestionarEquiposEditar.setVisible(true);
        }

        //Funcion buscar el equipo a editar
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

        //Funcion editar equipo al pulsar boton
        public void onEditarDatosEquipo (ActionEvent actionEvent){
            try {
                ValidarDatos.validarUsername(tfNombreEquipoEditar.getText());

                String nombre = tfNombreEquipoEditar.getText();
                LocalDate fechaFundacion = dpFechaFundacionEditar.getValue();

                EquipoController.editarEquipo(tfNombreEquipoBuscar.getText(), nombre, fechaFundacion);

                vaciarOpcionesEquipo();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
       
    //Funcion vaciar todos los apartados de gestionar equipos
    public void vaciarOpcionesEquipo(){
        tfNombreEquipo.clear();
        dpFechaFundacion.setValue(null);
        tfNombreEquipoBorrar.clear();
        tfNombreEquipoBuscar.clear();
        tfNombreEquipoEditar.clear();
        dpFechaFundacionEditar.setValue(null);
    }

    //Funcion para mostrar el panel ver informes
    public void onVerInformes(MouseEvent actionEvent) {
        apVerInformes.setVisible(true);
        spVerEquipos.setVisible(false);
        spVerJugadores.setVisible(false);
    }

    //Funcion para mostrar el panel con los jugadores al pulsar el boton ver jugadores
    public void onVerJugadores(MouseEvent mouseEvent) {
        rellenarVerJugadores();
        spVerJugadores.setVisible(true);
    }

    //Funcion para crear la vbox de cada jugador a mostrar
    public Node crearCartasJugador(Jugador jugador) {
        VBox carta = new VBox();
        carta.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0)");
        carta.setPadding(new Insets(15));
        carta.setPrefWidth(400);


        Label nickname = new Label();
        nickname.setText(jugador.getNickname());
        nickname.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        nickname.setTextFill(Color.web("#0089ed"));

        Label nombre = new Label();
        nombre.setText("Nombre: " + jugador.getNombre());

        Label apellido = new Label();
        apellido.setText("Apellido: " + jugador.getApellido());

        Label fecha = new Label();
        fecha.setText("Fecha de nacimiento: " + jugador.getFechaNacimiento().toString());

        Label rol = new Label();
        rol.setText("Rol: " + jugador.getRol());

        Label sueldo = new Label();
        sueldo.setText("Sueldo: " + jugador.getSueldo() + "€");

        Label equipo = new Label();
        equipo.setText("Equipo: " + (jugador.getEquipo() != null ? jugador.getEquipo().getNombre() : "Sin equipo"));

        carta.getChildren().addAll(nickname, nombre, apellido, fecha, rol, sueldo, equipo);
        return carta;
    }

    //Funcion para recorrer los jugadores y ir creando la vbox por cada jugador
    public void rellenarVerJugadores(){
        try {
            vboxContenedorJugadores.getChildren().clear();
            ArrayList<Jugador> jugadores = JugadorController.verJugadores();
            for (int i = 0; i < jugadores.size(); i += 2) {

                HBox fila = new HBox(30);

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
            System.out.println(e.getMessage());
        }
    }

    //Funcion para volver al panel de ver informes
    public void onVolverVerInformes(ActionEvent actionEvent) {
        spVerJugadores.setVisible(false);
        spVerEquipos.setVisible(false);
    }

    //Funcion para mostrar el panel de ver equipos al pulsar el boton
    public void onVerEquipos(MouseEvent mouseEvent) {
        rellenarVerEquipos();
        spVerEquipos.setVisible(true);
    }

    //Funcion para crear la carta por cada equipo
    public Node crearCartasEquipo(Equipo equipo) {
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
        numeroJugadores.setText("Numero de Jugadores: " + equipo.getJugadores().size());

        carta.getChildren().addAll(nombre, fecha, numeroJugadores);
        return carta;
    }

    //Funcion para reccorrer los equipos y crear la carta por cada equipo
    public void rellenarVerEquipos() {
        try {
            vboxContenedorEquipos.getChildren().clear();
            ArrayList<Equipo> equipos = EquipoController.listarEquipos();
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
            System.out.println(e.getMessage());
        }
    }
    //Funcion para cerrar la competición
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

                mostarMensaje("Confirmación", "La competición se ha cerrado con éxito", Alert.AlertType.INFORMATION);

            }catch (Exception e){
                mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    public void crearJornadas(LocalDate fechaInicio, int numeroEtapas) throws Exception{

        for (int i = 1; i < numeroEtapas + 1; i++) {

            JornadaController.crearJornada(fechaInicio, i );

            fechaInicio = fechaInicio.plusDays(7);

        }
    }

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



        // Función que muestra los usuarios y roles en la tabla

    public void mostrarUsuariosYRoles(){
        try {
            ObservableList<Usuario> usuarios = UsuarioDAO.obtenerUsuarios();
           // Vamos a comprobar que se han obtenido correctamente los usuarios
            for (Usuario usuario : usuarios) {
                System.out.println("Usuario: " + usuario.getNombreUsuario() + ", Contraseña: " + usuario.getContrasena());
            }


        // 3. Finalmente, le pasamos la lista a la Lista que se vera por pantalla
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
            System.out.println("Error al mostrar los usuarios y roles en la tabla");
        }
    }

}
