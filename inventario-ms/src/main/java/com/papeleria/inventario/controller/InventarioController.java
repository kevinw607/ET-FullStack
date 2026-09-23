package com.papeleria.inventario.controller;

import com.papeleria.inventario.dto.InventarioDTO;
import com.papeleria.inventario.model.Inventario;
import com.papeleria.inventario.service.InventarioService;
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
@RequestMapping("/api/inventarios")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping
    public List<Inventario> listarStock() {
        return inventarioService.obtenerTodos();
    }

    // --- GET POR ID CON HATEOAS ---
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Inventario>> obtenerStockPorId(@PathVariable Long id) {
        return inventarioService.buscarPorId(id)
                .map(inventario -> {
                    EntityModel<Inventario> inventarioModel = EntityModel.of(inventario);

                    // Enlace hacia este registro de inventario
                    inventarioModel.add(linkTo(methodOn(InventarioController.class).obtenerStockPorId(id)).withSelfRel());
                    // Enlace para volver al listado completo de stock
                    inventarioModel.add(linkTo(methodOn(InventarioController.class).listarStock()).withRel("todo-el-inventario"));

                    return ResponseEntity.ok(inventarioModel);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // --- NUEVO ENDPOINT PARA VENTAS ---
    // Este permite que ventas-ms descuente stock directamente
    @PutMapping("/{productoId}/reducir")
    public ResponseEntity<Void> reducirStock(@PathVariable Long productoId, @RequestParam Integer cantidad) {
        inventarioService.reducirStock(productoId, cantidad);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Inventario> registrarStock(@Valid @RequestBody InventarioDTO inventarioDTO) {
        Inventario inventario = new Inventario();
        inventario.setProductoId(inventarioDTO.getProductoId());
        inventario.setCantidad(inventarioDTO.getCantidad());
        inventario.setBodega(inventarioDTO.getBodega());

        Inventario nuevoStock = inventarioService.guardarInventario(inventario);
        return new ResponseEntity<>(nuevoStock, HttpStatus.CREATED);
    }
}