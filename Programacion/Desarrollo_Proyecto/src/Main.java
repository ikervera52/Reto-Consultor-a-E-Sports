import Excepciones.*;
import Modelo.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    // Scanner
    final static Scanner sc = new Scanner(System.in);

    // Variables
    public static ArrayList<Equipo> equipos = new ArrayList<>();
    public static ArrayList<Jugador> jugadores = new ArrayList<>();
    public static ArrayList<Usuario> usuarios = new ArrayList<>();
    public static ArrayList<Competicion> competiciones = new ArrayList<>();
    public static ArrayList<Jornada> jornadas = new ArrayList<>();
    public static ArrayList<Enfrentamiento> enfrentamientos = new ArrayList<>();
    public static ArrayList<Resultado> resultados = new ArrayList<>();
    public static Usuario usuario;
    public static DateTimeFormatter formatofecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static double SMI = 17094;
    public static LocalDate fechaHoy = LocalDate.now();

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
                            f) Gestionar Competiciones
                            g) Cerrar Sesion
                            h) Salir
                            i) Cambiar Fecha Hoy
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
                        verEquiposCompeticion();
                        break;
                    case "b":
                        verInformeUltimaJornada();
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
                            gestionarCompeticiones();
                        }else {
                            System.out.println("Opcion no valida");
                        }
                        break;
                    case "g":
                        if (usuario instanceof Admin) {
                            finalizar = true;
                        }else {
                            System.out.println("Opcion no valida");
                        }
                        break;
                    case "h":
                        if (usuario instanceof Admin) {
                            finalizar = true;
                            cont = false;
                        }else {
                            System.out.println("Opcion no valida");
                        }
                        break;
                    case "i":
                        if (usuario instanceof Admin) {
                            fechaHoy = LocalDate.parse(validarDato("Fecha Hoy","Dime cual quieres que sea la fecha de hoy:","^([0-2][0-9]|3[0-1])/(0[1-9]|1[0-2])/(19|20)[0-9]{2}$"),formatofecha);
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
        if (competiciones.isEmpty() ||competiciones.getLast().getEtapa().equalsIgnoreCase("Inscripcion")) {
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
            } while (cont);
        }else  {
            System.out.println("Hay una competicion en curso, no se puede gestionar equipos");
        }
    }

    public static void crearEquipo(){
        if (equipos.isEmpty()){
            if (competiciones.isEmpty()){
                equipos.add(new Equipo(1,validarDato("Nombre Equipo","Nombre Equipo:","^[A-Za-z0-9]+$"),validarFecha("Fecha Fundacion","Fecha Fundacion")));
            }else {
                Competicion competicion = competiciones.getLast();
                ArrayList<Competicion> competicionesEquipo = new ArrayList<>();
                competicionesEquipo.add(competicion);
                equipos.add(new Equipo(1,validarDato("Nombre Equipo","Nombre Equipo:","^[A-Za-z0-9]+$"),validarFecha("Fecha Fundacion","Fecha Fundacion"),competicionesEquipo));
            }

        }else {
            String nombreEquipo = validarDato("Nombre Equipo", "Nombre Equipo:", "^[A-Za-z0-9]+$");
            if (equipos.stream().noneMatch(u -> u.getNombre().equalsIgnoreCase(nombreEquipo))) {
                if (competiciones.isEmpty()){
                    equipos.add(new Equipo(equipos.size()+1,nombreEquipo,validarFecha("Fecha Fundacion","Fecha Fundacion")));
                }else {
                    Competicion competicion = competiciones.getLast();
                    ArrayList<Competicion> competicionesEquipo = new ArrayList<>();
                    competicionesEquipo.add(competicion);
                    equipos.add(new Equipo(equipos.size()+1,validarDato("Nombre Equipo","Nombre Equipo:","^[A-Za-z0-9]+$"),validarFecha("Fecha Fundacion","Fecha Fundacion"),competicionesEquipo));
                }
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
                            String nombreNuevo = validarDato("Nombre", "Nuevo nombre de Equipo:", "^[A-Za-z0-9]+$");
                            if (equipos.stream().noneMatch(u -> u.getNombre().equalsIgnoreCase(nombreNuevo))) {
                                equipos.get(posicionEditar).setNombre(nombreNuevo);
                            } else {
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
                } while (editar);
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
        if (competiciones.isEmpty() || competiciones.getLast().getEtapa().equalsIgnoreCase("Inscripcion")) {
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
            } while (cont);
        }else {
            System.out.println("Hay una competicion en curso, no se puede gestionar jugadores");
        }
    }

    public static void crearJugador(){
        if (jugadores.isEmpty()){
            jugadores.add(new Jugador(1,
                    validarDato("Nombre", "Nombre jugador:","^[A-Z][a-z]+$"),
                    validarDato("Apellido", "Apellido jugador:","^[A-Z][a-z]+$"),
                    validarFecha("Fecha Nacimiento", "Fecha Nacimiento:"),
                    validarDato("Nacinalidad", "Nacionalidad jugador:","^[A-Z][a-z]+$"),
                    validarDato("Nickname", "Nickname jugador:","^[A-Za-z0-9]+$"),
                    validarDato("Rol","Rol jugador (SUPPORT|AWPER|IGL|LURKER|RIFLER|ENTRYFRAGER):","^(SUPPORT|AWPER|IGL|LURKER|RIFLER|ENTRYFRAGER)$"),
                    validarSueldo("Sueldo","Sueldo jugador:")));
        }else {
            String nombre = validarDato("Nombre", "Nombre jugador:","^[A-Z][a-z]+$");
            String apellido = validarDato("Apellido", "Apellido jugador:","^[A-Z][a-z]+$");
            LocalDate fechaNacimiento = validarFecha("Fecha Nacimiento","Fecha Nacimiento:");
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
            jugadores.add(new Jugador(jugadores.size()+1,nombre,apellido,fechaNacimiento,nacionalidad,finalnickname,rol,sueldo));
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
                    c) Fecha Nacimiento
                    d) Nacionalidad
                    e) Nickname
                    f) Rol
                    g) Sueldo
                    h) Equipo
                    i) Salir
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
                            LocalDate fechaNacimientoNueva = validarFecha("Fecha Nacimiento","Nueva fecha nacimiento de jugador:");
                            jugadores.get(posicionEditar).setFechaNacimiento(fechaNacimientoNueva);
                        case "d":
                            String nacionalidadNueva = validarDato("Nacionalidad", "Nueva nacionalidad de Jugador:","^[A-Z][a-z]+$");
                            jugadores.get(posicionEditar).setNacionalidad(nacionalidadNueva);
                            break;
                        case "e":
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
                        case "f":
                            String rol = validarDato("Rol","Nuevo rol de jugador (SUPPORT|AWPER|IGL|LURKER|RIFLER|ENTRYFRAGER):","^(SUPPORT|AWPER|IGL|LURKER|RIFLER|ENTRYFRAGER)$");
                            jugadores.get(posicionEditar).setRol(rol);
                            break;
                        case "g":
                            double sueldo = validarSueldo("Sueldo","Nuevo sueldo de jugador:");
                            jugadores.get(posicionEditar).setSueldo(sueldo);
                        case "h":
                            editarJugadorEquipo(posicionEditar);
                            break;
                        case "i":
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

    public static void gestionarCompeticiones(){
        boolean cont = true;
        do {
            System.out.print("""
                    -- Menu Gestion Competiciones--
                    a) Crear Competicion
                    b) Editar Competicion
                    c) Eliminar Competicion
                    d) Cerrar etapa de la Competicion
                    e) Guardar Resultado
                    f) Salir
                    """);
            String opcion = sc.nextLine();
            switch (opcion) {
                case "a":
                    crearCompeticion();
                    break;
                case "b":
                    editarCompeticion();
                    break;
                case "c":
                    eliminarCompeticion();
                    break;
                case "d":
                    cerrarEtapa();
                    break;
                case "e":
                    resultado();
                    break;
                case "f":
                    cont = false;
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        }while (cont);
    }

    public static void crearCompeticion(){
        if (competiciones.isEmpty()){
            competiciones.add(new Competicion(1,validarDato("Nombre","Nombre Competicion:","^[A-Za-z09]+$"),"Inscripcion",validarDato("Tipo Puntuacion","Tipo Puntuacion de la competicion","^[A-Za-z]+$"),equipos));
        }else {
            if (competiciones.getLast().getFechaFin() == null | competiciones.getLast().getFechaFin().isAfter(fechaHoy)) {
                System.out.println("La competicion no se puede crear por que hay una en curso");
            }else{
            String nombreCompeticion = validarDato("Nombre","Nombre Competicion:","^[A-Za-z09]+$");
            if (competiciones.stream().noneMatch(u -> u.getNombre().equals(nombreCompeticion))) {
                competiciones.add(new Competicion(competiciones.size()+1,nombreCompeticion,"Inscripcion",validarDato("Tipo Puntuacion","Tipo Puntuacion de la competicion","^[A-Za-z]+$"),equipos));
            }else {
                System.out.println("Ya existe una competicion con ese nombre");
            }
            }
        }
    }

    public static void editarCompeticion(){
        if (competiciones.isEmpty()){
            System.out.println("No hay competiciones para editar");
        }else {
            String nombreCompeticion = validarDato("Nombre","Nombre Competicion para editar:","^[A-Za-z09]+$");
            if (competiciones.stream().noneMatch(u -> u.getNombre().equals(nombreCompeticion))) {
                System.out.println("El nombre de la competicion no existe");
            }else {
                int posicionEditar = competiciones.indexOf(competiciones.stream().filter(u -> u.getNombre().equalsIgnoreCase(nombreCompeticion)).findFirst().orElse(null));
                boolean editar = true;
                do {
                    System.out.print("""
                    -- Menu Editar --
                    a) Nombre
                    b) Tipo Puntuacion
                    c) Salir
                    """);
                    String opcion = sc.nextLine();
                    switch (opcion) {
                        case "a":
                            String nombreNuevo = validarDato("Nombre","Nuevo nombre de la Competicion:","^[A-Za-z0-9]+$");
                            if (competiciones.stream().noneMatch(u -> u.getNombre().equalsIgnoreCase(nombreNuevo))) {
                                competiciones.get(posicionEditar).setNombre(nombreNuevo);
                            }else  {
                                System.out.println("El nombre de competicion ya existe");
                            }
                            break;
                        case "b":
                            String tipoPuntuacionNuevo = validarDato("Tipo Puntuacion", "Nuevo tipo de puntuacion de la Competicion:","^[A-Za-z]$");
                            competiciones.get(posicionEditar).setTipoPuntuacion(tipoPuntuacionNuevo);
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
    }

    public static void eliminarCompeticion(){
        if (competiciones.isEmpty()){
            System.out.println("No hay competiciones para eliminar");
        }else {
            String competicionEliminar = validarDato("Nombre","Nombre Competicion para eliminar:","^[A-Za-z0-9]+$");
            if (competiciones.stream().noneMatch(u -> u.getNombre().equals(competicionEliminar))) {
                System.out.println("El nombre de la competicion no existe");
            }else {
                Competicion competicion = competiciones.stream().filter(u -> u.getNombre().equals(competicionEliminar)).findFirst().orElse(null);
                for (Equipo equipo : equipos) {
                    if (equipo.getCompeticiones().stream().anyMatch(u -> u.getNombre().equals(competicionEliminar))) {
                        equipo.getCompeticiones().remove(competicion);
                    }
                }
                competiciones.remove(competicion);
            }
        }
    }

    public static void cerrarEtapa(){
        if (competiciones.isEmpty()){
            System.out.println("No hay competiciones para cerrar");
        }else {
            if (competiciones.getLast().getEtapa().equalsIgnoreCase("Competicion")){
                System.out.println("La competicion ya esta cerrada");
            }else {
                Competicion competicion = competiciones.getLast();
                competicion.setEtapa("Competicion");
                LocalDate fechaInicio = fechaHoy.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
                competicion.setFechaInicio(fechaInicio);
                long diasHastaFinal = 7L * (long) (equipos.size()-1);
                LocalDate fechaFin = fechaInicio.plusDays(diasHastaFinal);
                competicion.setFechaFin(fechaFin);
                ArrayList<Jornada> jornadasCompeticion = new ArrayList<>();
                LocalTime horaEnfrentamiento = LocalTime.of(13,0);
                ArrayList<Equipo> equiposCompeticion = new ArrayList<>(equipos);
                for (int i = 1; i < equipos.size(); i++) {
                    ArrayList<Enfrentamiento> enfrentamientoJornada = new ArrayList<>();
                    if (jornadas.isEmpty()){
                        Jornada jornada = new Jornada(1,fechaInicio,i,competicion);
                        jornadas.add(jornada);
                        jornadasCompeticion.add(jornada);
                        for (int x = 0; x < equipos.size()/2; x++) {
                            if (enfrentamientos.isEmpty()){
                                Equipo equipolocal = equiposCompeticion.get(x);
                                Equipo equipofinal = equiposCompeticion.get(equipos.size()-x-1);
                                Equipo[] equiposEnfrentamiento = new Equipo[2];
                                equiposEnfrentamiento[0] = equipolocal;
                                equiposEnfrentamiento[1] = equipofinal;
                                Enfrentamiento enfrentamiento = new Enfrentamiento(1,horaEnfrentamiento.plusHours(x),equiposEnfrentamiento,jornada);
                                enfrentamientos.add(enfrentamiento);
                                enfrentamientoJornada.add(enfrentamiento);
                            }else {
                                Equipo equipolocal = equiposCompeticion.get(x);
                                Equipo equipofinal = equiposCompeticion.get(equipos.size()-x-1);
                                Equipo[] equiposEnfrentamiento = new Equipo[2];
                                equiposEnfrentamiento[0] = equipolocal;
                                equiposEnfrentamiento[1] = equipofinal;
                                Enfrentamiento enfrentamiento = new Enfrentamiento(enfrentamientos.size()+1,horaEnfrentamiento.plusHours(x),equiposEnfrentamiento,jornada);
                                enfrentamientos.add(enfrentamiento);
                                enfrentamientoJornada.add(enfrentamiento);
                            }
                        }
                        equiposCompeticion.add(equiposCompeticion.get(1));
                        equiposCompeticion.remove(equiposCompeticion.get(1));
                        jornada.getEnfrentamientos().addAll(enfrentamientoJornada);
                    }else {
                        Jornada jornada = new Jornada(jornadas.size()+1, fechaInicio,i,competicion);
                        jornadas.add(jornada);
                        jornadasCompeticion.add(jornada);
                        for (int x = 0; x < equipos.size()/2; x++) {
                            if (enfrentamientos.isEmpty()) {
                                Equipo equipolocal = equiposCompeticion.get(x);
                                Equipo equipofinal = equiposCompeticion.get(equipos.size() - x-1);
                                Equipo[] equiposEnfrentamiento = new Equipo[2];
                                equiposEnfrentamiento[0] = equipolocal;
                                equiposEnfrentamiento[1] = equipofinal;
                                Enfrentamiento enfrentamiento = new Enfrentamiento(1, horaEnfrentamiento.plusHours(x), equiposEnfrentamiento, jornada);
                                enfrentamientos.add(enfrentamiento);
                                enfrentamientoJornada.add(enfrentamiento);
                            }else {
                                Equipo equipolocal = equiposCompeticion.get(x);
                                Equipo equipofinal = equiposCompeticion.get(equipos.size()-x-1);
                                Equipo[] equiposEnfrentamiento = new Equipo[2];
                                equiposEnfrentamiento[0] = equipolocal;
                                equiposEnfrentamiento[1] = equipofinal;
                                Enfrentamiento enfrentamiento = new Enfrentamiento(enfrentamientos.size()+1,horaEnfrentamiento.plusHours(x),equiposEnfrentamiento,jornada);
                                enfrentamientos.add(enfrentamiento);
                                enfrentamientoJornada.add(enfrentamiento);
                            }
                        }
                        equiposCompeticion.add(equiposCompeticion.get(1));
                        equiposCompeticion.remove(equiposCompeticion.get(1));
                        jornada.getEnfrentamientos().addAll(enfrentamientoJornada);
                    }
                    competicion.setJornadas(jornadasCompeticion);
                    fechaInicio = fechaInicio.plusDays(7);
                }


            }
        }
    }

    public static void verEquiposCompeticion(){
        if (competiciones.isEmpty()){
            System.out.println("No hay competiciones creadas");
        }else {
            String nombreCompeticion = validarDato("Nombre", "Nombre Competicion a buscar:", "^[A-Za-z0-9]+$");
            Competicion competicion = competiciones.stream().filter(competicion1 ->  competicion1.getNombre().equalsIgnoreCase(nombreCompeticion)).findFirst().orElse(null);
            if (competicion != null){
                System.out.println("--Lista de equipos de "+ competicion.getNombre()+"--");
                for (Equipo equipo : competicion.getEquipos()){
                    System.out.println("-"+equipo.getNombre());
                }
            }else {
                System.out.println("La competicion no tiene equipos");
            }
        }
    }

    public static void resultado(){
        if (competiciones.isEmpty()){
            System.out.println("No hay competiciones creadas");
        }else {
            if (competiciones.getLast().getEtapa().equalsIgnoreCase("Incripcion")){
                System.out.println("La competicion no ha sido iniciada");
            }else {
                boolean jugado = false;
                for (Jornada jornada : competiciones.getLast().getJornadas()){
                    if (jornada.getFecha().isBefore(fechaHoy)){
                        System.out.println("--Jornada "+jornada.getNumJornada()+"--");
                        for (Enfrentamiento enfrentamiento : jornada.getEnfrentamientos()){
                            System.out.println("--"+enfrentamiento.getEquipos()[0].getNombre()+" VS "+enfrentamiento.getEquipos()[1].getNombre()+"--");
                            resultados.add(new Resultado(enfrentamiento.getEquipos()[0],
                                    enfrentamiento,
                                    Integer.parseInt(validarDato("Puntuacion","Puntuacion del equipo "+ enfrentamiento.getEquipos()[0].getNombre(),"^[0-9]+$"))));
                            resultados.add(new Resultado(enfrentamiento.getEquipos()[1],
                                    enfrentamiento,
                                    Integer.parseInt(validarDato("Puntuacion","Puntuacion del equipo "+ enfrentamiento.getEquipos()[1].getNombre(),"^[0-9]+$"))));
                        }
                        jugado = true;
                    }
                }
                if (!jugado){
                    System.out.println("No se ha jugado niguna jornada");
                }else {
                    System.out.println("No se ha jugado ninguna jornada mas o no quedan jornadas");
                }
            }
        }
    }

    public static void verInformeUltimaJornada(){
        boolean jugado = false;
        if (jornadas.isEmpty()){
            System.out.println("No hay jornadas creadas");
        }else {
            Jornada ultimaJornada = null;
            StringBuilder marcador =  new StringBuilder();
            for (Jornada jornada : jornadas){
                if (jornada.getFecha().isBefore(fechaHoy)){
                    ultimaJornada=jornada;
                }
            }
            for (Resultado resultado : resultados){
                if (resultado.getEnfrentamiento().getJornada() == ultimaJornada){
                    jugado=true;
                    marcador.append(" - ").append(resultado.getPuntuacion());
                }
            }
            if (jugado){
                if (ultimaJornada != null) {
                    for (Enfrentamiento enfrentamiento : ultimaJornada.getEnfrentamientos()){
                        System.out.println("--"+enfrentamiento.getEquipos()[0].getNombre()+" VS "+enfrentamiento.getEquipos()[1].getNombre()+"--");

                    }
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
                if (fechaFundacion.isAfter(fechaHoy)) {
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