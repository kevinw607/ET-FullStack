package com.papeleria.notificaciones.controller;

import com.papeleria.notificaciones.dto.NotificacionDTO;
import com.papeleria.notificaciones.model.RegistroNotificacion;
import com.papeleria.notificaciones.service.NotificacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// --- IMPORTACIONES HATEOAS ---
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @GetMapping("/historial")
    public List<RegistroNotificacion> verHistorial() {
        return notificacionService.obtenerHistorial();
    }

    // --- NUEVO: GET POR ID CON HATEOAS ---
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<RegistroNotificacion>> obtenerNotificacion(@PathVariable Long id) {
        return notificacionService.buscarPorId(id)
                .map(notificacion -> {
                    EntityModel<RegistroNotificacion> notificacionModel = EntityModel.of(notificacion);

                    // Enlace hacia esta notificación específica
                    notificacionModel.add(linkTo(methodOn(NotificacionController.class).obtenerNotificacion(id)).withSelfRel());
                    // Enlace para volver al historial
                    notificacionModel.add(linkTo(methodOn(NotificacionController.class).verHistorial()).withRel("historial-completo"));

                    return ResponseEntity.ok(notificacionModel);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/enviar")
    public ResponseEntity<RegistroNotificacion> enviarNotificacion(@Valid @RequestBody NotificacionDTO peticionDTO) {
        RegistroNotificacion peticion = new RegistroNotificacion();
        peticion.setDestinatario(peticionDTO.getDestinatario());
        peticion.setAsunto(peticionDTO.getAsunto());
        peticion.setMensaje(peticionDTO.getMensaje());

        RegistroNotificacion resultado = notificacionService.procesarEnvio(peticion);
        return new ResponseEntity<>(resultado, HttpStatus.CREATED);
    }
}