package com.papeleria.crm.service;

import com.papeleria.crm.model.Cliente;
import com.papeleria.crm.repository.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CrmService {

    private static final Logger log = LoggerFactory.getLogger(CrmService.class);
    private final ClienteRepository clienteRepository;

    public CrmService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> obtenerTodosLosClientes() {
        log.info("Consultando todos los clientes de la base de datos CRM");
        return clienteRepository.findAll();
    }

    public Optional<Cliente> obtenerClientePorId(Long id) {
        log.info("Buscando cliente en CRM por ID: {}", id);
        return clienteRepository.findById(id);
    }

    public Optional<Cliente> obtenerClientePorRut(String rut) {
        log.info("Buscando cliente en CRM por RUT: {}", rut);
        return clienteRepository.findByRut(rut);
    }

    public Cliente guardarCliente(Cliente cliente) {
        log.info("Guardando cliente: {} {}", cliente.getNombre(), cliente.getApellido());
        return clienteRepository.save(cliente);
    }

    public void eliminarCliente(Long id) {
        log.warn("Eliminando cliente con ID: {}", id);
        clienteRepository.deleteById(id);
    }
}