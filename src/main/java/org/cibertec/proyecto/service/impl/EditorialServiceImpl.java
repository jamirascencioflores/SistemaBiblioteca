package org.cibertec.proyecto.service.impl;

import org.cibertec.proyecto.entity.EditorialEntity;
import org.cibertec.proyecto.repository.EditorialRepository;
import org.cibertec.proyecto.service.EditorialService;
import org.springframework.stereotype.Service;

@Service
public class EditorialServiceImpl extends GenericServiceImpl<EditorialEntity, Integer> implements EditorialService {

    public EditorialServiceImpl(EditorialRepository repository) {
        super(repository);
    }
}
