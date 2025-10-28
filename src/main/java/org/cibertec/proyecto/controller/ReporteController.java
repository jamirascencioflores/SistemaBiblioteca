package org.cibertec.proyecto.controller;

import lombok.RequiredArgsConstructor;
import org.cibertec.proyecto.entity.StockEntity;
import org.cibertec.proyecto.service.StockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/reporte")
@RequiredArgsConstructor
public class ReporteController {

    private final StockService stockService;

    @GetMapping("/stock")
    public String reporteStockPorCategoria(Model model) {
        List<StockEntity> stockList = stockService.getAll();

        // Agrupar por nombreCategoria
        Map<String, Map<String, Integer>> resumen = new LinkedHashMap<>();

        for (StockEntity stock : stockList) {
            String categoria = stock.getLibro().getCategoria().getNombreCategoria();

            resumen.putIfAbsent(categoria, new HashMap<>());
            Map<String, Integer> valores = resumen.get(categoria);

            valores.put("totalLibros", valores.getOrDefault("totalLibros", 0) + 1);
            int total = stock.getDisponible() + stock.getPrestado() + stock.getMantenimiento();
            valores.put("totalStock", valores.getOrDefault("totalStock", 0) + total);
        }

        model.addAttribute("resumen", resumen);
        return "reporte/reporte_stock";
    }
}
