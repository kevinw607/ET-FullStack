CREATE TABLE IF NOT EXISTS categorias (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          nombre VARCHAR(100) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS productos (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    precio_unitario DOUBLE NOT NULL,
    categoria_id BIGINT NOT NULL,
    CONSTRAINT fk_categoria FOREIGN KEY (categoria_id) REFERENCES categorias(id)
    );