package org.cibertec.proyecto.controller;

import lombok.RequiredArgsConstructor;
import org.cibertec.proyecto.entity.EditorialEntity;
import org.cibertec.proyecto.service.EditorialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/editorial")
@RequiredArgsConstructor
public class EditorialController {

    private final EditorialService editorialService;

    @GetMapping("/lista")
    public String listarEditoriales(Model model) {
        model.addAttribute("lista", editorialService.getAll());
        return "editorial/lista";
    }

    @GetMapping("/registrar")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("editorial", new EditorialEntity());
        return "editorial/registro";
    }

    @PostMapping("/guardar")
    public String guardarEditorial(@ModelAttribute EditorialEntity editorial) {
        if (editorial.getIdEditorial() == null) {
            editorialService.create(editorial);
        } else {
            editorialService.modify(editorial);
        }
        return "redirect:/editorial/lista";
    }

    @GetMapping("/editar/{id}")
    public String editarEditorial(@PathVariable Integer id, Model model) {
        EditorialEntity editorial = editorialService.getById(id);
        model.addAttribute("editorial", editorial);
        return "editorial/registro";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEditorial(@PathVariable Integer id) {
        editorialService.remove(id);
        return "redirect:/editorial/lista";
    }
}
