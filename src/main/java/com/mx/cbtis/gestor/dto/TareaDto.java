package com.mx.cbtis.gestor.dto;

import com.mx.cbtis.gestor.modelo.Tareas;

/**
 * Datos que el cliente envia en las peticiones de Tareas. Se usa en lugar de la entidad
 * persistente para que el cliente no pueda asignar campos internos (asignacion masiva, CWE-915).
 */
public record TareaDto(Integer id_tarea, String nombre, String descripcion, String fecha_vencimiento, String lista, String estado, Integer id_proyecto) {

	/** Convierte los datos recibidos en la entidad que se guarda en la base de datos. */
	public Tareas toEntity() {
		Tareas entidad = new Tareas();
		entidad.setId_tarea(id_tarea);
		entidad.setNombre(nombre);
		entidad.setDescripcion(descripcion);
		entidad.setFecha_vencimiento(fecha_vencimiento);
		entidad.setLista(lista);
		entidad.setEstado(estado);
		entidad.setId_proyecto(id_proyecto);
		return entidad;
	}
}
