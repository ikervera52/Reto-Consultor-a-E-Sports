import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static Scanner sc = new Scanner(System.in);
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
                nombre_equipo();
                break;
            case 2:
                nombre_jugador();
                break;
            case 3:
                System.out.println("Se ha finalizado el programa.");
                break;
            default:
                System.out.println("Opcion no valida, vuelve a intentarlo.");
                menu();
                break;
        }
    }
    public static void nombre_equipo(){
        System.out.print("""
                ---- Nombre Equipo ----
                Ingrese el nombre:\s""");
        String nombre = sc.next();
        Pattern p = Pattern.compile("^[a-zA-Z0-9 ]+$");
        Matcher m = p.matcher(nombre);
        if (!m.matches()){
            System.out.println("El nombre no es correcto. Solo puede incluir letras, numeros y espacios.\nVuelve a intentarlo.");
            nombre_equipo();
        };
        fecha_equipo();
    }
    public static void fecha_equipo(){
        try {
            System.out.print("""
                    ---- Fecha Equipo ----
                    Ingrese la fecha (dd/mm/yyyy):\s""");
            String fecha = sc.next();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fecha1 = LocalDate.parse(fecha, formatter);
        }catch (DateTimeParseException e){
            System.out.println("La fecha introducida no es correcta.\nVuelve a intentarlo.");
            fecha_equipo();
        }
        jugadores();
    }
    public static void jugadores(){
        try {
            System.out.print("""
                    ---- Jugadores ----
                    Ingrese el numero de jugadores:\s""");
            int jugadores = sc.nextInt();
            if (jugadores < 2 || jugadores > 6) {
                System.out.println("El numero de jugadores no es correcto. Solo se puede tener entre 2 y 6 jugadores.\nVuelve a intentarlo.");
                jugadores();
            }
            System.out.println("El equipo se ha añadidio correctamente.");
            add_jugador();
        }catch (InputMismatchException e){
            System.out.println("El numero introducido no es correcto.\nVuelve a intentarlo.");
            sc.nextLine();
            jugadores();
        }
    }
    public static void add_jugador(){
        System.out.print("""
                ---- Añadir Jugadores ----
                Quieres añadir algun jugador creado a este equipo:\s""");
        String respuesta = sc.next();
        if (respuesta.equalsIgnoreCase("si")) {
            add_jugador2();
        }
        else if (respuesta.equalsIgnoreCase("no")) {
            menu();
        }
        else {
            System.out.println("La respuesta no es correcta.\nVuelve a intentarlo.");
            add_jugador();
        }
    }
    public static void add_jugador2(){
        System.out.print("""
                Dime el nickname del jugador a añadir:\s""");
        String nickname = sc.next();
        Pattern p = Pattern.compile("^[a-zA-Z0-9 ]+$");
        Matcher m = p.matcher(nickname);
        if (!m.matches()){
            System.out.println("El nickname no es correcto. Solo puede incluir letras, numeros y espacios.\nVuelve a intentarlo.");
            add_jugador2();
        }
        System.out.print("""
                Quieres añadir algun jugador mas:\s""");
        String respuesta = sc.next();
        if (respuesta.equalsIgnoreCase("si")) {
            add_jugador2();
        }
        else if (respuesta.equalsIgnoreCase("no")) {
            menu();
        }
        else {
            System.out.println("La respuesta no es correcta.\nVuelve a intentarlo.");
            add_jugador();
        }
    }
    public static void nombre_jugador(){}

}