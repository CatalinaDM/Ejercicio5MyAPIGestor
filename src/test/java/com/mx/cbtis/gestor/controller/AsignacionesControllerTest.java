package com.mx.cbtis.gestor.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import com.mx.cbtis.gestor.modelo.Asignaciones;
import com.mx.cbtis.gestor.exception.ReferenciaInvalidaException;
import com.mx.cbtis.gestor.service.IntegridadService;
import com.mx.cbtis.gestor.repository.AsignacionesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AsignacionesController.class)
class AsignacionesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AsignacionesRepository repository;

    @MockBean
    private IntegridadService integridad;

    @Test
    void buscarAsignacionDevuelveLaEncontrada() throws Exception {
        Asignaciones asignacion = new Asignaciones(4, 2, 8, "2026-10-06");
        when(repository.findById(4)).thenReturn(Optional.of(asignacion));

        mockMvc.perform(get("/buscarAsignacion").param("id", "4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_asignacion").value(4));
    }

    @Test
    void agregarAsignacionGuardaElRegistro() throws Exception {
        mockMvc.perform(post("/addAsignacion").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id_usuario\":2,\"id_tarea\":8,\"fecha_asignacion\":\"2026-10-06\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(repository).save(any(Asignaciones.class));
    }

    @Test
    void actualizarAsignacionGuardaElRegistro() throws Exception {
        mockMvc.perform(post("/updateAsignacion").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id_asignacion\":4,\"id_usuario\":2,\"id_tarea\":8,\"fecha_asignacion\":\"2026-10-06\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(repository).save(any(Asignaciones.class));
    }

    @Test
    void eliminarAsignacionEliminaElRegistro() throws Exception {
        mockMvc.perform(delete("/deleteAsignacion").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id_asignacion\":4,\"id_usuario\":2,\"id_tarea\":8}"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(repository).delete(any(Asignaciones.class));
    }

    @Test
    void buscarAsignacionesDevuelveLaLista() throws Exception {
        when(repository.findAll()).thenReturn(List.of(new Asignaciones(4, 2, 8, "2026-10-06")));

        mockMvc.perform(post("/buscarAsignaciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_asignacion").value(4));
    }

    @Test
    void agregarAsignacionConUsuarioInexistenteResponde400SinGuardar() throws Exception {
        doThrow(new ReferenciaInvalidaException("id_usuario", "El usuario 99999 no existe"))
                .when(integridad).validarUsuario(99999);

        mockMvc.perform(post("/addAsignacion").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id_usuario\":99999,\"id_tarea\":8,\"fecha_asignacion\":\"2026-10-06\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.campo").value("id_usuario"));

        verify(repository, never()).save(any(Asignaciones.class));
    }

    @Test
    void agregarAsignacionConTareaInexistenteResponde400SinGuardar() throws Exception {
        doThrow(new ReferenciaInvalidaException("id_tarea", "La tarea 99999 no existe"))
                .when(integridad).validarTarea(99999);

        mockMvc.perform(post("/addAsignacion").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id_usuario\":2,\"id_tarea\":99999,\"fecha_asignacion\":\"2026-10-06\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.campo").value("id_tarea"));

        verify(repository, never()).save(any(Asignaciones.class));
    }
}
