package com.mx.cbtis.gestor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mx.cbtis.gestor.modelo.Asignaciones;

public interface AsignacionesRepository extends CrudRepository<Asignaciones,Integer>{
	
	@Query(value = "select id_asignacion, id_usuario, id_tarea, fecha_asignacion "
			+ "from asignaciones where id_tarea = :id_tarea", nativeQuery = true)
	List<Asignaciones> findByTarea(@Param("id_tarea") Integer id_tarea);
}
