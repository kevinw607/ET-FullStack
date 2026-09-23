package com.papeleria.precios.controller;

import com.papeleria.precios.model.Precio;
import com.papeleria.precios.dto.PrecioDTO;
import com.papeleria.precios.service.PrecioService;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

@RestController
@RequestMapping("/api/precios")
public class PrecioController {

    private final PrecioService precioService;

    public PrecioController(PrecioService precioService) {
        this.precioService = precioService;
    }

    @GetMapping
    public List<Precio> listarTodos() {
        return precioService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Precio>> obtenerPorId(@PathVariable Long id) {
        return precioService.obtenerPorId(id)
                .map(precio -> {
                    EntityModel<Precio> model = EntityModel.of(precio);
                    model.add(linkTo(methodOn(PrecioController.class).obtenerPorId(id)).withSelfRel());
                    model.add(linkTo(methodOn(PrecioController.class).listarTodos()).withRel("todos-precios"));
                    return ResponseEntity.ok(model);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Precio> registrarOActualizarPrecio(@Valid @RequestBody PrecioDTO precioDTO) {
        return new ResponseEntity<>(precioService.guardarPrecio(precioDTO), HttpStatus.CREATED);
    }
}