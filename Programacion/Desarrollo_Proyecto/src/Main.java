import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    final static Scanner sc = new Scanner(System.in);
    public static String nombreEquipo;
    public static int cantidadEquipo;
    public static LocalDate fechaEquipo;

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

    // equipos (nombre, fecha de fundación, jugadores)
    public static void pedirDatosEquipo (){
        System.out.print("-- Guardar equipos --");
        nombreEquipo();
        fechaFundacionEquipo();
        jugadoresEquipo();
    }

    public static void nombreEquipo(){
        try {
            Pattern patron = Pattern.compile("^[A-za-z0-9]+$");
            System.out.print("Nombre del equipo: ");
            nombreEquipo = sc.nextLine();
            Matcher m = patron.matcher(nombreEquipo);
            if(!m.matches()){
                throw new NombreEquipoNoValido();
            }
        }
        catch (NombreEquipoNoValido ex){
            System.out.println("** Nombre del equipo no valido **");
            nombreEquipo();
        }

    }
    public static void fechaFundacionEquipo (){
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            System.out.print("Fecha de fundación del equipo: ");
            fechaEquipo = LocalDate.parse(sc.nextLine(), dtf);
        }
        catch (DateTimeParseException ex){
            System.out.println("** Formato de fecha no valido **");
            fechaFundacionEquipo();
        }
    }
    public static void jugadoresEquipo (){
        try {
            Pattern patron = Pattern.compile("^[2-6]$");
            System.out.print("Numero de jugadores del equipo: ");
            String cantidadString = sc.nextLine();
            Matcher m = patron.matcher(cantidadString);
            if(!m.matches()){
                throw new CantidadJugadoresNoValida();
            }
            cantidadEquipo = Integer.parseInt(cantidadString);
            anadirJugadorExistente();
        }
        catch (CantidadJugadoresNoValida ex){
            System.out.println("** La cantidad de jugadores tiene que ser de 2 a 6 **");
        }
    }
    public static void anadirJugadorExistente(){
        try {
            System.out.print("Quieres añadir un jugador ya existente (si/no):");
            String respuesta = sc.nextLine().toLowerCase();
            if (respuesta.equals("si")){
                anadirJugadorExistenteDatos();
            } else if(respuesta.equals("no")){
                anadirMasEquipos();
            } else  {
                throw new OpcionNoValida();
            }
        }
        catch (OpcionNoValida ex){
            System.out.println("** Opción no valida **");
        }
    }
    public static void anadirJugadorExistenteDatos(){
        Pattern patron = Pattern.compile("^[A-za-z0-9]+$");
        System.out.print("Nombre del jugador: ");
        String jugadorExistente = sc.nextLine();
        Matcher m = patron.matcher(jugadorExistente);
        // Se tendrá que comparar con los jugadores que ya estén creados
        anadirJugadorExistente();
    }

    public static void anadirMasEquipos(){
        try {
            System.out.print("Quieres añadir más equipos (si/no):");
            String respuesta = sc.nextLine().toLowerCase();
            if (respuesta.equals("si")){
                pedirDatosEquipo();
            } else if(respuesta.equals("no")){
                opciones();
            } else  {
                throw new OpcionNoValida();
            }
        }
        catch (OpcionNoValida ex){
            System.out.println("** Opción no valida **");
        }
    }


}
