package org.cibertec.proyecto.controller;

import lombok.RequiredArgsConstructor;
import org.cibertec.proyecto.entity.AutorEntity;
import org.cibertec.proyecto.service.AutorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/autor")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;

    @GetMapping("/lista")
    public String listarAutores(Model model) {
        model.addAttribute("autor", new AutorEntity());
        model.addAttribute("lista", autorService.getAll());
        return "autor/lista";
    }

    @PostMapping("/guardar")
    public String guardarAutor(@ModelAttribute AutorEntity autor) {
        if (autor.getIdAutor() == null) {
            autorService.create(autor);
        } else {
            autorService.modify(autor);
        }
        return "redirect:/autor/lista";
    }

    @GetMapping("/editar/{id}")
    public String editarAutor(@PathVariable Integer id, Model model) {
        model.addAttribute("autor", autorService.getById(id));
        model.addAttribute("lista", autorService.getAll());
        return "autor/lista";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarAutor(@PathVariable Integer id) {
        autorService.remove(id);
        return "redirect:/autor/lista";
    }
}
