import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static int contador = 0;
    public static void main(String[] args) {
        menu();
    }
    public static void menu (){
        System.out.print("""
                -------- Menu --------
                1. Añadir un equipo
                2. Añadir un jugador
                3. Salir
                ----------------------
                Selecciona una opcion:\s""");
        int opcion = sc.nextInt();
        switch (opcion) {
            case 1:
                equipo();
                break;
            case 2:
                jugador();
                break;
            case 3:
                finalizar();
                break;
            default:
                System.out.println("Opcion no valida, vuelve a intentarlo.");
                menu();
                break;
        }
    }
    public static void equipo(){
        do {
            contador++;
            nombreEquipo();
            fechaEquipo();
            jugadores();
            addJugador();
            if (contador % 2 != 0) {
                System.out.println("Tienes que añadir otro equipo para que sean equipos pares.");
            }
        }while (contador % 2 != 0);
    }
    public static void jugador(){
        nombreJugador();
        apellidoJugador();
        nacionalidadJugador();
        fechaJugador();
        nicknameJugador();
        rolJugador();
        sueldoJugador();
        addEquipo();
    }
    public static void nombreEquipo(){
        System.out.print("""
                ---- Nombre Equipo ----
                Ingrese el nombre:\s""");
        String nombre = sc.next();
        Pattern p = Pattern.compile("^[a-zA-Z0-9 ]+$");
        Matcher m = p.matcher(nombre);
        if (!m.matches()){
            System.out.println("El nombre no es correcto. Solo puede incluir letras, numeros y espacios.\nVuelve a intentarlo.");
            nombreEquipo();
        }
    }
    public static void fechaEquipo(){
        boolean error = true;
        do {
            try {
                System.out.print("""
                        ---- Fecha Equipo ----
                        Ingrese la fecha (dd/mm/yyyy):\s""");
                String fecha = sc.next();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate fecha1 = LocalDate.parse(fecha, formatter);
                LocalDate fecha2 = LocalDate.now();
                if (fecha2.isBefore(fecha1)) {
                    System.out.println("La fecha debe ser inferior a la de hoy.\nVuelve a intentarlo.");
                }
                else {
                    error = false;
                }
            } catch (DateTimeParseException e) {
                System.out.println("La fecha introducida no es correcta.\nVuelve a intentarlo.");
            }
        }while (error);
    }
    public static void jugadores(){
        boolean error = true;
        do {
            try {
                System.out.print("""
                        ---- Jugadores ----
                        Ingrese el numero de jugadores:\s""");
                int jugadores = sc.nextInt();
                if (jugadores < 2 || jugadores > 6) {
                    System.out.println("El numero de jugadores no es correcto. Solo se puede tener entre 2 y 6 jugadores.\nVuelve a intentarlo.");
                }
                else {
                    error = false;
                }
                System.out.println("El equipo se ha añadidio correctamente.");
            } catch (InputMismatchException e) {
                System.out.println("El numero introducido no es correcto.\nVuelve a intentarlo.");
                sc.nextLine();
            }
        }while (error);
    }
    public static void addJugador(){
        boolean error = true;
        do {
            System.out.print("""
                    ---- Añadir Jugadores ----
                    Quieres añadir algun jugador creado a este equipo:\s""");
            String respuesta = sc.next();
            if (respuesta.equalsIgnoreCase("si")) {
                error = false;
                addJugador2();
            } else if (respuesta.equalsIgnoreCase("no")) {
                error = false;
                if (contador % 2 == 0) {
                    menu();
                }
            } else {
                System.out.println("La respuesta no es correcta.\nVuelve a intentarlo.");
            }
        }while (error);
    }
    public static void addJugador2(){
        System.out.print("""
                Dime el nickname del jugador a añadir:\s""");
        String nickname = sc.next();
        Pattern p = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúÑñ0-9 ]+$");
        Matcher m = p.matcher(nickname);
        if (!m.matches()){
            System.out.println("El nickname no es correcto. Solo puede incluir letras, numeros y espacios.\nVuelve a intentarlo.");
            addJugador2();
        }
        System.out.print("""
                Quieres añadir algun jugador mas al equipo:\s""");
        String respuesta = sc.next();
        if (respuesta.equalsIgnoreCase("si")) {
            addJugador2();
        }
        else if (respuesta.equalsIgnoreCase("no")) {
            if (contador % 2 == 0){
                menu();
            }
        }
        else {
            System.out.println("La respuesta no es correcta.\nVuelve a intentarlo.");
            addJugador2();
        }
    }
    public static void nombreJugador(){
        System.out.print("""
                ---- Nombre Jugador ----
                Ingresa el nombre:\s""");
        String nombre = sc.next();
        Pattern p = Pattern.compile("^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+$");
        Matcher m = p.matcher(nombre);
        if (!m.matches()){
            System.out.println("El nombre no es correcto. La primera letra debe ser mayuscula y las siguientes minusculas.\nVuelve a intentarlo.");
            nombreJugador();
        }
    }
    public static void apellidoJugador(){
        System.out.print("""
                ---- Apellido Jugador ----
                Ingresa el apellido:\s""");
        String apellido = sc.next();
        Pattern p = Pattern.compile("^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+$");
        Matcher m = p.matcher(apellido);
        if (!m.matches()){
            System.out.println("El apellido no es correcto. La primera letra debe ser mayuscula y las siguientes minusculas.\nVuelve a intentarlo.");
            apellidoJugador();
        }
    }
    public static void nacionalidadJugador(){
        System.out.print("""
                ---- Nacionalidad Jugador ----
                Ingresa la nacionalidad:\s""");
        String nacionalidad = sc.next();
        Pattern p = Pattern.compile("^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+$");
        Matcher m = p.matcher(nacionalidad);
        if (!m.matches()){
            System.out.println("La nacionalidad no es correcta. La primera letra debe ser mayuscula y las siguientes minusculas.\nVuelve a intentarlo.");
            nacionalidadJugador();
        }
    }
    public static void fechaJugador(){
        boolean error = true;
        do {
            try {
                System.out.print("""
                        ---- Fecha Jugador ----
                        Ingresa la fecha de nacimiento (dd/mm/yyyy):\s""");
                String fecha = sc.next();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate fecha1 = LocalDate.parse(fecha, formatter);
                LocalDate fecha2 = LocalDate.now();
                if (fecha1.isAfter(fecha2)) {
                    System.out.println("La fecha debe ser inferior a la de hoy.\nVuelve a intentarlo.");
                }
                else {
                    error = false;
                }
            } catch (DateTimeParseException e) {
                System.out.println("La fecha introducida no es correcta.\nVuelve a intentarlo.");
            }
        }while(error);
    }
    public static void nicknameJugador(){
        boolean error = true;
        do {
            System.out.print("""
                    ---- Nickname Jugador ----
                    Ingresa el nickname:\s""");
            String nickname = sc.next();
            Pattern p = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúÑñ0-9 ]+$");
            Matcher m = p.matcher(nickname);
            if (!m.matches()) {
                System.out.println("El nickname no es correcto. Solo puede incluir letras, numeros y espacios.\nVuelve a intentarlo.");
            }
            else {
                error = false;
            }
        }while(error);
    }
    public static void rolJugador(){
        boolean error = true;
        do {
            System.out.print("""
                    ---- Rol Jugador ----
                    Roles disponibles: Entry Fragger/Support/AWPer/IGL/Lurker/Rifler
                    Ingresa el rol del jugador:\s""");
            String rol = sc.next();
            if (!rol.equalsIgnoreCase("entry fragger") && !rol.equalsIgnoreCase("awper") && !rol.equalsIgnoreCase("igl") && !rol.equalsIgnoreCase("lurker") && !rol.equalsIgnoreCase("rifler") && !rol.equalsIgnoreCase("support")) {
                System.out.println("El rol no es correcto.\nVuelve a intentarlo.");
            }
            else {
                error = false;
            }
        }while (error);
    }
    public static void sueldoJugador(){
        boolean error = true;
        do {
            try {
                System.out.print("""
                        ---- Sueldo Jugador ----
                        Ingresa el sueldo:\s""");
                double sueldo = sc.nextDouble();
                if (sueldo < 1294) {
                    System.out.println("EL sueldo tiene que ser mayor que el salario minino inter-profesional.\nVuelve a intentarlo.");
                }
                else {
                    error = false;
                    System.out.println("El jugador se ha añadidio correctamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("El numero introducido no es correcto.\nVuelve a intentarlo.");
                sc.nextLine();
            }
        }while (error);
    }
    public static void addEquipo(){
        System.out.print("""
                ---- Añadir a Equipo ----
                Quieres añadir el jugador a algun equipo creado:\s""");
        String respuesta = sc.next();
        if (respuesta.equalsIgnoreCase("si")) {
            addEquipo2();
        }
        else if (respuesta.equalsIgnoreCase("no")) {
            menu();
        }
        else {
            System.out.println("La respuesta no es correcta.\nVuelve a intentarlo.");
            addEquipo();
        }
    }
    public static void addEquipo2(){
        System.out.print("""
                Dime el nombre del equipo a añadir:\s""");
        String nombre = sc.next();
        Pattern p = Pattern.compile("^[a-zA-Z0-9 ]+$");
        Matcher m = p.matcher(nombre);
        if (!m.matches()){
            System.out.println("El nombre no es correcto. Solo puede incluir letras, numeros y espacios.\nVuelve a intentarlo.");
            addEquipo2();
        }
        System.out.print("""
                Quieres añadir algun jugador mas:\s""");
        String respuesta = sc.next();
        if (respuesta.equalsIgnoreCase("si")) {
            nombreJugador();
        }
        else if (respuesta.equalsIgnoreCase("no")) {
            menu();
        }
        else {
            System.out.println("La respuesta no es correcta.\nVuelve a intentarlo.");
            addEquipo2();
        }
    }
    public static void finalizar (){
        System.out.println("Se ha finalizado el programa.");
    }
}