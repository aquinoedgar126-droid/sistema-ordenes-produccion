package com.bopisa.controlordenes.repositories;

import com.bopisa.controlordenes.entities.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenRepository extends JpaRepository<Orden, Long> {
    boolean existsByOt(String ot);
    Orden findByOt(String ot);
}