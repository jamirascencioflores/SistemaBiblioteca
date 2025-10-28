package org.cibertec.proyecto.repository;

import org.cibertec.proyecto.entity.EditorialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepository extends JpaRepository<EditorialEntity, Integer> {
}

