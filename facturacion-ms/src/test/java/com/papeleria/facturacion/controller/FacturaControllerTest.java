package com.papeleria.facturacion.controller;

import com.papeleria.facturacion.model.Factura;
import com.papeleria.facturacion.service.FacturacionService;
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

@WebMvcTest(controllers = FacturaController.class)
public class FacturaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacturacionService facturacionService;

    // --- TEST 1: Éxito en buscar por ID ---
    @Test
    @DisplayName("shouldReturnFacturaWhenExists: Retorna 200 y la factura si el ID existe")
    void shouldReturnFacturaWhenExists() throws Exception {
        // ARRANGE
        Factura factura = new Factura();
        factura.setId(1L);
        factura.setNumeroFactura("FAC-001");

        when(facturacionService.buscarPorId(1L)).thenReturn(Optional.of(factura));

        // ACT & ASSERT: Ruta base /api/facturas + /1
        mockMvc.perform(get("/api/facturas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroFactura").value("FAC-001"));
    }

    // --- TEST 2: Error 404 cuando no existe ---
    @Test
    @DisplayName("shouldReturn404WhenFacturaNotFound: Retorna 404 si el ID no existe")
    void shouldReturn404WhenFacturaNotFound() throws Exception {
        // ARRANGE
        when(facturacionService.buscarPorId(99L)).thenReturn(Optional.empty());

        // ACT & ASSERT
        mockMvc.perform(get("/api/facturas/99"))
                .andExpect(status().isNotFound());
    }
}