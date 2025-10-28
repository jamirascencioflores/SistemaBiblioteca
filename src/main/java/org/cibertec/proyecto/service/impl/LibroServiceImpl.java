package org.cibertec.proyecto.service.impl;

import org.cibertec.proyecto.entity.LibroEntity;
import org.cibertec.proyecto.repository.LibroRepository;
import org.cibertec.proyecto.service.LibroService;
import org.springframework.stereotype.Service;

@Service
public class LibroServiceImpl extends GenericServiceImpl<LibroEntity, Integer> implements LibroService {

    public LibroServiceImpl(LibroRepository repository) {
        super(repository);
    }
}
