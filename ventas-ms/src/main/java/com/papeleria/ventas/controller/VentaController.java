package com.papeleria.ventas.controller;

import com.papeleria.ventas.dto.VentaDTO;
import com.papeleria.ventas.model.Venta;
import com.papeleria.ventas.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public List<Venta> listarVentas() {
        return ventaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Venta>> obtenerVenta(@PathVariable Long id) {
        Venta venta = ventaService.buscarPorId(id);
        if (venta != null) {
            EntityModel<Venta> model = EntityModel.of(venta);
            model.add(linkTo(methodOn(VentaController.class).obtenerVenta(id)).withSelfRel());
            model.add(linkTo(methodOn(VentaController.class).listarVentas()).withRel("todas-ventas"));
            return ResponseEntity.ok(model);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Venta> crearVenta(@Valid @RequestBody VentaDTO ventaDTO) {
        Venta venta = new Venta();
        venta.setClienteId(ventaDTO.getClienteId());
        venta.setProductoId(ventaDTO.getProductoId());
        venta.setCantidad(ventaDTO.getCantidad());
        venta.setFechaVenta(LocalDateTime.now());

        return new ResponseEntity<>(ventaService.registrarVenta(venta), HttpStatus.CREATED);
    }
}