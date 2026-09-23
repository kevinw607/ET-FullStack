package com.papeleria.crm.repository;

import com.papeleria.crm.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Derived Query para buscar directamente por el RUT del cliente
    Optional<Cliente> findByRut(String rut);
}