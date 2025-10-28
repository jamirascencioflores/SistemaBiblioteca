package org.cibertec.proyecto.repository;

import org.cibertec.proyecto.entity.LibroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<LibroEntity, Integer> {
}

