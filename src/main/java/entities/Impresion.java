package com.bopisa.controlordenes.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "impresion")
public class Impresion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double kgReqSurt;
    private Double kgSurtido;
    private Double anchoMaterial;
    private Double kgImpreso;
    private Double kgDesperdicio;
    private Double metros;
    private Double metrosCuadrados;
    private Double kgTinta;
    private Double difSurtVsImpreso;
    private Double retornoReal;
    private Double kgDesperdicioEdit;
    private String estadoProceso;
    private String codigoMaterial;
    private String descripcionMaterial;
    private Double kgReqMaterial;
    private Double kgSurtMaterial;

    @OneToOne
    @JoinColumn(name = "orden_id", nullable = false, unique = true)
    private Orden orden;

    public Long getId() {
        return id;
    }

    public Double getKgReqSurt() {
        return kgReqSurt;
    }

    public void setKgReqSurt(Double kgReqSurt) {
        this.kgReqSurt = kgReqSurt;
    }

    public Double getKgSurtido() {
        return kgSurtido;
    }

    public void setKgSurtido(Double kgSurtido) {
        this.kgSurtido = kgSurtido;
    }

    public Double getAnchoMaterial() {
        return anchoMaterial;
    }

    public void setAnchoMaterial(Double anchoMaterial) {
        this.anchoMaterial = anchoMaterial;
    }

    public Double getKgImpreso() {
        return kgImpreso;
    }

    public void setKgImpreso(Double kgImpreso) {
        this.kgImpreso = kgImpreso;
    }

    public Double getKgDesperdicio() {
        return kgDesperdicio;
    }

    public void setKgDesperdicio(Double kgDesperdicio) {
        this.kgDesperdicio = kgDesperdicio;
    }

    public Double getMetros() {
        return metros;
    }

    public void setMetros(Double metros) {
        this.metros = metros;
    }

    public Double getMetrosCuadrados() {
        return metrosCuadrados;
    }

    public void setMetrosCuadrados(Double metrosCuadrados) {
        this.metrosCuadrados = metrosCuadrados;
    }

    public Double getKgTinta() {
        return kgTinta;
    }

    public void setKgTinta(Double kgTinta) {
        this.kgTinta = kgTinta;
    }

    public Double getDifSurtVsImpreso() {
        return difSurtVsImpreso;
    }

    public void setDifSurtVsImpreso(Double difSurtVsImpreso) {
        this.difSurtVsImpreso = difSurtVsImpreso;
    }

    public Double getRetornoReal() {
        return retornoReal;
    }

    public void setRetornoReal(Double retornoReal) {
        this.retornoReal = retornoReal;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }
    
    public Double getKgDesperdicioEdit(){
        return kgDesperdicioEdit;
    }
    
    public void setKgDesperdicioEdit(Double kgDesperdicioEdit){
        this.kgDesperdicioEdit = kgDesperdicioEdit;
    }
    
    public String getEstadoProceso(){
        return estadoProceso;
    }
    
    public void setEstadoProceso(String estadoProceso){
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
