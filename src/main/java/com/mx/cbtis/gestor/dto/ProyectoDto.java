package com.mx.cbtis.gestor.dto;

import com.mx.cbtis.gestor.modelo.Proyectos;

/**
 * Datos que el cliente envia en las peticiones de Proyectos. Se usa en lugar de la entidad
 * persistente para que el cliente no pueda asignar campos internos (asignacion masiva, CWE-915).
 */
public class ProyectoDto {

	private Integer id_proyecto;
	private String nombre;
	private String descripcion;
	private String fecha_inicio;
	private String fecha_finalizacion;
	private String estado_proyecto;
	private String fondo;

	public Integer getId_proyecto() {
		return id_proyecto;
	}

	public void setId_proyecto(Integer id_proyecto) {
		this.id_proyecto = id_proyecto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(String fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public String getFecha_finalizacion() {
		return fecha_finalizacion;
	}

	public void setFecha_finalizacion(String fecha_finalizacion) {
		this.fecha_finalizacion = fecha_finalizacion;
	}

	public String getEstado_proyecto() {
		return estado_proyecto;
	}

	public void setEstado_proyecto(String estado_proyecto) {
		this.estado_proyecto = estado_proyecto;
	}

	public String getFondo() {
		return fondo;
	}

	public void setFondo(String fondo) {
		this.fondo = fondo;
	}

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
