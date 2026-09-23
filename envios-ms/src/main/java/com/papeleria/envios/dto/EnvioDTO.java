package com.papeleria.envios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EnvioDTO {

    @NotBlank(message = "La dirección de envío es obligatoria")
    @Size(max = 255, message = "La dirección no puede exceder los 255 caracteres")
    private String direccion;

    @NotBlank(message = "El estado del envío es obligatorio (ej: PENDIENTE)")
    @Size(max = 50, message = "El estado no puede exceder los 50 caracteres")
    private String estado;

    // Puede ser nulo al principio, pero si se envía, controlamos su tamaño
    @Size(max = 100, message = "El código de seguimiento no puede exceder los 100 caracteres")
    private String seguimiento;

    public EnvioDTO() {}

    public EnvioDTO(String direccion, String estado, String seguimiento) {
        this.direccion = direccion;
        this.estado = estado;
        this.seguimiento = seguimiento;
    }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getSeguimiento() { return seguimiento; }
    public void setSeguimiento(String seguimiento) { this.seguimiento = seguimiento; }
}