package com.mx.cbtis.gestor.dto;

import com.mx.cbtis.gestor.modelo.Usuario;

/**
 * Datos que el cliente envia en las peticiones de Usuario. Se usa en lugar de la entidad
 * persistente para que el cliente no pueda asignar campos internos (asignacion masiva, CWE-915).
 */
public record UsuarioDto(Integer id_usuario, String nombre, String correo_electronico, String contrasena, String rol, String imagen_usuario) {

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
