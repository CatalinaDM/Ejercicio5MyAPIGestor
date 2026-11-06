package com.mx.cbtis.gestor.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.Test;

import com.mx.cbtis.gestor.repository.AsignacionesRepository;
import com.mx.cbtis.gestor.repository.ComentariosRepository;
import com.mx.cbtis.gestor.repository.ProtectosRepository;
import com.mx.cbtis.gestor.repository.TareasRepository;
import com.mx.cbtis.gestor.repository.UsuarioRepository;
import com.mx.cbtis.gestor.service.IntegridadService;

/** Si el cuerpo recibido es nulo, los controladores no guardan, actualizan ni borran nada y devuelven 0. */
class CuerpoNuloControllersTest {

	private final IntegridadService integridad = mock(IntegridadService.class);

	@Test
	void asignacionesConCuerpoNuloDevuelveCero() {
		AsignacionesRepository repo = mock(AsignacionesRepository.class);
		AsignacionesController c = new AsignacionesController(integridad, repo);
		assertEquals(0, c.addAsignacion(null));
		assertEquals(0, c.updateAsignacion(null));
		assertEquals(0, c.deleteAsignacion(null));
		verifyNoInteractions(repo, integridad);
	}

	@Test
	void comentariosConCuerpoNuloDevuelveCero() {
		ComentariosRepository repo = mock(ComentariosRepository.class);
		ComentariosController c = new ComentariosController(integridad, repo);
		assertEquals(0, c.addComentario(null));
		assertEquals(0, c.updateComentario(null));
		assertEquals(0, c.deleteComentario(null));
		verifyNoInteractions(repo, integridad);
	}

	@Test
	void proyectosConCuerpoNuloDevuelveCero() {
		ProtectosRepository repo = mock(ProtectosRepository.class);
		ProyectosController c = new ProyectosController(integridad, repo);
		assertEquals(0, c.addProyecto(null));
		assertEquals(0, c.updateProyecto(null));
		assertEquals(0, c.deleteProyecto(null));
		verifyNoInteractions(repo, integridad);
	}

	@Test
	void tareasConCuerpoNuloDevuelveCero() {
		TareasRepository repo = mock(TareasRepository.class);
		TareasController c = new TareasController(integridad, repo);
		assertEquals(0, c.addTareas(null));
		assertEquals(0, c.updateTareas(null));
		assertEquals(0, c.deleteTareas(null));
		verifyNoInteractions(repo, integridad);
	}

	@Test
	void usuariosConCuerpoNuloDevuelveCero() {
		UsuarioRepository repo = mock(UsuarioRepository.class);
		UsuarioController c = new UsuarioController(repo);
		assertEquals(0, c.addUsuario(null));
		assertEquals(0, c.updateUsuario(null));
		assertEquals(0, c.deleteUsuario(null));
		verifyNoInteractions(repo);
	}
}
