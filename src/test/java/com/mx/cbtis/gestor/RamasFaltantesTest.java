package com.mx.cbtis.gestor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.mx.cbtis.gestor.controller.AsignacionesController;
import com.mx.cbtis.gestor.controller.ComentariosController;
import com.mx.cbtis.gestor.controller.ProyectosController;
import com.mx.cbtis.gestor.controller.TareasController;
import com.mx.cbtis.gestor.controller.UsuarioController;
import com.mx.cbtis.gestor.exception.ReferenciaInvalidaException;
import com.mx.cbtis.gestor.dto.UsuarioDto;
import com.mx.cbtis.gestor.repository.AsignacionesRepository;
import com.mx.cbtis.gestor.repository.ComentariosRepository;
import com.mx.cbtis.gestor.repository.ProtectosRepository;
import com.mx.cbtis.gestor.repository.TareasRepository;
import com.mx.cbtis.gestor.repository.UsuarioRepository;
import com.mx.cbtis.gestor.service.IntegridadService;

/** Ramas que faltaban: busquedas sin resultado, login fallido y referencias nulas o inexistentes. */
class RamasFaltantesTest {

	private final IntegridadService integridad = mock(IntegridadService.class);

	@Test
	void busquedasSinResultadoDevuelvenObjetoVacio() {
		AsignacionesRepository asig = mock(AsignacionesRepository.class);
		ComentariosRepository com = mock(ComentariosRepository.class);
		ProtectosRepository pro = mock(ProtectosRepository.class);
		TareasRepository tar = mock(TareasRepository.class);
		UsuarioRepository usu = mock(UsuarioRepository.class);
		when(asig.findById(1)).thenReturn(Optional.empty());
		when(com.findById(1)).thenReturn(Optional.empty());
		when(pro.findById(1)).thenReturn(Optional.empty());
		when(tar.findById(1)).thenReturn(Optional.empty());
		when(usu.findById(1)).thenReturn(Optional.empty());

		assertNull(new AsignacionesController(integridad, asig).getAsignacion(1).getId_asignacion());
		assertNull(new ComentariosController(integridad, com).getComentario(1).getId_comentario());
		assertNull(new ProyectosController(integridad, pro).getProyecto(1).getId_proyecto());
		assertNull(new TareasController(integridad, tar).getTarea(1).getId_tarea());
		assertNull(new UsuarioController(usu).getUsuario(1).getId_usuario());
	}

	@Test
	void loginConCredencialesIncorrectasDevuelveMarcador000() {
		UsuarioRepository usu = mock(UsuarioRepository.class);
		when(usu.findByCorreoPass("a@b.c", "mala")).thenReturn(Optional.empty());
		UsuarioDto param = new UsuarioDto();
		param.setCorreo_electronico("a@b.c");
		param.setContrasena("mala");

		assertEquals("000", new UsuarioController(usu).getUsuarioLogin(param).getNombre());
	}

	@Test
	void validacionesRechazanNulosYInexistentes() {
		ProtectosRepository pro = mock(ProtectosRepository.class);
		TareasRepository tar = mock(TareasRepository.class);
		UsuarioRepository usu = mock(UsuarioRepository.class);
		IntegridadService s = new IntegridadService(pro, tar, usu, mock(ComentariosRepository.class),
				mock(AsignacionesRepository.class));
		when(tar.existsById(2)).thenReturn(false);
		when(usu.existsById(3)).thenReturn(false);
		when(usu.existsById(4)).thenReturn(true);

		assertThrows(ReferenciaInvalidaException.class, () -> s.validarTarea(null));
		assertThrows(ReferenciaInvalidaException.class, () -> s.validarTarea(2));
		assertThrows(ReferenciaInvalidaException.class, () -> s.validarUsuario(null));
		assertThrows(ReferenciaInvalidaException.class, () -> s.validarUsuario(3));
		assertDoesNotThrow(() -> s.validarUsuario(4));
	}

	@Test
	void eliminarConIdNuloOInexistenteNoHaceNada() {
		ProtectosRepository pro = mock(ProtectosRepository.class);
		TareasRepository tar = mock(TareasRepository.class);
		IntegridadService s = new IntegridadService(pro, tar, mock(UsuarioRepository.class),
				mock(ComentariosRepository.class), mock(AsignacionesRepository.class));
		when(pro.existsById(9)).thenReturn(false);
		when(tar.findByIdProyecto(9)).thenReturn(java.util.List.of());

		s.eliminarTarea(null);
		s.eliminarProyecto(null);
		verifyNoInteractions(tar);
		verifyNoInteractions(pro);

		s.eliminarProyecto(9);
		verify(pro, never()).deleteById(9);

		when(pro.existsById(8)).thenReturn(true);
		when(tar.findByIdProyecto(8)).thenReturn(java.util.List.of());
		s.eliminarProyecto(8);
		verify(pro).deleteById(8);
	}
}
