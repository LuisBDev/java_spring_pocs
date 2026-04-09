package pe.unmsm.oev.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Incidencia {
    private String id;
    private String titulo;
    private String descripcion;
    private Prioridad prioridad;
    private Estado estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaCierre;

    // Constructor
    public Incidencia(String titulo, String descripcion, Prioridad prioridad) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.estado = Estado.ABIERTA;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaCierre = null;
    }

    // Getters
    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public Prioridad getPrioridad() { return prioridad; }
    public Estado getEstado() { return estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaCierre() { return fechaCierre; }

    // Setters
    public void setEstado(Estado estado) { this.estado = estado; }
    public void setFechaCierre(LocalDateTime fechaCierre) { this.fechaCierre = fechaCierre; }

    @Override
    public String toString() {
        return String.format("ID: %s | Título: %s | Prioridad: %s | Estado: %s | Fecha: %s",
                id, titulo, prioridad, estado, fechaCreacion);
    }
}