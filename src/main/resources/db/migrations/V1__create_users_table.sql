-- Crear tabla ENUM simulada para roles (como MySQL no tiene ENUMs como Java)
CREATE TABLE IF NOT EXISTS roles (
    name VARCHAR(20) PRIMARY KEY
);

-- Insertar roles base
INSERT IGNORE INTO roles (name) VALUES
    ('ROLE_ADMIN'),
    ('ROLE_MENTOR'),
    ('ROLE_USER');

-- Crear tabla de usuarios
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    image_url VARCHAR(255),
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_role FOREIGN KEY (role) REFERENCES roles(name)
);


-- Contraseña encriptada: SkillLink123
-- Hash generado con BCrypt: $2a$12$LsuZrsZGx50wqkRNNwBkCurdviALoBj77NFcpda9SponynVglLbJe

-- User test admin
INSERT INTO users (id, name, email, password, role, enabled, created_at)
VALUES (
    1,
    'Admin',
    'admin@skilllink.com',
    '$2a$12$LsuZrsZGx50wqkRNNwBkCurdviALoBj77NFcpda9SponynVglLbJe',
    'ROLE_ADMIN',
    TRUE,
    CURRENT_TIMESTAMP
);

-- User test user
INSERT INTO users (id, name, email, password, role, enabled, created_at)
VALUES (
    2,
    'usuario test',
    'test@skilllink.com',
    '$2a$12$LsuZrsZGx50wqkRNNwBkCurdviALoBj77NFcpda9SponynVglLbJe',
    'ROLE_USER',
    TRUE,
    CURRENT_TIMESTAMP
);

-- User test user
INSERT INTO users (id, name, email, password, role, enabled, created_at)
VALUES (
    3,
    'Usuario mentor',
    'mentor@skilllink.com',
    '$2a$12$LsuZrsZGx50wqkRNNwBkCurdviALoBj77NFcpda9SponynVglLbJe',
    'ROLE_MENTOR',
    TRUE,
    CURRENT_TIMESTAMP
);

