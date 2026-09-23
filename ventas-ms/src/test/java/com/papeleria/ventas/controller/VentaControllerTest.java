package com.papeleria.ventas.controller;

import com.papeleria.ventas.client.InventarioClient;
import com.papeleria.ventas.model.Venta;
import com.papeleria.ventas.service.VentaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = VentaController.class)
public class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VentaService ventaService;

    // Aunque no se usa en el GET, WebMvcTest necesita que resolvamos todas las dependencias del constructor del controlador
    @MockBean
    private InventarioClient inventarioClient;

    // --- TEST 1: Éxito en buscar venta por ID ---
    @Test
    @DisplayName("shouldReturnVentaWhenExists: Retorna 200 y la venta si el ID existe")
    void shouldReturnVentaWhenExists() throws Exception {
        // ARRANGE
        Venta venta = new Venta();
        venta.setId(1L);
        venta.setProductoId(10L);
        venta.setCantidad(2);

        when(ventaService.buscarPorId(1L)).thenReturn(venta);

        // ACT & ASSERT: Ruta base /api/ventas + /1
        mockMvc.perform(get("/api/ventas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productoId").value(10))
                .andExpect(jsonPath("$.cantidad").value(2));
    }

    // --- TEST 2: Error 404 cuando el ID no existe ---
    @Test
    @DisplayName("shouldReturn404WhenVentaNotFound: Retorna 404 si el ID no existe")
    void shouldReturn404WhenVentaNotFound() throws Exception {
        // ARRANGE
        when(ventaService.buscarPorId(99L)).thenReturn(null);

        // ACT & ASSERT
        mockMvc.perform(get("/api/ventas/99"))
                .andExpect(status().isNotFound());
    }
}