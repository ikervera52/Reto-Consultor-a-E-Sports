import Excepciones.*;
import Excepciones.NicknameExiste;
import Excepciones.OpcionNoValida;
import Modelo.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    //Suelo mínimo interprofessional
    final static int SMI = 17094;
    // Tipos de roles
    final static String ROLES = "^(support|awper|igl|lurker|rifler|entry fragger)$";
    // Scanner
    final static Scanner sc = new Scanner(System.in);

    // Variables
    public static ArrayList<Jornada> jornadas = new ArrayList<>();
    public static ArrayList<Enfrentamiento> enfrentamientos = new ArrayList<>();
    public static ArrayList<Resultado> resultados = new ArrayList<>();
    public static Usuario usuario;
    public static DateTimeFormatter formatofecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static double SMI = 17094;
    public static LocalDate fechaHoy = LocalDate.now();

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

    //Menu de Equipo para añadir o borrar jugadores
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

    //Menu de Jugadores para añadir o borrar equipo
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
    // Listas
    public static ArrayList<Usuario> usuarios;
    public static ArrayList<Jugador> jugadores;
    public static ArrayList<Equipo> equipos;
    public static ArrayList<Resultado> resultados;
    public static Competicion competicion;

    public static void main(String[] args) {
        crearUsuariosPrueba();
        jugadores = new ArrayList<>();
        equipos = new ArrayList<>();
        resultados = new ArrayList<>();
        competicion = new Competicion(0, Competicion.TipoEtapa.INSCRIPCION);

        //Borrar después
        crearJugadoresEquiposPrueba();

        menuInicioSesion();
    }

    //Crear primer usuario
    public static void crearUsuariosPrueba() {
        usuarios = new ArrayList<>();
        Admin admin = new Admin(0, "admin", "admin");
        usuarios.add(admin);
        Estandar estandar = new Estandar(0, "estandar", "estandar");
        usuarios.add(estandar);
    }
    public static void crearJugadoresEquiposPrueba() {
        Jugador jug1 = new Jugador(0, "jug1", "e", "e", "e", "awper");
        jug1.setSueldo(200000, SMI);

        Jugador jug2 = new Jugador(1, "jug2", "e", "e", "e", "awper");
        jug2.setSueldo(200000, SMI);

        Jugador jug3 = new Jugador(1, "jug3", "e", "e", "e", "awper");
        jug2.setSueldo(200000, SMI);

        Jugador jug4 = new Jugador(1, "jug4", "e", "e", "e", "awper");
        jug2.setSueldo(200000, SMI);



        Equipo eq1 = new Equipo(0, "eq1");
        Equipo eq2 = new Equipo(1, "eq2");
        Equipo eq3 = new Equipo(2, "eq3");
        Equipo eq4 = new Equipo(3, "eq4");

        eq1.setJugador(jug1);
        eq2.setJugador(jug2);
        eq3.setJugador(jug3);
        eq4.setJugador(jug4);

        equipos.add(eq1);
        equipos.add(eq2);
        equipos.add(eq3);
        equipos.add(eq4);

        jugadores.add(jug1);
        jugadores.add(jug2);
        jugadores.add(jug3);
        jugadores.add(jug4);
    }

    //Menu antesala
    public static void menuInicioSesion(){
        boolean salir = false;
        do {
            try {
                System.out.print("""
                \n
                a) Iniciar sesión
                b) Salir
                Que quieres hacer:\s""");
                String respueta = sc.nextLine();
                switch (respueta) {
                    case "a" :
                        Usuario usuario = menuDeIdentification();
                        menuPrincipal(usuario);
                        break;
                    case "b" :
                        salir = true;
                        break;
                    default : throw new Error();
                }
            }
            catch (Error e){
                System.out.println("\n * Opción no valida * \n");
            }
        }while (!salir);
    }
    //Identificar al usuario
    public static Usuario menuDeIdentification() {
        boolean error = true;
        Usuario usuario = null;
        do {
            try {
                System.out.print("\nUsuario: ");
                String nombreUsuario = sc.nextLine();

                System.out.print("Contraseña: ");
                String password = sc.nextLine();

                usuario= usuarios.stream()
                        .filter(p -> p.getNombreUsuario().equals(nombreUsuario) && p.getContrasena().equals(password))
                        .findFirst().orElse(null);
                if (usuario == null) {
                    throw new Error();
                }


                error = false;
            } catch (Error e) {
                System.out.println("usuario o contraseña no validos");
            }
        } while (error);

        return usuario;

    }
    //Menú principal
    public static void menuPrincipal(Usuario usuario){
    boolean salir = false;
    do {
        try {
            System.out.println("\n-- > Hola, " + usuario.getNombreUsuario());
            if (usuario instanceof Estandar && competicion.getEtapa() == Competicion.TipoEtapa.COMPETICION) {
                System.out.println("""
                        -- Menú de usuario --
                        a) Ver equipos de la competición
                        b) Ver resultados de la ultima jornada
                        c) Cerrar sesión
                        Que quieres hacer:\s""");
                String respuesta = sc.nextLine();
                switch (respuesta) {
                    case "a":
                        verEquiposPorCompeticion();
                        break;
                    case "b":
                        verInformeUltimaJornada();
                        break;
                    case "c":
                        salir = true;
                        break;
                    default:
                        throw new Error();
                }
            }
            if (usuario instanceof Estandar && competicion.getEtapa() == Competicion.TipoEtapa.INSCRIPCION) {
                throw new OpcionNoValida();
            }

            if (usuario instanceof Admin && competicion.getEtapa() != Competicion.TipoEtapa.COMPETICION) {
                System.out.print("""
                        -- Menú de administrador, Etapa de Inscripción --
                        a) Gestionar usuarios
                        b) Gestionar jugadores
                        c) Gestionar equipos
                        d) Cerrar etapa de inscripción
                        e) Ver informes
                        f) Cerrar sesión
                        Que quieres hacer:\s""");
                String respuesta = sc.nextLine();
                switch (respuesta) {
                    case "a":
                        gestionar("usuario");
                        break;
                    case "b":
                        gestionar("jugador");
                        break;
                    case "c":
                        gestionar("equipo");
                        break;
                    case "d":
                        cerrarEtapaInscripcion();
                        break;
                    case "e":
                        verInformesEtapaInscripcion();
                        break;
                    case "f":
                        salir = true;
                        break;
                    default:
                        throw new Error();
                }
            }
            if (usuario instanceof Admin && competicion.getEtapa() == Competicion.TipoEtapa.COMPETICION) {
                System.out.print("""
                        -- Menú de administrador, Etapa de Competición --
                        a) Añadir puntuación
                        b) Ver informes
                        c) Terminar Competición
                        d) Cerrar sesión
                        Que quieres hacer:
                        """);
                String respuesta = sc.nextLine();
                switch (respuesta) {
                    case "a" -> resultado();
                    case "b" -> verInformes();
                    case "c" -> terminarCompeticion();
                    case "d" -> salir = true;
                    default -> throw new Error();
                }
            }
        } catch(Error e){
            System.out.println("\n * Opción no valida *\n");
        }
        catch (OpcionNoValida e){
            System.out.println("\n * No hay ninguna competición en marcha en este momento, vuelve más tarde * \n");
            salir = true;
        }
    }while (!salir);
    }
    //Gestion de clases
    public static void gestionar(String clase){
        boolean salir = false;
        do {
            try {
                System.out.print(
                        "\n-- Menu de edición --" + "\n" +
                        "a) Crear " + clase + "\n"  +
                                "b) Editar " + clase + "\n" +
                                "c) Eliminar " + clase + "\n" +
                                "d) Salir" + "\n" +
                                "Que quieres hacer: ");

                String respuesta = sc.nextLine();
                switch (clase){
                    case "usuario": switch (respuesta){
                        case "a" -> crearUsuario();
                        case "b" -> editarUsuario();
                        case "c" -> eliminarUsuario();
                        case "d" -> salir = true;
                        default ->  throw new Error();}
                        break;
                    case "jugador": switch (respuesta){
                        case "a" -> crearJugador();
                        case "b" -> editarJugador();
                        case "c" -> eliminarJugador();
                        case "d" -> salir = true;
                        default ->  throw new Error();}
                        break;
                    case "equipo": switch (respuesta){
                        case "a" -> crearEquipo();
                        case "b" -> editarEquipo();
                        case "c" -> eliminarEquipo();
                        case "d" -> salir = true;
                        default ->  throw new Error();}
                        break;
                }
            }
            catch (Error e){
                System.out.println("Opción no valida");
            }
        }while (!salir);
    }

    //Acciones con usuarios
    public static void crearUsuario(){
        try {
            System.out.println("\n-- Crear usuario --");
            String tipoUsuario = validarDato("Tipo de usuario", "Tipo de usuario: ", "^(admin|estandar)$");

            String nombreUsuario = validarDato("Nombre", "Nombre del usuario: ", "^[A-Za-z0-9]+$");
            if (usuarios.stream().anyMatch(u -> u.getNombreUsuario().equalsIgnoreCase(nombreUsuario))){
                throw new Error();
            }

            String contrasena = validarDato("Contraseña", "Contraseña: ", "^[A-Za-z0-9]+$");

            if (tipoUsuario.equals("admin")){
                usuarios.add(new Admin(usuarios.size(), nombreUsuario, contrasena));
            } else  {
                usuarios.add(new Estandar(usuarios.size(), nombreUsuario, contrasena));
            }
        }
        catch (Error e){
            System.out.println("Nombre de usuario no valido");
        }
    }
    public static void editarUsuario(){
        boolean salir = false;
        System.out.println("\n-- Editar usuario --");
        System.out.print("Que usuario quieres editar: ");
        String nombre = sc.nextLine();
        do {
            try {

                String finalNombre = nombre;
                Usuario usuarioEditar = usuarios.stream()
                        .filter(u -> u.getNombreUsuario().equalsIgnoreCase(finalNombre))
                        .findFirst().orElse(null);
                if (usuarioEditar == null){
                    throw new Error();
                }

                int posicion = usuarios.indexOf(usuarioEditar);

                System.out.println("""
                a) Editar nombre
                b) Editar contraseña
                c) Salir""");
                String respuesta = sc.nextLine();
                switch (respuesta){
                    case "a" -> nombre = editarNombreUsuario(posicion);
                    case "b" -> editarContrasenaUsuario( posicion);
                    case "c" -> salir = true;
                    default -> throw new OpcionNoValida();
                }
            }
            catch (Error e){
                System.out.println("\n * Nombre de usuario no valido * \n");
                salir = true;
            }
            catch (OpcionNoValida e){
                System.out.println("Opción no valida");
            }
        }while (!salir);
    }
    public static String editarNombreUsuario(int posicion){
        String nuevoNombre = null;
        try {
            nuevoNombre =  validarDato("Nombre", "Nuevo nombre: ", "^[A-Za-z0-9]+$");
            String finalNuevoNombre = nuevoNombre;
            if (usuarios.stream().anyMatch(u -> u.getNombreUsuario().equalsIgnoreCase(finalNuevoNombre))){
                throw new Error();
            }
            usuarios.get(posicion).setNombreUsuario(nuevoNombre);
        }catch (Error e){
            System.out.println("Nombre de usuario no valido");
        }

        return nuevoNombre;
    }
    public static void editarContrasenaUsuario(int posicion){
            String nuevaContrasena = validarDato("Nueva Contraseña ", "Nueva contraseña: ", "^[A-Za-z0-9]+$");
            usuarios.get(posicion).setContrasena(nuevaContrasena);
    }
    public static void eliminarUsuario(){
        try{
            System.out.println("\n-- Eliminar usuario --");
            System.out.println("Que usuario quieres eliminar: ");
            String nombreEliminar = sc.nextLine();

            Usuario usuarioEditar = usuarios.stream()
                    .filter(u -> u.getNombreUsuario().equalsIgnoreCase(nombreEliminar))
                    .findFirst().orElse(null);
            if (usuarioEditar == null){
                throw new Error();
            }

            usuarios.remove(usuarioEditar);
            System.out.println("--> Usuario eliminado");
        }
        catch (Error e){
            System.out.println("\n * Este usuario no existe * \n");
        }
    }

    //Acciones con jugadores
    public static void crearJugador(){
        boolean error = true;
        do {
            try {
                System.out.println("\n-- Crear Jugador --");
                String nickJugador = validarDato("Nickname", "Nickname del Jugador: ", "^[A-Za-z0-9]+$");
                if (jugadores.stream().anyMatch(u -> u.getNickname().equalsIgnoreCase(nickJugador))){
                    throw new NicknameExiste();
                }

                int contador = 0;
                if(!jugadores.isEmpty()){
                    contador = jugadores.size();
                }
                Jugador jugador = new Jugador(contador, nickJugador,
                        validarDato("Nombre", "Nombre: ", "^[A-Za-z]+$"),
                        validarDato("Apellido", "Apellido: ", "^[A-Za-z]+$"),
                        validarDato("Nacionalidad", "Nacionalidad: ", "^[A-Za-z]+$"),
                        validarDato("Rol", "Rol: ", ROLES));

                String sueldo = validarDato("Sueldo", "Sueldo: ", "^[0-9]+$");
                boolean sueldoBalido = jugador.setSueldo(Double.parseDouble(sueldo), SMI);
                if (!sueldoBalido){
                    throw new Error();
                }

                jugadores.add(jugador);
                error = false;
            }
            catch (Error e){
                System.out.println("\n* Sueldo no valido, tiene que ser superior al SMI *\n");
            }
            catch (NicknameExiste e){
                System.out.println("\n* NickName ya existente *\n");
            }
        }while (error);
    }
    
        public static void editarJugador(){
        boolean salir = false;
        String nick;

        System.out.println("\n-- Editar jugador --");
        System.out.println("Que jugador quieres editar: ");
        nick = sc.nextLine();

        do {
            try {
                String finalNick = nick;
                Jugador jugador = jugadores.stream()
                        .filter(u -> u.getNickname().equalsIgnoreCase(finalNick))
                        .findFirst().orElse(null);
                if (jugador == null){
                    throw new Error();
                }

                int posicion = jugadores.indexOf(jugador);

                System.out.println("""
                a) Editar nickname
                b) Editar nombre
                c) Editar apellido
                d) Editar nacionalidad
                e) Editar rol
                f) Editar sueldo
                g) Editar equipo
                h) Salir""");
                String respuesta = sc.nextLine();
                switch (respuesta){
                    case "a" -> nick = editarNickJugador(posicion);
                    case "b" -> editarJugadorGeneral(posicion, "Nuevo nombre", "Nuevo nombre: ", "^[A-Za-z]+$");
                    case "c" -> editarJugadorGeneral(posicion, "Nuevo apellido", "Nuevo apellido: ", "^[A-Za-z]+$");
                    case "d" -> editarJugadorGeneral(posicion, "Nueva nacionalidad", "Nueva nacionalidad: ", "^[A-Za-z]+$");
                    case "e" -> editarJugadorGeneral(posicion, "Nuevo rol", "Nuevo rol: ", ROLES);
                    case "f" -> editarSueldoJugador(posicion);
                    case "g" -> editarJugadorEquipo(posicion);
                    case "h" -> salir = true;
                    default -> throw new OpcionNoValida();
                }
            }
            catch (Error e){
                System.out.println("\n * Nickname no valido * \n");
                salir = true;
            }
            catch (OpcionNoValida e){
                System.out.println("\n * Opción no valida * \n");
            }
        }while (!salir);
    }

    
    public static String editarNickJugador(int posicion){
        String nickNuevo = validarDato("Nuevo nickname", "Nuevo nickname: ", "^[A-Za-z0-9]+$");
        if (jugadores.stream().anyMatch(u -> u.getNickname().equals(nickNuevo))){
            throw new Error();
        }
        jugadores.get(posicion).setNickname(nickNuevo);
        return nickNuevo;
    }
    public static void editarJugadorGeneral(int posicion ,String dato, String frase, String formato){
        String edit = validarDato(dato, frase, formato);
        switch (dato){
            case "Nuevo apellido" -> jugadores.get(posicion).setApellido(edit);
            case "Nueva nacionalidad" -> jugadores.get(posicion).setNacionalidad(edit);
            case "Nuevo nombre" -> jugadores.get(posicion).setNombre(edit);
        }
    }
    public static void editarSueldoJugador(int posicion){
        boolean error = true;
        do{
           try {
              String sueldoString = validarDato("Nuevo sueldo", "Nuevo sueldo: ", "^[0-9]+$");
              int sueldo = Integer.parseInt(sueldoString);

              boolean sueldoMinimo = jugadores.get(posicion).setSueldo(sueldo, SMI);
              if (!sueldoMinimo){
                  throw new Error();
              }
              error = false;
          }
          catch (Error e){
              System.out.println("Sueldo no valido");
          }
        }while(error)
    }
    
    //Acciones con equipos
    public static void crearEquipo(){
        try {
            System.out.println("\n-- Crear equipo --");
            String nombreEquipo = validarDato("Nombre", "Nombre del Equipo: ", "^[A-Za-z0-9]+$");
            if (equipos.stream().anyMatch(u -> u.getNombre().equalsIgnoreCase(nombreEquipo))){
                throw new Error();
            }

            int contador = 0;
            if(!equipos.isEmpty()){
                contador = equipos.size();
            }
            Equipo equipo = new Equipo(contador, nombreEquipo);

            LocalDate fechaFundacion = LocalDate.parse(validarDato("Fecha de fundación",
                    "Fecha de fundación (dd/mm/yyyy): ", "^[0-9/]+$"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            equipo.setFechaFundacion(fechaFundacion);
            equipos.add(equipo);
        }
        catch (Error e){
            System.out.println("\n * Ya existe un equipo con ese nombre *\n");
        }
    }
    public static void editarEquipo(){
        boolean salir = false;
        if(equipos.isEmpty()){
        throw new Error();}
        
        System.out.println("\n-- Editar equipo --");
        System.out.print("Que equipo quieres editar: ");
        String nombre = sc.nextLine();
        do {
            try {
                String finalNombre = nombre;
                Equipo equipo = equipos.stream()
                        .filter(u -> u.getNombre().equalsIgnoreCase(finalNombre))
                        .findFirst().orElse(null);
                if (equipo == null){
                    throw new Error();
                }

                int posicion = equipos.indexOf(equipo);

                System.out.println("""
                a) Editar nombre
                b) Editar fecha de fundación
                c) Editar jugadores del equipo
                d) Salir""");
                String respuesta = sc.nextLine();
                switch (respuesta){
                    case "a" -> nombre = editarNombreEquipo(posicion);
                    case "b" -> editarFechaFundacion(posicion);
                    case "c" -> editarEquipoJugadores(posicion);
                    case "d" -> salir = true;
                    default -> throw new OpcionNoValida();
                }
            }
            catch (Error e){
                System.out.println("\n * No existe un equipo con ese nombre *\n");
                salir = true;
            }
            catch (OpcionNoValida e){
                System.out.println("\n * Opción no valida *\n");
            }
        }while (!salir);
    }
    public static String editarNombreEquipo(int posicion){
        String nombreNuevo = validarDato("Nuevo nombre", "Nuevo nombre: ", "^[A-Za-z0-9]+$");
        if (equipos.stream().anyMatch(u -> u.getNombre().equals(nombreNuevo))){
            throw new Error();
        }
        equipos.get(posicion).setNombre(nombreNuevo);


    //Añadir resultados de los enfrentamientos
    public static void resultado(){
  
                boolean jugado = false;
                for (Jornada jornada : competicion.getJornadas()){
                    if (resultados.isEmpty() || resultados.getLast().getEnfrentamiento().getJornada().getNumJornada() < jornada.getNumJornada()){
                        if (jornada.getFecha().isBefore(fechaHoy)){
                            System.out.println("--Jornada "+jornada.getNumJornada()+"--");
                            for (Enfrentamiento enfrentamiento : jornada.getEnfrentamientos()){
                                System.out.println("--"+enfrentamiento.getEquipos()[0].getNombre()+" VS "+enfrentamiento.getEquipos()[1].getNombre()+"--");
                                resultados.add(new Resultado(enfrentamiento.getEquipos()[0],
                                        enfrentamiento,
                                        Integer.parseInt(validarDato("Puntuacion","Puntuacion del equipo "+ enfrentamiento.getEquipos()[0].getNombre()+":","^[0-9]+$"))));
                                resultados.add(new Resultado(enfrentamiento.getEquipos()[1],
                                        enfrentamiento,
                                        Integer.parseInt(validarDato("Puntuacion","Puntuacion del equipo "+ enfrentamiento.getEquipos()[1].getNombre()+":","^[0-9]+$"))));
                            }
                            jugado = true;
                        }
                    }
                }
                if (!jugado){
                    System.out.println("No se ha jugado niguna jornada");
                }else {
                    System.out.println("No se ha jugado ninguna jornada mas o no quedan jornadas");
                }
    }

    //Informe de la ultima jornada
    public static void verInformeUltimaJornada(){
        boolean jugado = false;
        if (jornadas.isEmpty()){
            System.out.println("No hay jornadas creadas");
        }else {
            Jornada ultimaJornada = null;
            for (Jornada jornada : jornadas){
                if (jornada.getFecha().isBefore(fechaHoy)){
                    ultimaJornada=jornada;
                }
            }
            boolean mostrado = false;
            for (Resultado resultado1 : resultados){
                if (resultado1.getEnfrentamiento().getJornada() == ultimaJornada && !mostrado){
                    if (ultimaJornada != null) {
                        System.out.println("--Resultados ultima jornada--");
                        for (Enfrentamiento enfrentamiento : ultimaJornada.getEnfrentamientos()){
                            System.out.println("--"+enfrentamiento.getEquipos()[0].getNombre()+" VS "+enfrentamiento.getEquipos()[1].getNombre()+"--");
                            for (Resultado resultado : resultados){
                                if (resultado.getEnfrentamiento()==enfrentamiento && resultado.getEquipo()==enfrentamiento.getEquipos()[0]){
                                    System.out.print(resultado.getPuntuacion() + " - ");
                                }else if(resultado.getEnfrentamiento()==enfrentamiento && resultado.getEquipo()==enfrentamiento.getEquipos()[1]){
                                    System.out.print(resultado.getPuntuacion());
                                    System.out.println();
                                }
                            }
                        }
                        mostrado = true;
                    }
                }
            }
            if (!mostrado){
                System.out.println("No se han guardado datos de ningun enfrentamiento");
            }
        }
    }

    //Generar y ver clasificacion
    public static void verClasificacion(){
        if (resultados.isEmpty()){
            System.out.println("No hay resultados de ningun enfrentamiento");
        }else {
            Map <Equipo, Integer> clasificacion = new HashMap<Equipo, Integer>();
            for (Equipo equipo : equipos){
                clasificacion.put(equipo, 0);
            }
            String nombreCompeticion = validarDato("Nombre","Nombre de la competicion","^[A-Za-z0-9]+$");
            Competicion competicion = competiciones.stream().filter(comp -> comp.getNombre().equals(nombreCompeticion)).findFirst().orElse(null);
            if (competicion != null) {
                for (Jornada jornada : competicion.getJornadas()){
                    if (resultados.getLast().getEnfrentamiento().getJornada().getNumJornada() >= jornada.getNumJornada()){
                        for (Enfrentamiento enfrentamiento : jornada.getEnfrentamientos()){
                            int puntuacionEquipoLocal = 0;
                            int puntuacionEquipoVisitante = 0;
                            for (Resultado resultado : resultados){
                                if (resultado.getEnfrentamiento() ==  enfrentamiento){
                                    if (resultado.getEquipo() == enfrentamiento.getEquipos()[0]){
                                        puntuacionEquipoLocal = resultado.getPuntuacion();
                                    }else {
                                        puntuacionEquipoVisitante = resultado.getPuntuacion();
                                    }
                                }
                            }
                            if (puntuacionEquipoLocal == puntuacionEquipoVisitante){
                                clasificacion.put(enfrentamiento.getEquipos()[0],clasificacion.get(enfrentamiento.getEquipos()[0]) + 1);
                                clasificacion.put(enfrentamiento.getEquipos()[1],clasificacion.get(enfrentamiento.getEquipos()[1]) + 1);
                            }if (puntuacionEquipoLocal > puntuacionEquipoVisitante){
                                clasificacion.put(enfrentamiento.getEquipos()[0],clasificacion.get(enfrentamiento.getEquipos()[0]) + 3);
                            }else {
                                clasificacion.put(enfrentamiento.getEquipos()[1],clasificacion.get(enfrentamiento.getEquipos()[1]) + 3);
                            }
                        }
                    }
                }
            }
            System.out.println("--Clasificacion--");
            clasificacion.entrySet().stream()
                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEach(e -> {
                        System.out.println(e.getKey().getNombre() + " -> " + e.getValue());
                    });
        }
    }

      //Informe de todos los equipos
    public static void verEquipos(){
        System.out.println("--- Equipos ---");
        for (Equipo equipo : equipos){
            System.out.println(equipo.toString());
        }
    }

    //Informe de todos los jugadores
    public static void verJugadores(){
        System.out.println("--- Jugadores ---");
        for (Jugador jugador : jugadores){
            System.out.println(jugador.toString());
        }
    }

    //Validacion de datos tipo STRING
    public static String validarDato(String dato, String mensaje, String exp){
        String respuesta = "";
        boolean error = false;
        do {
            try {
                System.out.println(mensaje);
                respuesta = sc.nextLine();
                if (!respuesta.matches(exp)) {
                    throw new DatoNoValido();
        return nombreNuevo;
    }
    public static void editarFechaFundacion(int posicion){
        LocalDate nuevaFecha = LocalDate.parse(validarDato("Nueva fecha de fundación",
                "Nueva fecha de fundación (dd/mm/yyyy): ", "^[0-9-]+$"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        equipos.get(posicion).setFechaFundacion(nuevaFecha);
    }

    //Cerrar etapa de inscripción
    public static void cerrarEtapaInscripcion(){
        try {
            // Mirar si hay equipos pares
            if (equipos.size() % 2 != 0){
                throw new Error();
            }

            // Mirar si hay dos jugadores por lo menos por equipo
        /*boolean dosJugadoresMinimo = equipos.stream()
                .anyMatch(e -> e.getJugadores().size() < 2);
        if (dosJugadoresMinimo){
            throw new Error();
        }*/

            competicion.setEtapa(Competicion.TipoEtapa.COMPETICION);

            System.out.println("\n-- Cerrando etapa inscripción --");

            System.out.println("Que tipo de puntuación se usa: ");
            String puntuacion = sc.nextLine();

            competicion.setTipoPuntuacion(puntuacion);

            // Fecha de inicio
            LocalDate primerSabado = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
            competicion.setFechaInicio(primerSabado);

            // Fecha de fin
            int numeroDeEtapas = equipos.size() - 1;
            competicion.setFechaFin(primerSabado.plusDays(7 * (long) numeroDeEtapas));

            // Añadir la competicion a cada Equipo
            for (Equipo equipo : equipos){
                equipo.setCompeticion(competicion);
            }

            //Relacionar equipos con la competición
            competicion.setEquipos(equipos);
            for (Equipo equipo : equipos){
                equipo.setCompeticion(competicion);
            }

            // JORNADAS

            LocalDate fechaJornada = primerSabado;

            for (int i = 0; i < numeroDeEtapas; i++) {
                Jornada jornada = new Jornada();

                jornada.setIdJornada(competicion.getJornadas().size());
                jornada.setCompeticion(competicion);

                jornada.setFechaJornada(fechaJornada);

                jornada.setNumeroJornada(competicion.getJornadas().size() + 1);

                fechaJornada = fechaJornada.plusDays(7);

                competicion.setJornada(jornada);
                jornadas.add(jornada);
            }

            int numeroEnfrentamientos = equipos.size() / 2;

            for (Jornada jornada : competicion.getJornadas()) {

                LocalTime horaEnfrentamiento = LocalTime.of(13, 0);

                for (int i = 0; i < numeroEnfrentamientos; i++) {
                    Enfrentamiento enfrentamiento = new Enfrentamiento();

                    enfrentamiento.setIdEnfrentamiento(jornada.getEnfrentamientos().size());
                    horaEnfrentamiento = horaEnfrentamiento.plusHours(1);
                    enfrentamiento.setHoraEnfrentamiento(horaEnfrentamiento);

                    jornada.setEnfrentamiento(enfrentamiento);
                    enfrentamientos.add(enfrentamiento);
                }
            }

            distribuirEquiposPorEnfrentamiento();

            System.out.println("\n--> La competición se ha cerrado \n");
        }
        catch (Error e){
            System.out.println("\n La competición no se ha podido crear, no hay equipos pares o los equipos no tienen 2 jugadores mínimo *\n ");
        }
    }
    public static void distribuirEquiposPorEnfrentamiento(){

        ArrayList<Equipo> equiposAlterados = new ArrayList<>(equipos);
        for (Jornada jornada : competicion.getJornadas()){

            for (int i = 0; i < jornada.getEnfrentamientos().size(); i++) {
                Equipo[] equiposArray = new Equipo[2];
                equiposArray[0] = equiposAlterados.get(i);
                equiposArray[1] = equiposAlterados.get(equiposAlterados.size() - i - 1);

                jornada.getEnfrentamientos().get(i).setEquipos(equiposArray);
            }

            Equipo equipoCambiar = equiposAlterados.get(1);
            equiposAlterados.remove(equipoCambiar);
            equiposAlterados.add(equipoCambiar);
        }
    }
              
    //Ver informes
    public static void verEquiposPorCompeticion(){
        for (Equipo equipo : competicion.getEquipos()){
            System.out.println(equipo.toString());
        }
    }
       
    public static void verInformes(){
        boolean salir = false;
        do {
            try {
                System.out.print("""
                -- Menú de informes --
                a) Ver los equipos de la competición
                b) Ver los jugadores de la competición
                c) Ver resultados de todas las jornadas
                d) Clasificación
                e) Salir
                Que quieres hacer:\s""");
                String respuesta = sc.nextLine();
                switch (respuesta){
                    case "a" -> verEquiposPorCompeticion();
                    case "b" -> verJugadores();
                    case "c" -> verResultadosJornadas();
                    case "d" -> verInformeUltimaJornada();
                    case "e" -> salir = true;
                    default -> throw new OpcionNoValida();
                }
            }
            catch (OpcionNoValida e){
                System.out.println("\n * Opción no valida * \n");
            }
        }while (!salir);
    }


    // Vista de informes antes de cerrar el periodo de prueba
    public static void verInformesEtapaInscripcion(){
        boolean salir = false;
        do {
            try{

                System.out.print("""
                --- Menú de informes ---
                a) Ver todos los equipos
                b) Ver todos los jugadores
                c) Salir
                Que quieres hacer:\s""");
                String respuesta = sc.nextLine();

                switch (respuesta){
                    case "a" -> verEquipos();
                    case "b" -> verJugadores();
                    case "c" -> salir = true;
                    default ->  throw new Error();
                }
                error = true;
            }catch (DatoNoValido e){
                System.out.println(dato + " no es correcto, vuelve a intentarlo");
            }
                  }while (!error);
        return respuesta;
    }

    //Validacion de datos tipo LOCALDATE
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

    //Validacion del sueldo

    public static void verEquipos(){
        try {
            if (equipos.isEmpty()){
                throw  new Error();
            }
            int contador = 0;
            System.out.println("--- Equipos ---");
            for (Equipo equipo : equipos){
                System.out.println(contador + ")");
                System.out.println(equipo.toString());
                contador++;
            }
        }
        catch (Error e){
            System.out.println("\n * No hay ningún equipo en este momento * \n");
        }
    }
    public static void verJugadores(){
        try {
            if (jugadores.isEmpty()){
                throw  new Error();
            }
            int contador = 0;
            System.out.println("--- Jugadores ---");
            for (Jugador jugador : jugadores){
                System.out.println(contador + ")");
                System.out.println(jugador.toString());
                contador++;
            }
        }
        catch (Error e){
            System.out.println("\n * No hay ningun jugador en este momento * \n");
        }
    }

    //Terminar competición
    public static void terminarCompeticion(){
        if (competicion.getFechaFin().plusDays(1).isBefore(LocalDate.now())){
            competicion = new Competicion();
            competicion.setEtapa(Competicion.TipoEtapa.INSCRIPCION);
        }
        else {
            System.out.println("\n * No puedes cerrar la competición, todavía no ha terminado * \n");
        }
    }

    //Validación de datos
    public static String validarDato(String dato, String frase, String formato){
        boolean error = true;
        String respuesta = null;
        do {
            try {
                System.out.print(frase);
                respuesta = sc.nextLine();
                Matcher matcher = Pattern.compile(formato).matcher(respuesta);
                if (!matcher.matches()){
                    throw new Error();
                }
                error = false;

            }
            catch (Error e){
                System.out.println(dato + " no valido");
            }
        }while (error);
        return respuesta;
    }
}