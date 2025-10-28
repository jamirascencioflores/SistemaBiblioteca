package org.cibertec.proyecto.service.impl;

import org.cibertec.proyecto.entity.LibroEntity;
import org.cibertec.proyecto.entity.StockEntity;
import org.cibertec.proyecto.repository.StockRepository;
import org.cibertec.proyecto.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl extends GenericServiceImpl<StockEntity, Integer> implements StockService {

    private final StockRepository stockRepository;

    @Autowired
    public StockServiceImpl(StockRepository stockRepository) {
        super(stockRepository);
        this.stockRepository = stockRepository;
    }

    @Override
    public StockEntity getByLibro(LibroEntity libro) {
        return stockRepository.findByLibro(libro).orElse(null);
    }

    @Override
    public void actualizarStockAlPrestar(LibroEntity libro, int cantidad) {
        StockEntity stock = getByLibro(libro);
        if (stock != null && stock.getDisponible() >= cantidad) {
            stock.setDisponible(stock.getDisponible() - cantidad);
            stock.setPrestado(stock.getPrestado() + cantidad);
            stockRepository.save(stock);
        } else {
            throw new IllegalArgumentException("Stock insuficiente para el libro: " + libro.getTitulo());
        }
    }

    @Override
    public void actualizarStockAlDevolver(LibroEntity libro) {
        StockEntity stock = getByLibro(libro);
        if (stock != null && stock.getPrestado() > 0) {
            stock.setDisponible(stock.getDisponible() + 1);
            stock.setPrestado(stock.getPrestado() - 1);
            stockRepository.save(stock);
        }
    }

    @Override
    public void actualizarStockAlPrestar(LibroEntity libro) {
        actualizarStockAlPrestar(libro, 1);
    }
}
