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
        while (!salir) {
            mostrarMenu();
            String opcionStr = scanner.nextLine();
            try {
                int opcion = Integer.parseInt(opcionStr);
                switch (opcion) {
                    case 1 -> registrar();
                    case 2 -> listarTodas();
                    case 3 -> buscarPorId();
                    case 4 -> cambiarEstado();
                    case 5 -> {
                        System.out.println("Saliendo...");
                        salir = true;
                    }
                    default -> System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número.");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- SISTEMA OEV UNMSM ---");
        System.out.println("1. Registrar Incidencia");
        System.out.println("2. Listar Todas");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Cambiar Estado");
        System.out.println("5. Salir");
        System.out.print("Opción: ");
    }

    private static void registrar() {
        try {
            System.out.print("Título: ");
            String t = scanner.nextLine();
            System.out.print("Descripción: ");
            String d = scanner.nextLine();
            System.out.print("Prioridad (BAJA, MEDIA, ALTA): ");
            Prioridad p = Prioridad.valueOf(scanner.nextLine().toUpperCase());
            service.crearIncidencia(t, d, p);
            System.out.println("¡Registrado!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarTodas() {
        var lista = service.listarTodas();
        if (lista.isEmpty()) System.out.println("No hay incidencias.");
        else lista.forEach(System.out::println);
    }

    private static void buscarPorId() {
        System.out.print("ID a buscar: ");
        String id = scanner.nextLine();
        // REFACTOR: Manejo de Optional
        service.buscarPorId(id).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("No se encontró la incidencia.")
        );
    }

    private static void cambiarEstado() {
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Nuevo Estado (ABIERTA, EN_PROCESO, CERRADA): ");
        try {
            Estado est = Estado.valueOf(scanner.nextLine().toUpperCase());
            if (service.cambiarEstado(id, est)) System.out.println("Estado actualizado.");
            else System.out.println("No se pudo cambiar el estado.");
        } catch (Exception e) {
            System.out.println("Error en el estado ingresado.");
        }
    }
}