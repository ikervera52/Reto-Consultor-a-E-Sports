import Clases.Equipos;
import Clases.Jugadores;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

    private static Scanner input = new Scanner(System.in);
    private static ArrayList<Jugadores> jugadores = new ArrayList<>();
    private static ArrayList<Equipos> equipos = new ArrayList<>();



    public static void main(String[] args) {
        Menu();
    }


    public static void Menu() {
        System.out.println("---Menu---" + "\n1. Ingresar jugadores" + "\n2. Ingresar equipos" + "\n3. Salir");
        int opcion = input.nextInt();
        switch (opcion) {
            case 1:
                IngresarJugadores();
                break;
            case 2:
                IngresarEquipo();
                break;
            case 3:
                Finalizar();
                break;
            default:
                System.out.println("Opcion no valida");
        }
    }


    public static void IngresarJugadores() {

        String nombre = null;
        String apellido = null;
        String nacionalidad = null;
        String fechaNacimiento = null;
        String nickname = null;
        int rol = 0;
        double sueldo = 0;



            //Introducir el nombre
        try {
            System.out.print("Ingrese el nombre del jugador: ");
            Pattern pattern = Pattern.compile("^[A-ZÑ][a-zA-ZñÑ]{3,50}");
            nombre = input.nextLine();
            Matcher matcher = pattern.matcher(nombre);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("El nombre debe contener entre 3 y 50 caracteres y la primera letra en mayusculas.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + " Error al ingresar el nombre del jugador(Ejemplo: Juan)");
        }
            //Introducir el apellido
        try {
            System.out.print("Ingrese la apellido del jugador: ");
            apellido = input.nextLine();
            Pattern pattern = Pattern.compile("^[A-ZÑ][a-zA-ZñÑ]{3,50}");
            Matcher matcher = pattern.matcher(apellido);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("El apellido debe contener entre 3 y 50 caracteres y la primera letra en mayusculas.");
            }
        } catch (Exception e) {
            System.out.println( e.getMessage() + " Error al ingresar el apellido del jugador (Ejemplo: Perez)");
        }
            //Introducir la nacionalidad

        try {
            System.out.print("Ingrese la nacionalidad del jugador: ");
            nacionalidad = input.nextLine();
            Pattern pattern = Pattern.compile("^[A-Z][a-zA-ZñÑ]{3,50}");
            Matcher matcher = pattern.matcher(nacionalidad);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("La nacionalidad debe contener entre 3 y 50 caracteres y la primera letra en mayusculas.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + " Error al ingresar la nacionalidad del jugador (Ejemplo: Española)");
        }

            //Introducir la fecha de nacimiento
        try {
            System.out.print("Ingrese la fecha de nacimeinto del jugador (dd/mm/aaaa): ");
            fechaNacimiento = input.nextLine();
            Pattern pattern = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/([0-9]{4})$");
            Matcher matcher = pattern.matcher(fechaNacimiento);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("La fecha de nacimiento debe tener el formato dd/mm/aaaa");
            }
            else if (matcher.matches()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                if (java.time.LocalDate.parse(fechaNacimiento, formatter).isAfter(java.time.LocalDate.now())) {
                    throw new IllegalArgumentException("La fecha de nacimiento no puede ser una fecha futura.");
                }
            }
        } catch(Exception e){
            System.out.println( e.getMessage() + " Error al ingresar la fecha de nacimiento del jugador (Ejemplo: 25/12/1990)");

        }

            //Introducir el nickname
        try {
            System.out.println("Introduce el nickname del jugador: ");
            nickname = input.nextLine();
            Pattern pattern = Pattern.compile("^[a-zA-ZñÑ0-9_]{3,15}$");
            Matcher matcher = pattern.matcher(nickname);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("El nickname debe contener entre 3 y 15 caracteres y solo puede incluir letras, numeros y guiones bajos.");
            }
            else if (matcher.matches()) {
                Pattern pattern1 = Pattern.compile("");
                matcher = pattern1.matcher(nickname);
                if (matcher.matches()) {
                    throw new IllegalArgumentException("El nickname no puede contener ese tipo de palabras.");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage()  + " Error al ingresar el nickname del jugador (Ejemplo: Pro_Player123)");
        }

            //Introducir el rol
        try {
            System.out.println("Que rol va a ser este jugador: ");
            System.out.println("1.Suppport" + "\n2.Entry Fragger" + "\n3.AWPer" + "\n4.IGL" + "\n5.Lurker" + "\n6.Rifler");
            rol = input.nextInt();
            if (rol < 1 || rol > 6) {
                throw new IllegalArgumentException("El rol debe ser uno de los siguientes: Support, Entry Fragger, AWPer, IGL, Lurker, Rifler");
            }

        } catch(Exception e){
            System.out.println(e.getMessage() + " Error al ingresar el rol del jugador");
        }

            //Introducir el sueldo
        try {
            System.out.println("Introduce el sueldo anual del jugador(€):");
            sueldo = input.nextDouble();
            if (sueldo < 16.576) {
                throw new IllegalArgumentException("El sueldo no puede ser menor a 16.576 €.");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage() + " Error al ingresar el sueldo del jugador (Ejemplo: 50000)");
        }

        //Introducir el Jugador al ArrayList
        Jugadores jugador = new Jugadores(nombre, apellido, nacionalidad, fechaNacimiento, nickname, rol, sueldo);
        jugadores.add(jugador);

        //Volver al menu
        Menu();
    }

    public static void IngresarEquipo() {

        String nombreEquipo = null;
        String fechaFundacion = null;

        //Introducir el nombre del equipo
        try {
            System.out.print("Ingrese el nombre del equipo: ");
            nombreEquipo = input.nextLine();
            Pattern pattern = Pattern.compile("^[A-Z][a-zA-ZñÑ_ ]{3,50}");
            Matcher matcher = pattern.matcher(nombreEquipo);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("El nombre del equipo debe contener entre 3 y 50 caracteres y la primera letra en mayusculas.");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage() + " Error al ingresar el nombre del equipo (Ejemplo: Escuadron Salchichon)");
        }
        //Introducir la fecha de fundacion del equipo
        try {
            System.out.print("Ingrese la fecha de fundacion del equipo: ");
            fechaFundacion = input.nextLine();
            Pattern pattern = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/([0-9]{4})$");
            Matcher matcher = pattern.matcher(fechaFundacion);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("La fecha de fundacion debe tener el formato dd/mm/aaaa");
            }
           else if (matcher.matches()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                if (java.time.LocalDate.parse(fechaFundacion, formatter).isAfter(java.time.LocalDate.now())) {
                     throw new IllegalArgumentException("La fecha de fundacion no puede ser una fecha futura.");
                }
              }
        }
        catch (Exception e) {
            System.out.println(e.getMessage() + " Error al ingresar la fecha de fundacion del equipo (Ejemplo: 15/08/2010)");
        }

        //Añadir jugadores al equipo

        System.out.print("Cuntos jugadores quieres añadir? ");
            int cantidadJugadores = input.nextInt();
            input.nextLine(); // Limpiar el buffer de entrada
            if (cantidadJugadores < 6 || cantidadJugadores > 2) {
                System.out.println("La cantidad de jugadores debe ser entre 2 y 6");
                return;
            }

        ArrayList<Jugadores> jugadoresEquipo = new ArrayList<>();
        for (int i = 0; i < cantidadJugadores; i++) {
            System.out.println("Ingrese el nombre del jugador " + (i + 1) + ": ");
            String nombreJugador = input.nextLine();
            boolean jugadorEncontrado = false;
            for (Jugadores jugador : jugadores) {
                if (jugador.getNombre().equalsIgnoreCase(nombreJugador)) {
                    jugadoresEquipo.add(jugador);
                    jugadorEncontrado = true;
                    break;
                }
            }
            if (!jugadorEncontrado) {
                System.out.println("El jugador " + nombreJugador + " no se encuentra registrado. Por favor, ingrese un jugador válido.");
                i--; // Decrementar el contador para volver a solicitar el nombre del jugador
            }

            //Crear el equipo y añadirlo al ArrayList

        Equipos equipo = new Equipos(nombreEquipo, fechaFundacion, jugadoresEquipo);
        }


    }

    public static void Finalizar() {
        System.out.println("Programa finalizado.");
        System.exit(0);
    }











    }