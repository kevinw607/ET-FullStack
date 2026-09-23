package com.papeleria.crm.controller;

import com.papeleria.crm.model.Cliente;
import com.papeleria.crm.service.CrmService;
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

@WebMvcTest(CrmController.class)
public class CrmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrmService crmService;

    // --- TEST 1: Caso de éxito ---
    @Test
    @DisplayName("shouldReturnClienteWhenExists: Retorna 200 y el cliente si el ID existe")
    void shouldReturnClienteWhenExists() throws Exception {
        // ARRANGE
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Nicolás");
        when(crmService.obtenerClientePorId(1L)).thenReturn(Optional.of(cliente));

        // ACT & ASSERT
        mockMvc.perform(get("/api/crm/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Nicolás"));
    }

    // --- TEST 2: Caso de error (404) ---
    @Test
    @DisplayName("shouldReturn404WhenClienteNotFound: Retorna 404 si el cliente no existe")
    void shouldReturn404WhenClienteNotFound() throws Exception {
        // ARRANGE
        when(crmService.obtenerClientePorId(99L)).thenReturn(Optional.empty());

        // ACT & ASSERT
        mockMvc.perform(get("/api/crm/clientes/99"))
                .andExpect(status().isNotFound());
    }
}