package com.mx.cbtis.gestor.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.RecordComponent;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/** Cada DTO conserva lo que recibe y lo copia completo a su entidad con toEntity(). */
class DtoPojoTest {

    static Stream<Class<?>> dtos() {
        return Stream.of(AsignacionDto.class, ComentarioDto.class, ProyectoDto.class, TareaDto.class, UsuarioDto.class);
    }

    private static Object valorDeEjemplo(Class<?> tipo) {
        return tipo == String.class ? "valor" : (Object) 7;
    }

    @ParameterizedTest
    @MethodSource("dtos")
    void toEntityCopiaTodosLosCampos(Class<?> dtoClase) throws Exception {
        RecordComponent[] componentes = dtoClase.getRecordComponents();
        Class<?>[] tipos = new Class<?>[componentes.length];
        Object[] valores = new Object[componentes.length];
        for (int i = 0; i < componentes.length; i++) {
            tipos[i] = componentes[i].getType();
            valores[i] = valorDeEjemplo(tipos[i]);
        }
        Object dto = dtoClase.getDeclaredConstructor(tipos).newInstance(valores);
        for (int i = 0; i < componentes.length; i++) {
            assertEquals(valores[i], componentes[i].getAccessor().invoke(dto), componentes[i].getName());
        }

        Object entidad = dtoClase.getMethod("toEntity").invoke(dto);
        int comparados = 0;
        for (Method getter : entidad.getClass().getDeclaredMethods()) {
            if (getter.getName().startsWith("get") && Modifier.isPublic(getter.getModifiers())) {
                String campo = Character.toLowerCase(getter.getName().charAt(3)) + getter.getName().substring(4);
                assertEquals(dtoClase.getMethod(campo).invoke(dto), getter.invoke(entidad), campo);
                comparados++;
            }
        }
        assertEquals(componentes.length, comparados);
    }
}
