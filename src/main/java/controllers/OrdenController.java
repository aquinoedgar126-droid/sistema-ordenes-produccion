package com.bopisa.controlordenes.controllers;

import com.bopisa.controlordenes.entities.Orden;
import com.bopisa.controlordenes.repositories.OrdenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/ordenes")
public class OrdenController {

    private final OrdenRepository repo;

    public OrdenController(OrdenRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ordenes", repo.findAll());
        return "ordenes/list";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("orden", new Orden());
        return "ordenes/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Orden orden, Model model) {

        if (orden.getOt() == null || orden.getOt().trim().isEmpty()) {
            model.addAttribute("error", "La OT es obligatoria.");
            return "ordenes/form";
        }

        String ot = orden.getOt().trim();
        if (repo.existsByOt(ot)) {
            model.addAttribute("error", "Esa OT ya existe.");
            return "ordenes/form";
        }

        orden.setOt(ot);
        repo.save(orden);
        return "redirect:/ordenes";
    }
    @GetMapping("/{id}")
    public String verDetalle(@PathVariable Long id, Model model) {
    Optional<Orden> ordenOpt = repo.findById(id);

    if (ordenOpt.isEmpty()) {
        return "redirect:/ordenes";
    }

    model.addAttribute("orden", ordenOpt.get());
    return "ordenes/detalle";
}
}