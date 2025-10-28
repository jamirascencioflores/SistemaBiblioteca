package org.cibertec.proyecto.service.impl;

import org.cibertec.proyecto.entity.ClienteEntity;
import org.cibertec.proyecto.repository.ClienteRepository;
import org.cibertec.proyecto.service.ClienteService;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl extends GenericServiceImpl<ClienteEntity, Integer> implements ClienteService {

    private final ClienteRepository repository;

    public ClienteServiceImpl(ClienteRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public ClienteEntity findByDni(String dni) {
        return repository.findByDni(dni);
    }
}
