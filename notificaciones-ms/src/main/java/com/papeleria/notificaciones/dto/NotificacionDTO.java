package com.papeleria.notificaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NotificacionDTO {

    @NotBlank(message = "El destinatario es obligatorio")
    @Size(max = 150, message = "El destinatario no puede exceder los 150 caracteres")
    private String destinatario;

    @NotBlank(message = "El asunto es obligatorio")
    @Size(max = 100, message = "El asunto no puede exceder los 100 caracteres")
    private String asunto;

    @NotBlank(message = "El mensaje no puede estar vacío")
    private String mensaje;

    public NotificacionDTO() {}

    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }

    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}