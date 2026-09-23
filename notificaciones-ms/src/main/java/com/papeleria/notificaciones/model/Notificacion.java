package com.papeleria.notificaciones.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String destinatario;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String mensaje;

    @Column(name = "enviado_at", nullable = false)
    private LocalDateTime enviadoAt;

    public Notificacion() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public LocalDateTime getEnviadoAt() { return enviadoAt; }
    public void setEnviadoAt(LocalDateTime enviadoAt) { this.enviadoAt = enviadoAt; }
}