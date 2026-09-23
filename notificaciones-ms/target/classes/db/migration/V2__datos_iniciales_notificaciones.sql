-- Datos para las alertas rápidas
INSERT INTO notificaciones (destinatario, mensaje)
VALUES ('carlos.soto@gmail.com', 'Tu pedido ha sido preparado con éxito.'),
       ('admin@papeleria.com', 'Alerta: Stock bajo en Cuadernos Universitarios.'),
       ('ana.valdes@hotmail.com', 'Tu envío TRK-100002 ya está EN_TRANSITO.'),
       ('lcardenas@empresa.cl', 'Factura FAC-2026-003 generada correctamente.');

-- Datos para el historial de correos formales
INSERT INTO registro_notificacion (destinatario, asunto, mensaje, estado)
VALUES ('carlos.soto@gmail.com', 'Confirmación de Pedido #100001','Estimado Carlos, hemos recibido su pedido de artículos de oficina para despacho en Calle Antonio Varas 123. Adjuntamos el detalle.','ENVIADO'),
       ('ana.valdes@hotmail.com', 'Actualización de Envío','Hola Ana, tu pedido va en camino a Avenida Presidente Ibáñez 456, Puerto Montt.', 'PENDIENTE'),
       ('camila.reyes@duocuc.cl', 'Bienvenida a nuestra tienda','Hola Camila, gracias por registrarte en nuestra tienda de papelería. Ya puedes comenzar a comprar.', 'ENVIADO'),
       ('pgomez@yahoo.es', 'Aviso de Retraso en Entrega', 'Estimado Pedro, lamentamos informarle que su entrega en Costanera 200, Llanquihue, presenta un retraso.','ERROR');