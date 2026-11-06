package com.mx.cbtis.gestor.dto;

import com.mx.cbtis.gestor.modelo.Proyectos;

/**
 * Datos que el cliente envia en las peticiones de Proyectos. Se usa en lugar de la entidad
 * persistente para que el cliente no pueda asignar campos internos (asignacion masiva, CWE-915).
 */
public record ProyectoDto(Integer id_proyecto, String nombre, String descripcion, String fecha_inicio, String fecha_finalizacion, String estado_proyecto, String fondo) {

	/** Convierte los datos recibidos en la entidad que se guarda en la base de datos. */
	public Proyectos toEntity() {
		Proyectos entidad = new Proyectos();
		entidad.setId_proyecto(id_proyecto);
		entidad.setNombre(nombre);
		entidad.setDescripcion(descripcion);
		entidad.setFecha_inicio(fecha_inicio);
		entidad.setFecha_finalizacion(fecha_finalizacion);
		entidad.setEstado_proyecto(estado_proyecto);
		entidad.setFondo(fondo);
		return entidad;
	}
}
