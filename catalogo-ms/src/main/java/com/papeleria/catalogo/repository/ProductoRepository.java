package com.papeleria.catalogo.repository;

import com.papeleria.catalogo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Método personalizado (Derived Query)
    List<Producto> findByCategoriaId(Long categoriaId);
}