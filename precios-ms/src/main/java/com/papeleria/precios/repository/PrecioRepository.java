package com.papeleria.precios.repository;

import com.papeleria.precios.model.Precio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrecioRepository extends JpaRepository<Precio, Long> {

    Optional<Precio> findByProductoId(Long productoId);
}