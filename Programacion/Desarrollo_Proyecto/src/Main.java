import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("-----MENU-----\n1.Ingresar jugadores\n2.Ingresar equipos\n3.Salir\n");
        int opcion = input.nextInt();
        switch (opcion) {
            case 1: IngresarJugadores();
                break;
            case 2: IngresarEquipo();
                break;
            case 3: System.out.println("Saliendo...");
                break;
            default: System.out.println("Opcion no valida");
        }

    }

    public static void IngresarJugadores(){
        Scanner input = new Scanner(System.in);
        try {
            System.out.print("Ingrese el nombre del jugador: ");
            Pattern pattern = Pattern.compile("^[A-Z]{3,50}");
            String nombre = input.nextLine();
            Matcher matcher = pattern.matcher(nombre);
        }
        catch (Exception e){
            System.out.println("Error al ingresar el nombre del jugador");
        }
        System.out.print("Ingrese la apellido del jugador: ");
        String apellido = input.nextLine();
        System.out.print("Ingrese la nacionalidad del jugador: ");
        String nacionalidad = input.nextLine();
        System.out.print("Ingrese la fecha de nacimeinto del jugador (dd/mm/aaaa): ");
        String fechaNacimiento = input.nextLine();
        System.out.println("Introduce el nickname del jugador: ");
        String nickname = input.nextLine();
        System.out.println("Introduce el rol del jugador:");
        String rol = input.nextLine();
        System.out.println("Introduce el sueldo del jugador:");
        double sueldo = input.nextDouble();

    }

    public static void IngresarEquipo(){

    }
}