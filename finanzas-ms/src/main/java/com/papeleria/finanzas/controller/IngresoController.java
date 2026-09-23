package com.papeleria.finanzas.controller;

import com.papeleria.finanzas.model.Ingreso;
import com.papeleria.finanzas.dto.IngresoDTO;
import com.papeleria.finanzas.service.IngresoService;
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
@RequestMapping("/api/finanzas")
public class IngresoController {

    private final IngresoService ingresoService;

    public IngresoController(IngresoService ingresoService) {
        this.ingresoService = ingresoService;
    }

    @GetMapping
    public List<Ingreso> listarIngresos() {
        return ingresoService.obtenerTodos();
    }

    // --- NUEVO: GET POR ID CON HATEOAS ---
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Ingreso>> obtenerIngreso(@PathVariable Long id) {
        return ingresoService.obtenerPorId(id)
                .map(ingreso -> {
                    EntityModel<Ingreso> ingresoModel = EntityModel.of(ingreso);

                    // Enlace hacia este ingreso específico
                    ingresoModel.add(linkTo(methodOn(IngresoController.class).obtenerIngreso(id)).withSelfRel());
                    // Enlace para volver al listado de finanzas
                    ingresoModel.add(linkTo(methodOn(IngresoController.class).listarIngresos()).withRel("todos-los-ingresos"));

                    return ResponseEntity.ok(ingresoModel);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Ingreso> crearIngreso(@Valid @RequestBody IngresoDTO ingresoDTO) {
        try {
            Ingreso nuevoIngreso = ingresoService.registrarIngreso(ingresoDTO);
            return new ResponseEntity<>(nuevoIngreso, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}