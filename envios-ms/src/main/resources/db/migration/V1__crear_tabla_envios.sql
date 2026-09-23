CREATE TABLE envios
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    direccion   VARCHAR(255) NOT NULL,
    estado      VARCHAR(50) DEFAULT 'PREPARACION',
    seguimiento VARCHAR(100)
);