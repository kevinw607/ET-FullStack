package com.papeleria.precios.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PrecioDTO {

    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;

    @NotNull(message = "El precio de oferta es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio de oferta no puede ser negativo ni cero")
    private Double precioOferta;

    @Size(max = 255, message = "La descripción del descuento no puede exceder los 255 caracteres")
    private String descripcionDescuento;

    public PrecioDTO() {}

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public Double getPrecioOferta() { return precioOferta; }
    public void setPrecioOferta(Double precioOferta) { this.precioOferta = precioOferta; }

    public String getDescripcionDescuento() { return descripcionDescuento; }
    public void setDescripcionDescuento(String descripcionDescuento) { this.descripcionDescuento = descripcionDescuento; }
}