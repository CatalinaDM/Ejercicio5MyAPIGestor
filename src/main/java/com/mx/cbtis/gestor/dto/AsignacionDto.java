package com.mx.cbtis.gestor.dto;

import com.mx.cbtis.gestor.modelo.Asignaciones;

/**
 * Datos que el cliente envia en las peticiones de Asignaciones. Se usa en lugar de la entidad
 * persistente para que el cliente no pueda asignar campos internos (asignacion masiva, CWE-915).
 */
public record AsignacionDto(Integer id_asignacion, Integer id_usuario, Integer id_tarea, String fecha_asignacion) {

	/** Convierte los datos recibidos en la entidad que se guarda en la base de datos. */
	public Asignaciones toEntity() {
		Asignaciones entidad = new Asignaciones();
		entidad.setId_asignacion(id_asignacion);
		entidad.setId_usuario(id_usuario);
		entidad.setId_tarea(id_tarea);
		entidad.setFecha_asignacion(fecha_asignacion);
		return entidad;
	}
}
