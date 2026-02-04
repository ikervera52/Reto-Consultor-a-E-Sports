import Clases.*;
import Exepciones.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

    static ArrayList<Equipo> listaEquiposTemporal = new ArrayList<>();
    static Competicion competicionActual;
    static ArrayList<Perfil> usuarios = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        usuarios.add(new Admin(1, "admin", "1234"));
        usuarios.add(new Usuario(2, "user", "1234"));

        competicionActual = new Competicion(1, LocalDate.now(), LocalDate.now().plusMonths(3));

        boolean salir = false;
        while (!salir) {
            try {
                System.out.println("\n#################################");
                System.out.println("###   SISTEMA E-SPORTS 2025   ###");
                System.out.println("#################################");
                System.out.println("1. Iniciar Sesión");
                System.out.println("2. Salir");
                System.out.print("Seleccione una opción: ");

                int op = Integer.parseInt(sc.nextLine());

                if (op == 1) login();
                else if (op == 2) salir = true;
                else throw new OpcionNoValida();

            } catch (Exception e) {
                System.out.println(" (!) Error: " + e.getMessage());
            }
        }
        System.out.println("¡Hasta luego!");
    }

    static void login() {
        System.out.print("Usuario: ");
        String user = sc.nextLine();
        System.out.print("Contraseña: ");
        String pass = sc.nextLine();

        Perfil perfilEncontrado = usuarios.stream()
                .filter(p -> p.getNombre().equals(user) && p.getContraseña().equals(pass))
                .findFirst()
                .orElse(null);

        if (perfilEncontrado instanceof Admin) menuAdmin();
        else if (perfilEncontrado instanceof Usuario) menuUsuario();
        else System.out.println(" (!) Usuario o contraseña incorrectos.");
    }

    static void menuAdmin() {
        boolean atras = false;
        while (!atras) {
            try {
                System.out.println("\n=== MENÚ ADMINISTRADOR ===");
                System.out.println("1. Crear Equipos - Jugadores");
                System.out.println("2. Editar Equipos - Jugadores");
                System.out.println("3. Eliminar Equipos - Jugadores");
                System.out.println("4. Generar Calendario y Jugar");
                System.out.println("5. Ver Clasificación (Liga)");
                System.out.println("6. Ver Informe de Equipos");
                System.out.println("7. Ver Informe de Jugadores");
                System.out.println("8. Ver Informe de la Competición");
                System.out.println("9. Ver Informe Completo");
                System.out.println("10. Salir (Cerrar Sesión)");
                System.out.print("Opción: ");

                int op = Integer.parseInt(sc.nextLine());

                switch (op) {
                    case 1 -> menuCrear();
                    case 2 -> menuEditar();
                    case 3 -> menuEliminar();
                    case 4 -> generarCalendarioCustom();
                    case 5 -> mostrarClasificacion();
                    case 6 -> informeEquipos();
                    case 7 -> informeJugadores();
                    case 8 -> informeCompeticion();
                    case 9 -> informeCompleto();
                    case 10 -> atras = true;
                    default -> throw new OpcionNoValida();
                }
            } catch (Exception e) {
                System.out.println(" (!) Error: " + e.getMessage());
            }
        }
    }

    static void menuCrear() {
        if (competicionActual.getEtapaIndex() == 1) {
            System.out.println(" (!) ERROR: La competición ya ha empezado.");
            return;
        }

        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- 1. CREAR EQUIPOS / JUGADORES ---");
            System.out.println("1. Añadir Equipos");
            System.out.println("2. Añadir Jugadores");
            System.out.println("3. Volver");
            System.out.print("Elige: ");

            try {
                int op = Integer.parseInt(sc.nextLine());
                switch (op) {
                    case 1 -> crearEquipos();
                    case 2 -> anadirJugadorSuelto();
                    case 3 -> volver = true;
                    default -> throw new OpcionNoValida();
                }
            } catch (Exception e) {
                System.out.println(" (!) Error: " + e.getMessage());
            }
        }
    }

    static void crearEquipos() {
        try {
            System.out.print("¿Cuántos equipos van a participar? (Debe ser PAR): ");
            int numEquipos = Integer.parseInt(sc.nextLine());

            if (numEquipos % 2 != 0) throw new EquiposNoPar();

            for (int i = 1; i <= numEquipos; i++) {
                System.out.println("\n--- Creando Equipo Nuevo " + i + " ---");
                Equipo nuevoEquipo = null;
                boolean datosOk = false;

                while(!datosOk){
                    try{
                        System.out.print("Nombre del Equipo: ");
                        String nombre = sc.nextLine();
                        System.out.print("Fecha Fundación (YYYY-MM-DD): ");
                        LocalDate fecha = LocalDate.parse(sc.nextLine());

                        nuevoEquipo = new Equipo(nombre);
                        nuevoEquipo.setFechaFundacion(fecha);
                        datosOk = true;
                    } catch(DateTimeParseException e) {
                        System.out.println(" (!) Formato de fecha incorrecto. Usa AAAA-MM-DD.");
                    } catch(Exception e){
                        System.out.println(" (!) Error: " + e.getMessage());
                    }
                }

                System.out.print("¿Cuántos jugadores tiene " + nuevoEquipo.getNombre() + "? (2-6): ");
                int numJugadores = Integer.parseInt(sc.nextLine());
                if(numJugadores < 2 || numJugadores > 6) {
                    System.out.println(" (!) Cantidad inválida (se ajusta a 2 mínimo).");
                    numJugadores = 2;
                }
                agregarJugadoresBucle(nuevoEquipo, numJugadores);
                listaEquiposTemporal.add(nuevoEquipo);
            }
            System.out.println(" --> Equipos añadidos.");
        } catch (Exception e) {
            System.out.println(" (!) Error: " + e.getMessage());
        }
    }

    static void anadirJugadorSuelto() {
        if (listaEquiposTemporal.isEmpty()) { System.out.println(" (!) No hay equipos creados."); return; }
        listarEquipos();
        System.out.print("Elige el número del equipo: ");
        try {
            int idx = Integer.parseInt(sc.nextLine()) - 1;
            if (idx >= 0 && idx < listaEquiposTemporal.size()) {
                Equipo e = listaEquiposTemporal.get(idx);
                if (e.getJugadores().size() >= 6) System.out.println(" (!) ESTE EQUIPO ESTÁ LLENO.");
                else agregarJugadoresBucle(e, 1);
            } else System.out.println(" (!) Equipo no existe.");
        } catch (Exception e) { System.out.println(" (!) Dato inválido."); }
    }

    static void agregarJugadoresBucle(Equipo e, int cantidad) {
        for (int j = 1; j <= cantidad; j++) {
            boolean agregado = false;
            while (!agregado) {
                try {
                    System.out.println("   > Datos Jugador (" + j + "/" + cantidad + "):");
                    System.out.print("     Nombre: "); String n = sc.nextLine();
                    System.out.print("     Nickname: "); String ni = sc.nextLine();
                    System.out.print("     Sueldo (Min 16576): ");
                    double s = Double.parseDouble(sc.nextLine());
                    System.out.println("     Roles: 1.Support 2.AWPer 3.IGL 4.Lurker 5.Rifler 6.Entry");
                    System.out.print("     Elige Rol (1-6): ");
                    int r = Integer.parseInt(sc.nextLine());

                    if (r < 1 || r > 6) throw new RolNoValido();
                    e.agregarJugador(new Jugador(n, ni, s, Jugador.Rol.values()[r-1]));
                    agregado = true;
                } catch (Exception ex) {
                    System.out.println("     (!) Error: " + ex.getClass().getSimpleName());
                }
            }
        }
    }

    static void menuEditar() {
        if (competicionActual.getEtapaIndex() == 1) {
            System.out.println(" (!) Competición iniciada. No se puede editar.");
            return;
        }

        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- 2. EDITAR EQUIPOS / JUGADORES ---");
            System.out.println("1. Editar Equipo (Nombre)");
            System.out.println("2. Editar Jugador (Datos)");
            System.out.println("3. Volver");
            System.out.print("Elige: ");

            try {
                int op = Integer.parseInt(sc.nextLine());
                switch (op) {
                    case 1 -> editarEquipoFlow();
                    case 2 -> editarJugadorFlow();
                    case 3 -> volver = true;
                    default -> throw new OpcionNoValida();
                }
            } catch (Exception e) {
                System.out.println(" (!) Error: " + e.getMessage());
            }
        }
    }

    static void editarEquipoFlow() {
        if (listaEquiposTemporal.isEmpty()) { System.out.println("No hay equipos."); return; }
        listarEquipos();
        System.out.print("Escribe el número del equipo a editar: ");

        try {
            int idx = Integer.parseInt(sc.nextLine()) - 1;
            if (idx >= 0 && idx < listaEquiposTemporal.size()) {
                Equipo equipoSeleccionado = listaEquiposTemporal.get(idx);
                System.out.print("Nuevo nombre para el equipo: ");
                String nuevoNombre = sc.nextLine();
                if(!nuevoNombre.isEmpty()) {
                    equipoSeleccionado.setNombre(nuevoNombre);
                    System.out.println(" -> Nombre cambiado.");
                }

                System.out.print("¿Deseas añadir jugadores a este equipo ahora? (s/n): ");
                if (sc.nextLine().trim().equalsIgnoreCase("s")) {
                    if (equipoSeleccionado.getJugadores().size() < 6) {
                        agregarJugadoresBucle(equipoSeleccionado, 1);
                    } else {
                        System.out.println(" (!) El equipo ya está lleno.");
                    }
                }
            } else {
                System.out.println(" (!) Equipo no encontrado.");
            }
        } catch (Exception e) {
            System.out.println(" (!) Error: " + e.getMessage());
        }
    }

    static void editarJugadorFlow() {
        if (listaEquiposTemporal.isEmpty()) { System.out.println("No hay equipos."); return; }

        ArrayList<Jugador> todosLosJugadores = new ArrayList<>();
        int contador = 1;
        System.out.println("--- LISTADO GLOBAL DE JUGADORES ---");
        for (Equipo eq : listaEquiposTemporal) {
            for (Jugador j : eq.getJugadores()) {
                System.out.println(contador + ". " + j.getNickname() + " [Equipo: " + eq.getNombre() + "]");
                todosLosJugadores.add(j);
                contador++;
            }
        }

        if (todosLosJugadores.isEmpty()) { System.out.println("No hay jugadores."); return; }

        System.out.print("Selecciona el número del jugador a editar: ");
        try {
            int idx = Integer.parseInt(sc.nextLine()) - 1;
            if (idx >= 0 && idx < todosLosJugadores.size()) {
                Jugador j = todosLosJugadores.get(idx);
                System.out.println("Editando a: " + j.getNombre() + " (" + j.getNickname() + ")");

                System.out.print("Nuevo Nombre (Enter salta): ");
                String n = sc.nextLine();
                if(!n.isEmpty()) j.setNombre(n);

                System.out.print("Nuevo Nick (Enter salta): ");
                String nick = sc.nextLine();
                if(!nick.isEmpty()) j.setNickname(nick);

                System.out.println(" -> Jugador actualizado.");
            } else {
                System.out.println(" (!) Jugador no encontrado.");
            }
        } catch (Exception e) {
            System.out.println(" (!) Error.");
        }
    }

    static void menuEliminar() {
        if (competicionActual.getEtapaIndex() == 1) {
            System.out.println(" (!) Competición en curso. No se puede eliminar.");
            return;
        }

        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- 3. ELIMINAR EQUIPOS / JUGADORES ---");
            System.out.println("1. Eliminar Equipo");
            System.out.println("2. Eliminar Jugador");
            System.out.println("3. Volver");
            System.out.print("Elige: ");

            try {
                int op = Integer.parseInt(sc.nextLine());
                switch (op) {
                    case 1 -> {
                        listarEquipos();
                        System.out.print("Nº Equipo a eliminar: ");
                        int idx = Integer.parseInt(sc.nextLine()) - 1;
                        if(idx >= 0 && idx < listaEquiposTemporal.size()) {
                            listaEquiposTemporal.remove(idx);
                            System.out.println(" -> Equipo eliminado.");
                        } else System.out.println(" (!) Inválido.");
                    }
                    case 2 -> {
                        ArrayList<Jugador> todosLosJugadores = new ArrayList<>();
                        ArrayList<Equipo> referenciaEquipo = new ArrayList<>();
                        int contador = 1;
                        for (Equipo eq : listaEquiposTemporal) {
                            for (Jugador j : eq.getJugadores()) {
                                System.out.println(contador + ". " + j.getNickname() + " [" + eq.getNombre() + "]");
                                todosLosJugadores.add(j);
                                referenciaEquipo.add(eq);
                                contador++;
                            }
                        }
                        if(todosLosJugadores.isEmpty()) { System.out.println("No hay jugadores."); break; }

                        System.out.print("Nº Jugador a eliminar: ");
                        int idxJ = Integer.parseInt(sc.nextLine()) - 1;
                        if (idxJ >= 0 && idxJ < todosLosJugadores.size()) {
                            Equipo eDelJugador = referenciaEquipo.get(idxJ);
                            eDelJugador.getJugadores().remove(todosLosJugadores.get(idxJ));
                            System.out.println(" -> Jugador eliminado.");
                        } else System.out.println(" (!) Inválido.");
                    }
                    case 3 -> volver = true;
                    default -> throw new OpcionNoValida();
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    static void generarCalendarioCustom() {
        if (listaEquiposTemporal.isEmpty() || listaEquiposTemporal.size() % 2 != 0) {
            System.out.println(" (!) Error: Faltan equipos o el número es impar.");
            return;
        }

        try {
            listaEquiposTemporal.forEach(e -> {
                try { e.validarMinimoJugadores(); }
                catch (CantidadJugadoresNoValida ex) { throw new RuntimeException(ex); }
            });

            System.out.print("¿Cuántas jornadas generar?: ");
            int numJornadas = Integer.parseInt(sc.nextLine());

            competicionActual.setEquipos(new ArrayList<>(listaEquiposTemporal));
            ArrayList<Equipo> equipos = new ArrayList<>(competicionActual.getEquipos());
            LocalDate fecha = competicionActual.getFechaInicio();

            for (int i = 0; i < numJornadas; i++) {
                Jornada j = new Jornada(i + 1, fecha);
                for (int k = 0; k < equipos.size() / 2; k++) {
                    LocalTime hora = LocalTime.of(16, 0).plusHours(k * 2);
                    j.agregarEnfrentamiento(new Enfrentamiento(equipos.get(k), equipos.get(equipos.size() - 1 - k), hora));
                }
                competicionActual.agregarJornada(j);
                equipos.add(1, equipos.remove(equipos.size() - 1));
                fecha = fecha.plusWeeks(1);
            }

            competicionActual.setEtapa(1);
            System.out.println(" ¡Calendario generado!");

            System.out.println("¿Estado de la competición?\n1. En Juego\n2. Finalizada (Simular)");
            if (sc.nextLine().equals("2")) {
                System.out.println(" Simulando resultados...");
                competicionActual.getJornadas().stream()
                        .flatMap(jor -> jor.getEnfrentamientos().stream())
                        .forEach(enf -> {
                            enf.simularResultado();
                            if (enf.getRondasLocal() > enf.getRondasVisitante()) {
                                enf.getLocal().sumarVictoria();
                                enf.getVisitante().sumarDerrota();
                            } else if (enf.getRondasVisitante() > enf.getRondasLocal()) {
                                enf.getVisitante().sumarVictoria();
                                enf.getLocal().sumarDerrota();
                            }
                        });
                mostrarClasificacion();
            }

        } catch (Exception e) {
            System.out.println(" (!) Error: " + e.getMessage());
        }
    }

    static void mostrarClasificacion() {
        System.out.println("\n=== CLASIFICACIÓN ===");
        System.out.println("POS | EQUIPO           | PTOS | V  | D ");

        ArrayList<Equipo> tabla = new ArrayList<>(competicionActual.getEquipos());
        tabla.stream()
                .sorted(Comparator.comparingInt(Equipo::getPuntos).reversed())
                .forEach(e -> System.out.printf(" -  | %-16s |  %-3d | %-2d | %-2d\n",
                        e.getNombre(), e.getPuntos(), e.getVictorias(), e.getDerrotas()));
    }

    static void informeEquipos() {
        System.out.println("\n--- INFORME DE EQUIPOS ---");
        var lista = (competicionActual.getEtapaIndex() == 1) ? competicionActual.getEquipos() : listaEquiposTemporal;

        lista.stream()
                .forEach(e -> System.out.println("Equipo: " + e.getNombre() + " | Fundado: " + e.getFechaFundacion()));
    }

    static void informeJugadores() {
        System.out.println("\n--- INFORME DE JUGADORES ---");
        var lista = (competicionActual.getEtapaIndex() == 1) ? competicionActual.getEquipos() : listaEquiposTemporal;

        lista.stream()
                .flatMap(e -> e.getJugadores().stream()
                        .map(j -> "Nombre: " + j.getNombre() + " | Nick: " + j.getNickname() + " | Equipo: " + e.getNombre()))
                .forEach(System.out::println);
    }

    static void informeCompeticion() {
        System.out.println("\n--- INFORME JORNADAS ---");
        competicionActual.getJornadas().stream()
                .forEach(j -> {
                    System.out.println(j);
                    j.getEnfrentamientos().forEach(enf -> System.out.println("  " + enf));
                });
    }

    static void informeCompleto() {
        informeEquipos();
        informeJugadores();
        informeCompeticion();
        if(competicionActual.getEtapaIndex() == 1) mostrarClasificacion();
    }

    static void listarEquipos() {
        for (int i = 0; i < listaEquiposTemporal.size(); i++) {
            System.out.println((i + 1) + ". " + listaEquiposTemporal.get(i).getNombre());
        }
    }

    static void menuUsuario() {
        boolean atras = false;
        while (!atras) {
            System.out.println("\n1. Ver Informe | 2. Ver Clasificación | 3. Salir");
            switch (sc.nextLine()) {
                case "1" -> informeCompleto();
                case "2" -> {
                    if(competicionActual.getEtapaIndex() == 1) mostrarClasificacion();
                    else System.out.println("La competición no ha empezado.");
                }
                case "3" -> atras = true;
            }
        }
    }
}