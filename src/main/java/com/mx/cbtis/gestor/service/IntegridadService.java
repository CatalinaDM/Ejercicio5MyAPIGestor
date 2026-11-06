package com.mx.cbtis.gestor.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mx.cbtis.gestor.exception.ReferenciaInvalidaException;
import com.mx.cbtis.gestor.modelo.Tareas;
import com.mx.cbtis.gestor.repository.AsignacionesRepository;
import com.mx.cbtis.gestor.repository.ComentariosRepository;
import com.mx.cbtis.gestor.repository.ProtectosRepository;
import com.mx.cbtis.gestor.repository.TareasRepository;
import com.mx.cbtis.gestor.repository.UsuarioRepository;

/**
 * Reglas de integridad referencial a nivel de aplicacion (la base de datos original no
 * define llaves foraneas): valida que los registros referenciados existan y elimina en
 * cascada los registros dependientes (proyecto -> tareas -> comentarios y asignaciones).
 */
@Service
public class IntegridadService {
	private final ProtectosRepository proyectostable;
	private final TareasRepository tareastable;
	private final UsuarioRepository usuariotable;
	private final ComentariosRepository comentariostable;
	private final AsignacionesRepository asignacionestable;

	public IntegridadService(ProtectosRepository proyectostable, TareasRepository tareastable, UsuarioRepository usuariotable, ComentariosRepository comentariostable, AsignacionesRepository asignacionestable) {
		this.proyectostable = proyectostable;
		this.tareastable = tareastable;
		this.usuariotable = usuariotable;
		this.comentariostable = comentariostable;
		this.asignacionestable = asignacionestable;
	}

	public void validarProyecto(Integer idProyecto) {
		if (idProyecto == null || !proyectostable.existsById(idProyecto)) {
			throw new ReferenciaInvalidaException("id_proyecto", "El proyecto " + idProyecto + " no existe");
		}
	}

	public void validarTarea(Integer idTarea) {
		if (idTarea == null || !tareastable.existsById(idTarea)) {
			throw new ReferenciaInvalidaException("id_tarea", "La tarea " + idTarea + " no existe");
		}
	}

	public void validarUsuario(Integer idUsuario) {
		if (idUsuario == null || !usuariotable.existsById(idUsuario)) {
			throw new ReferenciaInvalidaException("id_usuario", "El usuario " + idUsuario + " no existe");
		}
	}

	/**
	 * comentarios.id_usuario es VARCHAR y hoy guarda el nombre del usuario (ver F-03):
	 * se acepta un nombre existente o, para la futura migracion, un id numerico existente.
	 */
	public void validarUsuarioComentario(String idUsuario) {
		boolean existe = false;
		if (idUsuario != null && !idUsuario.trim().isEmpty()) {
			String valor = idUsuario.trim();
			if (valor.matches("\\d{1,9}")) {
				existe = usuariotable.existsById(Integer.valueOf(valor));
			}
			existe = existe || usuariotable.existsByNombre(valor);
		}
		if (!existe) {
			throw new ReferenciaInvalidaException("id_usuario", "El usuario '" + idUsuario + "' no existe");
		}
	}

	@Transactional
	public void eliminarTarea(Integer idTarea) {
		if (idTarea == null) {
			return;
		}
		comentariostable.deleteAll(comentariostable.findByTarea(idTarea));
		asignacionestable.deleteAll(asignacionestable.findByTarea(idTarea));
		if (tareastable.existsById(idTarea)) {
			tareastable.deleteById(idTarea);
		}
	}

	@Transactional
	public void eliminarProyecto(Integer idProyecto) {
		if (idProyecto == null) {
			return;
		}
		for (Tareas tarea : tareastable.findByIdProyecto(idProyecto)) {
			eliminarTarea(tarea.getId_tarea());
		}
		if (proyectostable.existsById(idProyecto)) {
			proyectostable.deleteById(idProyecto);
		}
	}
}
