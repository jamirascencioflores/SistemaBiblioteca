package org.cibertec.proyecto.service.impl;

import org.cibertec.proyecto.service.GenericService;
import org.springframework.data.jpa.repository.JpaRepository;

import org.cibertec.proyecto.entity.ClienteEntity;
import org.cibertec.proyecto.entity.LibroEntity;
import org.cibertec.proyecto.repository.ClienteRepository;
import org.cibertec.proyecto.repository.LibroRepository;
import org.cibertec.proyecto.service.GenericService;
import org.cibertec.proyecto.service.impl.GenericServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.List;

public abstract class GenericServiceImpl<T, ID> implements GenericService<T, ID> {

    private final JpaRepository<T, ID> repository;

    public GenericServiceImpl(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public T getById(ID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

    @Override
    public void create(T entity) {
        repository.save(entity);
    }

    @Override
    public void modify(T entity) {
        repository.save(entity);
    }

    @Override
    public void remove(ID id) {
        repository.deleteById(id);
    }
}
