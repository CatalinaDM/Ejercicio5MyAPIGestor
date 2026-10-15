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

import com.mx.cbtis.gestor.modelo.Comentarios;
import com.mx.cbtis.gestor.repository.ComentariosRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ComentariosController.class)
class ComentariosControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComentariosRepository repository;

    @Test
    void getComentarioDevuelveComentariosDeLaTarea() throws Exception {
        when(repository.findByTarea(8)).thenReturn(List.of(
                new Comentarios(3, "ana", 8, "Avance realizado", "2026-10-06 10:00:00")));

        mockMvc.perform(post("/getComentario").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id_tarea\":8}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_comentario").value(3));
    }

    @Test
    void buscarComentarioDevuelveElEncontrado() throws Exception {
        when(repository.findById(3)).thenReturn(Optional.of(
                new Comentarios(3, "ana", 8, "Avance realizado", "2026-10-06 10:00:00")));

        mockMvc.perform(get("/buscarComentario").param("id", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.texto_comentario").value("Avance realizado"));
    }

    @Test
    void agregarComentarioGuardaElRegistro() throws Exception {
        mockMvc.perform(post("/addComentario").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id_usuario\":\"ana\",\"id_tarea\":8,\"texto_comentario\":\"Nuevo\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(repository).save(any(Comentarios.class));
    }

    @Test
    void actualizarComentarioGuardaElRegistro() throws Exception {
        mockMvc.perform(post("/updateComentario").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id_comentario\":3,\"id_usuario\":\"ana\",\"id_tarea\":8,\"texto_comentario\":\"Editado\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(repository).save(any(Comentarios.class));
    }

    @Test
    void eliminarComentarioEliminaElRegistro() throws Exception {
        mockMvc.perform(delete("/deleteComentario").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id_comentario\":3,\"id_usuario\":\"ana\",\"id_tarea\":8}"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(repository).delete(any(Comentarios.class));
    }

    @Test
    void buscarComentariosDevuelveLaLista() throws Exception {
        when(repository.findAll()).thenReturn(List.of(
                new Comentarios(3, "ana", 8, "Avance realizado", "2026-10-06 10:00:00")));

        mockMvc.perform(post("/buscarComentarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_comentario").value(3));
    }
}