package com.papeleria.precios.service;

import com.papeleria.precios.model.Precio;
import com.papeleria.precios.dto.PrecioDTO;
import com.papeleria.precios.repository.PrecioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PrecioService {
    private static final Logger log = LoggerFactory.getLogger(PrecioService.class);
    private final PrecioRepository precioRepository;

    public PrecioService(PrecioRepository precioRepository) {
        this.precioRepository = precioRepository;
    }

    public List<Precio> obtenerTodos() {
        log.info("Consultando todos los precios registrados");
        return precioRepository.findAll();
    }

    public Optional<Precio> obtenerPorId(Long id) {
        log.info("Buscando precio con ID: {}", id);
        return precioRepository.findById(id);
    }

    public Optional<Precio> obtenerPorProductoId(Long productoId) {
        log.info("Buscando precio para el producto ID: {}", productoId);
        return precioRepository.findByProductoId(productoId);
    }

    public Precio guardarPrecio(PrecioDTO precioDTO) {
        log.info("Guardando/Actualizando precio para producto ID: {}", precioDTO.getProductoId());
        Optional<Precio> precioExistente = precioRepository.findByProductoId(precioDTO.getProductoId());

        Precio precio = precioExistente.orElse(new Precio());
        if (precioExistente.isEmpty()) {
            precio.setProductoId(precioDTO.getProductoId());
        }

        precio.setPrecioOferta(precioDTO.getPrecioOferta());
        precio.setDescripcionDescuento(precioDTO.getDescripcionDescuento());

        return precioRepository.save(precio);
    }
}