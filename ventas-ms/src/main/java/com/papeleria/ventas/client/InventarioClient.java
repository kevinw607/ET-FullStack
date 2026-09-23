package com.papeleria.ventas.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class InventarioClient {

    private final WebClient webClient;

    public InventarioClient(WebClient inventarioWebClient) {
        this.webClient = inventarioWebClient;
    }

    public void reducirStock(Long productoId, Integer cantidad) {
        webClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/inventarios/{productoId}/reducir")
                        .queryParam("cantidad", cantidad)
                        .build(productoId))
                .retrieve()
                .bodyToMono(Void.class)
                .block(); // Usamos block() para esperar la respuesta de forma síncrona
    }
}