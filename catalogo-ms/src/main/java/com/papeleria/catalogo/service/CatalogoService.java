package com.papeleria.catalogo.service;

import com.papeleria.catalogo.model.Categoria;
import com.papeleria.catalogo.model.Producto;
import com.papeleria.catalogo.repository.CategoriaRepository;
import com.papeleria.catalogo.repository.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatalogoService {

    // Inicializamos el Logger
    private static final Logger log = LoggerFactory.getLogger(CatalogoService.class);

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public CatalogoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Producto> obtenerTodosLosProductos() {
        log.info("Consultando todos los productos de la base de datos");
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerProductoPorId(Long id) {
        log.info("Buscando producto con ID: {}", id);
        return productoRepository.findById(id);
    }

    public List<Producto> obtenerProductosPorCategoria(Long categoriaId) {
        log.info("Buscando productos para la categoria ID: {}", categoriaId);
        return productoRepository.findByCategoriaId(categoriaId);
    }

    public Producto guardarProducto(Producto producto) {
        log.info("Guardando nuevo producto: {}", producto.getNombre());
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id) {
        log.warn("Eliminando producto con ID: {}", id);
        productoRepository.deleteById(id);
    }

    public List<Categoria> obtenerTodasLasCategorias() {
        log.info("Consultando todas las categorias");
        return categoriaRepository.findAll();
    }

    public Categoria guardarCategoria(Categoria categoria) {
        log.info("Guardando nueva categoria: {}", categoria.getNombre());
        return categoriaRepository.save(categoria);
    }
}