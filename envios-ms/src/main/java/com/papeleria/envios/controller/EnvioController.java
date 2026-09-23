package com.papeleria.envios.controller;

import com.papeleria.envios.model.Envio;
import com.papeleria.envios.dto.EnvioDTO;
import com.papeleria.envios.service.EnvioService;
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
@RequestMapping("/api/envios")
public class EnvioController {

    private final EnvioService envioService;

    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    @GetMapping
    public List<Envio> listarTodos() {
        return envioService.obtenerTodos();
    }

    // --- GET POR ID CON HATEOAS ---
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Envio>> buscarPorId(@PathVariable Long id) {
        return envioService.obtenerPorId(id)
                .map(envio -> {
                    EntityModel<Envio> envioModel = EntityModel.of(envio);
                    envioModel.add(linkTo(methodOn(EnvioController.class).buscarPorId(id)).withSelfRel());
                    envioModel.add(linkTo(methodOn(EnvioController.class).listarTodos()).withRel("todos-los-envios"));
                    return ResponseEntity.ok(envioModel);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Envio> guardarEnvio(@Valid @RequestBody EnvioDTO envioDTO) {
        return new ResponseEntity<>(envioService.crearEnvio(envioDTO), HttpStatus.CREATED);
    }

    // --- PUT (ACTUALIZAR ESTADO) CON HATEOAS ---
    @PutMapping("/{id}/estado")
    public ResponseEntity<EntityModel<Envio>> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        return envioService.actualizarEstado(id, estado)
                .map(envio -> {
                    EntityModel<Envio> envioModel = EntityModel.of(envio);
                    // Le damos el enlace hacia sí mismo para que puedan consultar cómo quedó el envío tras el cambio
                    envioModel.add(linkTo(methodOn(EnvioController.class).buscarPorId(id)).withSelfRel());
                    return ResponseEntity.ok(envioModel);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}