package com.bopisa.controlordenes.controllers;

import com.bopisa.controlordenes.entities.Impresion;
import com.bopisa.controlordenes.entities.Laminacion;
import com.bopisa.controlordenes.entities.Orden;
import com.bopisa.controlordenes.entities.Refilado;
import com.bopisa.controlordenes.repositories.ImpresionRepository;
import com.bopisa.controlordenes.repositories.LaminacionRepository;
import com.bopisa.controlordenes.repositories.OrdenRepository;
import com.bopisa.controlordenes.repositories.RefiladoRepository;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/refilado")
public class RefiladoController {

    private final RefiladoRepository refiladoRepo;
    private final OrdenRepository ordenRepo;
    private final ImpresionRepository impresionRepo;
    private final LaminacionRepository laminacionRepo;

    public RefiladoController(
            RefiladoRepository refiladoRepo,
            OrdenRepository ordenRepo,
            ImpresionRepository impresionRepo,
            LaminacionRepository laminacionRepo) {
        this.refiladoRepo = refiladoRepo;
        this.ordenRepo = ordenRepo;
        this.impresionRepo = impresionRepo;
        this.laminacionRepo = laminacionRepo;
    }

    @GetMapping
    public String listar(Model model) {
        List<Refilado> refilados = refiladoRepo.findAll();
        model.addAttribute("refilados", refilados);
        return "refilado/list";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("modoEdicion", false);
        return "refilado/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Refilado refilado = refiladoRepo.findById(id).orElse(null);

        if (refilado == null) {
            return "redirect:/refilado";
        }

        model.addAttribute("refilado", refilado);
        model.addAttribute("orden", refilado.getOrden());
        model.addAttribute("modoEdicion", true);

        return "refilado/form";
    }

    @PostMapping("/guardar")
    public String guardar(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam("ot") String ot,
            @RequestParam(value = "cantidadRequeridaot", required = false) Double cantidadRequeridaot,
            @RequestParam(value = "kgRefilado", required = false) Double kgRefilado,
            @RequestParam(value = "kgDesperdicio", required = false) Double kgDesperdicio,
            @RequestParam(value = "kgDesperdicioEdit", required = false) Double kgDesperdicioEdit,
            @RequestParam(value = "kgRefil", required = false) Double kgRefil,
            @RequestParam(value = "retorno", required = false) Double retorno
    ) {

        if (ot == null || ot.trim().isEmpty()) {
            return "redirect:/refilado/nueva";
        }

        Orden orden = ordenRepo.findByOt(ot.trim());
        if (orden == null) {
            return "redirect:/refilado/nueva";
        }

        String producto = orden.getProducto() != null ? orden.getProducto() : "";

        String tipoBase;
        Double kgBaseCorte = null;

        if (producto.contains("/")) {
            tipoBase = "LAMINADO";
            Laminacion laminacion = laminacionRepo.findByOrdenId(orden.getId());
            if (laminacion != null) {
                kgBaseCorte = laminacion.getKgLaminado();
            }
        } else {
            tipoBase = "IMPRESO";
            Impresion impresion = impresionRepo.findByOrdenId(orden.getId());
            if (impresion != null) {
                kgBaseCorte = impresion.getKgImpreso();
            }
        }

        Double difBaseVsRefiladoTotal = null;
        Double porcentajeEntrega = null;

        if (kgBaseCorte != null && kgRefilado != null && kgDesperdicio != null && kgRefil != null) {
            Double despEdit = (kgDesperdicioEdit != null) ? kgDesperdicioEdit : 0.0;
            difBaseVsRefiladoTotal = kgBaseCorte - kgRefilado - kgDesperdicio - kgRefil - despEdit;
        }

        if (cantidadRequeridaot != null && cantidadRequeridaot != 0 && kgRefilado != null) {
            porcentajeEntrega = (kgRefilado / cantidadRequeridaot) * 100.0;
        }
        
        String estadoProceso = "Sin dato";

        if (difBaseVsRefiladoTotal != null) {
        if (difBaseVsRefiladoTotal >= -30 && difBaseVsRefiladoTotal <= 30) {
        estadoProceso = "Secuencia ref completada";
            } else {
            estadoProceso = "Revisar exceso o faltante";
        }
        }

        Refilado refilado;

        if (id != null) {
            refilado = refiladoRepo.findById(id).orElse(new Refilado());
        } else {
            Refilado refiladoExistente = refiladoRepo.findByOrdenId(orden.getId());
            if (refiladoExistente != null) {
                refilado = refiladoExistente;
            } else {
                refilado = new Refilado();
            }
        }

        refilado.setOrden(orden);
        refilado.setTipoBase(tipoBase);
        refilado.setKgBaseCorte(kgBaseCorte);
        refilado.setCantidadRequeridaot(cantidadRequeridaot);
        refilado.setKgRefilado(kgRefilado);
        refilado.setKgDesperdicio(kgDesperdicio);
        refilado.setKgRefil(kgRefil);
        refilado.setDifBaseVsRefiladoTotal(difBaseVsRefiladoTotal);
        refilado.setPorcentajeEntrega(porcentajeEntrega);
        refilado.setRetorno(retorno);
        refilado.setKgDesperdicioEdit(kgDesperdicioEdit);
        refilado.setEstadoProceso(estadoProceso);
        

        refiladoRepo.save(refilado);

        return "redirect:/refilado";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        if (refiladoRepo.existsById(id)) {
            refiladoRepo.deleteById(id);
        }
        return "redirect:/refilado";
    }
}