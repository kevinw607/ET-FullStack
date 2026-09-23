package com.papeleria.finanzas.controller;

import com.papeleria.finanzas.model.Ingreso;
import com.papeleria.finanzas.service.IngresoService;
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

@WebMvcTest(controllers = IngresoController.class)
public class IngresoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngresoService ingresoService;

    // --- TEST 1: Éxito en buscar por ID ---
    @Test
    @DisplayName("shouldReturnIngresoWhenExists: Retorna 200 y el ingreso si el ID existe")
    void shouldReturnIngresoWhenExists() throws Exception {
        // ARRANGE
        Ingreso ingreso = new Ingreso();
        ingreso.setId(1L);
        ingreso.setMonto(100.0);
        ingreso.setDescripcion("Venta de cuadernos");

        when(ingresoService.obtenerPorId(1L)).thenReturn(Optional.of(ingreso));

        // ACT & ASSERT: Ruta base /api/finanzas + /1
        mockMvc.perform(get("/api/finanzas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monto").value(100.0))
                .andExpect(jsonPath("$.descripcion").value("Venta de cuadernos"));
    }

    // --- TEST 2: Error 404 cuando no existe ---
    @Test
    @DisplayName("shouldReturn404WhenIngresoNotFound: Retorna 404 si el ID no existe")
    void shouldReturn404WhenIngresoNotFound() throws Exception {
        // ARRANGE
        when(ingresoService.obtenerPorId(99L)).thenReturn(Optional.empty());

        // ACT & ASSERT
        mockMvc.perform(get("/api/finanzas/99"))
                .andExpect(status().isNotFound());
    }
}