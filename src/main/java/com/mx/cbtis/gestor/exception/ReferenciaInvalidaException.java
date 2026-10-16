package com.mx.cbtis.gestor.exception;

/**
 * Se lanza cuando una operacion hace referencia a un registro que no existe
 * (proyecto, tarea o usuario). Se traduce a una respuesta HTTP 400 controlada.
 */
public class ReferenciaInvalidaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String campo;

	public ReferenciaInvalidaException(String campo, String mensaje) {
		super(mensaje);
		this.campo = campo;
	}

	public String getCampo() {
		return campo;
	}
}
