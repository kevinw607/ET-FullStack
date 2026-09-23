package com.papeleria.inventario.controller;

import com.papeleria.inventario.model.Inventario;
import com.papeleria.inventario.service.InventarioService;
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

@WebMvcTest(controllers = InventarioController.class)
public class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventarioService inventarioService;

    // --- TEST 1: Éxito en buscar stock por ID ---
    @Test
    @DisplayName("shouldReturnStockWhenExists: Retorna 200 y el inventario si el ID existe")
    void shouldReturnStockWhenExists() throws Exception {
        // ARRANGE
        Inventario inventario = new Inventario();
        inventario.setId(1L);
        inventario.setCantidad(50);
        inventario.setBodega("Bodega Central");

        when(inventarioService.buscarPorId(1L)).thenReturn(Optional.of(inventario));

        // ACT & ASSERT: Ruta base /api/inventarios + /1
        mockMvc.perform(get("/api/inventarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidad").value(50))
                .andExpect(jsonPath("$.bodega").value("Bodega Central"));
    }

    // --- TEST 2: Error 404 cuando el ID no existe ---
    @Test
    @DisplayName("shouldReturn404WhenStockNotFound: Retorna 404 si el ID no existe")
    void shouldReturn404WhenStockNotFound() throws Exception {
        // ARRANGE
        when(inventarioService.buscarPorId(99L)).thenReturn(Optional.empty());

        // ACT & ASSERT
        mockMvc.perform(get("/api/inventarios/99"))
                .andExpect(status().isNotFound());
    }
}