import Modelo.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    //Suelo mínimo interprofessional
    final static int SMI = 17094;
    // Tipos de roles
    final static String ROLES = "^(support|awper|igl|lurker|rifler|entry frager)$";
    // Scanner
    final static Scanner sc = new Scanner(System.in);

    // Listas
    public static ArrayList<Usuario> usuarios;
    public static ArrayList<Jugador> jugadores;
    public static ArrayList<Equipo> equipos;
    //Competición;
    public static Competicion competicion;

    public static void main(String[] args) {
        crearUsuariosPrueba();
        jugadores = new ArrayList<>();
        equipos = new ArrayList<>();

        String salir;
        do {
            Usuario usuario = menuDeIdentification();
            menuPrincipal(usuario);

            System.out.print("Quieres salir: ");
            salir = sc.nextLine();
        }while (!salir.equalsIgnoreCase("si"));

    }

    //Crear primer usuario
    public static void crearUsuariosPrueba(){
        usuarios = new ArrayList<>();
        Admin admin = new Admin(0, "admin", "admin");
        usuarios.add(admin);
        Estandar estandar = new Estandar(0, "estandar", "estandar");
        usuarios.add(estandar);
    }

    //Identificar al usuario
    public static Usuario menuDeIdentification(){
        boolean error = true;
        Usuario usuario = null;
        do {
            try {
                System.out.print("Usuario: ");
                String nombreUsuario = sc.nextLine();

                if (usuarios.stream()
                        .noneMatch(u -> u.getNombreUsuario().equalsIgnoreCase(nombreUsuario))){
                    throw new Error();
                }

                usuario = usuarios.stream()
                        .filter(u -> u.getNombreUsuario().equalsIgnoreCase(nombreUsuario))
                        .toList().getFirst();

                System.out.print("Contraseña: ");
                String password = sc.nextLine();

                if (!usuario.getContrasena().equals(password)){
                    throw new Error();
                }

                error = false;
            }
            catch (Error e){
                System.out.println("usuario o contraseña no validos");
            }
        }while (error);

        return usuario;

    }
    //Menú principal
    public static void menuPrincipal(Usuario usuario){
        boolean salir = false;
        do {
            try {
                System.out.print("""
                a) Cerrar sesión
                b) Ver equipos que compiten en una competición
                c) Ver los resultados de la ultima jornada
                """);

                if (usuario instanceof Admin){
                    System.out.print("""
                    d) Gestionar usuarios
                    e) Gestionar jugadores
                    f) Gestionar equipos
                    g) Crear competición
                    h) Cerrar etapa de inscripción
                    i) Ver informes
                    Que quieres hacer:\s""");
                }
                String respuesta = sc.nextLine();
                switch (respuesta){
                    case "a": salir = true;
                        break;
                    case "b": verEquiposPorCompeticion();
                        break;
                    case "c": verResultadosUltimaJornada();
                        break;
                    case "d": if (usuario instanceof Admin){ gestionar("usuario");}
                    else throw new Error();
                        break;
                    case "e": if (usuario instanceof Admin){gestionar("jugador");}
                    else throw new Error();
                        break;
                    case "f": if (usuario instanceof Admin){gestionar("equipo");}
                    else throw new Error();
                        break;
                    case "g": if  (usuario instanceof Admin){crearCompeticion();}
                        break;
                    case "h": if (usuario instanceof Admin){cerrarEtapaInscripcion();}
                        else throw new Error();
                        break;
            /*case "h": if(usuario instanceof Admin){verInfomres();}
                        else throw new Error();
                        break;*/
                    default: throw new Error();
                }
            }
            catch (Error e){
                System.out.println("Error en el programación");
            }

        }while (!salir);
    }

    public static void verEquiposPorCompeticion(){}
    public static void verResultadosUltimaJornada(){}
    //Gestion de clases
    public static void gestionar(String clase){
        boolean salir = false;
        do {
            try {
                System.out.print(
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
        do {
            try {
                System.out.print("Que usuario quieres editar: ");
                String nombre = sc.nextLine();

                Usuario usuarioEditar = usuarios.stream()
                        .filter(u -> u.getNombreUsuario().equalsIgnoreCase(nombre))
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
                    case "a" -> editarNombreUsuario(posicion);
                    case "b" -> editarContrasenaUsuario( posicion);
                    case "c" -> salir = true;
                    default -> throw new Error();
                }
            }
            catch (Error e){
                System.out.println("Opción no valida");
            }
        }while (!salir);
    }
    public static void editarNombreUsuario(int posicion){
        try {
            String nuevoNombre =  validarDato("Nombre", "Nuevo nombre: ", "^[A-Za-z0-9]+$");
            if (usuarios.stream().anyMatch(u -> u.getNombreUsuario().equalsIgnoreCase(nuevoNombre))){
                throw new Error();
            }
            usuarios.get(posicion).setNombreUsuario(nuevoNombre);
        }catch (Error e){
            System.out.println("Nombre de usuario no valido");
        }
    }
    public static void editarContrasenaUsuario(int posicion){
            String nuevaContrasena = validarDato("Nueva Contraseña ", "Nueva contraseña: ", "^[A-Za-z0-9]+$");
            usuarios.get(posicion).setContrasena(nuevaContrasena);
    }
    public static void eliminarUsuario(){
        System.out.println("Que usuario quieres eliminar: ");
        String nombreEliminar = sc.nextLine();

        Usuario usuarioEditar = usuarios.stream()
                .filter(u -> u.getNombreUsuario().equalsIgnoreCase(nombreEliminar))
                .findFirst().orElse(null);
        if (usuarioEditar == null){
            throw new Error();
        }

        usuarios.remove(usuarioEditar);
    }

    //Acciones con jugadores
    public static void crearJugador(){
        try {
            String nickJugador = validarDato("Nombre", "Nombre del Jugador: ", "^[A-Za-z0-9]+$");
            if (jugadores.stream().anyMatch(u -> u.getNickname().equalsIgnoreCase(nickJugador))){
                throw new Error();
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
        }
        catch (Error e){
            System.out.println("Datos no validos");
        }
    }
    public static void editarJugador(){
        boolean salir = false;
        do {
            try {
                System.out.println("Que jugador quieres editar: ");
                String nick = sc.nextLine();

                Jugador jugador = jugadores.stream()
                        .filter(u -> u.getNickname().equalsIgnoreCase(nick))
                        .findFirst().orElse(null);
                if (jugador == null){
                    throw new Error();
                }

                int posicion = jugadores.indexOf(jugador);

                System.out.println("""
                a) Editar nickname
                a) Editar nombre
                b) Editar apellido
                c) Editar nacionalidad
                d) Editar rol
                e) Editar sueldo
                f) Editar equipo
                g) Salir""");
                String respuesta = sc.nextLine();
                switch (respuesta){
                    case "a" -> editarNickJugador(posicion);
                    case "b" -> editarJugadorGeneral(posicion, "Nuevo nombre", "Nuevo nombre: ", "^[A-Za-z]+$");
                    case "c" -> editarJugadorGeneral(posicion, "Nuevo apellido", "Nuevo apellido: ", "^[A-Za-z]+$");
                    case "d" -> editarJugadorGeneral(posicion, "Nueva nacionalidad", "Nueva nacionalidad: ", "^[A-Za-z]+$");
                    case "e" -> editarJugadorGeneral(posicion, "Nuevo rol", "Nuevo rol: ", ROLES);
                    case "f" -> editarSueldoJugador(posicion);
                    case "g" -> editarEquipoJugador(posicion);
                    case "h" -> salir = true;
                    default -> throw new Error();
                }
                salir = true;
            }
            catch (Error e){
                System.out.println("Opción no valida");
            }
        }while (!salir);
    }
    public static void editarNickJugador(int posicion){
        String nickNuevo = validarDato("Nuevo nickname", "Nuevo nickname: ", "^[A-Za-z0-9]+$");
        if (jugadores.stream().anyMatch(u -> u.getNickname().equals(nickNuevo))){
            throw new Error();
        }
        jugadores.get(posicion).setNombre(nickNuevo);
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
        try {
            String sueldoString = validarDato("Nuevo sueldo", "Nuevo sueldo: ", "^[0-9]+$");
            int sueldo = Integer.parseInt(sueldoString);

            boolean sueldoMinimo = jugadores.get(posicion).setSueldo(sueldo, SMI);
            if (!sueldoMinimo){
                throw new Error();
            }
        }
        catch (Error e){
            System.out.println("Sueldo no valido");
        }
    }
    public static void editarEquipoJugador(int posicion){
        try {
            if (equipos.isEmpty()){
                throw new Error();
            }

            System.out.println("A que equipo quieres añadirlo: ");
            String nombre = sc.nextLine();
            Equipo equipo = equipos.stream()
                    .filter(u -> u.getNombre().equals(nombre))
                    .findFirst().orElse(null);
            if (equipo == null){
                throw new Error();
            }

            if (equipo.getJugadores().size() > 6){
                throw new Error();
            }

            Jugador jugador = jugadores.get(posicion);
            if (!(jugador.getEquipo() == null)){
                throw new Error();
            }

            jugadores.get(posicion).getEquipo().getJugadores().remove(jugadores.get(posicion));

            jugadores.get(posicion).setEquipo(equipo);
            equipo.getJugadores().add(jugador);

        }
        catch (Error e){
            System.out.println("Equipo no valido");
        }
    }
    public static void eliminarJugador(){
        System.out.print("Que jugador quieres eliminar: ");
        String nick = sc.nextLine();

        Jugador jugador = jugadores.stream()
                .filter(j -> j.getNickname().equalsIgnoreCase(nick))
                .findFirst().orElse(null);
        if (jugador == null){
            throw new Error();
        }
        // Eliminar jugador de la lista Jugadores
        jugadores.remove(jugador);
        //Eliminar el jugador de su equipo
        jugador.getEquipo().getJugadores().remove(jugador);
    }

    //Acciones con equipos
    public static void crearEquipo(){
        try {
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
            System.out.println("Datos no validos");
        }
    }
    public static void editarEquipo(){
        boolean salir = false;
        do {
            try {
                System.out.print("Que equipo quieres editar: ");
                String nombre = sc.nextLine();

                Equipo equipo = equipos.stream()
                        .filter(u -> u.getNombre().equalsIgnoreCase(nombre))
                        .findFirst().orElse(null);
                if (equipo == null){
                    throw new Error();
                }

                int posicion = equipos.indexOf(equipo);

                System.out.println("""
                a) Editar nombre
                b) Editar fecha de fundación
                c) Añadir jugador
                d) Eliminar jugador
                e) Salir""");
                String respuesta = sc.nextLine();
                switch (respuesta){
                    case "a" -> editarNombreEquipo(posicion);
                    case "b" -> editarFechaFundacion(posicion);
                    case "c" -> anadirJugador(posicion);
                    case "d" -> eliminarJugador(posicion);
                    case "e" -> salir = true;
                    default -> throw new Error();
                }
                salir = true;
            }
            catch (Error e){
                System.out.println("Opción no valida");
            }
        }while (!salir);
    }
    public static void editarNombreEquipo(int posicion){
        String nombreNuevo = validarDato("Nuevo nombre", "Nuevo nombre: ", "^[A-Za-z0-9]+$");
        if (equipos.stream().anyMatch(u -> u.getNombre().equals(nombreNuevo))){
            throw new Error();
        }
        equipos.get(posicion).setNombre(nombreNuevo);
    }
    public static void editarFechaFundacion(int posicion){
        LocalDate nuevaFecha = LocalDate.parse(validarDato("Nueva fecha de fundación",
                "Nueva fecha de fundación (dd/mm/yyyy): ", "^[0-9-]+$"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        equipos.get(posicion).setFechaFundacion(nuevaFecha);
    }
    public static void anadirJugador(int posicion){

        try {
            //Comprobar que el equipo no tiene 6 jugadores antes de hacer el insert
            if (equipos.get(posicion).getJugadores().size() > 6){
                throw new Error();
            }

            System.out.print("Que jugador quieres añadir al equipo: ");
            String nickname = sc.nextLine();

            Jugador jugador = jugadores.stream()
                    .filter(c -> c.getNickname().equalsIgnoreCase(nickname))
                    .findFirst().orElse(null);

            if (jugador == null){
                throw new Error();
            }

            if (!(jugador.getEquipo() == null)){
                throw new Error();
            }

            equipos.get(posicion).setJugador(jugador);
        }
        catch (Error e){
            System.out.println("Datos no validos");
        }
    }
    public static void eliminarJugador(int posicion){
        try {
            System.out.print("Que jugador quieres eliminar del equipo: ");
            String nickname = sc.nextLine();

            Jugador jugador = jugadores.stream()
                    .filter(c -> c.getNickname().equalsIgnoreCase(nickname))
                    .findFirst().orElse(null);

            if (jugador == null){
                throw new Error();
            }
            if (!equipos.get(posicion).getJugadores().contains(jugador)){
                throw new Error();
            }

            equipos.get(posicion).getJugadores().remove(jugador);
        }
        catch (Error e){
            System.out.println("Datos no validos");
        }
    }
    public static void eliminarEquipo(){
        System.out.print("Que equipo quieres eliminar: ");
        String nombre = sc.nextLine();

        Equipo equipo = equipos.stream()
                .filter(e -> e.getNombre().equalsIgnoreCase(nombre))
                .findFirst().orElse(null);
        if (equipo == null){
            throw new Error();
        }
        // Eliminar equipo de la lista equipos
        equipos.remove(equipo);
        // Eliminar la asociación de jugadores con este equipo
        equipo.getJugadores().forEach(e -> e.setEquipo(null));
    }

    //Crear competición
    public static void crearCompeticion(){
        competicion = new Competicion(1, Competicion.TipoEtapa.INSCRIPCION);

        System.out.println("Que tipo de puntuación se usa: ");
        String puntuacion = sc.nextLine();

        competicion.setTipoPuntuacion(puntuacion);

        System.out.println("--> La competición se ha creado correctamente");
    }

    //Cerrar etapa de inscripción
    public static void cerrarEtapaInscripcion(){

        // Comprobar que existe una competición
        if (competicion == null){
            throw new Error();
        }

        if (!competicion.getJornadas().isEmpty()){
            throw new Error("Tienes que generar una nueva competición antes de cerrar la etapa de Inscripción");
        }
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
            }
        }

        distribuirEquiposPorEnfrentamiento();
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
