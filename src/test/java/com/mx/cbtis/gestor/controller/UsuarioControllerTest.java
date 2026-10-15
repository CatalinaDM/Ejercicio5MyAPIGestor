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

import com.mx.cbtis.gestor.modelo.Usuario;
import com.mx.cbtis.gestor.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioRepository repository;

    @Test
    void getUsuarioLoginDevuelveElUsuarioAutenticado() throws Exception {
        when(repository.findByCorreoPass("ana@example.com", "clave"))
                .thenReturn(Optional.of(new Usuario(5, "Ana", "ana@example.com", "clave", "Usuario", "foto.png")));

        mockMvc.perform(post("/getUsuarioLogin").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"correo_electronico\":\"ana@example.com\",\"contrasena\":\"clave\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Ana"));
    }

    @Test
    void buscarUsuarioDevuelveElEncontrado() throws Exception {
        when(repository.findById(5)).thenReturn(Optional.of(
                new Usuario(5, "Ana", "ana@example.com", "clave", "Usuario", "foto.png")));

        mockMvc.perform(get("/buscarUsuario").param("id", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correo_electronico").value("ana@example.com"));
    }

    @Test
    void agregarUsuarioGuardaElRegistro() throws Exception {
        mockMvc.perform(post("/addUsuario").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Ana\",\"correo_electronico\":\"ana@example.com\",\"contrasena\":\"clave\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(repository).save(any(Usuario.class));
    }

    @Test
    void actualizarUsuarioGuardaElRegistro() throws Exception {
        mockMvc.perform(post("/updateUsuario").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id_usuario\":5,\"nombre\":\"Ana actualizada\",\"correo_electronico\":\"ana@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(repository).save(any(Usuario.class));
    }

    @Test
    void eliminarUsuarioEliminaElRegistro() throws Exception {
        mockMvc.perform(delete("/deleteUsuario").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id_usuario\":5,\"nombre\":\"Ana\",\"correo_electronico\":\"ana@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(repository).delete(any(Usuario.class));
    }

    @Test
    void buscarUsuariosDevuelveLaLista() throws Exception {
        when(repository.findAll()).thenReturn(List.of(
                new Usuario(5, "Ana", "ana@example.com", "clave", "Usuario", "foto.png")));

        mockMvc.perform(post("/buscarUsuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_usuario").value(5));
    }
}