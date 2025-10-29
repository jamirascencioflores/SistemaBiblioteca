/* =========================================================
   📘 SCRIPT: data.sql
   SISTEMA DE GESTIÓN DE BIBLIOTECA
   Autor: Jamir Ascencio Flores
   Fecha: Octubre 2025
   Descripción:
   Inserta los datos de ejemplo para inicializar el sistema.
   ========================================================= */

/* =========================================================
   🧩 AUTORES
   ========================================================= */
INSERT INTO autor (codigo_autor, nombre_completo) VALUES
    ('A001', 'Gabriel García Márquez'),
    ('A002', 'Mario Vargas Llosa'),
    ('A003', 'Isabel Allende'),
    ('A004', 'Julio Cortázar'),
    ('A005', 'Laura Esquivel');

/* =========================================================
   🧩 CATEGORÍAS
   ========================================================= */
INSERT INTO categoria (nombre_categoria) VALUES
    ('Novela'),
    ('Cuento'),
    ('Ensayo'),
    ('Poesía'),
    ('Ciencia Ficción');

/* =========================================================
   🧩 EDITORIALES
   ========================================================= */
INSERT INTO editorial (nombre_editorial) VALUES
    ('Planeta'),
    ('Alfaguara'),
    ('Penguin Random House'),
    ('Editorial Norma'),
    ('Debolsillo');

/* =========================================================
   🧩 CLIENTES
   ========================================================= */
INSERT INTO cliente (nombre_completo, dni, direccion, telefono) VALUES
    ('Mario Mario', '12345678', 'Reino Champiñón, Calle 1', '999111222'),
    ('Luigi Mario', '87654321', 'Reino Champiñón, Calle 2', '999333444'),
    ('Princess Peach', '11223344', 'Castillo Peach, Nivel 3', '999555666'),
    ('Link Hyrule', '44556677', 'Bosque Kokiri, Ruta 8', '999777888'),
    ('Samus Aran', '77889900', 'Nave Galáctica, Sector Z', '999000111');

/* =========================================================
   🧩 LIBROS
   ========================================================= */
INSERT INTO libro (codigo_libro, id_autor, titulo, id_categoria, id_editorial, hash_registro, tarifa_prestamo) VALUES
    ('L001', 1, 'Cien años de soledad', 1, 1, SHA2('L001', 256), 5.00),
    ('L002', 2, 'La ciudad y los perros', 1, 2, SHA2('L002', 256), 4.50),
    ('L003', 3, 'La casa de los espíritus', 1, 3, SHA2('L003', 256), 3.75),
    ('L004', 4, 'Rayuela', 1, 4, SHA2('L004', 256), 6.00),
    ('L005', 5, 'Como agua para chocolate', 1, 5, SHA2('L005', 256), 4.00);

/* =========================================================
   🧩 STOCK DE LIBROS
   ========================================================= */
INSERT INTO stock (id_libro, disponible, prestado, mantenimiento) VALUES
    (1, 8, 1, 1),
    (2, 5, 2, 0),
    (3, 6, 0, 1),
    (4, 7, 3, 0),
    (5, 10, 0, 0);

/* =========================================================
   🧩 PRÉSTAMOS
   ========================================================= */
INSERT INTO prestamo (id_libro, id_cliente, fecha_prestamo, fecha_devolucion, precio_prestamo) VALUES
    (1, 1, '2025-10-01', '2025-10-07', 5.00),
    (2, 2, '2025-10-03', '2025-10-09', 4.50),
    (3, 3, '2025-10-04', NULL, 3.75),
    (4, 4, '2025-10-02', '2025-10-08', 6.00),
    (5, 5, '2025-10-05', NULL, 4.00);

/* =========================================================
   🧾 TICKETS DE PRÉSTAMOS
   ========================================================= */
INSERT INTO ticket_prestamo (id_prestamo, resumen) VALUES
    (1, 'Préstamo del libro "Cien años de soledad" para Mario Mario.'),
    (2, 'Préstamo del libro "La ciudad y los perros" para Luigi Mario.'),
    (3, 'Préstamo del libro "La casa de los espíritus" para Princess Peach.'),
    (4, 'Préstamo del libro "Rayuela" para Link Hyrule.'),
    (5, 'Préstamo del libro "Como agua para chocolate" para Samus Aran.');
