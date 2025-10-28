package org.cibertec.proyecto.controller;

import lombok.RequiredArgsConstructor;
import org.cibertec.proyecto.entity.StockEntity;
import org.cibertec.proyecto.service.LibroService;
import org.cibertec.proyecto.service.StockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;
    private final LibroService libroService;

    @GetMapping("/lista")
    public String listarStock(Model model) {
        model.addAttribute("lista", stockService.getAll());
        return "stock/lista";
    }

    @GetMapping("/registrar")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("stock", new StockEntity());
        model.addAttribute("libros", libroService.getAll());
        return "stock/registro";
    }

    @PostMapping("/guardar")
    public String guardarStock(@ModelAttribute StockEntity stock) {
        if (stock.getIdStock() == null) {
            stockService.create(stock);
        } else {
            stockService.modify(stock);
        }
        return "redirect:/stock/lista";
    }

    @GetMapping("/editar/{id}")
    public String editarStock(@PathVariable Integer id, Model model) {
        StockEntity stock = stockService.getById(id);
        model.addAttribute("stock", stock);
        model.addAttribute("libros", libroService.getAll());
        return "stock/registro";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarStock(@PathVariable Integer id) {
        stockService.remove(id);
        return "redirect:/stock/lista";
    }
}
