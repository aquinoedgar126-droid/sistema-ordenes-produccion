package com.bopisa.controlordenes.controllers;

import com.bopisa.controlordenes.entities.Impresion;
import com.bopisa.controlordenes.entities.Laminacion;
import com.bopisa.controlordenes.entities.Orden;
import com.bopisa.controlordenes.repositories.ImpresionRepository;
import com.bopisa.controlordenes.repositories.LaminacionRepository;
import com.bopisa.controlordenes.repositories.OrdenRepository;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/laminacion")
public class LaminacionController {

    private final LaminacionRepository laminacionRepo;
    private final OrdenRepository ordenRepo;
    private final ImpresionRepository impresionRepo;

    public LaminacionController(LaminacionRepository laminacionRepo, OrdenRepository ordenRepo, ImpresionRepository impresionRepo) {
        this.laminacionRepo = laminacionRepo;
        this.ordenRepo = ordenRepo;
        this.impresionRepo = impresionRepo;
    }

    @GetMapping
    public String listar(Model model) {
        List<Laminacion> laminaciones = laminacionRepo.findAll();
        model.addAttribute("laminaciones", laminaciones);
        return "laminacion/list";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("modoEdicion", false);
        return "laminacion/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Laminacion laminacion = laminacionRepo.findById(id).orElse(null);

        if (laminacion == null) {
            return "redirect:/laminacion";
        }

        model.addAttribute("laminacion", laminacion);
        model.addAttribute("orden", laminacion.getOrden());
        model.addAttribute("modoEdicion", true);

        return "laminacion/form";
    }

    @PostMapping("/guardar")
    public String guardar(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam("ot") String ot,
            @RequestParam(value = "kgSurtidoRespaldo", required = false) Double kgSurtidoRespaldo,
            @RequestParam(value = "kgLaminado", required = false) Double kgLaminado,
            @RequestParam(value = "kgDesperdicio", required = false) Double kgDesperdicio,
            @RequestParam(value = "kgDesperdicioEdit", required = false) Double kgDesperdicioEdit,
            @RequestParam(value = "metrosLaminados", required = false) Double metrosLaminados,
            @RequestParam(value = "retornoReal", required = false) Double retornoReal,
            @RequestParam(value = "codigoMaterial", required = false) String codigoMaterial,
            @RequestParam(value = "descripcionMaterial", required = false) String descripcionMaterial,
            @RequestParam(value = "kgReqMaterial", required = false) Double kgReqMaterial,
            @RequestParam(value = "kgSurtMaterial", required = false) Double kgSurtMaterial
    ) {

        if (ot == null || ot.trim().isEmpty()) {
            return "redirect:/laminacion/nueva";
        }

        Orden orden = ordenRepo.findByOt(ot.trim());
        if (orden == null) {
            return "redirect:/laminacion/nueva";
        }

        Impresion impresion = impresionRepo.findByOrdenId(orden.getId());
        Double kgImpreso = (impresion != null) ? impresion.getKgImpreso() : null;

        Double kgEstimadoLaminar = null;
        Double kgAdhesivo = null;
        Double difBaseLaminacion = null;
        Double difTotalLaminacion = null;

        if (kgImpreso != null && kgSurtidoRespaldo != null) {
            kgEstimadoLaminar = kgImpreso + kgSurtidoRespaldo;
        }

        if (metrosLaminados != null) {
            kgAdhesivo = metrosLaminados * 0.0018;
        }

        if (kgEstimadoLaminar != null && kgLaminado != null && kgDesperdicio != null) {
            difBaseLaminacion = kgEstimadoLaminar - kgLaminado - kgDesperdicio;
        }

        if (kgEstimadoLaminar != null && kgLaminado != null && kgDesperdicio != null && kgAdhesivo != null) {
            difTotalLaminacion = kgEstimadoLaminar - kgLaminado - kgDesperdicio - kgAdhesivo;
        }
        
            String estadoProceso = "Sin dato";

        if (difBaseLaminacion != null) {
            if (difBaseLaminacion >= -30 && difBaseLaminacion <= 30) {
                estadoProceso = "Secuencia lam completada";
            } else {
                estadoProceso = "Revisar exceso o faltante";
            }
        }

        Laminacion laminacion;

        if (id != null) {
            laminacion = laminacionRepo.findById(id).orElse(new Laminacion());
        } else {
            Laminacion laminacionExistente = laminacionRepo.findByOrdenId(orden.getId());
            if (laminacionExistente != null) {
                laminacion = laminacionExistente;
            } else {
                laminacion = new Laminacion();
            }
        }

        laminacion.setOrden(orden);
        laminacion.setKgSurtidoRespaldo(kgSurtidoRespaldo);
        laminacion.setKgEstimadoLaminar(kgEstimadoLaminar);
        laminacion.setKgLaminado(kgLaminado);
        laminacion.setKgDesperdicio(kgDesperdicio);
        laminacion.setMetrosLaminados(metrosLaminados);
        laminacion.setKgAdhesivo(kgAdhesivo);
        laminacion.setDifBaseLaminacion(difBaseLaminacion);
        laminacion.setDifTotalLaminacion(difTotalLaminacion);
        laminacion.setRetornoReal(retornoReal);
        laminacion.setKgDesperdicioEdit(kgDesperdicioEdit);
        laminacion.setEstadoProceso(estadoProceso);
        laminacion.setCodigoMaterial(codigoMaterial);
        laminacion.setDescripcionMaterial(descripcionMaterial);
        laminacion.setKgReqMaterial(kgReqMaterial);
        laminacion.setKgSurtMaterial(kgSurtMaterial);

        laminacionRepo.save(laminacion);

        return "redirect:/laminacion";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        if (laminacionRepo.existsById(id)) {
            laminacionRepo.deleteById(id);
        }
        return "redirect:/laminacion";
    }
}