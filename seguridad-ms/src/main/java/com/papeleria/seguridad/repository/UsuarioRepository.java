package com.papeleria.seguridad.repository;

import com.papeleria.seguridad.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método clave para el login: Spring Boot lo implementa mágicamente
    Optional<Usuario> findByUsername(String username);
}