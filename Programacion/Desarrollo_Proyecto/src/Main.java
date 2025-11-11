import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String nombreEquipo1 = "";
        String nombreEquipo2 = "";

        String[] nombres = new String[6];
        String[] apellidos = new String[6];
        String[] nacionalidades = new String[6];
        String[] nicknames = new String[6];
        String[] roles = new String[6];
        String[] fechasNacimiento = new String[6];
        double[] sueldos = new double[6];
        int cantidadJugadores = 0; 

        int opcion;

        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Registrar equipos");
            System.out.println("2. Registrar 6 jugadores");
            System.out.println("3. Salir");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            if (opcion == 1) {
                if (!nombreEquipo1.isEmpty() && !nombreEquipo2.isEmpty()) {
                    System.out.println("Ya hay 2 equipos registrados.");
                } else {
                    if (nombreEquipo1.isEmpty()) {
                        System.out.println("=== Registro del equipo 1 ===");
                        System.out.print("Nombre del equipo: "); nombreEquipo1 = sc.nextLine();
                        System.out.print("Fecha de fundación: ");
                        sc.nextLine();
                        System.out.print("Numero de jugadores: ");
                        sc.nextLine();
                        System.out.println("Equipo 1 registrado.");
                    } else {
                        System.out.println("=== Registro del equipo 2 ===");
                        System.out.print("Nombre del equipo: "); nombreEquipo2 = sc.nextLine();
                        System.out.print("Fecha de fundación: ");
                        sc.nextLine();
                        System.out.print("Numero de jugadores: ");
                        sc.nextLine();
                        System.out.println("Equipo 2 registrado.");
                    }
                }

            } else if (opcion == 2) {
                if (cantidadJugadores >= 6) {
                    System.out.println("Ya hay 6 jugadores registrados. No se pueden agregar más.");
                } else {
                    System.out.println("=== Registro de jugadores ===");
                    while (cantidadJugadores < 6) {
                        int j = cantidadJugadores;
                        System.out.println("Jugador " + (j + 1) + ":");
                        System.out.print("Nombre: "); nombres[j] = sc.nextLine();
                        System.out.print("Apellido: "); apellidos[j] = sc.nextLine();
                        System.out.print("Nacionalidad: "); nacionalidades[j] = sc.nextLine();
                        System.out.print("Nickname: "); nicknames[j] = sc.nextLine();
                        System.out.print("Rol: "); roles[j] = sc.nextLine();
                        System.out.print("Fecha de nacimiento: "); fechasNacimiento[j] = sc.nextLine();
                        System.out.print("Sueldo: "); sueldos[j] = sc.nextDouble(); sc.nextLine();
                        cantidadJugadores++;
                    }
                    System.out.println("Se registraron los 6 jugadores.");
                }

            } else if (opcion != 3) {
                System.out.println("Opción inválida.");
            }

        } while (opcion != 3);

        System.out.println("Programa finalizado.");
    }
}