package com.papeleria.ventas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient inventarioWebClient(WebClient.Builder builder) {
        // Apuntamos al puerto 8086 que es donde vive tu inventario-ms
        return builder.baseUrl("http://localhost:8086").build();
    }
}