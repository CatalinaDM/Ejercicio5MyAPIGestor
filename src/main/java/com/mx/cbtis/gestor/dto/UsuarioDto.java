package com.mx.cbtis.gestor.dto;

import com.mx.cbtis.gestor.modelo.Usuario;

/**
 * Datos que el cliente envia en las peticiones de Usuario. Se usa en lugar de la entidad
 * persistente para que el cliente no pueda asignar campos internos (asignacion masiva, CWE-915).
 */
public class UsuarioDto {

	private Integer id_usuario;
	private String nombre;
	private String correo_electronico;
	private String contrasena;
	private String rol;
	private String imagen_usuario;

	public Integer getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo_electronico() {
		return correo_electronico;
	}

	public void setCorreo_electronico(String correo_electronico) {
		this.correo_electronico = correo_electronico;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getImagen_usuario() {
		return imagen_usuario;
	}

	public void setImagen_usuario(String imagen_usuario) {
		this.imagen_usuario = imagen_usuario;
	}

	/** Convierte los datos recibidos en la entidad que se guarda en la base de datos. */
	public Usuario toEntity() {
		Usuario entidad = new Usuario();
		entidad.setId_usuario(id_usuario);
		entidad.setNombre(nombre);
		entidad.setCorreo_electronico(correo_electronico);
		entidad.setContrasena(contrasena);
		entidad.setRol(rol);
		entidad.setImagen_usuario(imagen_usuario);
		return entidad;
	}
}
