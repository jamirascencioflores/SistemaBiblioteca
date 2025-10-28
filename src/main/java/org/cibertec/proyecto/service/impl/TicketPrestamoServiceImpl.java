package org.cibertec.proyecto.service.impl;

import org.cibertec.proyecto.entity.TicketPrestamoEntity;
import org.cibertec.proyecto.repository.TicketPrestamoRepository;
import org.cibertec.proyecto.service.TicketPrestamoService;
import org.springframework.stereotype.Service;

@Service
public class TicketPrestamoServiceImpl extends GenericServiceImpl<TicketPrestamoEntity, Integer> implements TicketPrestamoService {
    public TicketPrestamoServiceImpl(TicketPrestamoRepository repository) {
        super(repository);
    }
}