package com.papeleria.envios.controller;

import com.papeleria.envios.model.Envio;
import com.papeleria.envios.service.EnvioService;
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

@WebMvcTest(controllers = EnvioController.class) // <--- Ahora es explícito
public class EnviosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnvioService envioService;

    // --- TEST 1: Caso de éxito ---
    @Test
    @DisplayName("shouldReturnEnvioWhenExists: Retorna 200 y el estado del envío si el ID existe")
    void shouldReturnEnvioWhenExists() throws Exception {
        // ARRANGE
        Envio envio = new Envio();
        envio.setId(1L);
        envio.setEstado("En camino");

        when(envioService.obtenerPorId(1L)).thenReturn(Optional.of(envio));

        // ACT & ASSERT: CORREGIDO A LA RUTA REAL: /api/envios/1
        mockMvc.perform(get("/api/envios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("En camino"));
    }

    // --- TEST 2: Caso de error (404) ---
    @Test
    @DisplayName("shouldReturn404WhenEnvioNotFound: Retorna 404 si el ID del envío no existe")
    void shouldReturn404WhenEnvioNotFound() throws Exception {
        // ARRANGE
        when(envioService.obtenerPorId(99L)).thenReturn(Optional.empty());

        // ACT & ASSERT: CORREGIDO A LA RUTA REAL: /api/envios/99
        mockMvc.perform(get("/api/envios/99"))
                .andExpect(status().isNotFound());
    }
}