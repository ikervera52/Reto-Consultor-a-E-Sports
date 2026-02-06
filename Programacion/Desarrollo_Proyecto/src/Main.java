import Clases.*;
import Exepciones.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    static ArrayList<Equipo> listaEquiposTemporal = new ArrayList<>();
    static Competicion competicionActual;
    static ArrayList<Perfil> usuarios = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        usuarios.add(new Admin(1, "admin", "1234"));
        usuarios.add(new Usuario(2, "usuario", "1234"));

        int idCompeticion = 9000 + (1 * 100);
        competicionActual = new Competicion(idCompeticion, LocalDate.now(), LocalDate.now().plusMonths(3));

        boolean salir = false;
        while (!salir) {
            try {
                System.out.println("###   SISTEMA E-SPORTS 2025   ###");
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
                .findFirst().orElse(null);
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
                System.out.println("4. Generar Calendario / Simular Partidos");
                System.out.println("5. Ver Clasificación");
                System.out.println("6. Informe Equipos");
                System.out.println("7. Informe Jugadores");
                System.out.println("8. Informe Competición (Jornadas)");
                System.out.println("9. Informe Completo");
                System.out.println("10. Cerrar Sesión");
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
            } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
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
            System.out.println("2. Añadir Jugadores (Por ID Equipo)");
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
            } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
        }
    }

    static void crearEquipos() {
        try {
            System.out.print("¿Cuántos equipos van a participar? (Debe ser PAR): ");
            int numEquipos = Integer.parseInt(sc.nextLine());

            if (numEquipos % 2 != 0) throw new EquiposNoPar();

            for (int i = 1; i <= numEquipos; i++) {
                int idEquipoGenerado = 3000 + (i * 100);

                System.out.println("\n--- Creando Equipo Nuevo " + i + " (ID: " + idEquipoGenerado + ") ---");

                String nombre = "";
                while(nombre.isEmpty()) {
                    System.out.print("Nombre del Equipo: ");
                    nombre = sc.nextLine();
                    if(nombre.trim().isEmpty()) System.out.println(" (!) El nombre no puede estar vacío.");
                }

                LocalDate fecha = null;
                while(fecha == null) {
                    try {
                        System.out.print("Fecha Fundación (YYYY-MM-DD): ");
                        fecha = LocalDate.parse(sc.nextLine());
                    } catch(DateTimeParseException e) {
                        System.out.println(" (!) Formato de fecha incorrecto. Usa AAAA-MM-DD.");
                    }
                }

                Equipo nuevoEquipo = new Equipo(idEquipoGenerado, nombre);
                nuevoEquipo.setFechaFundacion(fecha);

                int numJugadores = 0;
                while (numJugadores < 2 || numJugadores > 6) {
                    try {
                        System.out.print("¿Cuántos jugadores tiene " + nuevoEquipo.getNombre() + "? (2-6): ");
                        numJugadores = Integer.parseInt(sc.nextLine());
                        if(numJugadores < 2 || numJugadores > 6) System.out.println(" (!) Debes poner entre 2 y 6.");
                    } catch(NumberFormatException e) {
                        System.out.println(" (!) Introduce un número.");
                    }
                }

                agregarJugadoresBucle(nuevoEquipo, numJugadores);
                listaEquiposTemporal.add(nuevoEquipo);
            }
            System.out.println(" --> Equipos añadidos.");
        } catch (Exception e) {
            System.out.println(" (!) Error: " + e.getMessage());
        }
    }

    static void agregarJugadoresBucle(Equipo e, int cantidad) {
        int baseIdJugadores = e.getId() - 2000;
        int contadorActual = e.getJugadores().size();

        for (int j = 1; j <= cantidad; j++) {
            int numeroJugador = contadorActual + j;
            int idJugadorGenerado = baseIdJugadores + numeroJugador;

            System.out.println("   > Datos Jugador " + j + " (ID: " + idJugadorGenerado + "):");

            String nombre = "";
            boolean nombreValido = false;
            while (!nombreValido) {
                System.out.print("     Nombre: ");
                nombre = sc.nextLine();
                if (nombre.matches("^[A-ZÁÉÍÓÚÜÑ][a-záéíóúüñ]+$")) {
                    nombreValido = true;
                } else {
                    System.out.println("     (!) Error: El nombre debe empezar por mayúscula y solo contener letras.");
                }
            }

            String nick = "";
            boolean nickValido = false;
            while (!nickValido) {
                System.out.print("     Nick: ");
                nick = sc.nextLine();
                if (nick.matches("^[A-Za-z0-9_!@#$-]+$")) {
                    nickValido = true;
                } else {
                    System.out.println("     (!) Error: Nickname inválido (caracteres no permitidos).");
                }
            }

            double sueldo = 0;
            boolean sueldoValido = false;
            while (!sueldoValido) {
                try {
                    System.out.print("     Sueldo (Min 16576): ");
                    sueldo = Double.parseDouble(sc.nextLine());
                    if (sueldo >= 16576) {
                        sueldoValido = true;
                    } else {
                        System.out.println("     (!) Error: El sueldo debe ser mayor o igual a 16576.");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("     (!) Error: Introduce un número decimal válido.");
                }
            }

            int rolIndex = 0;
            boolean rolValido = false;
            while (!rolValido) {
                try {
                    System.out.println("     Roles: 1.Support 2.AWPer 3.IGL 4.Lurker 5.Rifler 6.Entry");
                    System.out.print("     Elige Rol (1-6): ");
                    rolIndex = Integer.parseInt(sc.nextLine());
                    if (rolIndex >= 1 && rolIndex <= 6) {
                        rolValido = true;
                    } else {
                        System.out.println("     (!) Error: Elige una opción entre 1 y 6.");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("     (!) Error: Introduce un número.");
                }
            }

            try {
                e.agregarJugador(new Jugador(idJugadorGenerado, nombre, nick, sueldo, Jugador.Rol.values()[rolIndex - 1]));
            } catch (Exception ex) {
                System.out.println("     (!) Error inesperado al guardar: " + ex.getMessage());
            }
        }
    }

    static void anadirJugadorSuelto() {
        if (listaEquiposTemporal.isEmpty()) {
            System.out.println("No hay equipos.");
            return;
        }
        listarEquipos();
        System.out.print("Introduce el ID del equipo al que añadir jugador: ");
        try {
            int idBuscado = Integer.parseInt(sc.nextLine());
            Equipo e = listaEquiposTemporal.stream()
                    .filter(eq -> eq.getId() == idBuscado)
                    .findFirst()
                    .orElse(null);

            if (e != null) {
                if (e.getJugadores().size() >= 6) System.out.println(" (!) Equipo Lleno.");
                else agregarJugadoresBucle(e, 1);
            } else {
                System.out.println(" (!) ID de equipo no encontrado.");
            }
        } catch (Exception e) {
            System.out.println(" (!) Dato inválido.");
        }
    }

    static void generarCalendarioCustom() {
        boolean calendarioYaCreado = !competicionActual.getJornadas().isEmpty();

        if (!calendarioYaCreado) {
            if (listaEquiposTemporal.isEmpty() || listaEquiposTemporal.size() % 2 != 0) {
                System.out.println(" (!) Error: Faltan equipos o el número es impar.");
                return;
            }

            try {
                int numEquipos = listaEquiposTemporal.size();
                int jornadasNecesarias = numEquipos - 1;

                System.out.println(" -> Generando automáticamente " + jornadasNecesarias + " jornadas (Todos contra todos - Ida)...");

                competicionActual.setEquipos(new ArrayList<>(listaEquiposTemporal));
                ArrayList<Equipo> equipos = new ArrayList<>(competicionActual.getEquipos());
                LocalDate fecha = competicionActual.getFechaInicio();

                for (int i = 0; i < jornadasNecesarias; i++) {
                    int idJornada = 5000 + ((i + 1) * 100);

                    Jornada j = new Jornada(idJornada, i + 1, fecha);

                    for (int k = 0; k < equipos.size() / 2; k++) {
                        LocalTime hora = LocalTime.of(16, 0).plusHours(k * 2);
                        Equipo local = equipos.get(k);
                        Equipo visitante = equipos.get(equipos.size() - 1 - k);

                        j.agregarEnfrentamiento(new Enfrentamiento(local, visitante, hora));
                    }
                    competicionActual.agregarJornada(j);

                    equipos.add(1, equipos.remove(equipos.size() - 1));
                    fecha = fecha.plusWeeks(1);
                }

                competicionActual.setEtapa(1);
                System.out.println(" ¡Calendario generado exitosamente!");

            } catch (Exception e) {
                System.out.println(" (!) Error al generar: " + e.getMessage());
                return;
            }
        } else {
            System.out.println(" --- Calendario ya existente. Pasando a gestión de partidos ---");
        }

        long partidosPendientes = competicionActual.getJornadas().stream()
                .flatMap(j -> j.getEnfrentamientos().stream())
                .filter(e -> e.toString().contains("Pendiente"))
                .count();

        if (partidosPendientes == 0) {
            System.out.println(" (!) Todos los partidos han sido jugados. La competición ha finalizado.");
        } else {
            System.out.println(" Hay " + partidosPendientes + " partidos pendientes.");
            System.out.print("¿Deseas simular los resultados pendientes ahora? (s/n): ");
            String resp = sc.nextLine();

            if (resp.trim().equalsIgnoreCase("s")) {
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
                            } else {
                                enf.getLocal().sumarEmpate();
                                enf.getVisitante().sumarEmpate();
                            }
                        });
                System.out.println(" -> Resultados simulados correctamente.");
            } else {
                System.out.println(" -> Simulación pospuesta. Puedes volver aquí más tarde.");
            }
        }
    }

    static void listarEquipos() {
        for (Equipo e : listaEquiposTemporal) {
            System.out.println("ID: " + e.getId() + " - " + e.getNombre());
        }
    }

    static void menuEditar() {
        if (competicionActual.getEtapaIndex() == 1) {
            System.out.println(" (!) Competición iniciada. No se puede editar.");
            return;
        }

        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- 2. EDITAR POR ID ---");
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
        System.out.print("Introduce el ID del equipo a editar: ");

        try {
            int idBuscado = Integer.parseInt(sc.nextLine());
            Equipo equipoSeleccionado = listaEquiposTemporal.stream()
                    .filter(eq -> eq.getId() == idBuscado)
                    .findFirst().orElse(null);

            if (equipoSeleccionado != null) {
                System.out.println("Editando: " + equipoSeleccionado.getNombre());
                System.out.print("Nuevo nombre: ");
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
                System.out.println(" (!) ID Equipo no encontrado.");
            }
        } catch (Exception e) {
            System.out.println(" (!) Error: " + e.getMessage());
        }
    }

    static void editarJugadorFlow() {
        if (listaEquiposTemporal.isEmpty()) { System.out.println("No hay equipos."); return; }

        System.out.println("--- JUGADORES DISPONIBLES ---");
        for (Equipo eq : listaEquiposTemporal) {
            for (Jugador j : eq.getJugadores()) {
                System.out.println("ID: " + j.getId() + " - " + j.getNickname() + " [" + eq.getNombre() + "]");
            }
        }

        System.out.print("Introduce el ID del jugador a editar: ");
        try {
            int idBuscado = Integer.parseInt(sc.nextLine());
            Jugador jugadorEncontrado = null;

            for(Equipo eq : listaEquiposTemporal) {
                for(Jugador j : eq.getJugadores()) {
                    if(j.getId() == idBuscado) {
                        jugadorEncontrado = j;
                        break;
                    }
                }
            }

            if (jugadorEncontrado != null) {
                System.out.println("Editando a: " + jugadorEncontrado.getNombre() + " (" + jugadorEncontrado.getNickname() + ")");
                System.out.print("Nuevo Nombre (Enter salta): ");
                String n = sc.nextLine();
                if(!n.isEmpty()) jugadorEncontrado.setNombre(n);

                System.out.print("Nuevo Nick (Enter salta): ");
                String nick = sc.nextLine();
                if(!nick.isEmpty()) jugadorEncontrado.setNickname(nick);

                System.out.println(" -> Jugador actualizado.");
            } else {
                System.out.println(" (!) ID Jugador no encontrado.");
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
            System.out.println("\n--- 3. ELIMINAR POR ID ---");
            System.out.println("1. Eliminar Equipo");
            System.out.println("2. Eliminar Jugador");
            System.out.println("3. Volver");
            System.out.print("Elige: ");

            try {
                int op = Integer.parseInt(sc.nextLine());
                switch (op) {
                    case 1 -> {
                        listarEquipos();
                        System.out.print("ID del Equipo a eliminar: ");
                        int idEq = Integer.parseInt(sc.nextLine());
                        boolean borrado = listaEquiposTemporal.removeIf(e -> e.getId() == idEq);
                        if(borrado) System.out.println(" -> Equipo eliminado.");
                        else System.out.println(" (!) ID no encontrado.");
                    }
                    case 2 -> {
                        System.out.println("--- Lista de Jugadores ---");
                        listaEquiposTemporal.forEach(e -> e.getJugadores().forEach(j ->
                                System.out.println("ID: " + j.getId() + " - " + j.getNickname())));

                        System.out.print("ID del Jugador a eliminar: ");
                        int idJug = Integer.parseInt(sc.nextLine());
                        boolean eliminado = false;

                        for (Equipo eq : listaEquiposTemporal) {
                            if (eq.getJugadores().removeIf(j -> j.getId() == idJug)) {
                                eliminado = true;
                                System.out.println(" -> Jugador eliminado del equipo " + eq.getNombre());
                                break;
                            }
                        }
                        if(!eliminado) System.out.println(" (!) ID no encontrado.");
                    }
                    case 3 -> volver = true;
                    default -> throw new OpcionNoValida();
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    static void mostrarClasificacion() {
        if(competicionActual.getEtapaIndex() == 0) {
            System.out.println(" (!) Falta por crear competición (Partidos no jugados).");
            return;
        }

        System.out.println("\n=== CLASIFICACIÓN (TEMPORADA: " + competicionActual.getFechaInicio() + " - " + competicionActual.getFechaInicio().plusWeeks(competicionActual.getJornadas().size()) + ") ===");
        System.out.println("POS | EQUIPO           | PTOS | V  | E  | D ");

        List<Equipo> ordenados = competicionActual.getEquipos().stream()
                .sorted(Comparator.comparingInt(Equipo::getPuntos).reversed())
                .collect(Collectors.toList());

        for (int i = 0; i < ordenados.size(); i++) {
            Equipo e = ordenados.get(i);
            System.out.printf(" %-2d | %-16s |  %-3d | %-2d | %-2d | %-2d\n",
                    (i+1), e.getNombre(), e.getPuntos(), e.getVictorias(), e.getEmpates(), e.getDerrotas());
        }
    }

    static void informeEquipos() {
        var lista = (competicionActual.getEtapaIndex() == 1) ? competicionActual.getEquipos() : listaEquiposTemporal;

        if (lista.isEmpty()) {
            System.out.println(" (!) Falta por crear equipos.");
            return;
        }

        System.out.println("\n--- INFORME DE EQUIPOS ---");
        lista.stream().forEach(e -> {
            System.out.println("ID: " + e.getId() + " | Equipo: " + e.getNombre() + " | Fundado: " + e.getFechaFundacion() + " | N.Jugadores: " + e.getJugadores().size());
            String nombresJugadores = e.getJugadores().stream()
                    .map(j -> j.getNombre() + "(" + j.getId() + ")")
                    .collect(Collectors.joining(", "));
            System.out.println(" -> Plantilla: " + nombresJugadores);
            System.out.println("------------------------------------------------");
        });
    }

    static void informeJugadores() {
        var lista = (competicionActual.getEtapaIndex() == 1) ? competicionActual.getEquipos() : listaEquiposTemporal;

        if (lista.isEmpty()) {
            System.out.println(" (!) Falta por crear equipos y jugadores.");
            return;
        }

        boolean hayJugadores = lista.stream().anyMatch(e -> !e.getJugadores().isEmpty());
        if (!hayJugadores) {
            System.out.println(" (!) Falta por crear jugadores (Equipos vacíos).");
            return;
        }

        System.out.println("\n--- INFORME DE JUGADORES ---");
        lista.stream()
                .flatMap(e -> e.getJugadores().stream()
                        .map(j -> "ID: " + j.getId() + " | Nombre: " + j.getNombre() + " | Nick: " + j.getNickname() + " | Equipo: " + e.getNombre()))
                .forEach(System.out::println);
    }

    static void informeCompeticion() {
        if(competicionActual.getJornadas().isEmpty()) {
            System.out.println(" (!) Falta por crear la competición (Generar Calendario).");
            return;
        }

        System.out.println("\n--- INFORME JORNADAS ---");
        System.out.println("COMPETICIÓN ID: " + competicionActual.getId() + " | INICIO: " + competicionActual.getFechaInicio() + " | FINAL: " + competicionActual.getFechaInicio().plusWeeks(competicionActual.getJornadas().size()));

        competicionActual.getJornadas().stream()
                .forEach(j -> {
                    System.out.println(j);
                    j.getEnfrentamientos().forEach(enf -> System.out.println("  " + enf));
                });
    }

    static void informeCompleto() {
        if (listaEquiposTemporal.isEmpty() && competicionActual.getEquipos().isEmpty()) {
            System.out.println(" (!) No se ha creado ningún dato.");
            return;
        }
        informeEquipos();
        informeJugadores();
        informeCompeticion();
        if(competicionActual.getEtapaIndex() == 1) mostrarClasificacion();
    }

    static void menuUsuario() {
        boolean atras = false;
        while (!atras) {
            try {
                System.out.println("\n=== MENÚ USUARIO ===");
                System.out.println("1. Ver Clasificación");
                System.out.println("2. Ver Informe Equipos");
                System.out.println("3. Ver Informe Jugadores");
                System.out.println("4. Ver Informe Partidos (Jornadas)");
                System.out.println("5. Ver Informe Completo");
                System.out.println("6. Salir");
                System.out.print("Opción: ");

                int op = Integer.parseInt(sc.nextLine());

                switch (op) {
                    case 1 -> mostrarClasificacion();
                    case 2 -> informeEquipos();
                    case 3 -> informeJugadores();
                    case 4 -> informeCompeticion();
                    case 5 -> informeCompleto();
                    case 6 -> atras = true;
                    default -> System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}