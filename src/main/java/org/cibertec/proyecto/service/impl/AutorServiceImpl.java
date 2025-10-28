package org.cibertec.proyecto.service.impl;

import org.cibertec.proyecto.entity.AutorEntity;
import org.cibertec.proyecto.repository.AutorRepository;
import org.cibertec.proyecto.service.AutorService;
import org.springframework.stereotype.Service;

@Service
public class AutorServiceImpl extends GenericServiceImpl<AutorEntity, Integer> implements AutorService {

    public AutorServiceImpl(AutorRepository repository) {
        super(repository);
    }
}
