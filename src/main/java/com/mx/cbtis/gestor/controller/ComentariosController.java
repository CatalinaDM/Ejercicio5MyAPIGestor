package com.mx.cbtis.gestor.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mx.cbtis.gestor.service.IntegridadService;

import com.mx.cbtis.gestor.dto.ComentarioDto;
import com.mx.cbtis.gestor.modelo.Comentarios;
import com.mx.cbtis.gestor.repository.ComentariosRepository;

@RestController
public class ComentariosController {
	private final IntegridadService integridad;
	private final ComentariosRepository comentariostable;

	public ComentariosController(IntegridadService integridad, ComentariosRepository comentariostable) {
		this.integridad = integridad;
		this.comentariostable = comentariostable;
	}

	
	@PostMapping("/getComentario")

	public List<Comentarios> getComentario(@RequestBody ComentarioDto idComentario){
		List<Comentarios> listaComentarios = comentariostable.findByTarea(idComentario.id_tarea());
		return listaComentarios;
	}
	
	@GetMapping("/buscarComentario")
	public Comentarios getComentario(@RequestParam int id) {
		
		Comentarios buscar = new Comentarios();
		Optional<Comentarios> comentario = comentariostable.findById(id);
		if(comentario.isPresent()) {
			buscar = comentario.get();
		}
		
		return buscar;
	}
	
	@PostMapping("/addComentario")
	public int addComentario(@RequestBody ComentarioDto comentario) {
		
		int flag = 0;
		if(comentario != null) {
			integridad.validarTarea(comentario.id_tarea());
			integridad.validarUsuarioComentario(comentario.id_usuario());
			comentariostable.save(comentario.toEntity());
			flag = 1;
		}
		
		return flag;
	}
	
	@PostMapping("/updateComentario")
	public int updateComentario(@RequestBody ComentarioDto comentario) {
		
		int flag = 0;
		if(comentario != null) {
			integridad.validarTarea(comentario.id_tarea());
			integridad.validarUsuarioComentario(comentario.id_usuario());
			comentariostable.save(comentario.toEntity());
			flag = 1;
		}
		
		return flag;
	}
	
	@DeleteMapping("/deleteComentario")
	public int deleteComentario(@RequestBody ComentarioDto comentario) {
		
		int flag = 0;
		if(comentario != null) {
			comentariostable.delete(comentario.toEntity());
			flag = 1;
		}
		
		return flag;
	}
	
	@PostMapping("/buscarComentarios")
	public List<Comentarios> getComentarios(){
		
		List<Comentarios> listaComentarios = new ArrayList<>();
		comentariostable.findAll().forEach(listaComentarios :: add);
		return listaComentarios;
	}

}
