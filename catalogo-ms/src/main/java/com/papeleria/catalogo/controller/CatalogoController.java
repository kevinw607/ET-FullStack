package com.papeleria.catalogo.controller;

import com.papeleria.catalogo.model.Categoria;
import com.papeleria.catalogo.model.Producto;
import com.papeleria.catalogo.service.CatalogoService;
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
@RequestMapping("/api/catalogo")
public class CatalogoController {

    private final CatalogoService catalogoService;

    public CatalogoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @GetMapping("/productos")
    public List<Producto> listarProductos() {
        return catalogoService.obtenerTodosLosProductos();
    }

    // --- MÉTODO MODIFICADO CON HATEOAS ---
    @GetMapping("/productos/{id}")
    public ResponseEntity<EntityModel<Producto>> buscarProducto(@PathVariable Long id) {
        return catalogoService.obtenerProductoPorId(id)
                .map(producto -> {
                    // Envolvemos el producto
                    EntityModel<Producto> productoModel = EntityModel.of(producto);

                    // Link hacia sí mismo
                    productoModel.add(linkTo(methodOn(CatalogoController.class).buscarProducto(id)).withSelfRel());

                    // Link hacia el listado general
                    productoModel.add(linkTo(methodOn(CatalogoController.class).listarProductos()).withRel("todos-los-productos"));

                    return ResponseEntity.ok(productoModel);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    // -------------------------------------

    @GetMapping("/productos/categoria/{categoriaId}")
    public List<Producto> listarPorCategoria(@PathVariable Long categoriaId) {
        return catalogoService.obtenerProductosPorCategoria(categoriaId);
    }

    @PostMapping("/productos")
    public ResponseEntity<Producto> registrarProducto(@Valid @RequestBody Producto producto) {
        return new ResponseEntity<>(catalogoService.guardarProducto(producto), HttpStatus.CREATED);
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> borrarProducto(@PathVariable Long id) {
        catalogoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categorias")
    public List<Categoria> listarCategorias() {
        return catalogoService.obtenerTodasLasCategorias();
    }

    @PostMapping("/categorias")
    public ResponseEntity<Categoria> registrarCategoria(@Valid @RequestBody Categoria categoria) {
        return new ResponseEntity<>(catalogoService.guardarCategoria(categoria), HttpStatus.CREATED);
    }
}