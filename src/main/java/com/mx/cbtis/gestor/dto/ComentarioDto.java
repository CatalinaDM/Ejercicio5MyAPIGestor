package com.mx.cbtis.gestor.dto;

import com.mx.cbtis.gestor.modelo.Comentarios;

/**
 * Datos que el cliente envia en las peticiones de Comentarios. Se usa en lugar de la entidad
 * persistente para que el cliente no pueda asignar campos internos (asignacion masiva, CWE-915).
 */
public class ComentarioDto {

	private Integer id_comentario;
	private String id_usuario;
	private Integer id_tarea;
	private String texto_comentario;
	private String fecha_hora;

	public Integer getId_comentario() {
		return id_comentario;
	}

	public void setId_comentario(Integer id_comentario) {
		this.id_comentario = id_comentario;
	}

	public String getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}

	public Integer getId_tarea() {
		return id_tarea;
	}

	public void setId_tarea(Integer id_tarea) {
		this.id_tarea = id_tarea;
	}

	public String getTexto_comentario() {
		return texto_comentario;
	}

	public void setTexto_comentario(String texto_comentario) {
		this.texto_comentario = texto_comentario;
	}

	public String getFecha_hora() {
		return fecha_hora;
	}

	public void setFecha_hora(String fecha_hora) {
		this.fecha_hora = fecha_hora;
	}

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
