package org.example.appesports.Vista;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.appesports.Controlador.EquipoController;
import org.example.appesports.Controlador.JugadorController;
import javafx.scene.layout.AnchorPane;
import org.example.appesports.Controlador.UsuarioController;
import org.example.appesports.Modelo.Admin;
import org.example.appesports.Modelo.Equipo;
import org.example.appesports.Modelo.Jugador;
import org.example.appesports.Modelo.Usuario;
import org.example.appesports.Utilidades.ValidarDatos;

import java.time.LocalDate;
import java.util.ArrayList;

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

        @FXML
        public AnchorPane apGestionarEquiposPrincipal;

        @FXML
        public AnchorPane apGestionarEquiposAnadir;

        @FXML
        public TextField tfNombreEquipo;

        @FXML
        public DatePicker dpFechaFundacion;
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
    }

    // Función que abre el menu principal de gestion de Jugadores
    @FXML
    public void onGestionarJugadores(ActionEvent event) {

        apGestionarJugadoresPrincipal.setVisible(true);
        apGestionarJugadoresAnadir.setVisible(false);
        apGestionarEquiposPrincipal.setVisible(false);
        apGestionarEquiposAnadir.setVisible(false);

        bGestionarJugadores.setTextFill(Color.WHITE);
        bGestionarJugadores.setStyle("-fx-background-color: #0086ed; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        bGestionarUsuarios.setTextFill(Color.BLACK);
        bGestionarUsuarios.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        bGestionarEquipos.setTextFill(Color.BLACK);
        bGestionarEquipos.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
      
        apGestionarJugadoresAnadir.setVisible(false);
        apGestionarJugadoresBorrar.setVisible(false);
        apGestionarJugadoresEditar.setVisible(false);

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

            vaciarOpcionesJugador();

        } catch (Exception e) {
            throw new RuntimeException(e);
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

            JugadorController.borrarJugador(tfNicknameBorrar.getText());

            // MENSAJE DE CONFIMRACION

            vaciarOpcionesJugador();

        } catch (Exception e) {
            throw new RuntimeException(e);
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
            cbSeleccionEquipoParaJugadorEditar.getSelectionModel().select(jugador.getEquipo().getNombre());

        } catch (Exception e) {
            throw new RuntimeException(e);
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
            LocalDate fechaNacimiento = dpFechaNacimiento.getValue();
            String nuevoNickname = tfNicknameEditar.getText();
            String rol = cbRolEditar.getValue();
            double sueldo = Double.parseDouble(tfSueldoEditar.getText());
            String nombreEquipo = cbSeleccionEquipoParaJugadorEditar.getValue();


            // MENSAJE DE SI QUIERE ACEPTAR LOS CAMBIOS O NO
            // SI ES QUE SI QUE SE HAGA ESTO
            JugadorController.editarJugador(nickname, nombre, apellido, nacionalidad, fechaNacimiento, nuevoNickname, rol, sueldo, nombreEquipo);


            vaciarOpcionesJugador();

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Funciones para apartado de GESTIONAR USUARIOS
    @FXML
    public void onGestionarUsuarios(ActionEvent event){
        // Activar la ventana
        apGestionarUsuariosPrincipal.setVisible(true);

        //Desactivar las demás ventanas
        apGestionarJugadoresPrincipal.setVisible(false);
        apGestionarEquiposPrincipal.setVisible(false);

        bGestionarJugadores.setTextFill(Color.BLACK);
        bGestionarJugadores.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        bGestionarUsuarios.setTextFill(Color.BLACK);
        bGestionarUsuarios.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        bGestionarEquipos.setTextFill(Color.BLACK);
        bGestionarEquipos.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        apGestionarJugadoresAnadir.setVisible(false);
        apGestionarJugadoresBorrar.setVisible(false);
        apGestionarJugadoresEditar.setVisible(false);

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

            vaciarOpcionesUsuario();


        }catch (Exception e){
            System.out.println("Error al añadir Usuario");
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
            // Si quiere que se haga esto
            UsuarioController.borrarUsuario(username);


            vaciarOpcionesUsuario();

        }catch (Exception e){
            System.out.println("Error al borrar Usuario");
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
            System.out.println("Error al buscar Por nombre");
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
            // Si si que pase esto
            UsuarioController.editarUsuario(username, usernameNuevo, contrasena, tipoUsuario);

            vaciarOpcionesUsuario();


        } catch (Exception e) {
            System.out.println("Error al editar usuario");
        }
   }

    @FXML
    public void onVolverGestionarUsuarios(){
        apGestionarUsuariosAnadir.setVisible(false);
        apGestionarUsuariosBorrar.setVisible(false);
        apGestionarUsuariosEditar.setVisible(false);
    }

    // Función que vuelve al menu principal de gestion de jugadores desde añadir, eliminar y editar
    @FXML
    public void onVolverGestionarJugadores(ActionEvent event){
        apGestionarJugadoresAnadir.setVisible(false);
        apGestionarJugadoresBorrar.setVisible(false);
        apGestionarJugadoresEditar.setVisible(false);

        vaciarOpcionesJugador();
    }

    @FXML
    public void onGestionarUsuarios(ActionEvent event){
        bGestionarUsuarios.setTextFill(Color.WHITE);
        bGestionarUsuarios.setStyle("-fx-background-color: #0086ed; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        bGestionarJugadores.setTextFill(Color.BLACK);
        bGestionarJugadores.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        bGestionarEquipos.setTextFill(Color.BLACK);
        bGestionarEquipos.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
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

    public void onGestionarEquipos(ActionEvent actionEvent) {
        apGestionarEquiposPrincipal.setVisible(true);
        apGestionarJugadoresAnadir.setVisible(false);
        apGestionarJugadoresPrincipal.setVisible(false);

        bGestionarEquipos.setTextFill(Color.WHITE);
        bGestionarEquipos.setStyle("-fx-background-color: #0086ed; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        bGestionarUsuarios.setTextFill(Color.BLACK);
        bGestionarUsuarios.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");
        bGestionarJugadores.setTextFill(Color.BLACK);
        bGestionarJugadores.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: #0089ED; -fx-border-radius: 20;");

    }

    public void onAnadirEquipo(MouseEvent mouseEvent) {
        apGestionarEquiposAnadir.setVisible(true);

    }

    public void onVolverGestionarEquipos(ActionEvent actionEvent) {
        apGestionarEquiposAnadir.setVisible(false);
    }

    public void onAnadirDatosEquipo(ActionEvent actionEvent) {
        try {
            ValidarDatos.validarString(tfNombreEquipo.getText());

            String nombre = tfNombreEquipo.getText();
            LocalDate fechaFundacion = dpFechaFundacion.getValue();

            EquipoController.insertarEquipo(nombre, fechaFundacion);

            tfNombreEquipo.clear();
            dpFechaFundacion.setValue(null);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
