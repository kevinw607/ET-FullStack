package com.papeleria.ventas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class VentaDTO {

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId; // Conectará con crm-ms

    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId; // Conectará con catalogo-ms y precios-ms

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima de venta es 1 unidad")
    private Integer cantidad;

    public VentaDTO() {}

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}