package org.cibertec.proyecto.repository;

import org.cibertec.proyecto.entity.LibroEntity;
import org.cibertec.proyecto.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<StockEntity, Integer> {
    Optional<StockEntity> findByLibro(LibroEntity libro);
}


