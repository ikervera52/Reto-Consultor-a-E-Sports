import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    final static Scanner sc = new Scanner(System.in);

    public static String nombreEquipo, nombreJugador, apellidoJugador,nacionalidadJugador, nicknameJugador = " ", rol;
    public static int cantidadEquipo;
    public static LocalDate fechaEquipo, fechaJugador;
    public static double sueldo, SMI = 16576;

    public static void main(String[] args) {
        opciones();
    }

    // Menú de opciones
    public static void opciones (){
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

    //Jugador (nombre, apellido, nacionalidad, fecha_nacimiento, nickname, rol, sueldo)
    public static void pedirDatosJugador(){
        System.out.println("-- Guardar jugadores --");
        nombreJugador();
        apellidoJugador();
        nacionalidadJugador();
        fechaNacimiento();
        nickname();
        rolJugador();
        sueldo();
        volverOpciones();
    }

    public static void nombreJugador(){
        try{
            Pattern patron = Pattern.compile("^[A-Z][a-z]+");
            System.out.print("Nombre del jugador: ");
            nombreJugador = sc.nextLine();
            Matcher m = patron.matcher(nombreJugador);
            if(!m.matches()){
                throw new NombreEquipoNoValido();
            }
        }
        catch (NombreEquipoNoValido e){
            System.out.println("** Nombre del jugador no valido **");
            nombreJugador();
        }
    }
    public static void apellidoJugador(){
        try{
            Pattern patron = Pattern.compile("^[A-Z][a-z]+");
            System.out.print("Primer apellido del jugador: ");
            apellidoJugador = sc.nextLine();
            Matcher m = patron.matcher(apellidoJugador);
            if(!m.matches()){
                throw new NombreEquipoNoValido();
            }
        }
        catch (NombreEquipoNoValido e){
            System.out.println("** Apellido del jugador no valido **");
            apellidoJugador();
        }
    }
    public static void nacionalidadJugador(){
        try{
            Pattern patron = Pattern.compile("^[A-Z][a-z]+");
            System.out.print("Nacionalidad del jugador: ");
            nacionalidadJugador = sc.nextLine();
            Matcher m = patron.matcher(nacionalidadJugador);
            if(!m.matches()){
                throw new NombreEquipoNoValido();
            }
        }
        catch (NombreEquipoNoValido e){
            System.out.println("** Nacionalidad del jugador no valida **");
            nacionalidadJugador();
        }
    }
    public static void fechaNacimiento(){
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            System.out.print("Fecha de nacimiento: ");
            fechaJugador = LocalDate.parse(sc.nextLine(), dtf);
            if(fechaJugador.isAfter(LocalDate.now())){
                throw new FechaMayorHoy();
            }
        }
        catch (DateTimeParseException e){
            System.out.println("** Fecha de nacimiento no valida **");
            fechaNacimiento();
        }
        catch (FechaMayorHoy ex){
            System.out.println("** Fecha de nacimiento mayor a la fecha actual **");
        }
    }
    public static void nickname(){
        try{
            Pattern patron = Pattern.compile("^[A-Za-z0-9_]+");
            System.out.print("Nickname del jugador: ");
            nicknameJugador = sc.nextLine();
            Matcher m = patron.matcher(nicknameJugador);
            if(!m.matches()){
                throw new NombreEquipoNoValido();
            }
        }
        catch (NombreEquipoNoValido e){
            System.out.println("** Nickname no valido **");
            nickname();
        }
    }
    public static void rolJugador(){
        try{
            System.out.print("""
                    Tipos de roles:\s
                    Entry Fragger\s
                    Support\s
                    AWPer\s
                    IGL\s
                    Lurker\s
                    Rifler\s
                    Rol del jugador:\s""");
            rol =  sc.nextLine();
            if(!rol.equals("Entry Fragger") && !rol.equals("Support") && !rol.equals("AWPer") && !rol.equals("IGL") && !rol.equals("Lurker")){
                throw new RolNoValido();
            }
        }
        catch (RolNoValido ex){
            System.out.println("** Rol no valido **");
            rolJugador();
        }
    }
    public static void sueldo(){
        try{
            System.out.print("Sueldo del jugador: ");
            sueldo = sc.nextDouble();
            if(sueldo < SMI){
                throw new SueldoNoValido();
            }
        }
        catch (SueldoNoValido ex){
            System.out.println("** Sueldo no valido **");
            sueldo();
        }
    }

    // equipos (nombre, fecha de fundación, jugadores)
    public static void pedirDatosEquipo (){
        System.out.println("-- Guardar equipos --");
        nombreEquipo();
        fechaFundacionEquipo();
        jugadoresEquipo();
        volverOpciones();
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
            if(fechaEquipo.isAfter(LocalDate.now())){
                throw new FechaMayorHoy();
            }
        }
        catch (DateTimeParseException ex){
            System.out.println("** Formato de fecha no valido **");
            fechaFundacionEquipo();
        }
        catch (FechaMayorHoy ex){
            System.out.print("** Fecha de fundación mayor a la fecha actual **");
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
            jugadoresEquipo();
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
    // Se tendrán que comprar con los nicknames de los jugadores que ya existan
    public static void anadirJugadorExistenteDatos(){
        try {
            System.out.print("Nickname del jugador: ");
            String nicknameExistente = sc.nextLine();
            if (!nicknameExistente.equals(nicknameJugador)){
                throw new NicknameNoExiste();
            }
            anadirJugadorExistente();
        }
        catch (NicknameNoExiste ex) {
            System.out.println("** Nickname no existe **");
            repetirPreguntar();
        }
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
            anadirMasEquipos();
        }
    }

    /*Repetir pregunta de si quiere añadir jugador existente por si no existe ese jugador
    para salir de la repetitiva */
    public static void repetirPreguntar(){
        try {
            System.out.print("Quieres intentarlo de nuevo? ");
            String respuesta = sc.nextLine();
            if (respuesta.equals("si")){
                anadirJugadorExistenteDatos();
            } else if(respuesta.equals("no")){
                anadirMasEquipos();
            } else{
                throw new OpcionNoValida();
            }
        }
        catch (OpcionNoValida ex){
            System.out.println("** Respuesta no valida **");
        }

    }

    // Preguntar si quiere volver al menu de opciones
    public static void volverOpciones(){
        try {
            sc.nextLine();
            System.out.print("Quieres volver a opciones? ");
            String respuesta = sc.nextLine();
            if (respuesta.equals("si")){
                opciones();
            } else if(respuesta.equals("no")){
                finPrograma();
            } else {
                throw new OpcionNoValida();
            }
        }
        catch (OpcionNoValida ex){
            System.out.println("** Respuesta no valida **");
            volverOpciones();
        }
    }

    // Fin del programa
    public static void finPrograma(){
        System.out.println("-- Programa finalizado --");
        System.exit(0);
    }
}
