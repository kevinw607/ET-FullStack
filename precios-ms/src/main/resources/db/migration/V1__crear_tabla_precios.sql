CREATE TABLE precios
(
    id                    BIGINT AUTO_INCREMENT PRIMARY KEY,
    producto_id           BIGINT NOT NULL,
    precio_oferta DOUBLE NOT NULL,
    descripcion_descuento VARCHAR(100)
);