import Exepciones.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    // Scanner
    final static Scanner sc = new Scanner(System.in);

    // Variables
    public static String nombreEquipo, nombreJugador, apellidoJugador,nacionalidadJugador, nicknameJugador = " ", rol;
    public static int cantidadEquipo, contadorEquipos;
    public static LocalDate fechaEquipo, fechaJugador;

    public static void main(String[] args) {
        try {
            opciones();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getClass());
        }
    }

    // Menú de opciones
    public static void opciones() throws Exception {
        try {
            System.out.print("""
                    -- Menu de opciones --
                    a) Guardar jugador\s
                    b) Guardar equipo\s
                    c) Salir\s
                    Que quieres hacer:\s""");
            String opcion = sc.nextLine().toLowerCase();
            switch (opcion){
                case "a":
                    pedirDatosJugador();
                    break;
                case "b":
                    pedirDatosEquipo();
                    break;
                case "c":
                    finPrograma();
                    break;
                default:
                    throw new OpcionNoValida();
            }
        }
        catch (OpcionNoValida e) {
            System.out.println("** Opción no valida **");
        }
    }

    // Jugador (nombre, apellido, nacionalidad, fecha_nacimiento, nickname, rol, sueldo)
    public static void pedirDatosJugador() throws Exception{
        System.out.println("-- Guardar jugador --");
        nombreJugador();
        apellidoJugador();
        nacionalidadJugador();
        fechaNacimiento();
        nickname();
        rolJugador();
        addEquipo();
        anadirMasJugadores();
    }
    // Estas funciones hacen cada una función específica para jugador
    public static void nombreJugador() {
        boolean error = true;

        do {
            try{
                Pattern patron = Pattern.compile("^[A-ZÁÉÍÓÚÜÑ][a-záéíóúüñ]+$");
                System.out.print("Nombre del jugador: ");
                nombreJugador = sc.nextLine();
                Matcher m = patron.matcher(nombreJugador);
                if(!m.matches()){
                    throw new NombreEquipoNoValido();
                }
                error = false;
            }
            catch (NombreEquipoNoValido e){
                System.out.println("**** El nombre tiene que tener la primera letra mayúscula ****");
            }
        } while (error);
    }
    public static void apellidoJugador(){
        boolean error = true;
        do {
            try{
                Pattern patron = Pattern.compile("^[A-ZÁÉÍÓÚÜÑ][a-záéíóúüñ]+$");
                System.out.print("Primer apellido del jugador: ");
                apellidoJugador = sc.nextLine();
                Matcher m = patron.matcher(apellidoJugador);
                if(!m.matches()){
                    throw new NombreEquipoNoValido();
                }
                error = false;
            }
            catch (NombreEquipoNoValido e){
                System.out.println("** El apellido tiene que tener la primera letra mayúscula **");

            }
        } while(error);
    }
    public static void nacionalidadJugador(){
        boolean error = true;
        do {
            try{
                Pattern patron = Pattern.compile("^[A-ZÁÉÍÓÚÜÑ][a-záéíóúüñ]+$");
                System.out.print("Nacionalidad del jugador: ");
                nacionalidadJugador = sc.nextLine();
                Matcher m = patron.matcher(nacionalidadJugador);
                if(!m.matches()){
                    throw new NombreEquipoNoValido();
                }
                error = false;
            }
            catch (NombreEquipoNoValido e){
                System.out.println("** La primera letra tiene que ser en mayúsculas **");

            }
        } while(error);
    }
    public static void fechaNacimiento(){
        boolean error = true;
        do {
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                System.out.print("Fecha de nacimiento (dd/MM/yyyy): ");
                fechaJugador = LocalDate.parse(sc.nextLine(), dtf);
                if(fechaJugador.isAfter(LocalDate.now())){
                    throw new FechaMayorHoy();
                }
                error = false;
            }
            catch (DateTimeParseException e){
                System.out.println("** Fecha de nacimiento no valida | Formato: (dd/MM/yyyy) **");
            }
            catch (FechaMayorHoy ex){
                System.out.println(" --> Añade otro equipo para sean equipos pares");
            }
        } while(error);
    }
    public static void nickname(){
        boolean error = true;
        do {
            try{
                Pattern patron = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[_!-#$]).+$");
                System.out.print("""
                        Nickname del jugador:\s
                        Tiene que contener:\s
                        - Mayúsculas\s
                        - Minúsculas\s
                        - Carácter especial\s
                        Nickname:\s""");
                nicknameJugador = sc.nextLine();
                Matcher m = patron.matcher(nicknameJugador);
                if(!m.matches()){
                    throw new NombreEquipoNoValido();
                }
                error = false;
            }
            catch (NombreEquipoNoValido e){
                System.out.println("** Nickname no valido **");
            }
        } while(error);
    }
    public static void rolJugador(){
        boolean error = true;
        do {
            try{
                System.out.print("""
                    Tipos de roles:\s
                    ----------------------\s
                    Entry Fragger\s
                    Support\s
                    AWPer\s
                    IGL\s
                    Lurker\s
                    Rifler\s
                    -----------------------\s
                    Rol del jugador:\s""");
                rol =  sc.nextLine().toLowerCase();
                if(!rol.equals("entry fragger") && !rol.equals("support") && !rol.equals("awper") && !rol.equals("igl") && !rol.equals("lurker")){
                    throw new RolNoValido();
                }
                error = false;
            }
            catch (RolNoValido ex){
                System.out.println("** Rol no valido **");
            }
        } while(error);
    }

    public static void anadirMasJugadores() throws Exception {
        boolean error = true;
        do {
            try {
                System.out.print("Quieres añadir más jugadores (si/no):");
                String respuesta = sc.nextLine().toLowerCase();
                if (respuesta.equals("si")){
                    pedirDatosJugador();
                    error = false;
                } else if(respuesta.equals("no")){
                    opciones();
                    error = false;
                } else  {
                    throw new OpcionNoValida();
                }
            }
            catch (OpcionNoValida ex){
                System.out.println("** Opción no valida **");
            }
        }while(error);
    }
    // Estas funciones preguntan si quieres añadir a un equipo el jugador que crean
    public static void addEquipo() {
        boolean error = true;
        do {
            System.out.print("Quieres añadir este jugador a un equipo (si/no): ");
            String respuesta = sc.nextLine();
            if (respuesta.equalsIgnoreCase("si")) {
                addJEquipo2();
                error = false;
            } else if (respuesta.equalsIgnoreCase("no")) {
                error = false;
            } else {
                System.out.println("** La respuesta no es valida **");
            }
        }while (error);
    }
    public static void addJEquipo2(){
        boolean error = true;
        do {
            try {
                System.out.print("Ha que equipo lo quieres añadir: ");
                String comprobarNickname = sc.nextLine();
                Pattern p = Pattern.compile("^[A-za-z0-9]+$");
                Matcher m = p.matcher(comprobarNickname);
                if (!m.matches()){
                    throw new NicknameNoExiste();
                }
                error = false;
            }
            catch (NicknameNoExiste ex){
                sc.nextLine();
                System.out.println("** Este equipo no existe **");
            }
        } while(error);
    }


    // Equipos (nombre, fecha de fundación, jugadores)
    public static void pedirDatosEquipo() throws Exception{

        contadorEquipos = 0;
        do {
            System.out.println("-- Guardar equipo --");
            contadorEquipos++;
            nombreEquipo();
            fechaFundacionEquipo();
            jugadoresEquipo();
            addJugador();
            if (contadorEquipos %2 != 0) {
                System.out.println("--> Los equipos añadidos tienen que ser pares ");
            }
        } while (contadorEquipos %2 != 0);
        anadirMasEquipos();

    }
    // Cada función hace su parte definida
    public static void nombreEquipo(){
        boolean error = true;
        do {
            try {
                Pattern patron = Pattern.compile("^[A-za-z0-9]+$");
                System.out.print("Nombre del equipo: ");
                nombreEquipo = sc.nextLine();
                Matcher m = patron.matcher(nombreEquipo);
                if(!m.matches()){
                    throw new NombreEquipoNoValido();
                }
                error = false;
            }
            catch (NombreEquipoNoValido ex){
                System.out.println("** Nombre del equipo no valido **");
            }
        } while(error);

    }
    public static void fechaFundacionEquipo (){
        boolean error = true;
        do {
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                System.out.print("Fecha de fundación del equipo: ");
                fechaEquipo = LocalDate.parse(sc.nextLine(), dtf);
                if(fechaEquipo.isAfter(LocalDate.now())){
                    throw new FechaMayorHoy();
                }
                error = false;
            }
            catch (DateTimeParseException ex){
                System.out.println("** Formato de fecha no valido **");

            }
            catch (FechaMayorHoy ex){
                System.out.println("** Fecha de fundación mayor a la fecha actual **");
            }
        } while(error);
    }
    public static void jugadoresEquipo (){
        boolean error = true;
        do {
            try {
                Pattern patron = Pattern.compile("^[2-6]$");
                System.out.print("Numero de jugadores del equipo: ");
                String cantidadString = sc.nextLine();
                Matcher m = patron.matcher(cantidadString);
                if(!m.matches()){
                    throw new CantidadJugadoresNoValida();
                }
                cantidadEquipo = Integer.parseInt(cantidadString);
                error = false;
            }
            catch (CantidadJugadoresNoValida ex){
                System.out.println("** La cantidad de jugadores tiene que ser de 2 a 6 **");
            }

        }while (error);
    }
    public static void anadirMasEquipos() throws Exception{
        boolean error = true;
        do {
            try {
                System.out.print("Quieres añadir más equipos (si/no):");
                String respuesta = sc.nextLine().toLowerCase();
                if (respuesta.equals("si")){
                    pedirDatosEquipo();
                    error = false;
                } else if(respuesta.equals("no")){
                    opciones();
                    error = false;
                } else  {
                    throw new OpcionNoValida();
                }
            }
            catch (OpcionNoValida ex){
                System.out.println("** Opción no valida **");
            }
        }while(error);
    }
    // Estas funciones preguntan si quieres añadir jugadores a los equipos que están creando
    public static void addJugador() {
        boolean error = true;
        do {
            System.out.print("Quieres añadir algún jugador creado a este equipo (si/no): ");
            String respuesta = sc.nextLine();
            if (respuesta.equalsIgnoreCase("si")) {
                addJugador2();
                error = false;
            } else if (respuesta.equalsIgnoreCase("no")) {
                error = false;
            } else {
                System.out.println("** La respuesta no es valida **");
            }
        }while (error);
    }
    public static void addJugador2(){
        boolean error = true;
        do {
            try {
                System.out.print("Dime el nickname del jugador a añadir: ");
                String comprobarNickname = sc.nextLine();
                Pattern p = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[_!-#$]).+$");
                Matcher m = p.matcher(comprobarNickname);
                if (!m.matches()){
                    throw new NicknameNoExiste();
                }
                preguntaAddMasJugador();
                error = false;
            }
            catch (NicknameNoExiste ex){
                System.out.println("** Este nickname no existe **");
            }
        } while(error);
    }
    public static void preguntaAddMasJugador(){
        boolean error = true;
        do {

            System.out.print("Quieres añadir algún jugador mas al equipo (si/no): ");
            String respuesta = sc.next();
            sc.nextLine();
            if (respuesta.equalsIgnoreCase("si")) {
                error = false;
                addJugador2();
            }
            else if (respuesta.equalsIgnoreCase("no")) {
                error = false;
            } else {
                System.out.println("** La respuesta no valida **");
            }
        } while(error);
    }

    // Fin del programa
    public static void finPrograma(){
        System.out.println("-- Programa finalizado --");
    }
}
