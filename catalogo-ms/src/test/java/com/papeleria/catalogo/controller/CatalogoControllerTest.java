package com.papeleria.catalogo.controller;

import com.papeleria.catalogo.model.Producto;
import com.papeleria.catalogo.service.CatalogoService;
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

@WebMvcTest(CatalogoController.class)
public class CatalogoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatalogoService catalogoService;

    // --- TEST 1: Caso de éxito ---
    @Test
    @DisplayName("shouldReturnProductoWhenExists: Retorna 200 y el producto si el ID existe")
    void shouldReturnProductoWhenExists() throws Exception {
        // ARRANGE
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Cuaderno");
        when(catalogoService.obtenerProductoPorId(1L)).thenReturn(Optional.of(producto));

        // ACT & ASSERT
        mockMvc.perform(get("/api/catalogo/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Cuaderno"));
    }

    // --- TEST 2: Caso de error (404) ---
    @Test
    @DisplayName("shouldReturn404WhenProductoNotFound: Retorna 404 si el producto no existe")
    void shouldReturn404WhenProductoNotFound() throws Exception {
        // ARRANGE
        when(catalogoService.obtenerProductoPorId(99L)).thenReturn(Optional.empty());

        // ACT & ASSERT
        mockMvc.perform(get("/api/catalogo/productos/99"))
                .andExpect(status().isNotFound());
    }
}