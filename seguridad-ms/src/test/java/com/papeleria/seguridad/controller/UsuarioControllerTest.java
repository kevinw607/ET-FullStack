package com.papeleria.seguridad.controller;

import com.papeleria.seguridad.model.Usuario;
import com.papeleria.seguridad.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    // --- TEST 1: Éxito en buscar usuario por ID ---
    @Test
    @DisplayName("shouldReturnUsuarioWhenExists: Retorna 200 y el usuario si el ID existe")
    void shouldReturnUsuarioWhenExists() throws Exception {
        // ARRANGE
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("nico14");
        usuario.setEmail("nico@test.com");

        when(usuarioService.buscarPorId(1L)).thenReturn(usuario);

        // ACT & ASSERT: Ruta base /api/usuarios + /1
        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("nico14"))
                .andExpect(jsonPath("$.email").value("nico@test.com"));
    }

    // --- TEST 2: Error 404 cuando el ID no existe ---
    @Test
    @DisplayName("shouldReturn404WhenUsuarioNotFound: Retorna 404 si el ID no existe")
    void shouldReturn404WhenUsuarioNotFound() throws Exception {
        // ARRANGE
        when(usuarioService.buscarPorId(99L)).thenReturn(null);

        // ACT & ASSERT
        mockMvc.perform(get("/api/usuarios/99"))
                .andExpect(status().isNotFound());
    }
}