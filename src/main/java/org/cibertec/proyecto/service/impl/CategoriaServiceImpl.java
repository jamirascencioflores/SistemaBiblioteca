package org.cibertec.proyecto.service.impl;

import org.cibertec.proyecto.entity.CategoriaEntity;
import org.cibertec.proyecto.repository.CategoriaRepository;
import org.cibertec.proyecto.service.CategoriaService;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl extends GenericServiceImpl<CategoriaEntity, Integer> implements CategoriaService {

    public CategoriaServiceImpl(CategoriaRepository repository) {
        super(repository);
    }
}
