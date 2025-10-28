package org.cibertec.proyecto.service.impl;

import org.cibertec.proyecto.entity.PrestamoEntity;
import org.cibertec.proyecto.repository.PrestamoRepository;
import org.cibertec.proyecto.service.PrestamoService;
import org.springframework.stereotype.Service;

@Service
public class PrestamoServiceImpl extends GenericServiceImpl<PrestamoEntity, Integer> implements PrestamoService {
    public PrestamoServiceImpl(PrestamoRepository repository) {
        super(repository);
    }
}
