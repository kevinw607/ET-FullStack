package com.papeleria.ventas.service;

import com.papeleria.ventas.client.InventarioClient;
import com.papeleria.ventas.model.Venta;
import com.papeleria.ventas.repository.VentaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VentaService {
    private static final Logger log = LoggerFactory.getLogger(VentaService.class);
    private final VentaRepository ventaRepository;
    private final InventarioClient inventarioClient; // <-- Inyectamos el cliente

    public VentaService(VentaRepository ventaRepository, InventarioClient inventarioClient) {
        this.ventaRepository = ventaRepository;
        this.inventarioClient = inventarioClient;
    }

    public List<Venta> obtenerTodas() {
        log.info("Consultando historial de ventas");
        return ventaRepository.findAll();
    }

    public Venta buscarPorId(Long id) {
        log.info("Buscando venta con ID: {}", id);
        return ventaRepository.findById(id).orElse(null);
    }

    @Transactional // <-- Importante: si falla el stock, no se guarda la venta
    public Venta registrarVenta(Venta venta) {
        log.info("Procesando nueva venta para Cliente ID: {} - Producto ID: {}", venta.getClienteId(), venta.getProductoId());

        // 1. Descontamos el stock en inventario-ms primero
        log.info("Llamando a inventario-ms para reducir stock...");
        inventarioClient.reducirStock(venta.getProductoId(), venta.getCantidad());

        // 2. Si el paso anterior no lanza error, guardamos la venta
        log.info("Stock descontado. Guardando venta en base de datos local...");
        return ventaRepository.save(venta);
    }
}