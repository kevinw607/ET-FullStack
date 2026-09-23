package com.papeleria.inventario.service;

import com.papeleria.inventario.model.Inventario;
import com.papeleria.inventario.repository.InventarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    private static final Logger log = LoggerFactory.getLogger(InventarioService.class);
    private final InventarioRepository inventarioRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    // Listar todo el stock
    public List<Inventario> obtenerTodos() {
        log.info("Consultando el registro completo de inventario en todas las bodegas");
        return inventarioRepository.findAll();
    }

    // Buscar stock de un producto por ID con Optional
    public Optional<Inventario> buscarPorId(Long id) {
        log.info("Buscando registro de inventario con ID: {}", id);
        return inventarioRepository.findById(id);
    }

    // Guardar o actualizar stock
    public Inventario guardarInventario(Inventario inventario) {
        log.info("Actualizando inventario: {} unidades del producto ID {} en bodega '{}'",
                inventario.getCantidad(), inventario.getProductoId(), inventario.getBodega());
        return inventarioRepository.save(inventario);
    }

    // --- NUEVA LÓGICA DE NEGOCIO PARA VENTAS ---
    @Transactional
    public void reducirStock(Long productoId, Integer cantidad) {
        log.info("Intentando reducir stock de {} unidades para el producto ID: {}", cantidad, productoId);

        // Asumimos que tienes un método en tu Repository para buscar por productoId
        Inventario inventario = inventarioRepository.findByProductoId(productoId)
                .orElseThrow(() -> {
                    log.error("Error: Producto ID {} no encontrado en inventario", productoId);
                    return new RuntimeException("Producto no encontrado en inventario");
                });

        if (inventario.getCantidad() < cantidad) {
            log.warn("Stock insuficiente: solicitado {}, disponible {}", cantidad, inventario.getCantidad());
            throw new RuntimeException("Stock insuficiente para el producto: " + productoId);
        }

        inventario.setCantidad(inventario.getCantidad() - cantidad);
        inventarioRepository.save(inventario);
        log.info("Stock actualizado exitosamente para producto ID {}. Nueva cantidad: {}", productoId, inventario.getCantidad());
    }
}