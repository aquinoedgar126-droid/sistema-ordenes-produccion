package com.bopisa.controlordenes.repositories;

import com.bopisa.controlordenes.entities.Laminacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaminacionRepository extends JpaRepository<Laminacion, Long> {
    Laminacion findByOrdenId(Long ordenId);
}