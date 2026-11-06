package com.mx.cbtis.gestor.dto;

import com.mx.cbtis.gestor.modelo.Asignaciones;

/**
 * Datos que el cliente envia en las peticiones de Asignaciones. Se usa en lugar de la entidad
 * persistente para que el cliente no pueda asignar campos internos (asignacion masiva, CWE-915).
 */
public class AsignacionDto {

	private Integer id_asignacion;
	private Integer id_usuario;
	private Integer id_tarea;
	private String fecha_asignacion;

	public Integer getId_asignacion() {
		return id_asignacion;
	}

	public void setId_asignacion(Integer id_asignacion) {
		this.id_asignacion = id_asignacion;
	}

	public Integer getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
	}

	public Integer getId_tarea() {
		return id_tarea;
	}

	public void setId_tarea(Integer id_tarea) {
		this.id_tarea = id_tarea;
	}

	public String getFecha_asignacion() {
		return fecha_asignacion;
	}

	public void setFecha_asignacion(String fecha_asignacion) {
		this.fecha_asignacion = fecha_asignacion;
	}

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
