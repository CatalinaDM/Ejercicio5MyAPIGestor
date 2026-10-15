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

import com.mx.cbtis.gestor.modelo.Proyectos;
import com.mx.cbtis.gestor.repository.ProtectosRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProyectosController.class)
class ProyectosControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProtectosRepository repository;

    @Test
    void buscarProyectoDevuelveElEncontrado() throws Exception {
        when(repository.findById(7)).thenReturn(Optional.of(
                new Proyectos(7, "Proyecto A", "Descripcion", "2026-10-01", "2026-10-31", "Activo", "azul")));

        mockMvc.perform(get("/buscarProyecto").param("id_proyecto", "7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Proyecto A"));
    }

    @Test
    void agregarProyectoGuardaElRegistro() throws Exception {
        mockMvc.perform(post("/addProyecto").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Proyecto A\",\"descripcion\":\"Descripcion\",\"estado_proyecto\":\"Activo\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(repository).save(any(Proyectos.class));
    }

    @Test
    void actualizarProyectoGuardaElRegistro() throws Exception {
        mockMvc.perform(post("/updateProyecto").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id_proyecto\":7,\"nombre\":\"Proyecto actualizado\",\"estado_proyecto\":\"Activo\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(repository).save(any(Proyectos.class));
    }

    @Test
    void eliminarProyectoEliminaElRegistro() throws Exception {
        mockMvc.perform(delete("/deleteProyecto").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id_proyecto\":7,\"nombre\":\"Proyecto A\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(repository).delete(any(Proyectos.class));
    }

    @Test
    void buscarProyectosDevuelveLaLista() throws Exception {
        when(repository.findAll()).thenReturn(List.of(
                new Proyectos(7, "Proyecto A", "Descripcion", "2026-10-01", "2026-10-31", "Activo", "azul")));

        mockMvc.perform(post("/buscarProyectos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_proyecto").value(7));
    }
}