package com.papeleria.inventario.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class InventarioDTO {

    // Ya no necesitamos el ID aquí porque este DTO es para crear un nuevo registro de stock

    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 0, message = "La cantidad en inventario no puede ser negativa")
    private Integer cantidad;

    @NotBlank(message = "La bodega de destino es obligatoria")
    private String bodega;

    public InventarioDTO() {}

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public String getBodega() { return bodega; }
    public void setBodega(String bodega) { this.bodega = bodega; }
}