package pe.unmsm.oev;

import pe.unmsm.oev.model.Estado;
import pe.unmsm.oev.model.Incidencia;
import pe.unmsm.oev.model.Prioridad;
import pe.unmsm.oev.service.IncidenciaService;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final IncidenciaService service = new IncidenciaService();

    public static void main(String[] args) {
        boolean salir = false;

        // El "while" que mantiene vivo el programa
        while (!salir) {
            mostrarMenu();
            String opcionStr = scanner.nextLine();

            try {
                int opcion = Integer.parseInt(opcionStr);
                switch (opcion) {
                    case 1 -> registrar();
                    case 2 -> listarTodas();
                    case 3 -> listarPorEstado();
                    case 4 -> listarPorPrioridad();
                    case 5 -> buscarPorId();
                    case 6 -> cambiarEstado();
                    case 7 -> cerrarIncidencia();
                    case 8 -> {
                        System.out.println("Saliendo del sistema. ¡Hasta luego!");
                        salir = true;
                    }
                    default -> System.out.println("Opción inválida. Intente entre 1 y 8.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }
            System.out.println("\nPresione Enter para continuar...");
            scanner.nextLine();
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n=== SISTEMA DE INCIDENCIAS - OEV UNMSM ===");
        System.out.println("1. Registrar incidencia");
        System.out.println("2. Listar todas las incidencias");
        System.out.println("3. Listar por estado");
        System.out.println("4. Listar por prioridad");
        System.out.println("5. Buscar por ID");
        System.out.println("6. Cambiar estado");
        System.out.println("7. Cerrar incidencia");
        System.out.println("8. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void registrar() {
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Descripción: ");
        String desc = scanner.nextLine();
        System.out.print("Prioridad (BAJA, MEDIA, ALTA): ");
        try {
            Prioridad prio = Prioridad.valueOf(scanner.nextLine().toUpperCase());
            Incidencia creada = service.crearIncidencia(titulo, desc, prio);
            System.out.println("Incidencia registrada con éxito.");
            System.out.println(creada);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarTodas() {
        var lista = service.listarTodas();
        if (lista.isEmpty()) System.out.println("No hay incidencias registradas.");
        else lista.forEach(System.out::println);
    }

    private static void listarPorEstado() {
        System.out.print("Estado a buscar (ABIERTA, EN_PROCESO, RESUELTA, CERRADA): ");
        try {
            Estado est = Estado.valueOf(scanner.nextLine().toUpperCase());
            var lista = service.listarPorEstado(est);
            if (lista.isEmpty()) System.out.println("No hay incidencias con ese estado.");
            else lista.forEach(System.out::println);
        } catch (Exception e) { System.out.println("Estado no válido."); }
    }

    private static void listarPorPrioridad() {
        System.out.print("Prioridad a buscar (BAJA, MEDIA, ALTA): ");
        try {
            Prioridad prio = Prioridad.valueOf(scanner.nextLine().toUpperCase());
            var lista = service.listarPorPrioridad(prio);
            if (lista.isEmpty()) System.out.println("No hay incidencias con esa prioridad.");
            else lista.forEach(System.out::println);
        } catch (Exception e) { System.out.println("Prioridad no válida."); }
    }

    private static void buscarPorId() {
        System.out.print("Ingrese ID (primeros 8 caracteres): ");
        String id = scanner.nextLine();
        Incidencia found = service.buscarPorId(id);
        if (found != null) System.out.println(found);
        else System.out.println("Incidencia no encontrada.");
    }

    private static void cambiarEstado() {
        System.out.print("ID de incidencia: ");
        String id = scanner.nextLine();
        System.out.print("Nuevo estado: ");
        try {
            Estado nuevo = Estado.valueOf(scanner.nextLine().toUpperCase());
            if (service.cambiarEstado(id, nuevo)) System.out.println("Estado actualizado");
        } catch (Exception e) { System.out.println("Error en el cambio de estado."); }
    }

    private static void cerrarIncidencia() {
        System.out.print("ID de incidencia a cerrar: ");
        String id = scanner.nextLine();
        if (service.cerrarIncidencia(id)) System.out.println("Incidencia cerrada .");
    }
}