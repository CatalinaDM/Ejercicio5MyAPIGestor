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

import com.mx.cbtis.gestor.dto.ProyectoDto;
import com.mx.cbtis.gestor.modelo.Proyectos;
import com.mx.cbtis.gestor.repository.ProtectosRepository;

@RestController
public class ProyectosController {
	private final IntegridadService integridad;
	private final ProtectosRepository proyectostable;

	public ProyectosController(IntegridadService integridad, ProtectosRepository proyectostable) {
		this.integridad = integridad;
		this.proyectostable = proyectostable;
	}

	
	/*@PostMapping("/buscarProyecto")
	public Proyectos getProyecto(@RequestBody ProyectoDto proyecto) {
		
		Proyectos buscar = new Proyectos();
		Optional<Proyectos> proyectos = proyectostable.findById(proyecto.id_proyecto());
		if(proyectos.isPresent()) {
			buscar = proyectos.get();
		}
		
		return buscar;
	}*/
	
	@GetMapping("/buscarProyecto")
	public Proyectos getProyecto(@RequestParam int id_proyecto) {
		
		Proyectos buscar = new Proyectos();
		Optional<Proyectos> proyecto = proyectostable.findById(id_proyecto);
		if(proyecto.isPresent()) {
			buscar = proyecto.get();
		}
		
		return buscar;
	}
	
	@PostMapping("/addProyecto")
	public int addProyecto(@RequestBody ProyectoDto proyecto) {
		
		int flag = 0;
		if(proyecto != null) {
			
			proyectostable.save(proyecto.toEntity());
			flag = 1;
		}
		
		return flag;
	}
	
	@PostMapping("/updateProyecto")
	public int updateProyecto(@RequestBody ProyectoDto proyecto) {
		
		int flag = 0;
		if(proyecto != null) {
			proyectostable.save(proyecto.toEntity());
			flag = 1;
		}
		
		return flag;
	}
	
	@DeleteMapping("/deleteProyecto")
	public int deleteProyecto(@RequestBody ProyectoDto proyecto) {
		
		int flag = 0;
		if(proyecto != null) {
			integridad.eliminarProyecto(proyecto.id_proyecto());
			flag = 1;
		}
		
		return flag;
	}   
	
	@PostMapping("/buscarProyectos")
	public List<Proyectos> getProyectos(){
		
		List<Proyectos> listaProyectos = new ArrayList<>();
		proyectostable.findAll().forEach(listaProyectos :: add);
		return listaProyectos;
	}

}
