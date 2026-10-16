package com.mx.cbtis.gestor.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ManejadorErrores {

	@ExceptionHandler(ReferenciaInvalidaException.class)
	public ResponseEntity<Map<String, Object>> referenciaInvalida(ReferenciaInvalidaException ex) {
		Map<String, Object> cuerpo = new LinkedHashMap<>();
		cuerpo.put("codigo", "REF-INVALIDA");
		cuerpo.put("campo", ex.getCampo());
		cuerpo.put("mensaje", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cuerpo);
	}
}
