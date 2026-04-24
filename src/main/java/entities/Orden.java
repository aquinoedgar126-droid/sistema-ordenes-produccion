package com.bopisa.controlordenes.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "orden")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String ot;

    @Column(length = 50)
    private String codigo;

    @Column(length = 150)
    private String producto;

    private LocalDate fechaRequerida;

    private Double kgRequerido;

    // Getters y Setters
    public Long getId() { return id; }

    public String getOt() { return ot; }
    public void setOt(String ot) { this.ot = ot; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }

    public LocalDate getFechaRequerida() { return fechaRequerida; }
    public void setFechaRequerida(LocalDate fechaRequerida) { this.fechaRequerida = fechaRequerida; }

    public Double getKgRequerido() { return kgRequerido; }
    public void setKgRequerido(Double kgRequerido) { this.kgRequerido = kgRequerido; }
}