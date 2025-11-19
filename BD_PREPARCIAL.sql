-- Crear base de datos
CREATE DATABASE preparcial_security
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE preparcial_security;

-- Tabla principal de usuarios para Spring Security
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,     -- contraseña en BCrypt
    email VARCHAR(150),
    fecha_nacimiento DATE,
    nombre_imagen VARCHAR(255),
    role VARCHAR(50) DEFAULT 'ROLE_USER'
);
