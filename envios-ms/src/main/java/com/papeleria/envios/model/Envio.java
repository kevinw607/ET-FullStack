package com.papeleria.envios.model;

import jakarta.persistence.*;

@Entity
@Table(name = "envios")
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String direccion;

    @Column(nullable = false, length = 50)
    private String estado;

    @Column(length = 100, unique = true)
    private String seguimiento;

    public Envio() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getSeguimiento() { return seguimiento; }
    public void setSeguimiento(String seguimiento) { this.seguimiento = seguimiento; }
}