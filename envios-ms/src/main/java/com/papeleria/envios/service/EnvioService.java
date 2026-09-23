package com.papeleria.envios.service;

import com.papeleria.envios.model.Envio;
import com.papeleria.envios.dto.EnvioDTO;
import com.papeleria.envios.repository.EnvioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnvioService {

    private static final Logger log = LoggerFactory.getLogger(EnvioService.class);
    private final EnvioRepository envioRepository;

    public EnvioService(EnvioRepository envioRepository) {
        this.envioRepository = envioRepository;
    }

    public List<Envio> obtenerTodos() {
        log.info("Consultando todos los envíos registrados");
        return envioRepository.findAll();
    }

    public Optional<Envio> obtenerPorId(Long id) {
        log.info("Buscando envío con ID: {}", id);
        return envioRepository.findById(id);
    }

    public Envio crearEnvio(EnvioDTO envioDTO) {
        log.info("Creando nuevo envío hacia la dirección: {}", envioDTO.getDireccion());
        Envio envio = new Envio();
        envio.setDireccion(envioDTO.getDireccion());
        envio.setEstado(envioDTO.getEstado() != null ? envioDTO.getEstado() : "PREPARACION");
        envio.setSeguimiento(envioDTO.getSeguimiento());
        return envioRepository.save(envio);
    }

    public Optional<Envio> actualizarEstado(Long id, String nuevoEstado) {
        log.info("Actualizando estado del envío ID {} a: {}", id, nuevoEstado);
        return envioRepository.findById(id).map(envio -> {
            envio.setEstado(nuevoEstado);
            return envioRepository.save(envio);
        });
    }
}