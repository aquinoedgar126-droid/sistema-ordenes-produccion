package com.bopisa.controlordenes.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "laminacion")
public class Laminacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double kgSurtidoRespaldo;
    private Double kgEstimadoLaminar;
    private Double kgLaminado;
    private Double kgDesperdicio;
    private Double metrosLaminados;
    private Double kgAdhesivo;
    private Double difBaseLaminacion;
    private Double difTotalLaminacion;
    private Double retornoReal;
    private Double kgDesperdicioEdit;
    private String estadoProceso;
    private String codigoMaterial;
    private String descripcionMaterial;
    private Double kgReqMaterial;
    private Double kgSurtMaterial;

    @ManyToOne
    @JoinColumn(name = "orden_id", nullable = false)
    private Orden orden;

    public Long getId() { return id; }

    public Double getKgSurtidoRespaldo() { return kgSurtidoRespaldo; }
    public void setKgSurtidoRespaldo(Double kgSurtidoRespaldo) { this.kgSurtidoRespaldo = kgSurtidoRespaldo; }

    public Double getKgEstimadoLaminar() { return kgEstimadoLaminar; }
    public void setKgEstimadoLaminar(Double kgEstimadoLaminar) { this.kgEstimadoLaminar = kgEstimadoLaminar; }

    public Double getKgLaminado() { return kgLaminado; }
    public void setKgLaminado(Double kgLaminado) { this.kgLaminado = kgLaminado; }

    public Double getKgDesperdicio() { return kgDesperdicio; }
    public void setKgDesperdicio(Double kgDesperdicio) { this.kgDesperdicio = kgDesperdicio; }

    public Double getMetrosLaminados() { return metrosLaminados; }
    public void setMetrosLaminados(Double metrosLaminados) { this.metrosLaminados = metrosLaminados; }

    public Double getKgAdhesivo() { return kgAdhesivo; }
    public void setKgAdhesivo(Double kgAdhesivo) { this.kgAdhesivo = kgAdhesivo; }

    public Double getDifBaseLaminacion() { return difBaseLaminacion; }
    public void setDifBaseLaminacion(Double difBaseLaminacion) { this.difBaseLaminacion = difBaseLaminacion; }

    public Double getDifTotalLaminacion() { return difTotalLaminacion; }
    public void setDifTotalLaminacion(Double difTotalLaminacion) { this.difTotalLaminacion = difTotalLaminacion; }

    public Double getRetornoReal() { return retornoReal; }
    public void setRetornoReal(Double retornoReal) { this.retornoReal = retornoReal; }

    public Orden getOrden() { return orden; }
    public void setOrden(Orden orden) { this.orden = orden; }
    
    public Double getKgDesperdicioEdit() {
    return kgDesperdicioEdit;
}

    public void setKgDesperdicioEdit(Double kgDesperdicioEdit) {
        this.kgDesperdicioEdit = kgDesperdicioEdit;
    }

    public String getEstadoProceso() {
        return estadoProceso;
    }

    public void setEstadoProceso(String estadoProceso) {
        this.estadoProceso = estadoProceso;
    }
    
    public String getCodigoMaterial() { return codigoMaterial; }
    public void setCodigoMaterial(String codigoMaterial) { this.codigoMaterial = codigoMaterial; }

    public String getDescripcionMaterial() { return descripcionMaterial; }
    public void setDescripcionMaterial(String descripcionMaterial) { this.descripcionMaterial = descripcionMaterial; }

    public Double getKgReqMaterial() { return kgReqMaterial; }
    public void setKgReqMaterial(Double kgReqMaterial) { this.kgReqMaterial = kgReqMaterial; }

    public Double getKgSurtMaterial() { return kgSurtMaterial; }
    public void setKgSurtMaterial(Double kgSurtMaterial) { this.kgSurtMaterial = kgSurtMaterial; }
}