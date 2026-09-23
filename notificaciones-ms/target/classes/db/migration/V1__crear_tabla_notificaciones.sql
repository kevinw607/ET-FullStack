CREATE TABLE notificaciones
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    destinatario VARCHAR(100) NOT NULL,
    mensaje      VARCHAR(255) NOT NULL,
    enviado_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE registro_notificacion
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    destinatario   VARCHAR(150) NOT NULL,
    asunto         VARCHAR(100),
    mensaje        TEXT         NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado         VARCHAR(20)  NOT NULL
);