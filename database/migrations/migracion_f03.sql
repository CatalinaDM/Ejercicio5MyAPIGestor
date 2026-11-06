-- ======================================================================
-- F-03  Migracion PROPUESTA de comentarios.id_usuario (VARCHAR -> INT)
-- Base de datos: dbgestor (MariaDB 11.3)
--
-- ESTADO: PROPUESTA. NO se ha ejecutado ni modifica datos todavia.
-- Problema: usuario.id_usuario es INT, pero comentarios.id_usuario es
--           VARCHAR(100) y guarda NOMBRES de usuario
--           (por ejemplo 'Perla Joceline Martinez Meza').
-- Estrategia segura: expandir, rellenar, verificar, cambiar la aplicacion
-- y contraer. Cada paso es reversible hasta el paso 6.
-- ======================================================================

-- 0. Respaldo previo (obligatorio)
--    mysqldump -uroot -p dbgestor comentarios usuario > respaldo_f03.sql
CREATE TABLE comentarios_respaldo_f03 AS
SELECT
    id_comentario,
    texto_comentario,
    fecha_hora,
    id_usuario,
    id_tarea
FROM comentarios;

-- 1. EXPANDIR: columna nueva, anulable, sin tocar la existente
ALTER TABLE comentarios
    ADD COLUMN id_usuario_num INT NULL AFTER id_usuario;

-- 2. RELLENAR: emparejar por nombre exacto (solo si el nombre es unico)
UPDATE comentarios AS c
INNER JOIN (
    SELECT
        nombre,
        MIN(id_usuario) AS id_usuario,
        COUNT(*) AS repetidos
    FROM usuario
    GROUP BY nombre
) AS u
    ON c.id_usuario = u.nombre AND u.repetidos = 1
SET c.id_usuario_num = u.id_usuario
WHERE c.id_usuario_num IS NULL;

-- 3. VERIFICAR: comentarios sin pareja
--    (deben revisarse a mano ANTES de continuar)
SELECT
    id_comentario,
    id_usuario AS nombre_guardado
FROM comentarios
WHERE id_usuario_num IS NULL;

-- 3b. Nombres duplicados en usuario (ambiguos)
SELECT
    nombre,
    COUNT(*) AS veces
FROM usuario
GROUP BY nombre
HAVING COUNT(*) > 1;

-- 4. APLICACION (Java y frontend): cambiar Comentarios.id_usuario a
--    Integer, enviar usuario.id_usuario desde proyecto.js
--    (guardarComentario) y leer el nombre con JOIN.
--    IntegridadService.validarUsuarioComentario ya acepta id numerico.

-- 5. INTEGRIDAD: solo cuando el paso 3 devuelva 0 filas
ALTER TABLE comentarios MODIFY id_usuario_num INT NOT NULL;
ALTER TABLE comentarios
    ADD CONSTRAINT fk_comentarios_usuario
    FOREIGN KEY (id_usuario_num) REFERENCES usuario (id_usuario);

-- 6. CONTRAER (irreversible: hacerlo hasta validar la aplicacion)
-- ALTER TABLE comentarios DROP COLUMN id_usuario;
-- ALTER TABLE comentarios CHANGE id_usuario_num id_usuario INT NOT NULL;

-- Reversion (antes del paso 6):
-- ALTER TABLE comentarios DROP FOREIGN KEY fk_comentarios_usuario;
-- ALTER TABLE comentarios DROP COLUMN id_usuario_num;
