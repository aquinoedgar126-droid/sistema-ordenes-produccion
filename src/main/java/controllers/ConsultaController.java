package com.bopisa.controlordenes.controllers;

import com.bopisa.controlordenes.entities.Impresion;
import com.bopisa.controlordenes.entities.Laminacion;
import com.bopisa.controlordenes.entities.Orden;
import com.bopisa.controlordenes.entities.Refilado;
import com.bopisa.controlordenes.repositories.ImpresionRepository;
import com.bopisa.controlordenes.repositories.LaminacionRepository;
import com.bopisa.controlordenes.repositories.OrdenRepository;
import com.bopisa.controlordenes.repositories.RefiladoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/consulta")
public class ConsultaController {

    private final OrdenRepository ordenRepo;
    private final ImpresionRepository impresionRepo;
    private final LaminacionRepository laminacionRepo;
    private final RefiladoRepository refiladoRepo;

    public ConsultaController(
            OrdenRepository ordenRepo,
            ImpresionRepository impresionRepo,
            LaminacionRepository laminacionRepo,
            RefiladoRepository refiladoRepo) {
        this.ordenRepo = ordenRepo;
        this.impresionRepo = impresionRepo;
        this.laminacionRepo = laminacionRepo;
        this.refiladoRepo = refiladoRepo;
    }

    @GetMapping
    public String formularioBusqueda() {
        return "consulta/index";
    }

    @PostMapping("/buscar")
    public String buscar(@RequestParam("ot") String ot, Model model) {
        if (ot == null || ot.trim().isEmpty()) {
            model.addAttribute("error", "Debes capturar una OT.");
            return "consulta/index";
        }

        Orden orden = ordenRepo.findByOt(ot.trim());

        if (orden == null) {
            model.addAttribute("error", "No se encontró la OT capturada.");
            return "consulta/index";
        }

        Impresion impresion = impresionRepo.findByOrdenId(orden.getId());
        Laminacion laminacion = laminacionRepo.findByOrdenId(orden.getId());
        Refilado refilado = refiladoRepo.findByOrdenId(orden.getId());

        Double porcentajeEntregaFinal = null;
        String estadoFinal = "SIN DATOS";

        if (refilado != null) {
            porcentajeEntregaFinal = refilado.getPorcentajeEntrega();

            if (porcentajeEntregaFinal != null) {
                if (porcentajeEntregaFinal >= 100) {
                    estadoFinal = "VERDE";
                } else if (porcentajeEntregaFinal >= 95) {
                    estadoFinal = "AMARILLO";
                } else {
                    estadoFinal = "ROJO";
                }
            }
        }

        model.addAttribute("orden", orden);
        model.addAttribute("impresion", impresion);
        model.addAttribute("laminacion", laminacion);
        model.addAttribute("refilado", refilado);
        model.addAttribute("porcentajeEntregaFinal", porcentajeEntregaFinal);
        model.addAttribute("estadoFinal", estadoFinal);

        return "consulta/index";
    }
}