package com.papeleria.facturacion.controller;

import com.papeleria.facturacion.dto.FacturaDTO;
import com.papeleria.facturacion.model.Factura;
import com.papeleria.facturacion.service.FacturacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// --- IMPORTACIONES HATEOAS ---
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    private final FacturacionService facturacionService;

    public FacturaController(FacturacionService facturacionService) {
        this.facturacionService = facturacionService;
    }

    // POST: Crear una nueva factura
    @PostMapping
    public ResponseEntity<Factura> crearFactura(@Valid @RequestBody FacturaDTO facturaDTO) {
        Factura factura = new Factura();
        factura.setNumeroFactura(facturaDTO.getNumeroFactura());
        factura.setMontoTotal(facturaDTO.getMontoTotal());
        factura.setFechaEmision(LocalDateTime.now()); // Sella la fecha exacta del servidor

        return new ResponseEntity<>(facturacionService.guardarFactura(factura), HttpStatus.CREATED);
    }

    // GET: Listar todas las facturas
    @GetMapping
    public List<Factura> listarFacturas() {
        return facturacionService.obtenerTodas();
    }

    // GET: Obtener una factura específica con HATEOAS
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Factura>> obtenerFactura(@PathVariable Long id) {
        return facturacionService.buscarPorId(id)
                .map(factura -> {
                    EntityModel<Factura> facturaModel = EntityModel.of(factura);

                    // Enlace hacia la factura misma
                    facturaModel.add(linkTo(methodOn(FacturaController.class).obtenerFactura(id)).withSelfRel());
                    // Enlace para volver al listado de facturación
                    facturaModel.add(linkTo(methodOn(FacturaController.class).listarFacturas()).withRel("todas-las-facturas"));

                    return ResponseEntity.ok(facturaModel);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}