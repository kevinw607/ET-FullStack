package com.papeleria.finanzas.service;

import com.papeleria.finanzas.model.Ingreso;
import com.papeleria.finanzas.dto.IngresoDTO;
import com.papeleria.finanzas.repository.IngresoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngresoService {

    private static final Logger log = LoggerFactory.getLogger(IngresoService.class);
    private final IngresoRepository ingresoRepository;

    public IngresoService(IngresoRepository ingresoRepository) {
        this.ingresoRepository = ingresoRepository;
    }

    public List<Ingreso> obtenerTodos() {
        log.info("Consultando el historial completo de ingresos financieros");
        return ingresoRepository.findAll();
    }

    // --- NUEVO MÉTODO PARA SOPORTAR HATEOAS ---
    public Optional<Ingreso> obtenerPorId(Long id) {
        log.info("Buscando ingreso financiero con ID: {}", id);
        return ingresoRepository.findById(id);
    }

    public Ingreso registrarIngreso(IngresoDTO ingresoDTO) {
        log.info("Registrando nuevo ingreso por un monto de: {}", ingresoDTO.getMonto());

        if (ingresoDTO.getMonto() == null || ingresoDTO.getMonto() <= 0) {
            log.warn("Intento de registrar un ingreso con monto inválido");
            throw new IllegalArgumentException("El monto del ingreso debe ser mayor a cero");
        }

        Ingreso ingreso = new Ingreso();
        ingreso.setMonto(ingresoDTO.getMonto());
        ingreso.setDescripcion(ingresoDTO.getDescripcion());

        return ingresoRepository.save(ingreso);
    }
}