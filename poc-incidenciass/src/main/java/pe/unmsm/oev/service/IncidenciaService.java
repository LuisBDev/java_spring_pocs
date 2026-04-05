package pe.unmsm.oev.service;

import pe.unmsm.oev.model.Estado;
import pe.unmsm.oev.model.Incidencia;
import pe.unmsm.oev.model.Prioridad;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IncidenciaService {
    private List<Incidencia> incidencias;

    public IncidenciaService() {
        this.incidencias = new ArrayList<>();
    }

    public Incidencia crearIncidencia(String titulo, String descripcion, Prioridad prioridad) {
        if (titulo == null || titulo.isBlank()) throw new IllegalArgumentException("El título no puede estar vacío.");
        if (descripcion == null || descripcion.isBlank()) throw new IllegalArgumentException("La descripción no puede estar vacía.");

        Incidencia nueva = new Incidencia(titulo, descripcion, prioridad);
        incidencias.add(nueva);
        return nueva;
    }

    public List<Incidencia> listarTodas() {
        return new ArrayList<>(incidencias);
    }

    public List<Incidencia> listarPorEstado(Estado estado) {
        return incidencias.stream()
                .filter(i -> i.getEstado() == estado)
                .collect(Collectors.toList());
    }

    public List<Incidencia> listarPorPrioridad(Prioridad prioridad) {
        return incidencias.stream()
                .filter(i -> i.getPrioridad() == prioridad)
                .collect(Collectors.toList());
    }

    public Incidencia buscarPorId(String id) {
        return incidencias.stream()
                .filter(i -> i.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public boolean cambiarEstado(String id, Estado nuevoEstado) {
        Incidencia found = buscarPorId(id);
        if (found != null) {
            if (found.getEstado() == Estado.CERRADA) {
                System.out.println("Error: No se puede cambiar una incidencia CERRADA.");
                return false;
            }
            found.setEstado(nuevoEstado);
            return true;
        }
        return false;
    }

    public boolean cerrarIncidencia(String id) {
        Incidencia found = buscarPorId(id);
        if (found != null) {
            if (found.getEstado() == Estado.CERRADA) {
                System.out.println("Esta incidencia ya está cerrada.");
                return false;
            }
            found.setEstado(Estado.CERRADA);
            found.setFechaCierre(LocalDateTime.now());
            return true;
        }
        return false;
    }
}