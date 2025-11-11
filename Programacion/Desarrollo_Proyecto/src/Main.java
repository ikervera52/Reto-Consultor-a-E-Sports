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

    public static void IngresarJugadores() {

        Scanner input = new Scanner(System.in);
        try {
            System.out.print("Ingrese el nombre del jugador: ");
            Pattern pattern = Pattern.compile("^[A-ZÑ][a-zA-ZñÑ]{3,50}");
            String nombre = input.nextLine();
            Matcher matcher = pattern.matcher(nombre);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("El nombre debe contener entre 3 y 50 caracteres y la primera letra en mayusculas.");
            }
        } catch (Exception e) {
            System.out.println("Error al ingresar el nombre del jugador(Ejemplo: Juan)");
        }

        try {
            System.out.print("Ingrese la apellido del jugador: ");
            String apellido = input.nextLine();
            Pattern pattern = Pattern.compile("^[A-ZÑ][a-zA-ZñÑ]{3,50}");
            Matcher matcher = pattern.matcher(apellido);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("El apellido debe contener entre 3 y 50 caracteres y la primera letra en mayusculas.");
            }
        } catch (Exception e) {
            System.out.println("Error al ingresar el apellido del jugador (Ejemplo: Perez)");
        }
        try {
            System.out.print("Ingrese la nacionalidad del jugador: ");
            String nacionalidad = input.nextLine();
            Pattern pattern = Pattern.compile("^[A-Z][a-zA-ZñÑ]{3,50}");
            Matcher matcher = pattern.matcher(nacionalidad);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("La nacionalidad debe contener entre 3 y 50 caracteres y la primera letra en mayusculas.");
            }
        } catch (Exception e) {
            System.out.println("Error al ingresar la nacionalidad del jugador (Ejemplo: Española)");
        }

        try {
            System.out.print("Ingrese la fecha de nacimeinto del jugador (dd/mm/aaaa): ");
            String fechaNacimiento = input.nextLine();
            Pattern pattern = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/([0-9]{4})$");
            Matcher matcher = pattern.matcher(fechaNacimiento);

            if (!matcher.matches()) {
                throw new IllegalArgumentException("La fecha de nacimiento debe tener el formato dd/mm/aaaa");
            }
            } catch(Exception e){
                System.out.println("Error al ingresar la fecha de nacimiento del jugador (Ejemplo: 25/12/1990)");
            }

            try {
                System.out.println("Introduce el nickname del jugador: ");
                String nickname = input.nextLine();
                Pattern pattern = Pattern.compile("^[a-zA-ZñÑ0-9_]{3,15}$");
                Matcher matcher = pattern.matcher(nickname);
                if (!matcher.matches()) {
                    throw new IllegalArgumentException("El nickname debe contener entre 3 y 15 caracteres y solo puede incluir letras, numeros y guiones bajos.");
                }
            } catch (Exception e) {
                System.out.println("Error al ingresar el nickname del jugador (Ejemplo: Pro_Player123)");
            }

            try {
                System.out.println("Introduce el rol del jugador (Entry Fragger|Support|AWPer|IGL|Lurker|Rifler):");
                String rol = input.nextLine();
                Pattern pattern = Pattern.compile("^(Entry Fragger|Support|AWPer|IGL|Lurker|Rifler)$");
                Matcher matcher = pattern.matcher(rol);
                if (!matcher.matches()) {
                    throw new IllegalArgumentException("El rol debe ser uno de los siguientes: Entry Fragger|Support|AWPer|IGL|Lurker|Rifler");
                }
                    }
                    catch(Exception e){
                    System.out.println("Error al ingresar el rol del jugador (Ejemplo: AWPer)");
                }

                try {
                    System.out.println("Introduce el sueldo anual del jugador(€):");
                    double sueldo = input.nextDouble();
                    if (sueldo < 16.576) {
                        throw new IllegalArgumentException("El sueldo no puede ser menor a 16.576 €.");
                    }
                }
                catch (Exception e) {
                    System.out.println("Error al ingresar el sueldo del jugador (Ejemplo: 50000)");
                }

            }

    public static void IngresarEquipo() {
        Scanner scaner = new Scanner(System.in);
        try {
            System.out.print("Ingrese el nombre del equipo: ");
            String nombreEquipo = scaner.nextLine();
            Pattern pattern = Pattern.compile("^[A-Z][a-zA-ZñÑ_ ]{3,50}");
            Matcher matcher = pattern.matcher(nombreEquipo);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("El nombre del equipo debe contener entre 3 y 50 caracteres y la primera letra en mayusculas.");
            }
        }
        catch (Exception e) {
            System.out.println("Error al ingresar el nombre del equipo (Ejemplo: Escuadron Salchichon)");
        }

        try {
            System.out.print("Ingrese la fecha de fundacion del equipo: ");
            String fechaFundacion = scaner.nextLine();
            Pattern pattern = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/([0-9]{4})$");
            Matcher matcher = pattern.matcher(fechaFundacion);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("La fecha de fundacion debe tener el formato dd/mm/aaaa");
            }
        }
        catch (Exception e) {
            System.out.println("Error al ingresar la fecha de fundacion del equipo (Ejemplo: 15/08/2010)");
        }

        try {
            System.out.print("Ingrese la cantidad de jugadores del equipo: ");
            int cantidadJugadores = scaner.nextInt();
            if (cantidadJugadores < 2 || cantidadJugadores > 6) {
                throw new IllegalArgumentException("La cantidad de jugadores debe estar entre 2 y 6.");
            }
        }
        catch (Exception e) {
            System.out.println("Error al ingresar la cantidad de jugadores del equipo (Ejemplo: 5)");
        }
    }


    }