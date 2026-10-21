# Acta de Evidencia de Pruebas

> Plantilla para registrar resultados reales. No marques un caso como aprobado ni reportes cobertura hasta ejecutar la prueba correspondiente.

## 1. Informacion general

| Campo | Valor |
|---|---|
| Proyecto | Administrador de Proyectos |
| Modulo / microservicio | Ejercicio5MyAPIGestor (API REST) |
| Sprint / iteracion | Pendiente de especificar |
| Responsable | Pendiente de especificar |
| Fecha de ejecucion | 06/10/2026 |
| Rama / commit | Pendiente de registrar |
| JDK | 21.0.10 (ambiente verificado) |

## 2. Resumen de resultados

Completar despues de ejecutar las pruebas. JUnit y Postman se reportan por separado porque validan niveles distintos.

| Tipo | Ejecutados | Aprobados | Fallidos | Pendientes | Cobertura |
|---|---:|---:|---:|---:|---|
| Controladores MVC (JUnit / MockMvc) | 28 | 28 | 0 | 0 | No medida |
| API / integracion Postman | 0 | 0 | 0 | 28 endpoints por validar | No aplica |

**Resultado general:** APROBADO para los 28 casos MVC ejecutados. Las pruebas Postman y la cobertura siguen pendientes.

**Alcance:** los 28 casos MVC envian solicitudes a los controladores con `MockMvc` y simulan los repositorios con Mockito; no acceden a MariaDB. No son pruebas Postman ni una prueba de integracion con la base de datos. Los proyectos tambien contienen un test `contextLoads()` cada uno, que comprueba el arranque del contexto.

**Comando ejecutado:** `PowerShell: .\mvnw.cmd "-Dtest=AsignacionesControllerTest,ComentariosControllerTest,ProyectosControllerTest,TareasControllerTest,UsuarioControllerTest" test` (desde `Ejercicio5MyAPIGestor`).

## 3. Casos de prueba unitaria (JUnit)

Registrar una fila por cada comportamiento probado. Para pruebas de API con Postman, usar la seccion siguiente.

| ID | Clase / metodo | Escenario probado | Resultado obtenido | Estado |
|---|---|---|---|---|
| UT-01 | `GET /buscarAsignacion` | Devuelve la asignacion simulada por ID | HTTP 200; ID esperado | Pasa |
| UT-02 | `POST /addAsignacion` | Guarda asignacion | HTTP 200; respuesta `1`; se invoca `save` | Pasa |
| UT-03 | `POST /updateAsignacion` | Actualiza asignacion | HTTP 200; respuesta `1`; se invoca `save` | Pasa |
| UT-04 | `DELETE /deleteAsignacion` | Elimina asignacion | HTTP 200; respuesta `1`; se invoca `delete` | Pasa |
| UT-05 | `POST /buscarAsignaciones` | Devuelve la lista simulada | HTTP 200; ID esperado | Pasa |
| UT-06 | `POST /getComentario` | Busca comentarios por tarea | HTTP 200; comentario esperado | Pasa |
| UT-07 | `GET /buscarComentario` | Devuelve comentario simulado por ID | HTTP 200; texto esperado | Pasa |
| UT-08 | `POST /addComentario` | Guarda comentario | HTTP 200; respuesta `1`; se invoca `save` | Pasa |
| UT-09 | `POST /updateComentario` | Actualiza comentario | HTTP 200; respuesta `1`; se invoca `save` | Pasa |
| UT-10 | `DELETE /deleteComentario` | Elimina comentario | HTTP 200; respuesta `1`; se invoca `delete` | Pasa |
| UT-11 | `POST /buscarComentarios` | Devuelve la lista simulada | HTTP 200; ID esperado | Pasa |
| UT-12 | `GET /buscarProyecto` | Devuelve proyecto simulado por ID | HTTP 200; nombre esperado | Pasa |
| UT-13 | `POST /addProyecto` | Guarda proyecto | HTTP 200; respuesta `1`; se invoca `save` | Pasa |
| UT-14 | `POST /updateProyecto` | Actualiza proyecto | HTTP 200; respuesta `1`; se invoca `save` | Pasa |
| UT-15 | `DELETE /deleteProyecto` | Elimina proyecto | HTTP 200; respuesta `1`; se invoca `delete` | Pasa |
| UT-16 | `POST /buscarProyectos` | Devuelve la lista simulada | HTTP 200; ID esperado | Pasa |
| UT-17 | `POST /getTarea` | Busca tareas por proyecto | HTTP 200; tarea esperada | Pasa |
| UT-18 | `GET /buscarTarea` | Devuelve tarea simulada por ID | HTTP 200; nombre esperado | Pasa |
| UT-19 | `POST /addTareas` | Guarda tarea | HTTP 200; respuesta `1`; se invoca `save` | Pasa |
| UT-20 | `POST /updateTareas` | Actualiza tarea | HTTP 200; respuesta `1`; se invoca `save` | Pasa |
| UT-21 | `DELETE /deleteTareas` | Elimina tarea | HTTP 200; respuesta `1`; se invoca `delete` | Pasa |
| UT-22 | `POST /buscarTareas` | Devuelve la lista simulada | HTTP 200; ID esperado | Pasa |
| UT-23 | `POST /getUsuarioLogin` | Devuelve usuario autenticado simulado | HTTP 200; nombre esperado | Pasa |
| UT-24 | `GET /buscarUsuario` | Devuelve usuario simulado por ID | HTTP 200; correo esperado | Pasa |
| UT-25 | `POST /addUsuario` | Guarda usuario | HTTP 200; respuesta `1`; se invoca `save` | Pasa |
| UT-26 | `POST /updateUsuario` | Actualiza usuario | HTTP 200; respuesta `1`; se invoca `save` | Pasa |
| UT-27 | `DELETE /deleteUsuario` | Elimina usuario | HTTP 200; respuesta `1`; se invoca `delete` | Pasa |
| UT-28 | `POST /buscarUsuarios` | Devuelve la lista simulada | HTTP 200; ID esperado | Pasa |

## 4. Casos de API (Postman)

La API tiene 28 rutas activas. Esta lista es una guia de cobertura de endpoints, no significa que ya se hayan ejecutado 28 pruebas. Anadir casos negativos cuando sean pertinentes (por ejemplo, ID inexistente o credenciales invalidas).

| ID | Controlador | Metodo | Endpoint | Escenario / datos | Estado |
|---|---|---|---|---|---|
| API-01 | Asignaciones | GET | `/buscarAsignacion` | Consultar asignacion existente y no existente | Pendiente |
| API-02 | Asignaciones | POST | `/addAsignacion` | Crear asignacion valida | Pendiente |
| API-03 | Asignaciones | POST | `/updateAsignacion` | Actualizar asignacion existente | Pendiente |
| API-04 | Asignaciones | DELETE | `/deleteAsignacion` | Eliminar asignacion de prueba | Pendiente |
| API-05 | Asignaciones | POST | `/buscarAsignaciones` | Consultar listado | Pendiente |
| API-06 | Comentarios | POST | `/getComentario` | Consultar comentario | Pendiente |
| API-07 | Comentarios | GET | `/buscarComentario` | Consultar comentario existente y no existente | Pendiente |
| API-08 | Comentarios | POST | `/addComentario` | Crear comentario valido | Pendiente |
| API-09 | Comentarios | POST | `/updateComentario` | Actualizar comentario existente | Pendiente |
| API-10 | Comentarios | DELETE | `/deleteComentario` | Eliminar comentario de prueba | Pendiente |
| API-11 | Comentarios | POST | `/buscarComentarios` | Consultar listado | Pendiente |
| API-12 | Proyectos | GET | `/buscarProyecto` | Consultar proyecto existente y no existente | Pendiente |
| API-13 | Proyectos | POST | `/addProyecto` | Crear proyecto valido | Pendiente |
| API-14 | Proyectos | POST | `/updateProyecto` | Actualizar proyecto existente | Pendiente |
| API-15 | Proyectos | DELETE | `/deleteProyecto` | Eliminar proyecto de prueba | Pendiente |
| API-16 | Proyectos | POST | `/buscarProyectos` | Consultar listado | Pendiente |
| API-17 | Tareas | POST | `/getTarea` | Consultar tarea | Pendiente |
| API-18 | Tareas | GET | `/buscarTarea` | Consultar tarea existente y no existente | Pendiente |
| API-19 | Tareas | POST | `/addTareas` | Crear tarea valida | Pendiente |
| API-20 | Tareas | POST | `/updateTareas` | Actualizar tarea existente | Pendiente |
| API-21 | Tareas | DELETE | `/deleteTareas` | Eliminar tarea de prueba | Pendiente |
| API-22 | Tareas | POST | `/buscarTareas` | Consultar listado | Pendiente |
| API-23 | Usuario | POST | `/getUsuarioLogin` | Probar credenciales validas e invalidas | Pendiente |
| API-24 | Usuario | GET | `/buscarUsuario` | Consultar usuario existente y no existente | Pendiente |
| API-25 | Usuario | POST | `/addUsuario` | Crear usuario de prueba | Pendiente |
| API-26 | Usuario | POST | `/updateUsuario` | Actualizar usuario existente | Pendiente |
| API-27 | Usuario | DELETE | `/deleteUsuario` | Eliminar usuario de prueba | Pendiente |
| API-28 | Usuario | POST | `/buscarUsuarios` | Consultar listado | Pendiente |

## 5. Evidencia y artefactos

| Evidencia | Ubicacion / referencia |
|---|---|
| Salida de pruebas JUnit | Maven Surefire: `Ejercicio5MyAPIGestor/target/surefire-reports` (28 pruebas, 0 fallidas) |
| Coleccion y resultados Postman | Exportar la coleccion y adjuntar resultados del Collection Runner |
| Cobertura JaCoCo | No configurada al crear esta plantilla; agregar el reporte solo despues de instrumentar y ejecutar JaCoCo |
| Capturas de pantalla | Guardar en `docs/actas/evidencias/` |

## 6. Criterios de cierre

- Registrar datos de entrada y resultado observado para cada caso ejecutado.
- Marcar `Pasa` o `Falla` solo despues de revisar el resultado.
- Mantener las pruebas unitarias JUnit separadas de las pruebas API/Postman.
- No publicar contrasenas, tokens ni datos personales en capturas o colecciones.

## 7. Firmas

| Responsable de pruebas | Revision / aprobacion |
|---|---|
| Nombre y fecha: ____________________ | Nombre y fecha: ____________________ |