package org.cibertec.proyecto.service;

import org.cibertec.proyecto.entity.LibroEntity;
import org.cibertec.proyecto.entity.StockEntity;

public interface StockService extends GenericService<StockEntity, Integer> {

    StockEntity getByLibro(LibroEntity libro);

    void actualizarStockAlPrestar(LibroEntity libro);

    void actualizarStockAlPrestar(LibroEntity libro, int cantidad);

    void actualizarStockAlDevolver(LibroEntity libro);
}
