package org.example.appesports.Vista;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.appesports.Controlador.*;
import javafx.scene.layout.AnchorPane;
import org.example.appesports.Modelo.Admin;
import org.example.appesports.Modelo.Equipo;
import org.example.appesports.Modelo.Jugador;
import org.example.appesports.Modelo.Usuario;
import org.example.appesports.Utilidades.ValidarDatos;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Optional;

public class MenuPrincipalAdminController {

        private String username;

        @FXML
        public Label lbCantEquipos;

        @FXML
        public Label lbCantJugadores;

        @FXML
        public Label lbNombreBienvenida;

        @FXML
        public Button bGestionarUsuarios;

        @FXML
        public Button bGestionarEquipos;

        @FXML
        public Button bGestionarJugadores;

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



    public Stage stage;
    public MenuInicioSesionController controller;

    public void init (Stage stage, MenuInicioSesionController menu, String username){
        this.controller = menu;
        this.stage = stage;
        this.username = username;

        actualizarDatosPanelPrincipal();
    }

    // Funciones para apartado de GESTIONAR JUGADORES
    // Función para volver al menu principal desde cualquiera de los menus secundarios
    @FXML
    public void onVolverMenuPrincipal(ActionEvent event){

        actualizarDatosPanelPrincipal();
        apGestionarJugadoresPrincipal.setVisible(false);
        apGestionarUsuariosPrincipal.setVisible(false);
        apGestionarEquiposPrincipal.setVisible(false);

        bGestionarJugadores.setTextFill(Color.BLACK);
        bGestionarJugadores.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        bGestionarUsuarios.setTextFill(Color.BLACK);
        bGestionarUsuarios.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        bGestionarEquipos.setTextFill(Color.BLACK);
        bGestionarEquipos.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
    }

    // Función que abre el menu principal de gestion de Jugadores
    @FXML
    public void onGestionarJugadores(ActionEvent event) {

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


        bGestionarJugadores.setTextFill(Color.WHITE);
        bGestionarJugadores.setStyle("-fx-background-color: #0086ed; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        bGestionarUsuarios.setTextFill(Color.BLACK);
        bGestionarUsuarios.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        bGestionarEquipos.setTextFill(Color.BLACK);
        bGestionarEquipos.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");

        apGestionarEquiposEditar.setVisible(false);
        apGestionarEquiposBorrar.setVisible(false);
        apGestionarEquiposAnadir.setVisible(false);

        vaciarOpcionesJugador();

    }

    // Función que abre la ventana de añadir jugador
    @FXML
    public void onAnadirJugador(MouseEvent mouseEvent) {
        apGestionarJugadoresAnadir.setVisible(true);

        // Rellenar el apartado de ROL
        cbRol.getItems().addAll("Suport", "AWPer", "IGL", "Lurker", "Rifler", "Entry-flager");

        //Rellenar el apartado de EQUIPOS
        cbSeleccionEquipoParaJugador.getItems().clear();
        ArrayList<String> nombreEquipos = EquipoController.rellenarComboEquipo();
        for (String nombre : nombreEquipos){
            cbSeleccionEquipoParaJugador.getItems().add(nombre);
        }
    }

    // Función que añade un jugador a la base de datos
    @FXML
    public void onAnadirDatosJugador(ActionEvent event){

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

        } catch (Exception e) {
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Función que abre la ventana de eliminar Jugador
    @FXML
    public void onEliminarJugador(MouseEvent mouseEvent){

        apGestionarJugadoresBorrar.setVisible(true);
    }

    // Función que elimina el jugador de la base de datos
    @FXML
    public void onBorrarDatosJugador(ActionEvent event){
        try {

            // MENSAJE DE CONFIMRACION
            Optional<ButtonType> result =  mostarMensajeConfirmacion("Confirmación", "Confirmación de borrado");

            if (result.isPresent() && result.get() == ButtonType.OK){
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
    public void onEditarJugador(MouseEvent mouseEvent){
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
    public void onBuscarNickname(ActionEvent event){

        try {
            Jugador jugador = JugadorController.buscarPorNickname(tfNicknameBuscar.getText());

            // Rellenar el ComboBox de ROL
            cbRolEditar.getItems().addAll("Suport", "AWPer", "IGL", "Lurker", "Rifler", "Entry-flager");

            //Rellenar el ComboBox de EQUIPOS
            cbSeleccionEquipoParaJugadorEditar.getItems().clear();
            ArrayList<String> nombreEquipos = EquipoController.rellenarComboEquipo();
            for (String nombre : nombreEquipos){
                cbSeleccionEquipoParaJugadorEditar.getItems().add(nombre);
            }

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
            if (jugador.getEquipo() != null){
                cbSeleccionEquipoParaJugadorEditar.getSelectionModel().select(jugador.getEquipo().getNombre());
            }

        } catch (Exception e) {
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Función que edita los datos del jugador en la base de datos
    @FXML
    public void onEditarDatosJugador(){
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
            Optional<ButtonType> result =  mostarMensajeConfirmacion("Confirmación", "Confirmación de edición");

            if (result.isPresent() && result.get() == ButtonType.OK){
                JugadorController.editarJugador(nickname, nombre, apellido, nacionalidad, fechaNacimiento, nuevoNickname, rol, sueldo, nombreEquipo);
                mostarMensaje("Confirmación", "El jugador se ha editado con éxito", Alert.AlertType.INFORMATION);
            }

            vaciarOpcionesJugador();

        }
        catch (Exception e){
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Funciones para apartado de GESTIONAR USUARIOS
    @FXML
    public void onGestionarUsuarios(ActionEvent event){
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

        bGestionarJugadores.setTextFill(Color.BLACK);
        bGestionarJugadores.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        bGestionarUsuarios.setTextFill(Color.WHITE);
        bGestionarUsuarios.setStyle("-fx-background-color: #0089ED; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        bGestionarEquipos.setTextFill(Color.BLACK);
        bGestionarEquipos.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        apGestionarJugadoresAnadir.setVisible(false);
        apGestionarJugadoresBorrar.setVisible(false);
        apGestionarJugadoresEditar.setVisible(false);
        apGestionarEquiposEditar.setVisible(false);
        apGestionarEquiposBorrar.setVisible(false);
         apGestionarEquiposAnadir.setVisible(false);

    }

    @FXML
    public void onAnadirUsuario(MouseEvent MouseEvent){
        //Activar la ventana
        apGestionarUsuariosAnadir.setVisible(true);

        cbTipoUsuario.getItems().addAll("admin", "estandar");

    }

    @FXML
    public void onAnadirDatosUsuario(ActionEvent event){
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


        }catch (Exception e){
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onEliminarUsuario(MouseEvent MouseEvent){
        apGestionarUsuariosBorrar.setVisible(true);
    }

   @FXML
   public void onBorrarDatosUsuario(){
        try {

            ValidarDatos.validarUsername(tfNombreUsuarioBorrar.getText());

            String username = tfNombreUsuarioBorrar.getText();

            // Panel para que confirme si quiere borrar o no
            Optional<ButtonType> result =  mostarMensajeConfirmacion("Confirmación", "Confirmación de borrado");

            if (result.isPresent() && result.get() == ButtonType.OK){
                UsuarioController.borrarUsuario(username);
                mostarMensaje("Confirmación", "El usuario se ha borrado con éxito", Alert.AlertType.INFORMATION);

            }
            // Si quiere que se haga esto


            vaciarOpcionesUsuario();

        }catch (Exception e){
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
   }

   @FXML
   public void onEditarUsuario(){
       tfNombreUsuarioEditar.setDisable(true);
       pfContrasenaEditar.setDisable(true);
       cbTipoUsuarioEditar.setDisable(true);
       bEditarUsuario.setDisable(true);

       apGestionarUsuariosEditar.setVisible(true);

       cbTipoUsuarioEditar.getItems().addAll("admin", "estandar");
   }

   @FXML
   public void onBuscarNombreUsuario(){
        try {

            Usuario usuario = UsuarioController.buscarPorNombreUsusario(tfNombreUsuarioBuscar.getText());

            tfNombreUsuarioEditar.setText(usuario.getNombreUsuario());
            pfContrasenaEditar.setText(usuario.getContrasena());
            if (usuario instanceof Admin){
                cbTipoUsuarioEditar.getSelectionModel().select("admin");
            } else cbTipoUsuarioEditar.getSelectionModel().select("estandar");

            tfNombreUsuarioEditar.setDisable(false);
            pfContrasenaEditar.setDisable(false);
            cbTipoUsuarioEditar.setDisable(false);
            bEditarUsuario.setDisable(false);

        } catch (Exception e){
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
   }

   @FXML
   public void onEditarDatosUsuario(){
        try {
            ValidarDatos.validarUsername(tfNombreUsuarioEditar.getText());
            ValidarDatos.validarContrasena(pfContrasenaEditar.getText());

            String username = tfNombreUsuarioBuscar.getText();
            String usernameNuevo = tfNombreUsuarioEditar.getText();
            String contrasena = pfContrasenaEditar.getText();
            String tipoUsuario = cbTipoUsuarioEditar.getValue();

            // Menu para pedir al usuario si quiere realizar los cambios
            Optional<ButtonType> result =  mostarMensajeConfirmacion("Confirmación", "Confirmación de edición");

            if (result.isPresent() && result.get() == ButtonType.OK){
                UsuarioController.editarUsuario(username, usernameNuevo, contrasena, tipoUsuario);
                mostarMensaje("Confirmación", "El usuario se ha editado con éxito", Alert.AlertType.INFORMATION);

            }
            // Si si que pase esto

            vaciarOpcionesUsuario();


        } catch (Exception e) {
            mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
   }

    @FXML
    public void onVolverGestionarUsuarios(){
        apGestionarUsuariosAnadir.setVisible(false);
        apGestionarUsuariosBorrar.setVisible(false);
        apGestionarUsuariosEditar.setVisible(false);

        vaciarOpcionesUsuario();
    }

    // Función que vuelve al menu principal de gestion de jugadores desde añadir, eliminar y editar
    @FXML
    public void onVolverGestionarJugadores(ActionEvent event){
        apGestionarJugadoresAnadir.setVisible(false);
        apGestionarJugadoresBorrar.setVisible(false);
        apGestionarJugadoresEditar.setVisible(false);

        vaciarOpcionesJugador();
    }

    // Función que vacia los huecos donde el usuario introduce los datos en Gestión Jugadores
    private void vaciarOpcionesJugador(){
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
    private void vaciarOpcionesUsuario(){
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
    public void onCerrarSesion(javafx.event.ActionEvent actionEvent) {
        controller.show();
        stage.close();
    }

    // Función que actualiza los contadores de jugadores y equipos del menu principal
    private void actualizarDatosPanelPrincipal(){
        lbNombreBienvenida.setText(username);
        lbCantJugadores.setText(JugadorController.contarJugadores());
        lbCantEquipos.setText(EquipoController.contarEquipos());
    }

    //Funciones para apartado GESTION EQUIPOS
    public void onGestionarEquipos(ActionEvent actionEvent) {
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

        bGestionarEquipos.setTextFill(Color.WHITE);
        bGestionarEquipos.setStyle("-fx-background-color: #0086ed; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        bGestionarUsuarios.setTextFill(Color.BLACK);
        bGestionarUsuarios.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        bGestionarJugadores.setTextFill(Color.BLACK);
        bGestionarJugadores.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
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

    public void mostarMensaje(String titulo, String mensaje, Alert.AlertType alerta){

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

    //Funcion borrar equipo al pulsar boton
    public void onBorrarDatosEquipo(ActionEvent actionEvent) {
        try {

            ValidarDatos.validarString(tfNombreEquipoBorrar.getText());

            String nombre = tfNombreEquipoBorrar.getText();

            // Panel para que confirme si quiere borrar o no
            // Si quiere que se haga esto
            EquipoController.borrarEquipo(nombre);

            vaciarOpcionesEquipo();

        }catch (Exception e){
            System.out.println("Error al borrar Equipo");
        }
    }

    //Funcion abrir panel Editar Equipo
    public void onEditarEquipo(MouseEvent mouseEvent) {

        tfNombreEquipoEditar.setDisable(true);
        dpFechaFundacionEditar.setDisable(true);
        bEditarEquipo.setDisable(true);

        apGestionarEquiposEditar.setVisible(true);
    }

    //Funcion buscar el equipo a editar
    public void onBuscarNombreEquipo(ActionEvent actionEvent) {
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
    public void onEditarDatosEquipo(ActionEvent actionEvent) {
        try {
            ValidarDatos.validarUsername(tfNombreEquipoEditar.getText());

            String nombre = tfNombreEquipoEditar.getText();
            LocalDate fechaFundacion = dpFechaFundacionEditar.getValue();

            EquipoController.editarEquipo(tfNombreEquipoBuscar.getText(), nombre, fechaFundacion);

            vaciarOpcionesEquipo();

        }
        catch (Exception e){
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

    //Funcion para cerrar la competición
    @FXML
    public void onCerrarCompeticion(){

        Optional<ButtonType> result =  mostarMensajeConfirmacion("Confirmación", "Confirmación para cerrar la Competición");

        if (result.isPresent() && result.get() == ButtonType.OK){
            try {

                // FALTA PONER UN PANEL PARA QUE PONGA EL TIPO DE PUNTUACION QUE SE VA A USAR

                LocalDate fechaInicio = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

                int numeroDeEtapas = Integer.parseInt(EquipoController.contarEquipos()) - 1;

                LocalDate fechaFin = fechaInicio.plusDays(7 * (long) numeroDeEtapas);

                LocalDate fechaJornada = fechaInicio;

                crearJornadas(fechaInicio, numeroDeEtapas);

                crearEnfrentamientos(numeroDeEtapas);

                CompeticionController.cerrarCompeticion();



            }catch (Exception e){
                mostarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
            }

            mostarMensaje("Confirmación", "El usuario se ha editado con éxito", Alert.AlertType.INFORMATION);

        }
    }

    public void crearJornadas(LocalDate fechaInicio, int numeroEtapas) throws Exception{

        for (int i = 1; i < numeroEtapas + 1; i++) {

            JornadaController.crearJornada(fechaInicio, i );

            fechaInicio = fechaInicio.plusDays(7);

        }
    }

    public void crearEnfrentamientos(int numeroEtapas){
        int numeroEnfrentamientos = (numeroEtapas + 1) / 2;

        for (int i = 0; i < numeroEtapas; i++) {

            LocalTime horaEnfrentamiento = LocalTime.of(13, 0);

            for (int j = 0; i < numeroEnfrentamientos; j++) {
                Enfrentamiento enfrentamiento = new Enfrentamiento();

                enfrentamiento.setIdEnfrentamiento(jornada.getEnfrentamientos().size());
                horaEnfrentamiento = horaEnfrentamiento.plusHours(1);
                enfrentamiento.setHoraEnfrentamiento(horaEnfrentamiento);

                jornada.setEnfrentamiento(enfrentamiento);
                enfrentamientos.add(enfrentamiento);
            }
        }
    }
}
