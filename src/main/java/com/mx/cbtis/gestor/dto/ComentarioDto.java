package com.mx.cbtis.gestor.dto;

import com.mx.cbtis.gestor.modelo.Comentarios;

/**
 * Datos que el cliente envia en las peticiones de Comentarios. Se usa en lugar de la entidad
 * persistente para que el cliente no pueda asignar campos internos (asignacion masiva, CWE-915).
 */
public record ComentarioDto(Integer id_comentario, String id_usuario, Integer id_tarea, String texto_comentario, String fecha_hora) {

	/** Convierte los datos recibidos en la entidad que se guarda en la base de datos. */
	public Comentarios toEntity() {
		Comentarios entidad = new Comentarios();
		entidad.setId_comentario(id_comentario);
		entidad.setId_usuario(id_usuario);
		entidad.setId_tarea(id_tarea);
		entidad.setTexto_comentario(texto_comentario);
		entidad.setFecha_hora(fecha_hora);
		return entidad;
	}
}
