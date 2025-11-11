import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    final static Scanner sc = new Scanner(System.in);
    public static String nombreEquipo;
    public static void main(String[] args) {
        opciones();
    }
    public static void opciones (){
        System.out.print("-- Menu de opciones --" +
                "a) Guardar jugador" +
                "b) Guardar equipo" +
                "c) Salir" +
                "Que quieres hacer: ");
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
                System.out.println("Opción no valida");
        }
    }
    // Jugador (nombre, apellido, nacionalidad, fecha nacimiento, nickname, rol, sueldo)
    public static void pedirDatosJugador(){
        System.out.print("-- Guardar jugador --");
    }
    public static void nombreJugador(){
    }
    // equipos (nombre, fecha de fundación, jugadores)
    public static void pedirDatosEquipo (){
        System.out.print("-- Guardar equipo --");
        nombreEquipo();
        fechaFundacionEquipo();
        jugadoresEquipo();
    }
    public static void nombreEquipo(){
        Pattern patron = Pattern.compile("^[A-za-z0-9]+");
        System.out.print("Nombre del equipo: ");
        nombreEquipo = sc.nextLine();
        Matcher m = patron.matcher(nombreEquipo);
        if(!m.matches()){
            System.out.println("Nombre del equipo no valido");
        }
    }
    public static void fechaFundacionEquipo (){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.print("Fecha de fundación del equipo: ");
        LocalDate fecha = LocalDate.parse(sc.nextLine(), dtf);
    }
    //Pendiente (Comprobar si los jugadores existen)
    public static void jugadoresEquipo (){
        //Pendiente
    }
}
