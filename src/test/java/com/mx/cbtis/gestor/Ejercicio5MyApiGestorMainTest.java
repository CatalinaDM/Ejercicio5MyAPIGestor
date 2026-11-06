package com.mx.cbtis.gestor;

import org.junit.jupiter.api.Test;

/** Arranca la aplicacion por su metodo main (puerto aleatorio) para cubrir el punto de entrada. */
class Ejercicio5MyApiGestorMainTest {

	@Test
	void mainArrancaLaAplicacion() {
		Ejercicio5MyApiGestorApplication.main(new String[] { "--server.port=0" });
	}
}
