package org.cibertec.proyecto.controller;

import lombok.RequiredArgsConstructor;
import org.cibertec.proyecto.entity.CategoriaEntity;
import org.cibertec.proyecto.service.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categoria")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping("/lista")
    public String listarCategorias(Model model) {
        model.addAttribute("lista", categoriaService.getAll());
        return "categoria/lista";
    }

    @GetMapping("/registrar")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("categoria", new CategoriaEntity());
        return "categoria/registro";
    }

    @PostMapping("/guardar")
    public String guardarCategoria(@ModelAttribute CategoriaEntity categoria) {
        if (categoria.getIdCategoria() == null) {
            categoriaService.create(categoria);
        } else {
            categoriaService.modify(categoria);
        }
        return "redirect:/categoria/lista";
    }

    @GetMapping("/editar/{id}")
    public String editarCategoria(@PathVariable Integer id, Model model) {
        CategoriaEntity categoria = categoriaService.getById(id);
        model.addAttribute("categoria", categoria);
        return "categoria/registro";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Integer id) {
        categoriaService.remove(id);
        return "redirect:/categoria/lista";
    }
}
