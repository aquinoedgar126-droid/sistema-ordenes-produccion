package com.bopisa.controlordenes.repositories;

import com.bopisa.controlordenes.entities.Impresion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImpresionRepository extends JpaRepository<Impresion, Long> {
    Impresion findByOrdenId(Long ordenId);
}