CREATE TABLE inventarios
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    producto_id BIGINT       NOT NULL,
    cantidad    INT          NOT NULL,
    bodega      VARCHAR(100) NOT NULL
);