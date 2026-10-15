package com.mx.cbtis.gestor.controller;

import static org.mockito.ArgumentMatchers.any;
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

import com.mx.cbtis.gestor.modelo.Tareas;
import com.mx.cbtis.gestor.repository.TareasRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TareasController.class)
class TareasControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TareasRepository repository;

    @Test
    void getTareaDevuelveTareasDelProyecto() throws Exception {
        when(repository.findByIdProyecto(7)).thenReturn(List.of(
                new Tareas(12, "Tarea A", "Descripcion", "2026-10-31", "Pendientes", "Abierta", 7)));

        mockMvc.perform(post("/getTarea").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id_proyecto\":7}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_tarea").value(12));
    }

    @Test
    void buscarTareaDevuelveLaEncontrada() throws Exception {
        when(repository.findById(12)).thenReturn(Optional.of(
                new Tareas(12, "Tarea A", "Descripcion", "2026-10-31", "Pendientes", "Abierta", 7)));

        mockMvc.perform(get("/buscarTarea").param("id_tarea", "12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Tarea A"));
    }

    @Test
    void agregarTareaGuardaElRegistro() throws Exception {
        mockMvc.perform(post("/addTareas").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Tarea A\",\"id_proyecto\":7,\"estado\":\"Abierta\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(repository).save(any(Tareas.class));
    }

    @Test
    void actualizarTareaGuardaElRegistro() throws Exception {
        mockMvc.perform(post("/updateTareas").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id_tarea\":12,\"nombre\":\"Tarea actualizada\",\"id_proyecto\":7}"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(repository).save(any(Tareas.class));
    }

    @Test
    void eliminarTareaEliminaElRegistro() throws Exception {
        mockMvc.perform(delete("/deleteTareas").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id_tarea\":12,\"nombre\":\"Tarea A\",\"id_proyecto\":7}"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(repository).delete(any(Tareas.class));
    }

    @Test
    void buscarTareasDevuelveLaLista() throws Exception {
        when(repository.findAll()).thenReturn(List.of(
                new Tareas(12, "Tarea A", "Descripcion", "2026-10-31", "Pendientes", "Abierta", 7)));

        mockMvc.perform(post("/buscarTareas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_tarea").value(12));
    }
}