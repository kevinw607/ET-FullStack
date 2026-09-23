package com.papeleria.notificaciones.controller;

import com.papeleria.notificaciones.model.RegistroNotificacion;
import com.papeleria.notificaciones.service.NotificacionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = NotificacionController.class)
public class NotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificacionService notificacionService;

    // --- TEST 1: Éxito en buscar notificación por ID ---
    @Test
    @DisplayName("shouldReturnNotificacionWhenExists: Retorna 200 y la notificación si el ID existe")
    void shouldReturnNotificacionWhenExists() throws Exception {
        // ARRANGE
        RegistroNotificacion notificacion = new RegistroNotificacion();
        notificacion.setId(1L);
        notificacion.setDestinatario("nico@example.com");
        notificacion.setAsunto("Confirmación de Pedido");

        when(notificacionService.buscarPorId(1L)).thenReturn(Optional.of(notificacion));

        // ACT & ASSERT: Ruta base /api/notificaciones + /1
        mockMvc.perform(get("/api/notificaciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.destinatario").value("nico@example.com"))
                .andExpect(jsonPath("$.asunto").value("Confirmación de Pedido"));
    }

    // --- TEST 2: Error 404 cuando el ID no existe ---
    @Test
    @DisplayName("shouldReturn404WhenNotificacionNotFound: Retorna 404 si el ID no existe")
    void shouldReturn404WhenNotificacionNotFound() throws Exception {
        // ARRANGE
        when(notificacionService.buscarPorId(99L)).thenReturn(Optional.empty());

        // ACT & ASSERT
        mockMvc.perform(get("/api/notificaciones/99"))
                .andExpect(status().isNotFound());
    }
}