-- Esquema de la base de datos dbgestor (MariaDB 11.3)
--
-- Origen: volcado de mysqldump del 26/06/2024, depurado para el analisis
-- estatico con SQLFluff (se quitaron las directivas condicionales de
-- mysqldump y se agregaron las llaves foraneas que faltaban).
--
-- Relaciones (integridad referencial):
--   tareas.id_proyecto      -> proyectos.id_proyecto
--   asignaciones.id_usuario -> usuario.id_usuario
--   asignaciones.id_tarea   -> tareas.id_tarea
--   comentarios.id_tarea    -> tareas.id_tarea
-- comentarios.id_usuario aun guarda el nombre del usuario; su migracion a
-- llave foranea esta propuesta en migrations/migracion_f03.sql.

DROP TABLE IF EXISTS comentarios;
DROP TABLE IF EXISTS asignaciones;
DROP TABLE IF EXISTS tareas;
DROP TABLE IF EXISTS proyectos;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS hibernate_sequence;

CREATE TABLE usuario (
    id_usuario INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) DEFAULT NULL,
    correo_electronico VARCHAR(100) DEFAULT NULL,
    contrasena VARCHAR(100) DEFAULT NULL,
    rol VARCHAR(100) DEFAULT NULL,
    imagen_usuario VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id_usuario)
) ENGINE = InnoDB DEFAULT CHARSET = latin1;

CREATE TABLE proyectos (
    id_proyecto INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) DEFAULT NULL,
    descripcion VARCHAR(100) DEFAULT NULL,
    fecha_inicio DATE DEFAULT NULL,
    fecha_finalizacion DATE DEFAULT NULL,
    estado_proyecto VARCHAR(100) DEFAULT NULL,
    fondo VARCHAR(100) DEFAULT NULL,
    PRIMARY KEY (id_proyecto)
) ENGINE = InnoDB DEFAULT CHARSET = latin1;

CREATE TABLE tareas (
    id_tarea INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) DEFAULT NULL,
    descripcion VARCHAR(100) DEFAULT NULL,
    fecha_vencimiento DATE DEFAULT NULL,
    estado VARCHAR(255) DEFAULT NULL,
    lista VARCHAR(255) DEFAULT NULL,
    id_proyecto INT DEFAULT NULL,
    PRIMARY KEY (id_tarea),
    CONSTRAINT fk_tareas_proyectos
    FOREIGN KEY (id_proyecto) REFERENCES proyectos (id_proyecto)
) ENGINE = InnoDB DEFAULT CHARSET = latin1;

CREATE TABLE asignaciones (
    id_asignacion INT NOT NULL AUTO_INCREMENT,
    id_usuario INT DEFAULT NULL,
    id_tarea INT DEFAULT NULL,
    fecha_asignacion DATE DEFAULT NULL,
    PRIMARY KEY (id_asignacion),
    CONSTRAINT fk_asignaciones_usuario
    FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario),
    CONSTRAINT fk_asignaciones_tareas
    FOREIGN KEY (id_tarea) REFERENCES tareas (id_tarea)
) ENGINE = InnoDB DEFAULT CHARSET = latin1;

CREATE TABLE comentarios (
    id_comentario INT NOT NULL AUTO_INCREMENT,
    texto_comentario VARCHAR(250) DEFAULT NULL,
    fecha_hora DATETIME DEFAULT NULL,
    id_usuario VARCHAR(100) DEFAULT NULL,
    id_tarea INT DEFAULT NULL,
    PRIMARY KEY (id_comentario),
    CONSTRAINT fk_comentarios_tareas
    FOREIGN KEY (id_tarea) REFERENCES tareas (id_tarea)
) ENGINE = InnoDB DEFAULT CHARSET = latin1;

CREATE TABLE hibernate_sequence (
    next_val BIGINT DEFAULT NULL
) ENGINE = InnoDB DEFAULT CHARSET = latin1;

-- Datos de ejemplo
INSERT INTO usuario (
    id_usuario, nombre, correo_electronico, contrasena, rol, imagen_usuario
)
VALUES
(
    131, 'Ana Paola Ramírez Vázquez', 'ana22533@cbtis75.edu.mx',
    '0722', 'Estudiante', '#70FAB7'
),
(
    172, 'Liliana Gómez Martínez', 'liliana22512@cbtis75.edu.mx',
    'ada', 'Estudiante', '#54EA01'
),
(
    196, 'Perla Joceline Martínez Meza', 'perla22522@cbtis75.edu.mx',
    '12345', 'Estudiante', '#805611'
);

INSERT INTO proyectos (
    id_proyecto, nombre, descripcion, fecha_inicio, fecha_finalizacion,
    estado_proyecto, fondo
)
VALUES
(
    197, 'My API Rest',
    'Desarrollaran un Api Rest, que satisfaga un sistema de información con al menos 5 tablas.', -- noqa: LT05
    '2024-05-31', '2024-06-29', 'En proceso', '/Images/animacion1.gif'
);

INSERT INTO tareas (
    id_tarea, nombre, descripcion, fecha_vencimiento, estado, lista,
    id_proyecto
)
VALUES
(
    199, 'Base de datos', 'Realizar la estructura de las tablas',
    '2024-06-13', 'danger', 'terminado', 197
);

INSERT INTO comentarios (
    id_comentario, texto_comentario, fecha_hora, id_usuario, id_tarea
)
VALUES
(
    201, 'Subirlo YA!!!', '2024-06-26 13:48:00',
    'Perla Joceline Martínez Meza', 199
),
(
    202, 'hola', '2024-06-26 13:49:00', 'Liliana Gómez Martínez', 199
);

INSERT INTO hibernate_sequence (next_val) VALUES (203);
