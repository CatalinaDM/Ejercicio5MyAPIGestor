-- ============================================================================
-- F-03  Migración PROPUESTA de comentarios.id_usuario (VARCHAR(100) -> INT)
-- Base de datos: dbgestor (MariaDB 11.3)
--
-- ESTADO: PROPUESTA. NO se ha ejecutado ni modifica datos todavía.
-- Problema: usuario.id_usuario es INT, pero comentarios.id_usuario es VARCHAR(100)
--           y almacena NOMBRES de usuario (p. ej. 'Perla Joceline Martínez Meza').
-- Estrategia segura: expandir -> rellenar -> verificar -> cambiar la aplicación -> contraer.
-- Cada paso es reversible hasta el paso 6.
-- ============================================================================

-- 0. Respaldo previo (obligatorio)
--    mysqldump -uroot -p dbgestor comentarios usuario > respaldo_f03.sql
CREATE TABLE comentarios_respaldo_f03 AS SELECT * FROM comentarios;

-- 1. EXPANDIR: columna nueva, anulable, sin tocar la existente
ALTER TABLE comentarios ADD COLUMN id_usuario_num INT NULL AFTER id_usuario;

-- 2. RELLENAR: emparejar por nombre exacto (solo si el nombre es único)
UPDATE comentarios c
JOIN (SELECT nombre, MIN(id_usuario) AS id_usuario, COUNT(*) AS repetidos
        FROM usuario GROUP BY nombre) u
  ON u.nombre = c.id_usuario AND u.repetidos = 1
SET c.id_usuario_num = u.id_usuario
WHERE c.id_usuario_num IS NULL;

-- 3. VERIFICAR: comentarios sin pareja (deben revisarse a mano ANTES de continuar)
SELECT id_comentario, id_usuario AS nombre_guardado
  FROM comentarios
 WHERE id_usuario_num IS NULL;

-- 3b. Nombres duplicados en usuario (ambiguos)
SELECT nombre, COUNT(*) AS veces FROM usuario GROUP BY nombre HAVING COUNT(*) > 1;

-- 4. APLICACIÓN (Java/frontend): cambiar Comentarios.id_usuario a Integer,
--    enviar usuario.id_usuario desde proyecto.js (guardarComentario) y leer
--    el nombre con JOIN. IntegridadService.validarUsuarioComentario ya acepta id numérico.

-- 5. INTEGRIDAD: solo cuando el paso 3 devuelva 0 filas
ALTER TABLE comentarios MODIFY id_usuario_num INT NOT NULL;
ALTER TABLE comentarios
  ADD CONSTRAINT fk_comentarios_usuario FOREIGN KEY (id_usuario_num) REFERENCES usuario (id_usuario);

-- 6. CONTRAER (irreversible: hacerlo hasta validar la aplicación en producción)
-- ALTER TABLE comentarios DROP COLUMN id_usuario;
-- ALTER TABLE comentarios CHANGE id_usuario_num id_usuario INT NOT NULL;

-- Reversión (antes del paso 6):
-- ALTER TABLE comentarios DROP FOREIGN KEY fk_comentarios_usuario;
-- ALTER TABLE comentarios DROP COLUMN id_usuario_num;
