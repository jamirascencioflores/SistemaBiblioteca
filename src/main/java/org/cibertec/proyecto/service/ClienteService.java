package org.cibertec.proyecto.service;

import org.cibertec.proyecto.entity.ClienteEntity;

public interface ClienteService extends GenericService<ClienteEntity, Integer> {
    ClienteEntity findByDni(String dni);
}
