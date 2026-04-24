package com.bopisa.controlordenes.controllers;

import com.bopisa.controlordenes.entities.Impresion;
import com.bopisa.controlordenes.entities.Orden;
import com.bopisa.controlordenes.repositories.ImpresionRepository;
import com.bopisa.controlordenes.repositories.OrdenRepository;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/impresion")
public class ImpresionController {

    private final ImpresionRepository impresionRepo;
    private final OrdenRepository ordenRepo;

    public ImpresionController(ImpresionRepository impresionRepo, OrdenRepository ordenRepo) {
        this.impresionRepo = impresionRepo;
        this.ordenRepo = ordenRepo;
    }

    @GetMapping
    public String listar(Model model) {
        List<Impresion> impresiones = impresionRepo.findAll();
        model.addAttribute("impresiones", impresiones);
        return "impresion/list";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("modoEdicion", false);
        return "impresion/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Impresion impresion = impresionRepo.findById(id).orElse(null);

        if (impresion == null) {
            return "redirect:/impresion";
        }

        model.addAttribute("impresion", impresion);
        model.addAttribute("orden", impresion.getOrden());
        model.addAttribute("modoEdicion", true);

        return "impresion/form";
    }

    @PostMapping("/guardar")
    public String guardar(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam("ot") String ot,
            @RequestParam("codigo") String codigo,
            @RequestParam("producto") String producto,
            @RequestParam(value = "kgReqSurt", required = false) Double kgReqSurt,
            @RequestParam(value = "kgSurtido", required = false) Double kgSurtido,
            @RequestParam(value = "anchoMaterial", required = false) Double anchoMaterial,
            @RequestParam(value = "kgImpreso", required = false) Double kgImpreso,
            @RequestParam(value = "kgDesperdicio", required = false) Double kgDesperdicio,
            @RequestParam(value = "kgDesperdicioEdit", required = false) Double kgDesperdicioEdit,
            @RequestParam(value = "metros", required = false) Double metros,
            @RequestParam(value = "retornoReal", required = false) Double retornoReal,
            @RequestParam(value = "codigoMaterial", required = false) String codigoMaterial,
            @RequestParam(value = "descripcionMaterial", required = false) String descripcionMaterial,
            @RequestParam(value = "kgReqMaterial", required = false) Double kgReqMaterial,
            @RequestParam(value = "kgSurtMaterial", required = false) Double kgSurtMaterial
    ) {

        if (ot == null || ot.trim().isEmpty()) {
            return "redirect:/impresion/nueva";
        }

        String otLimpia = ot.trim();

        Orden orden;
        if (ordenRepo.existsByOt(otLimpia)) {
            orden = ordenRepo.findByOt(otLimpia);
        } else {
            orden = new Orden();
            orden.setOt(otLimpia);
        }

        orden.setCodigo(codigo);
        orden.setProducto(producto);
        orden = ordenRepo.save(orden);

        Double metrosCuadrados = null;
        Double kgTinta = null;
        Double difSurtVsImpreso = null;

        if (anchoMaterial != null && metros != null) {
            metrosCuadrados = (anchoMaterial / 100.0) * metros;
        }

        if (metros != null) {
            kgTinta = metros * 0.0005;
        }

        if (kgSurtido != null && kgImpreso != null && kgDesperdicio != null && kgTinta != null) {
            difSurtVsImpreso = kgSurtido - kgImpreso - kgDesperdicio - kgTinta;
        }
        
        String estadoProceso = "Sin dato";
        
        if (difSurtVsImpreso != null){
            if (difSurtVsImpreso >= -30 && difSurtVsImpreso <= 30){
                estadoProceso = "Secuencia imp completada";
            }
            else{
             estadoProceso = "Revisar exceso o faltante";   
            }
        }

        Impresion impresion;

        if (id != null) {
            impresion = impresionRepo.findById(id).orElse(new Impresion());
        } else {
            Impresion impresionExistente = impresionRepo.findByOrdenId(orden.getId());
            if (impresionExistente != null) {
                impresion = impresionExistente;
            } else {
                impresion = new Impresion();
            }
        }

        impresion.setOrden(orden);
        impresion.setKgReqSurt(kgReqSurt);
        impresion.setKgSurtido(kgSurtido);
        impresion.setAnchoMaterial(anchoMaterial);
        impresion.setKgImpreso(kgImpreso);
        impresion.setKgDesperdicio(kgDesperdicio);
        impresion.setMetros(metros);
        impresion.setMetrosCuadrados(metrosCuadrados);
        impresion.setKgTinta(kgTinta);
        impresion.setDifSurtVsImpreso(difSurtVsImpreso);
        impresion.setRetornoReal(retornoReal);
        impresion.setKgDesperdicioEdit(kgDesperdicioEdit);
        impresion.setEstadoProceso(estadoProceso);
        impresion.setCodigoMaterial(codigoMaterial);
        impresion.setDescripcionMaterial(descripcionMaterial);
        impresion.setKgReqMaterial(kgReqMaterial);
        impresion.setKgSurtMaterial(kgSurtMaterial);
        

        impresionRepo.save(impresion);

        return "redirect:/impresion";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        if (impresionRepo.existsById(id)) {
            impresionRepo.deleteById(id);
        }
        return "redirect:/impresion";
    }
}