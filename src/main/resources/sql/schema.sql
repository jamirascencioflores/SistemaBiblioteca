/* =========================================================
   📘 SCRIPT: schema.sql
   SISTEMA DE GESTIÓN DE BIBLIOTECA
   Autor: Jamir Ascencio Flores
   Fecha: Octubre 2025
   Descripción:
   Este script define la estructura de la base de datos
   BD_BIBLIOTECA para la aplicación de gestión de biblioteca.
   ========================================================= */

/*
-- Descomentar estas líneas si se desea crear la BD desde cero
DROP DATABASE IF EXISTS BD_BIBLIOTECA;
CREATE DATABASE BD_BIBLIOTECA;
USE BD_BIBLIOTECA;
*/

/* =========================================================
   🧩 TABLA: CLIENTE
   Contiene información básica de los clientes registrados.
   ========================================================= */
CREATE TABLE IF NOT EXISTS cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    dni VARCHAR(15) UNIQUE NOT NULL,
    direccion VARCHAR(150),
    telefono VARCHAR(20)
    );

/* =========================================================
   🧩 TABLA: AUTOR
   Contiene los autores de los libros disponibles.
   ========================================================= */
CREATE TABLE IF NOT EXISTS autor (
    id_autor INT AUTO_INCREMENT PRIMARY KEY,
    codigo_autor VARCHAR(10) UNIQUE NOT NULL,
    nombre_completo VARCHAR(100) NOT NULL
    );

/* =========================================================
   🧩 TABLA: CATEGORIA
   Clasificación de los libros según género o tipo.
   ========================================================= */
CREATE TABLE IF NOT EXISTS categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(50) NOT NULL
    );

/* =========================================================
   🧩 TABLA: EDITORIAL
   Información de las editoriales proveedoras de los libros.
   ========================================================= */
CREATE TABLE IF NOT EXISTS editorial (
    id_editorial INT AUTO_INCREMENT PRIMARY KEY,
    nombre_editorial VARCHAR(50) NOT NULL
    );

/* =========================================================
   🧩 TABLA: LIBRO
   Contiene los datos de los libros registrados en el sistema.
   Incluye claves foráneas a autor, categoría y editorial.
   ========================================================= */
CREATE TABLE IF NOT EXISTS libro (
    id_libro INT AUTO_INCREMENT PRIMARY KEY,
    codigo_libro VARCHAR(10) UNIQUE NOT NULL,
    id_autor INT NOT NULL,
    titulo VARCHAR(150) NOT NULL,
    id_categoria INT NOT NULL,
    id_editorial INT NOT NULL,
    hash_registro VARCHAR(256) NOT NULL,
    tarifa_prestamo DECIMAL(8,2) NOT NULL DEFAULT 0.00,
    CONSTRAINT uc_autor_titulo UNIQUE (id_autor, titulo),
    FOREIGN KEY (id_autor) REFERENCES autor(id_autor),
    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria),
    FOREIGN KEY (id_editorial) REFERENCES editorial(id_editorial)
    );

/* =========================================================
   🧩 TABLA: STOCK
   Controla la cantidad de libros disponibles, prestados
   o en mantenimiento.
   ========================================================= */
CREATE TABLE IF NOT EXISTS stock (
    id_stock INT AUTO_INCREMENT PRIMARY KEY,
    id_libro INT NOT NULL,
    disponible INT NOT NULL,
    prestado INT NOT NULL,
    mantenimiento INT NOT NULL,
    FOREIGN KEY (id_libro) REFERENCES libro(id_libro)
    );

/* =========================================================
   🧩 TABLA: PRESTAMO
   Registra los préstamos de libros realizados por los clientes.
   Incluye las fechas y el precio por préstamo.
   ========================================================= */
CREATE TABLE IF NOT EXISTS prestamo (
    id_prestamo INT AUTO_INCREMENT PRIMARY KEY,
    id_libro INT NOT NULL,
    id_cliente INT NOT NULL,
    fecha_prestamo DATE NOT NULL,
    fecha_devolucion DATE,
    precio_prestamo DECIMAL(8,2) NOT NULL DEFAULT 0.00,
    FOREIGN KEY (id_libro) REFERENCES libro(id_libro),
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
    );

/* =========================================================
   🧩 TABLA: TICKET_PRESTAMO
   Genera un registro resumen de cada préstamo con fecha y detalle.
   ========================================================= */
CREATE TABLE IF NOT EXISTS ticket_prestamo (
    id_ticket INT AUTO_INCREMENT PRIMARY KEY,
    id_prestamo INT NOT NULL,
    resumen TEXT NOT NULL,
    fecha_generado DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_prestamo) REFERENCES prestamo(id_prestamo)
    );
