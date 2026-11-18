-- ======================================================================
-- F-04  Llaves foraneas que faltaban en dbgestor (MariaDB 11.3)
--
-- SchemaSpy detecto cuatro relaciones solo implicitas. Este script las
-- convierte en FOREIGN KEY. Con RESTRICT (valor por defecto) no se puede
-- borrar un registro con dependientes; la aplicacion ya elimina en
-- cascada (IntegridadService: proyecto, tareas, comentarios y
-- asignaciones).
--
-- Antes de ejecutar: respaldo (mysqldump dbgestor) y los 4 conteos del
-- paso 1 deben devolver 0.
-- ======================================================================

-- 1. VERIFICAR: registros huerfanos que impedirian crear las llaves
SELECT COUNT(*) AS tareas_sin_proyecto
FROM tareas AS t
LEFT JOIN proyectos AS p ON t.id_proyecto = p.id_proyecto
WHERE t.id_proyecto IS NOT NULL AND p.id_proyecto IS NULL;

SELECT COUNT(*) AS asignaciones_sin_usuario
FROM asignaciones AS a
LEFT JOIN usuario AS u ON a.id_usuario = u.id_usuario
WHERE a.id_usuario IS NOT NULL AND u.id_usuario IS NULL;

SELECT COUNT(*) AS asignaciones_sin_tarea
FROM asignaciones AS a
LEFT JOIN tareas AS t ON a.id_tarea = t.id_tarea
WHERE a.id_tarea IS NOT NULL AND t.id_tarea IS NULL;

SELECT COUNT(*) AS comentarios_sin_tarea
FROM comentarios AS c
LEFT JOIN tareas AS t ON c.id_tarea = t.id_tarea
WHERE c.id_tarea IS NOT NULL AND t.id_tarea IS NULL;

-- 2. CREAR las llaves foraneas
ALTER TABLE tareas
    ADD CONSTRAINT fk_tareas_proyectos
    FOREIGN KEY (id_proyecto) REFERENCES proyectos (id_proyecto);

ALTER TABLE asignaciones
    ADD CONSTRAINT fk_asignaciones_usuario
    FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario);

ALTER TABLE asignaciones
    ADD CONSTRAINT fk_asignaciones_tareas
    FOREIGN KEY (id_tarea) REFERENCES tareas (id_tarea);

ALTER TABLE comentarios
    ADD CONSTRAINT fk_comentarios_tareas
    FOREIGN KEY (id_tarea) REFERENCES tareas (id_tarea);

-- Reversion:
-- ALTER TABLE comentarios DROP FOREIGN KEY fk_comentarios_tareas;
-- ALTER TABLE asignaciones DROP FOREIGN KEY fk_asignaciones_tareas;
-- ALTER TABLE asignaciones DROP FOREIGN KEY fk_asignaciones_usuario;
-- ALTER TABLE tareas DROP FOREIGN KEY fk_tareas_proyectos;
