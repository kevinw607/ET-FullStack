package com.papeleria.crm.controller;

import com.papeleria.crm.model.Cliente;
import com.papeleria.crm.service.CrmService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// --- NUEVAS IMPORTACIONES HATEOAS ---
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
// ------------------------------------

import java.util.List;

@RestController
@RequestMapping("/api/crm")
public class CrmController {

    private final CrmService crmService;

    public CrmController(CrmService crmService) {
        this.crmService = crmService;
    }

    @GetMapping("/clientes")
    public List<Cliente> listarClientes() {
        return crmService.obtenerTodosLosClientes();
    }

    // --- GET POR ID CON HATEOAS ---
    @GetMapping("/clientes/{id}")
    public ResponseEntity<EntityModel<Cliente>> buscarPorId(@PathVariable Long id) {
        return crmService.obtenerClientePorId(id)
                .map(cliente -> {
                    EntityModel<Cliente> clienteModel = EntityModel.of(cliente);
                    clienteModel.add(linkTo(methodOn(CrmController.class).buscarPorId(id)).withSelfRel());
                    clienteModel.add(linkTo(methodOn(CrmController.class).listarClientes()).withRel("todos-los-clientes"));
                    return ResponseEntity.ok(clienteModel);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // --- GET POR RUT CON HATEOAS ---
    @GetMapping("/clientes/rut/{rut}")
    public ResponseEntity<EntityModel<Cliente>> buscarPorRut(@PathVariable String rut) {
        return crmService.obtenerClientePorRut(rut)
                .map(cliente -> {
                    EntityModel<Cliente> clienteModel = EntityModel.of(cliente);
                    clienteModel.add(linkTo(methodOn(CrmController.class).buscarPorRut(rut)).withSelfRel());
                    clienteModel.add(linkTo(methodOn(CrmController.class).listarClientes()).withRel("todos-los-clientes"));
                    return ResponseEntity.ok(clienteModel);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/clientes")
    public ResponseEntity<Cliente> registrarCliente(@Valid @RequestBody Cliente cliente) {
        return new ResponseEntity<>(crmService.guardarCliente(cliente), HttpStatus.CREATED);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @Valid @RequestBody Cliente datosActualizados) {
        return crmService.obtenerClientePorId(id)
                .map(clienteExistente -> {
                    clienteExistente.setNombre(datosActualizados.getNombre());
                    clienteExistente.setApellido(datosActualizados.getApellido());
                    clienteExistente.setEmail(datosActualizados.getEmail());
                    clienteExistente.setTelefono(datosActualizados.getTelefono());
                    clienteExistente.setDireccionEnvio(datosActualizados.getDireccionEnvio());
                    return ResponseEntity.ok(crmService.guardarCliente(clienteExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        crmService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}