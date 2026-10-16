package com.mx.cbtis.gestor.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mx.cbtis.gestor.exception.ReferenciaInvalidaException;
import com.mx.cbtis.gestor.modelo.Asignaciones;
import com.mx.cbtis.gestor.modelo.Comentarios;
import com.mx.cbtis.gestor.modelo.Tareas;
import com.mx.cbtis.gestor.repository.AsignacionesRepository;
import com.mx.cbtis.gestor.repository.ComentariosRepository;
import com.mx.cbtis.gestor.repository.ProtectosRepository;
import com.mx.cbtis.gestor.repository.TareasRepository;
import com.mx.cbtis.gestor.repository.UsuarioRepository;

/** Pruebas de las reglas de integridad que corrigen las fallas F-01 a F-09. */
@ExtendWith(MockitoExtension.class)
class IntegridadServiceTest {

    @Mock ProtectosRepository proyectos;
    @Mock TareasRepository tareas;
    @Mock UsuarioRepository usuarios;
    @Mock ComentariosRepository comentarios;
    @Mock AsignacionesRepository asignaciones;
    @InjectMocks IntegridadService servicio;

    // ---------- F-02
    @Test
    void validarProyectoInexistenteLanzaExcepcion() {
        when(proyectos.existsById(99999)).thenReturn(false);
        ReferenciaInvalidaException ex = assertThrows(ReferenciaInvalidaException.class, () -> servicio.validarProyecto(99999));
        assertEquals("id_proyecto", ex.getCampo());
    }

    @Test
    void validarProyectoNuloLanzaExcepcion() {
        assertThrows(ReferenciaInvalidaException.class, () -> servicio.validarProyecto(null));
    }

    @Test
    void validarProyectoExistenteNoLanzaExcepcion() {
        when(proyectos.existsById(197)).thenReturn(true);
        assertDoesNotThrow(() -> servicio.validarProyecto(197));
    }

    // ---------- F-05 / F-08
    @Test
    void validarTareaInexistenteLanzaExcepcion() {
        when(tareas.existsById(99999)).thenReturn(false);
        ReferenciaInvalidaException ex = assertThrows(ReferenciaInvalidaException.class, () -> servicio.validarTarea(99999));
        assertEquals("id_tarea", ex.getCampo());
    }

    @Test
    void validarTareaExistenteNoLanzaExcepcion() {
        when(tareas.existsById(199)).thenReturn(true);
        assertDoesNotThrow(() -> servicio.validarTarea(199));
    }

    // ---------- F-07
    @Test
    void validarUsuarioInexistenteLanzaExcepcion() {
        when(usuarios.existsById(99999)).thenReturn(false);
        ReferenciaInvalidaException ex = assertThrows(ReferenciaInvalidaException.class, () -> servicio.validarUsuario(99999));
        assertEquals("id_usuario", ex.getCampo());
    }

    // ---------- F-04
    @Test
    void comentarioConNombreDeUsuarioExistenteEsValido() {
        when(usuarios.existsByNombre("Ana")).thenReturn(true);
        assertDoesNotThrow(() -> servicio.validarUsuarioComentario("Ana"));
    }

    @Test
    void comentarioConIdNumericoDeUsuarioExistenteEsValido() {
        when(usuarios.existsById(131)).thenReturn(true);
        assertDoesNotThrow(() -> servicio.validarUsuarioComentario("131"));
    }

    @Test
    void comentarioConUsuarioInexistenteLanzaExcepcion() {
        when(usuarios.existsByNombre("Usuario Fantasma")).thenReturn(false);
        assertThrows(ReferenciaInvalidaException.class, () -> servicio.validarUsuarioComentario("Usuario Fantasma"));
    }

    @Test
    void comentarioConUsuarioVacioONuloLanzaExcepcion() {
        assertThrows(ReferenciaInvalidaException.class, () -> servicio.validarUsuarioComentario("   "));
        assertThrows(ReferenciaInvalidaException.class, () -> servicio.validarUsuarioComentario(null));
        verify(usuarios, never()).existsByNombre("   ");
    }

    // ---------- F-06 / F-09
    @Test
    void eliminarTareaEliminaPrimeroComentariosYAsignaciones() {
        List<Comentarios> cs = List.of(new Comentarios(1, "Ana", 12, "hola", "26/10/16 10:00"));
        List<Asignaciones> as = List.of(new Asignaciones(2, 131, 12, "2026-10-16"));
        when(comentarios.findByTarea(12)).thenReturn(cs);
        when(asignaciones.findByTarea(12)).thenReturn(as);
        when(tareas.existsById(12)).thenReturn(true);

        servicio.eliminarTarea(12);

        InOrder orden = inOrder(comentarios, asignaciones, tareas);
        orden.verify(comentarios).deleteAll(cs);
        orden.verify(asignaciones).deleteAll(as);
        orden.verify(tareas).deleteById(12);
    }

    @Test
    void eliminarTareaInexistenteNoFallaNiBorraNada() {
        when(tareas.existsById(99999)).thenReturn(false);
        assertDoesNotThrow(() -> servicio.eliminarTarea(99999));
        verify(tareas, never()).deleteById(99999);
    }

    // ---------- F-01
    @Test
    void eliminarProyectoEliminaEnCascadaSusTareasYDependientes() {
        Tareas t1 = new Tareas(10, "A", "d", "2026-10-31", "pendientes", "danger", 7);
        Tareas t2 = new Tareas(11, "B", "d", "2026-10-31", "encurso", "danger", 7);
        when(tareas.findByIdProyecto(7)).thenReturn(List.of(t1, t2));
        when(comentarios.findByTarea(10)).thenReturn(List.of());
        when(comentarios.findByTarea(11)).thenReturn(List.of());
        when(asignaciones.findByTarea(10)).thenReturn(List.of());
        when(asignaciones.findByTarea(11)).thenReturn(List.of());
        when(tareas.existsById(10)).thenReturn(true);
        when(tareas.existsById(11)).thenReturn(true);
        when(proyectos.existsById(7)).thenReturn(true);

        servicio.eliminarProyecto(7);

        InOrder orden = inOrder(tareas, proyectos);
        orden.verify(tareas).deleteById(10);
        orden.verify(tareas).deleteById(11);
        orden.verify(proyectos).deleteById(7);
    }
}
