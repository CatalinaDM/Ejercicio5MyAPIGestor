package com.mx.cbtis.gestor.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/** Cada DTO guarda lo que recibe y lo copia completo a su entidad con toEntity(). */
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
        Object dto = dtoClase.getConstructor().newInstance();
        for (Method setter : dtoClase.getDeclaredMethods()) {
            if (setter.getName().startsWith("set") && Modifier.isPublic(setter.getModifiers())) {
                Object valor = valorDeEjemplo(setter.getParameterTypes()[0]);
                setter.invoke(dto, valor);
                assertEquals(valor, dtoClase.getMethod("get" + setter.getName().substring(3)).invoke(dto), setter.getName());
            }
        }
        Object entidad = dtoClase.getMethod("toEntity").invoke(dto);
        for (Method getter : entidad.getClass().getDeclaredMethods()) {
            if (getter.getName().startsWith("get") && Modifier.isPublic(getter.getModifiers())) {
                assertEquals(dtoClase.getMethod(getter.getName()).invoke(dto), getter.invoke(entidad), getter.getName());
            }
        }
    }
}
