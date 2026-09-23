package com.papeleria.facturacion.service;

import com.papeleria.facturacion.model.Factura;
import com.papeleria.facturacion.repository.FacturaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturacionService {

    private static final Logger log = LoggerFactory.getLogger(FacturacionService.class);
    private final FacturaRepository facturaRepository;

    public FacturacionService(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    public Factura guardarFactura(Factura factura) {
        log.info("Generando y guardando nueva factura N°: {}", factura.getNumeroFactura());
        return facturaRepository.save(factura);
    }

    public List<Factura> obtenerTodas() {
        log.info("Consultando el historial completo de facturas emitidas");
        return facturaRepository.findAll();
    }

    public Optional<Factura> buscarPorId(Long id) {
        log.info("Buscando factura en el sistema con ID: {}", id);
        return facturaRepository.findById(id);
    }
}