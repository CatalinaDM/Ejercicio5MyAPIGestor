package com.mx.cbtis.gestor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

/** Arranca la aplicacion por su metodo main (puerto aleatorio) para cubrir el punto de entrada. */
class Ejercicio5MyApiGestorMainTest {

	@Test
	void mainArrancaLaAplicacion() {
		assertDoesNotThrow(() -> Ejercicio5MyApiGestorApplication.main(new String[] { "--server.port=0" }));
	}
}
