package pe.unmsm.oev.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.unmsm.oev.model.Estado;
import pe.unmsm.oev.model.Incidencia;
import pe.unmsm.oev.model.Prioridad;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IncidenciaService {
    private static final Logger log = LoggerFactory.getLogger(IncidenciaService.class);
    private final List<Incidencia> incidencias = new ArrayList<>();

    private void validarTexto(String texto, String campo) {
        if (texto == null || texto.isBlank()) {
            log.error("Error de validación: El campo '{}' no puede estar vacío", campo);
            throw new IllegalArgumentException("El " + campo + " no puede estar vacío.");
        }
    }

    public Incidencia crearIncidencia(String titulo, String descripcion, Prioridad prioridad) {
        validarTexto(titulo, "título");
        validarTexto(descripcion, "descripción");

        Incidencia nueva = new Incidencia(titulo, descripcion, prioridad);
        incidencias.add(nueva);
        log.info("Incidencia creada con éxito. Título: {}", nueva.getTitulo());
        return nueva;
    }

    // RETORNAR OPTIONAL (Línea 44)
    public Optional<Incidencia> buscarPorId(String id) {
        log.info("Iniciando búsqueda de incidencia con ID: {}", id);
        return incidencias.stream()
                .filter(i -> i.getId().equalsIgnoreCase(id))
                .findFirst();
    }

    public boolean cambiarEstado(String id, Estado nuevoEstado) {
        Optional<Incidencia> encontrada = buscarPorId(id);

        // Uso de .isPresent (Línea 51)
        if (encontrada.isPresent()) {
            Incidencia inc = encontrada.get();
            if (inc.getEstado() == Estado.CERRADA) {
                log.warn("Intento de modificación fallido: La incidencia {} ya está CERRADA", id);
                return false;
            }
            inc.setEstado(nuevoEstado);
            log.info("Estado actualizado correctamente para ID: {}", id);
            return true;
        }
        log.error("No se encontró la incidencia para actualizar: {}", id);
        return false;
    }

    public List<Incidencia> listarTodas() {
        return new ArrayList<>(incidencias);
    }
}