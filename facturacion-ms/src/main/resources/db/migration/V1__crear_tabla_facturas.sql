CREATE TABLE facturas
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_factura VARCHAR(50) NOT NULL UNIQUE,
    monto_total DOUBLE NOT NULL,
    fecha_emision  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);