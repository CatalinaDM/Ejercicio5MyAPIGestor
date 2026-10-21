# Ejercicio5MyAPIGestor

API REST para la gestion de proyectos, construida con Java, Spring Boot y JPA.

## Requisitos

- JDK 11 o compatible.
- Maven 3.9.9, incluido mediante Maven Wrapper.
- MariaDB para la persistencia de datos.

## Ejecutar

Configura la conexion a la base de datos en `src/main/resources/application.properties`.
En Windows, desde esta carpeta, inicia la API con:

```powershell
.\mvnw.cmd spring-boot:run
```

La version de Java objetivo se configura como `11` en `pom.xml`.
