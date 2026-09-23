package com.papeleria.inventario.repository;

import com.papeleria.inventario.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // <-- Importación necesaria para evitar errores

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    // Método mágico de Spring Data para buscar por la columna producto_id
    Optional<Inventario> findByProductoId(Long productoId);

}