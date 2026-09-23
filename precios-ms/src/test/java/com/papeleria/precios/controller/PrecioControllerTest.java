package com.papeleria.precios.controller;

import com.papeleria.precios.model.Precio;
import com.papeleria.precios.service.PrecioService;
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

@WebMvcTest(controllers = PrecioController.class)
public class PrecioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrecioService precioService;

    // --- TEST 1: Éxito en buscar precio por ID ---
    @Test
    @DisplayName("shouldReturnPrecioWhenExists: Retorna 200 y el precio si el ID existe")
    void shouldReturnPrecioWhenExists() throws Exception {
        // ARRANGE
        Precio precio = new Precio();
        precio.setId(1L);
        precio.setPrecioOferta(19990.0);

        when(precioService.obtenerPorId(1L)).thenReturn(Optional.of(precio));

        // ACT & ASSERT: Ruta base /api/precios + /1
        mockMvc.perform(get("/api/precios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.precioOferta").value(19990.0));
    }

    // --- TEST 2: Error 404 cuando el ID no existe ---
    @Test
    @DisplayName("shouldReturn404WhenPrecioNotFound: Retorna 404 si el ID no existe")
    void shouldReturn404WhenPrecioNotFound() throws Exception {
        // ARRANGE
        when(precioService.obtenerPorId(99L)).thenReturn(Optional.empty());

        // ACT & ASSERT
        mockMvc.perform(get("/api/precios/99"))
                .andExpect(status().isNotFound());
    }
}