package com.papeleria.notificaciones.repository;

import com.papeleria.notificaciones.model.RegistroNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<RegistroNotificacion, Long> {

    // Derived query para buscar notificaciones que fallaron o están pendientes
    List<RegistroNotificacion> findByEstado(String estado);
}