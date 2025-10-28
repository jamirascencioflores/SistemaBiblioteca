/*
DROP DATABASE IF EXISTS BD_BIBLIOTECA;
CREATE DATABASE BD_BIBLIOTECA;
USE BD_BIBLIOTECA;
*/

CREATE TABLE cliente (
                         id_cliente INT AUTO_INCREMENT PRIMARY KEY,
                         nombre_completo VARCHAR(100) NOT NULL,
                         dni VARCHAR(15) UNIQUE NOT NULL,
                         direccion VARCHAR(150),
                         telefono VARCHAR(20)
);

CREATE TABLE autor (
                       id_autor INT AUTO_INCREMENT PRIMARY KEY,
                       codigo_autor VARCHAR(10) UNIQUE NOT NULL,
                       nombre_completo VARCHAR(100) NOT NULL
);

CREATE TABLE categoria (
                           id_categoria INT AUTO_INCREMENT PRIMARY KEY,
                           nombre_categoria VARCHAR(50) NOT NULL
);

CREATE TABLE editorial (
                           id_editorial INT AUTO_INCREMENT PRIMARY KEY,
                           nombre_editorial VARCHAR(50) NOT NULL
);

CREATE TABLE libro (
                       id_libro INT AUTO_INCREMENT PRIMARY KEY,
                       codigo_libro VARCHAR(10) UNIQUE NOT NULL,
                       id_autor INT NOT NULL,
                       titulo VARCHAR(150) NOT NULL,
                       id_categoria INT NOT NULL,
                       id_editorial INT NOT NULL,
                       hash_registro VARCHAR(256) NOT NULL,
                       CONSTRAINT uc_autor_titulo UNIQUE (id_autor, titulo),
                       FOREIGN KEY (id_autor) REFERENCES autor(id_autor),
                       FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria),
                       FOREIGN KEY (id_editorial) REFERENCES editorial(id_editorial)
);

CREATE TABLE stock (
                       id_stock INT AUTO_INCREMENT PRIMARY KEY,
                       id_libro INT NOT NULL,
                       disponible INT NOT NULL,
                       prestado INT NOT NULL,
                       mantenimiento INT NOT NULL,
                       FOREIGN KEY (id_libro) REFERENCES libro(id_libro)
);

CREATE TABLE prestamo (
                          id_prestamo INT AUTO_INCREMENT PRIMARY KEY,
                          id_libro INT NOT NULL,
                          id_cliente INT NOT NULL,
                          fecha_prestamo DATE NOT NULL,
                          fecha_devolucion DATE,
                          FOREIGN KEY (id_libro) REFERENCES libro(id_libro),
                          FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);

CREATE TABLE ticket_prestamo (
                                 id_ticket INT AUTO_INCREMENT PRIMARY KEY,
                                 id_prestamo INT NOT NULL,
                                 resumen TEXT NOT NULL,
                                 fecha_generado DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 FOREIGN KEY (id_prestamo) REFERENCES prestamo(id_prestamo)
);

INSERT INTO autor (codigo_autor, nombre_completo) VALUES
                                                      ('A001', 'Gabriel García Márquez'),
                                                      ('A002', 'Mario Vargas Llosa'),
                                                      ('A003', 'Isabel Allende'),
                                                      ('A004', 'Julio Cortázar'),
                                                      ('A005', 'Laura Esquivel');

INSERT INTO categoria (nombre_categoria) VALUES
                                             ('Novela'),
                                             ('Cuento'),
                                             ('Ensayo'),
                                             ('Poesía'),
                                             ('Ciencia Ficción');

INSERT INTO editorial (nombre_editorial) VALUES
                                             ('Planeta'),
                                             ('Alfaguara'),
                                             ('Penguin Random House'),
                                             ('Editorial Norma'),
                                             ('Debolsillo');

INSERT INTO cliente (nombre_completo, dni, direccion, telefono) VALUES
                                                                    ('Mario Mario', '12345678', 'Reino Champiñón, Calle 1', '999111222'),
                                                                    ('Luigi Mario', '87654321', 'Reino Champiñón, Calle 2', '999333444'),
                                                                    ('Princess Peach', '11223344', 'Castillo Peach, Nivel 3', '999555666'),
                                                                    ('Link Hyrule', '44556677', 'Bosque Kokiri, Ruta 8', '999777888'),
                                                                    ('Samus Aran', '77889900', 'Nave Galáctica, Sector Z', '999000111');


