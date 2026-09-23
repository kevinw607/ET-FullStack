package com.papeleria.notificaciones.service;

import com.papeleria.notificaciones.model.RegistroNotificacion;
import com.papeleria.notificaciones.repository.NotificacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacionService {

    private static final Logger log = LoggerFactory.getLogger(NotificacionService.class);
    private final NotificacionRepository notificacionRepository;

    public NotificacionService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    public List<RegistroNotificacion> obtenerHistorial() {
        log.info("Consultando historial completo de notificaciones");
        return notificacionRepository.findAll();
    }

    // --- NUEVO MÉTODO PARA SOPORTAR HATEOAS ---
    public Optional<RegistroNotificacion> buscarPorId(Long id) {
        log.info("Buscando registro de notificación con ID: {}", id);
        return notificacionRepository.findById(id);
    }

    public RegistroNotificacion procesarEnvio(RegistroNotificacion notificacion) {
        log.info("--- SIMULANDO ENVÍO DE CORREO ---");
        log.info("Para: {}", notificacion.getDestinatario());
        log.info("Asunto: {}", notificacion.getAsunto());
        log.info("---------------------------------");

        // Simulamos que el envío fue exitoso
        notificacion.setEstado("ENVIADO");
        log.info("Estado de notificación actualizado a ENVIADO. Guardando registro...");

        return notificacionRepository.save(notificacion);
    }
}