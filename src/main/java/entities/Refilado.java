package com.bopisa.controlordenes.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "refilado")
public class Refilado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoBase;
    private Double kgBaseCorte;
    private Double cantidadRequeridaot;
    private Double kgRefilado;
    private Double kgDesperdicio;
    private Double kgRefil;
    private Double difBaseVsRefiladoTotal;
    private Double porcentajeEntrega;
    private Double retorno;
    private Double kgDesperdicioEdit;
    private String estadoProceso;

    @ManyToOne
    @JoinColumn(name = "orden_id", nullable = false)
    private Orden orden;

    public Long getId() { return id; }

    public String getTipoBase() { return tipoBase; }
    public void setTipoBase(String tipoBase) { this.tipoBase = tipoBase; }

    public Double getKgBaseCorte() { return kgBaseCorte; }
    public void setKgBaseCorte(Double kgBaseCorte) { this.kgBaseCorte = kgBaseCorte; }

    public Double getCantidadRequeridaot() { return cantidadRequeridaot; }
    public void setCantidadRequeridaot(Double cantidadRequeridaot) { this.cantidadRequeridaot = cantidadRequeridaot; }

    public Double getKgRefilado() { return kgRefilado; }
    public void setKgRefilado(Double kgRefilado) { this.kgRefilado = kgRefilado; }

    public Double getKgDesperdicio() { return kgDesperdicio; }
    public void setKgDesperdicio(Double kgDesperdicio) { this.kgDesperdicio = kgDesperdicio; }

    public Double getKgRefil() { return kgRefil; }
    public void setKgRefil(Double kgRefil) { this.kgRefil = kgRefil; }

    public Double getDifBaseVsRefiladoTotal() { return difBaseVsRefiladoTotal; }
    public void setDifBaseVsRefiladoTotal(Double difBaseVsRefiladoTotal) { this.difBaseVsRefiladoTotal = difBaseVsRefiladoTotal; }

    public Double getPorcentajeEntrega() { return porcentajeEntrega; }
    public void setPorcentajeEntrega(Double porcentajeEntrega) { this.porcentajeEntrega = porcentajeEntrega; }

    public Double getRetorno() { return retorno; }
    public void setRetorno(Double retorno) { this.retorno = retorno; }

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
}