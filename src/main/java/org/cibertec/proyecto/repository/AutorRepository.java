package org.cibertec.proyecto.repository;

import org.cibertec.proyecto.entity.AutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<AutorEntity, Integer> {
}

