package com.bopisa.controlordenes.repositories;

import com.bopisa.controlordenes.entities.Refilado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefiladoRepository extends JpaRepository<Refilado, Long> {
    Refilado findByOrdenId(Long ordenId);
}