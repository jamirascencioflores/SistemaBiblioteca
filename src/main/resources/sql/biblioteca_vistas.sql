/* =========================================================
   📘 VISTAS DEL SISTEMA DE GESTIÓN DE BIBLIOTECA
   Autor: Jamir Ascencio Flores
   Fecha: Octubre 2025
   Descripción:
   Este script contiene las vistas SQL que permiten generar
   reportes dinámicos sobre préstamos, libros y clientes.
   ========================================================= */

/* ---------------------------------------------------------
   📊 Vista 1: Detalle de préstamos con cálculo de días y total
   --------------------------------------------------------- */
CREATE OR REPLACE VIEW vista_prestamos_detalle AS
SELECT
    p.id_prestamo,
    c.nombre_completo AS cliente,
    l.titulo AS libro,
    p.fecha_prestamo,
    p.fecha_devolucion,
    DATEDIFF(IFNULL(p.fecha_devolucion, NOW()), p.fecha_prestamo) AS dias_prestamo,
    p.precio_prestamo,
    (DATEDIFF(IFNULL(p.fecha_devolucion, NOW()), p.fecha_prestamo) * p.precio_prestamo) AS total_pagar
FROM prestamo p
         JOIN cliente c ON p.id_cliente = c.id_cliente
         JOIN libro l ON p.id_libro = l.id_libro;

/* ---------------------------------------------------------
   📗 Vista 2: Reporte de stock y disponibilidad de libros
   --------------------------------------------------------- */
CREATE OR REPLACE VIEW vista_stock_libros AS
SELECT
    l.titulo,
    a.nombre_completo AS autor,
    c.nombre_categoria AS categoria,
    e.nombre_editorial AS editorial,
    s.disponible,
    s.prestado,
    s.mantenimiento
FROM libro l
         JOIN autor a ON l.id_autor = a.id_autor
         JOIN categoria c ON l.id_categoria = c.id_categoria
         JOIN editorial e ON l.id_editorial = e.id_editorial
         JOIN stock s ON l.id_libro = s.id_libro;

/* ---------------------------------------------------------
   📙 Vista 3: Libros más prestados
   --------------------------------------------------------- */
CREATE OR REPLACE VIEW vista_libros_populares AS
SELECT
    l.titulo,
    COUNT(p.id_prestamo) AS total_prestamos
FROM prestamo p
         JOIN libro l ON p.id_libro = l.id_libro
GROUP BY l.titulo
ORDER BY total_prestamos DESC;
