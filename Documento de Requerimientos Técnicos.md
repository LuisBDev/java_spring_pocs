# Documento de Requerimientos Técnicos
## POC: Sistema de Registro de Incidencias

**Versión:** 1.0  
**Tecnología:** Java 21, Maven, Consola (sin base de datos)  

---

## 1. Descripción General

Sistema de consola que permite registrar y gestionar incidencias reportadas en la OEV. Las incidencias representan problemas, solicitudes o eventos que requieren atención (ej: acceso denegado, curso no visible, alumno no matriculado).

El sistema almacena la información en memoria durante la ejecución. Al cerrar el programa, los datos se pierden.

---

## 2. Requerimientos Funcionales

### RF-01: Crear incidencia
- El usuario ingresa: **título**, **descripción**, **prioridad** (BAJA, MEDIA, ALTA).
- El sistema genera automáticamente: **ID** (incremental desde 1), **estado** (ABIERTA), **fecha** (actual).
- Validaciones: título y descripción no vacíos, prioridad válida.

**Output:**
```text
Incidencia registrada con éxito.
ID: 1 | Estado: ABIERTA | Fecha: 2026-03-31 08:45:00
```

---

### RF-02: Listar todas las incidencias
- Muestra todas las incidencias registradas.
- Si no hay incidencias: `"No hay incidencias registradas."`
- Formato: ID, Título, Descripción, Prioridad, Estado, Fecha.

**Output:**
```text
=== LISTADO DE INCIDENCIAS ===
ID: 1 | Título: Problema acceso campus | Prioridad: ALTA | Estado: ABIERTA
ID: 2 | Título: Curso duplicado | Prioridad: MEDIA | Estado: EN_PROCESO
```

---

### RF-03: Listar por estado
- Parámetro: Estado (ABIERTA, EN_PROCESO, RESUELTA, CERRADA).
- Muestra solo incidencias con ese estado.
- Si no hay: `"No hay incidencias con estado [ESTADO]."`

---

### RF-04: Listar por prioridad
- Parámetro: Prioridad (BAJA, MEDIA, ALTA).
- Muestra solo incidencias con esa prioridad.
- Si no hay: `"No hay incidencias con prioridad [PRIORIDAD]."`

---

### RF-05: Buscar por ID
- Parámetro: ID (número entero).
- Muestra una incidencia específica con todos sus datos.
- Si no existe: `"Incidencia con ID [ID] no encontrada."`

---

### RF-06: Cambiar estado
- Parámetros: ID, nuevo estado.
- Validaciones: ID debe existir, estado válido, no cambiar si ya está CERRADA.
- Output: `"Estado actualizado. ID: [ID] | Nuevo estado: [ESTADO]"`

---

### RF-07: Cerrar incidencia
- Parámetro: ID.
- Cambia estado a CERRADA y registra fecha de cierre.
- Validación: no cerrar dos veces.
- Output: `"Incidencia cerrada. ID: [ID] | Fecha cierre: [FECHA]"`

---

## 3. Menú en Consola

```text
=== SISTEMA DE INCIDENCIAS - OEV UNMSM ===
1. Registrar incidencia
2. Listar todas las incidencias
3. Listar por estado
4. Listar por prioridad
5. Buscar por ID
6. Cambiar estado
7. Cerrar incidencia
8. Salir
Seleccione una opción:
```

- Validar opción entre 1 y 8.
- Repetir menú hasta que el usuario elija 8.
- Salida: `"Saliendo del sistema. ¡Hasta luego!"`

---

## 4. Estructura del Proyecto

```text
incidencias-poc/
├── pom.xml
├── src/main/java/pe/unmsm/oev/
│   ├── Main.java
│   ├── model/
│   │   ├── Incidencia.java
│   │   ├── Estado.java
│   │   └── Prioridad.java
│   └── service/
│       └── IncidenciaService.java
└── README.md
```

---

## 5. Clases Requeridas

### Estado.java
```java
public enum Estado {
    ABIERTA,
    EN_PROCESO,
    RESUELTA,
    CERRADA
}
```

---

### Prioridad.java
```java
public enum Prioridad {
    BAJA,
    MEDIA,
    ALTA
}
```

---

### Incidencia.java
**Atributos privados:**
- `id` (int)
- `titulo` (String)
- `descripcion` (String)
- `prioridad` (Prioridad)
- `estado` (Estado)
- `fechaCreacion` (LocalDateTime)
- `fechaCierre` (LocalDateTime, puede ser null)

**Métodos:**
- Constructor: `Incidencia(int id, String titulo, String descripcion, Prioridad prioridad)`.
- Getters para todos.
- Setters para `estado` y `fechaCierre`.
- `toString()` formateado.

---

### IncidenciaService.java
**Atributos:**
- `incidencias` (List<Incidencia>)
- `proximoId` (int)

**Métodos:**
- `crearIncidencia(titulo, descripcion, prioridad)` → Incidencia.
- `listarTodas()` → List<Incidencia>.
- `listarPorEstado(estado)` → List<Incidencia>.
- `listarPorPrioridad(prioridad)` → List<Incidencia>.
- `buscarPorId(id)` → Incidencia o null.
- `cambiarEstado(id, nuevoEstado)` → boolean.
- `cerrarIncidencia(id)` → boolean.

**Validaciones internas:**
- Título y descripción no vacíos.
- No cerrar dos veces.
- No cambiar estado de una incidencia cerrada.

---

### Main.java
**Responsabilidades:**
- `main()`: punto de entrada del programa.
- `mostrarMenu()`: imprime el menú en consola.
- Métodos para cada opción del menú.
- Leer entradas con `Scanner`.
- Mostrar resultados formateados.
- Mostrar errores claros.

**Métodos sugeridos:**
- `registrarIncidencia()`
- `listarTodas()`
- `listarPorEstado()`
- `listarPorPrioridad()`
- `buscarPorId()`
- `cambiarEstado()`
- `cerrarIncidencia()`

**Nota:** `Main.java` solo gestiona entrada/salida. Toda la lógica de negocio va en `IncidenciaService`.

---

## 6. Casos de Error Esperados

| Caso | Input | Output |
|------|-------|--------|
| Opción inválida | 9 | `"Opción inválida. Intente nuevamente."` |
| Título vacío | Enter | `"El título no puede estar vacío."` |
| Descripción vacía | Enter | `"La descripción no puede estar vacía."` |
| Prioridad inválida | URGENTE | `"Prioridad inválida. Válidas: BAJA, MEDIA, ALTA"` |
| ID no numérico | abc | `"ID inválido. Debe ser un número."` |
| ID no existe | 999 | `"Incidencia con ID 999 no encontrada."` |
| Cerrar cerrada | dos veces | `"Esta incidencia ya está cerrada."` |
| Cambiar cerrada | intento | `"Error: No se puede cambiar una incidencia CERRADA."` |

---

## 7. Criterios de Aceptación

1. Crear incidencia con validaciones.
2. Listar todas.
3. Filtrar por estado.
4. Filtrar por prioridad.
5. Buscar por ID.
6. Cambiar estado.
7. Cerrar incidencia.
8. No cerrar dos veces.
9. No cambiar cerrada.
10. Menú repetitivo hasta salir.
11. Mensajes de error claros.
12. Compila sin errores con `mvn clean compile`.
13. Se ejecuta con Maven.
14. Clases en paquetes correctos.
15. Separación clara de responsabilidades (no toda la lógica en `main`).

---


## 8. Entregables

1. Commits convencionales y graduales, subidos a repositorio GitHub.
