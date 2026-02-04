import Excepciones.*;
import Modelo.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // Scanner
    final static Scanner sc = new Scanner(System.in);

    // Variables
    public static ArrayList<Equipo> equipos = new ArrayList<>();
    public static ArrayList<Jugador> jugadores = new ArrayList<>();
    public static ArrayList<Usuario> usuarios = new ArrayList<>();
    public static Usuario usuario;
    public static DateTimeFormatter formatofecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static double SMI = 17094;

    public static void main(String[] args) {
        try {
            crearAdmin();
            identificacion();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getClass());
        }
    }

    //Crear Admin por defecto
    public static void crearAdmin() {
        usuarios.add(new Admin(1,"Admin","Admin"));
    }

    //Menú de identificación
    public static void identificacion() {
        boolean cont = true;
        do {
            System.out.println("-- Menu Identificacion --");
            boolean error;
            do {
                try {
                    System.out.println("Usuario:");
                    String nombre = sc.nextLine();
                    if (usuarios.stream().noneMatch(u -> u.getNombre().equals(nombre))) {
                        System.out.println("Usuario no encontrado");
                        throw new DatoNoValido();
                    }
                    usuario = usuarios.stream().filter(u -> u.getNombre().equals(nombre)).findFirst().orElse(null);
                    System.out.println("Contraseña");
                    String password = sc.nextLine();
                    if (!usuario.getContrasena().equals(password)) {
                        System.out.println("La contraseña no corresponde al usuario");
                        throw new DatoNoValido();
                    }
                    error = false;
                    cont = opciones(cont);
                }catch (DatoNoValido e){
                    error = true;
                }
            }while (error);
        }while (cont);

    }

    // Menú principal
    public static boolean opciones( boolean cont) {
        boolean finalizar = false;
        do {
            if (usuarios.stream().noneMatch(u -> u.getNombre().equals(usuario.getNombre()))) {
                System.out.println("Usuario no existe, se ha cerrado sesion");
                finalizar = true;
                identificacion();
            }else {
                System.out.print("""
                        -- Menu Principal--
                        a) Ver equipos que compiten una competición
                        b) Ver informe de la ultima jornada
                        """);
                if (usuario instanceof Admin) {
                    System.out.print("""
                            c) Gestionar Usuarios
                            d) Gestionar Equipos
                            e) Gestionar Jugadores
                            f) Cerrar Sesion
                            g) Salir
                            """);
                } else {
                    System.out.print("""
                            c) Cerrar Sesion
                            d) Salir
                            """);
                }

                String opcion = sc.nextLine();
                switch (opcion) {
                    case "a":
                        break;
                    case "b":
                        break;
                    case "c":
                        if (usuario instanceof Estandar) {
                            finalizar = true;
                        } else {
                            gestionarUsuarios();
                        }
                        break;
                    case "d":
                        if (usuario instanceof Admin) {
                            gestionarEquipos();
                        }else {
                            finalizar = true;
                            cont = false;
                        }
                        break;
                    case "e":
                        if (usuario instanceof Admin){
                            gestionarJugadores();
                        }else {
                            System.out.println("Opcion no valida");
                        }
                        break;
                    case "f":
                        if (usuario instanceof Admin) {
                            finalizar = true;
                        }else {
                            System.out.println("Opcion no valida");
                        }
                        break;
                    case "g":
                        if (usuario instanceof Admin) {
                            finalizar = true;
                            cont = false;
                        }else {
                            System.out.println("Opcion no valida");
                        }
                        break;
                    default:
                        System.out.println("Opcion no valida");
                        break;
                }
            }
        }while (!finalizar);
        return cont;
    }

    public static void gestionarUsuarios(){
        boolean cont = true;
        do {
            System.out.print("""
                    -- Menu Gestion Usuarios--
                    a) Crear Usuario
                    b) Editar Usuario
                    c) Eliminar Usuario
                    d) Salir
                    """);
            String opcion = sc.nextLine();
            switch (opcion) {
                case "a":
                    crearUsuario();
                    break;
                case "b":
                    editarUsuario();
                    break;
                case "c":
                    eliminarUsuario();
                    break;
                case "d":
                    cont = false;
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        }while (cont);
    }

    public static void crearUsuario(){
        String tipo = validarDato("Tipo", "Dime si el usuario es Admin o Estandar:", "^(Admin|Estandar)$");
        String nombreUsuario = validarDato("Usuario","Usuario:","^[A-Za-z0-9]+$");

        if (usuarios.stream().noneMatch(u -> u.getNombre().equalsIgnoreCase(nombreUsuario))) {
            if (tipo.equals("Admin")) {
                usuarios.add(new Admin(usuarios.size()+1,
                        nombreUsuario, validarDato("Contraseña","Contraseña:","^[A-Za-z0-9]+$")));
            }else if (tipo.equals("Estandar")) {
                usuarios.add(new Estandar(usuarios.size()+1,
                        nombreUsuario, validarDato("Contraseña","Contraseña:","^[A-Za-z0-9]+$")));
            }
        }else {
            System.out.println("El nombre de usuario ya existe");
        }
    }

    public static void editarUsuario(){

        System.out.println("Que usuario desea editar:");
        String nombreUsuario = sc.nextLine();
        if (usuarios.stream().noneMatch(u -> u.getNombre().equalsIgnoreCase(nombreUsuario))) {
            System.out.println("El usuario no existe");
        }else  {
            int posicionEditar = usuarios.indexOf(usuarios.stream().filter(u -> u.getNombre().equalsIgnoreCase(nombreUsuario)).findFirst().orElse(null));
            boolean editar = true;
            do {
                System.out.print("""
                    -- Menu Editar --
                    a) Nombre
                    b) Contraseña
                    c) Salir
                    """);
                String opcion = sc.nextLine();
                switch (opcion) {
                    case "a":
                        String nombreNuevo = validarDato("Nombre","Nuevo nombre de Usuario:","^[A-Za-z0-9]+$");
                        if (usuarios.stream().noneMatch(u -> u.getNombre().equalsIgnoreCase(nombreNuevo))) {
                            usuarios.get(posicionEditar).setNombre(nombreNuevo);
                        }else  {
                            System.out.println("El nombre de usuario ya existe");
                        }
                        break;
                    case "b":
                        String contrasenaNueva = validarDato("Contraseña","Contraseña:","^[A-Za-z0-9]+$");
                        usuarios.get(posicionEditar).setContrasena(contrasenaNueva);
                        break;
                    case "c":
                        editar = false;
                        break;
                    default:
                        System.out.println("Opcion no valida");
                        break;
                }
            }while (editar);
        }
    }

    public static void eliminarUsuario(){
        System.out.println("Que usuario desea eliminar:");
        String nombreUsuario = sc.nextLine();
        Usuario usuarioEliminar =  usuarios.stream().filter(u -> u.getNombre().equalsIgnoreCase(nombreUsuario)).findFirst().orElse(null);
        if (usuarioEliminar == null) {
            System.out.println("El nombre de usuario no existe");
        }else  {
            usuarios.remove(usuarioEliminar);
        }
    }

    public static void gestionarEquipos(){
        boolean cont = true;
        do {
            System.out.print("""
                    -- Menu Gestion Equipos--
                    a) Crear Equipo
                    b) Editar Equipo
                    c) Eliminar Equipo
                    d) Salir
                    """);
            String opcion = sc.nextLine();
            switch (opcion) {
                case "a":
                    crearEquipo();
                    break;
                case "b":
                    editarEquipo();
                    break;
                case "c":
                    eliminarEquipo();
                    break;
                case "d":
                    cont = false;
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        }while (cont);
    }

    public static void crearEquipo(){
        if (equipos.isEmpty()){

            equipos.add(new Equipo(1,validarDato("Nombre Equipo","Nombre Equipo:","^[A-Za-z0-9]+$"),validarFecha("Fecha Fundacion","Fecha Fundacion")));

        }else {
            String nombreEquipo = validarDato("Nombre Equipo", "Nombre Equipo:", "^[A-Za-z0-9]+$");
            if (equipos.stream().noneMatch(u -> u.getNombre().equalsIgnoreCase(nombreEquipo))) {

                equipos.add(new Equipo(equipos.size()+1,nombreEquipo,validarFecha("Fecha Fundacion","Fecha Fundacion")));
            }else  {
                System.out.println("El equipo ya existe");
            }
        }
    }

    public static void editarEquipo(){
        if (equipos.isEmpty()){
            System.out.println("No hay equipos para editar");
        }else {
            System.out.println("Que equipo desea editar:");
            String nombreEquipo = sc.nextLine();
            if (equipos.stream().noneMatch(u -> u.getNombre().equalsIgnoreCase(nombreEquipo))) {
                System.out.println("El equipo no existe");
            }else {
                int posicionEditar = equipos.indexOf(equipos.stream().filter(u -> u.getNombre().equalsIgnoreCase(nombreEquipo)).findFirst().orElse(null));
                boolean editar = true;
                do {
                    System.out.print("""
                    -- Menu Editar --
                    a) Nombre
                    b) Fecha Fundacion
                    c) Jugadores
                    d) Salir
                    """);
                    String opcion = sc.nextLine();
                    switch (opcion) {
                        case "a":
                            String nombreNuevo = validarDato("Nombre","Nuevo nombre de Equipo:","^[A-Za-z0-9]+$");
                            if (equipos.stream().noneMatch(u -> u.getNombre().equalsIgnoreCase(nombreNuevo))) {
                                equipos.get(posicionEditar).setNombre(nombreNuevo);
                            }else  {
                                System.out.println("El nombre de usuario ya existe");
                            }
                            break;
                        case "b":
                            LocalDate fechaNueva = validarFecha("Nueva Fecha Fundacion", "Nueva Fecha Fundacion:");
                            equipos.get(posicionEditar).setFechaFundacion(fechaNueva);
                            break;
                        case "c":
                            editarEquipoJugadores(posicionEditar);
                            break;
                        case "d":
                            editar = false;
                            break;
                        default:
                            System.out.println("Opcion no valida");
                            break;
                    }
                }while (editar);
            }
        }
    }

    public static void eliminarEquipo(){
        if (equipos.isEmpty()){
            System.out.println("No hay equipos para eliminar");
        }else  {
            System.out.println("Que equipo desea eliminar:");
            String nombreEquipo = sc.nextLine();
            if (equipos.stream().noneMatch(u -> u.getNombre().equalsIgnoreCase(nombreEquipo))) {
                System.out.println("El nombre de equipo no existe");
            }else {
                Equipo equipo = equipos.stream().filter(u -> u.getNombre().equalsIgnoreCase(nombreEquipo)).findFirst().orElse(null);
                if (equipo != null) {
                    for (Jugador jugador : equipo.getJugadores()) {
                        jugador.setEquipo(null);
                    }
                    equipos.remove(equipo);
                }
            }
        }
    }

    public static void editarEquipoJugadores(int posicionEditar){
        boolean editar = true;
        do {
            System.out.print("""
                    -- Gestionar Jugadores del Equipo --
                    a) Añadir Jugador
                    b) Eliminar Jugador
                    c) Salir
                    """);
            String opcion = sc.nextLine();
            switch (opcion) {
                case "a":
                    if (equipos.get(posicionEditar).getJugadores().size()>6) {
                        System.out.println("El equipo ya esta completo");
                    }else {
                        System.out.println("Ingresa el nickname del jugador:");
                        String nombreAnadir = sc.nextLine();
                        if (jugadores.stream().noneMatch(u -> u.getNickname().equalsIgnoreCase(nombreAnadir))) {
                            System.out.println("El nickname del jugador no existe");
                        } else {
                            Jugador jugadorAnadir = jugadores.stream().filter(u -> u.getNickname().equalsIgnoreCase(nombreAnadir)).findFirst().orElse(null);
                            if (equipos.get(posicionEditar).getJugadores().stream().noneMatch(u -> u.getNickname().equalsIgnoreCase(nombreAnadir))) {
                                equipos.get(posicionEditar).getJugadores().add(jugadorAnadir);
                                if (jugadorAnadir != null) {
                                    jugadorAnadir.setEquipo(equipos.get(posicionEditar));
                                }
                            } else {
                                System.out.println("El nickname del jugador ya existe");
                            }
                        }
                    }
                    break;
                case "b":
                    if (equipos.get(posicionEditar).getJugadores().isEmpty()){
                        System.out.println("El equipo no tiene jugadores para eliminar");
                    }else {
                        System.out.println("Ingresa el nickname del jugador:");
                        String nombreEliminar = sc.nextLine();
                        if (equipos.get(posicionEditar).getJugadores().stream().noneMatch(u -> u.getNickname().equalsIgnoreCase(nombreEliminar))) {
                            System.out.println("El jugador no existe");
                        } else {
                            Jugador jugadorEliminar = jugadores.stream().filter(u -> u.getNickname().equalsIgnoreCase(nombreEliminar)).findFirst().orElse(null);
                            equipos.get(posicionEditar).getJugadores().remove(jugadorEliminar);
                            if (jugadorEliminar != null) {
                                jugadorEliminar.setEquipo(null);
                            }
                        }
                    }
                    break;
                case "c":
                    editar = false;
                    break;
            }
        }while (editar);
    }

    public static void gestionarJugadores(){
        boolean cont = true;
        do {
            System.out.print("""
                    -- Menu Gestion Jugadores--
                    a) Crear Jugador
                    b) Editar Jugador
                    c) Eliminar Jugador
                    d) Salir
                    """);
            String opcion = sc.nextLine();
            switch (opcion) {
                case "a":
                    crearJugador();
                    break;
                case "b":
                    editarJugador();
                    break;
                case "c":
                    eliminarJugador();
                    break;
                case "d":
                    cont = false;
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        }while (cont);
    }

    public static void crearJugador(){
        if (jugadores.isEmpty()){
            jugadores.add(new Jugador(1,
                    validarDato("Nombre", "Nombre jugador:","^[A-Z][a-z]+$"),
                    validarDato("Apellido", "Apellido jugador:","^[A-Z][a-z]+$"),
                    validarDato("Nacinalidad", "Nacionalidad jugador:","^[A-Z][a-z]+$"),
                    validarDato("Nickname", "Nickname jugador:","^[A-Za-z0-9]+$"),
                    validarDato("Rol","Rol jugador (SUPPORT|AWPER|IGL|LURKER|RIFLER|ENTRYFRAGER):","^(SUPPORT|AWPER|IGL|LURKER|RIFLER|ENTRYFRAGER)$"),
                    validarSueldo("Sueldo","Sueldo jugador:")));
        }else {
            String nombre = validarDato("Nombre", "Nombre jugador:","^[A-Z][a-z]+$");
            String apellido = validarDato("Apellido", "Apellido jugador:","^[A-Z][a-z]+$");
            String nacionalidad = validarDato("Nacinalidad", "Nacionalidad jugador:","^[A-Z][a-z]+$");
            String finalnickname = "";
            boolean error = true;
            do {
                String nickname = validarDato("Nickname", "Nickname jugador:","^[A-Za-z0-9]+$");
                if (jugadores.stream().noneMatch(u -> u.getNickname().equalsIgnoreCase(nickname))) {
                    error = false;
                    finalnickname = nickname;
                }else {
                    System.out.println("El nickname del jugador ya existe");
                }
            }while (error);
            String rol = validarDato("Rol","Rol jugador (SUPPORT|AWPER|IGL|LURKER|RIFLER|ENTRYFRAGER):","^(SUPPORT|AWPER|IGL|LURKER|RIFLER|ENTRYFRAGER)$");
            double sueldo = validarSueldo("Sueldo","Sueldo jugador:");
            jugadores.add(new Jugador(jugadores.size()+1,nombre,apellido,nacionalidad,finalnickname,rol,sueldo));
        }
    }

    public static void editarJugador(){
        if (jugadores.isEmpty()){
            System.out.println("No hay jugadores para editar");
        }else {
            System.out.println("Ingresa el nickname del jugador:");
            String nickname = sc.nextLine();
            if (jugadores.stream().noneMatch(u -> u.getNickname().equalsIgnoreCase(nickname))) {
                System.out.println("El nickname del jugador no existe");
            }else {
                int posicionEditar = jugadores.indexOf(jugadores.stream().filter(u -> u.getNickname().equalsIgnoreCase(nickname)).findFirst().orElse(null));
                boolean editar = true;
                do {
                    System.out.print("""
                    -- Menu Editar --
                    a) Nombre
                    b) Apellido
                    c) Nacionalidad
                    d) Nickname
                    e) Rol
                    f) Sueldo
                    g) Equipo
                    h) Salir
                    """);
                    String opcion = sc.nextLine();
                    switch (opcion) {
                        case "a":
                            String nombreNuevo = validarDato("Nombre","Nuevo nombre de Jugador:","^[A-Z][a-z]+$");
                            jugadores.get(posicionEditar).setNombre(nombreNuevo);
                            break;
                        case "b":
                            String apellidoNuevo = validarDato("Nombre","Nuevo apellido de Jugador:","^[A-Z][a-z]+$");
                            jugadores.get(posicionEditar).setApellido(apellidoNuevo);
                            break;
                        case "c":
                            String nacionalidadNueva = validarDato("Nacionalidad", "Nueva nacionalidad de Jugador:","^[A-Z][a-z]+$");
                            jugadores.get(posicionEditar).setNacionalidad(nacionalidadNueva);
                            break;
                        case "d":
                            String finalnicknamenuevo = "";
                            boolean error = true;
                            do {
                                String nicknamenuevo = validarDato("Nickname", "Nuevo nickname de jugador:","^[A-Za-z0-9]+$");
                                if (jugadores.stream().noneMatch(u -> u.getNickname().equalsIgnoreCase(nickname))) {
                                    error = false;
                                    finalnicknamenuevo = nicknamenuevo;
                                }else {
                                    System.out.println("El nickname del jugador ya existe");
                                }
                            }while (error);
                            jugadores.get(posicionEditar).setNickname(finalnicknamenuevo);
                            break;
                        case "e":
                            String rol = validarDato("Rol","Nuevo rol de jugador (SUPPORT|AWPER|IGL|LURKER|RIFLER|ENTRYFRAGER):","^(SUPPORT|AWPER|IGL|LURKER|RIFLER|ENTRYFRAGER)$");
                            jugadores.get(posicionEditar).setRol(rol);
                            break;
                        case "f":
                            double sueldo = validarSueldo("Sueldo","Nuevo sueldo de jugador:");
                            jugadores.get(posicionEditar).setSueldo(sueldo);
                        case "g":
                            editarJugadorEquipo(posicionEditar);
                            break;
                        case "h":
                            editar = false;
                            break;
                        default:
                            System.out.println("Opcion no valida");
                            break;
                    }
                }while (editar);
            }
        }
    }

    public static void editarJugadorEquipo(int posicionEditar){
        boolean editar = true;
        do {
            System.out.print("""
                    -- Gestionar Equipo del Jugador --
                    a) Añadir Jugador a Equipo
                    b) Eliminar Jugador de Equipo
                    c) Salir
                    """);
            String opcion = sc.nextLine();
            switch (opcion) {
                case "a":
                    if (jugadores.get(posicionEditar).getEquipo() == null){
                        if (equipos.isEmpty()){
                            System.out.println("No hay equipos para añadir");
                        }else {
                            System.out.println("Dime el nombre del equipo:");
                            String nombreEquipo = validarDato("Nombre Equipo", "Nombre Equipo:","^[A-Za-z0-9]+$");
                            if (equipos.stream().noneMatch(equipo -> equipo.getNombre().equalsIgnoreCase(nombreEquipo))) {
                                System.out.println("El nombre del equipo no existe");
                            }else {
                                Equipo equipo = equipos.stream().filter(equipo1 ->  equipo1.getNombre().equalsIgnoreCase(nombreEquipo)).findFirst().orElse(null);
                                if (equipo != null && equipo.getJugadores().size() > 6) {
                                    System.out.println("El equipo ya esta completo");
                                }else {
                                    jugadores.get(posicionEditar).setEquipo(equipo);
                                    if (equipo != null) {
                                        equipo.getJugadores().add(jugadores.get(posicionEditar));
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "b":
                    if (jugadores.get(posicionEditar).getEquipo() == null){
                        System.out.println("No hay equipo para eliminar");
                    }else {
                        Equipo equipo = jugadores.get(posicionEditar).getEquipo();
                        jugadores.get(posicionEditar).setEquipo(null);
                        equipo.getJugadores().remove(jugadores.get(posicionEditar));
                    }
                    break;
                case "c":
                    editar = false;
                    break;
            }
        }while (editar);
    }

    public static void eliminarJugador(){
        if (jugadores.isEmpty()){
            System.out.println("No hay jugadores para eliminar");
        }else {
            String nicknameJugador = validarDato("Nickname", "Nickname del jugador:","^[A-Za-z0-9]+$");
            if (jugadores.stream().noneMatch(u -> u.getNickname().equalsIgnoreCase(nicknameJugador))) {
                System.out.println("El nickname del jugador no existe");
            }else {
                Jugador jugador = jugadores.stream().filter(u -> u.getNickname().equalsIgnoreCase(nicknameJugador)).findFirst().orElse(null);
                if (jugador != null) {
                    jugador.getEquipo().getJugadores().remove(jugador);
                    jugadores.remove(jugador);
                }
            }
        }
    }

    public static String validarDato(String dato, String mensaje, String exp){
        String respuesta = "";
        boolean error = false;
        do {
            try {
                System.out.println(mensaje);
                respuesta = sc.nextLine();
                if (!respuesta.matches(exp)) {
                    throw new DatoNoValido();
                }
                error = true;
            }catch (DatoNoValido e){
                System.out.println(dato + " no es correcto, vuelve a intentarlo");
            }
        }while (!error);
        return respuesta;
    }

    public static LocalDate validarFecha(String dato,String mensaje){
        LocalDate fechaFundacion = null;
        boolean error = true;
        do {
            try {
                fechaFundacion = LocalDate.parse(validarDato(dato,mensaje,"^([0-2][0-9]|3[0-1])/(0[1-9]|1[0-2])/(19|20)[0-9]{2}$"),formatofecha);
                if (fechaFundacion.isAfter(LocalDate.now())) {
                    throw new FechaMayorHoy();
                }
                error =false;
            }catch (FechaMayorHoy | DateTimeParseException e){
                System.out.println("La fecha introducida no es valida");
            }
        }while (error);
        return fechaFundacion;
    }

    public static double validarSueldo(String dato,String mensaje){
        double sueldo = 0;
        boolean error = true;
        do {
            try {
                sueldo = Double.parseDouble(validarDato(dato,mensaje,"^[0-9]+$"));
                if (sueldo<SMI){
                    throw new SueldoNoValido();
                }
                error =false;
            }catch (SueldoNoValido e){
                System.out.println("El sueldo tiene que ser superior al sueldo minimo interprofesional");
            }
        }while (error);
        return sueldo;
    }
}