package com.mx.cbtis.gestor.dto;

import com.mx.cbtis.gestor.modelo.Tareas;

/**
 * Datos que el cliente envia en las peticiones de Tareas. Se usa en lugar de la entidad
 * persistente para que el cliente no pueda asignar campos internos (asignacion masiva, CWE-915).
 */
public class TareaDto {

	private Integer id_tarea;
	private String nombre;
	private String descripcion;
	private String fecha_vencimiento;
	private String lista;
	private String estado;
	private Integer id_proyecto;

	public Integer getId_tarea() {
		return id_tarea;
	}

	public void setId_tarea(Integer id_tarea) {
		this.id_tarea = id_tarea;
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

	public String getFecha_vencimiento() {
		return fecha_vencimiento;
	}

	public void setFecha_vencimiento(String fecha_vencimiento) {
		this.fecha_vencimiento = fecha_vencimiento;
	}

	public String getLista() {
		return lista;
	}

	public void setLista(String lista) {
		this.lista = lista;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getId_proyecto() {
		return id_proyecto;
	}

	public void setId_proyecto(Integer id_proyecto) {
		this.id_proyecto = id_proyecto;
	}

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
