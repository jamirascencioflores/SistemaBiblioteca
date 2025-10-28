package org.cibertec.proyecto.controller;

import lombok.RequiredArgsConstructor;
import org.cibertec.proyecto.entity.LibroEntity;
import org.cibertec.proyecto.service.AutorService;
import org.cibertec.proyecto.service.CategoriaService;
import org.cibertec.proyecto.service.LibroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/consulta")
@RequiredArgsConstructor
public class ConsultaController {

    private final LibroService libroService;
    private final AutorService autorService;
    private final CategoriaService categoriaService;

    @GetMapping
    public String mostrarConsulta(Model model,
                                  @RequestParam(required = false) String titulo,
                                  @RequestParam(required = false) Integer autorId,
                                  @RequestParam(required = false) Integer categoriaId) {

        List<LibroEntity> libros = libroService.getAll();

        List<LibroEntity> filtrados = libros.stream()
                .filter(l -> titulo == null || titulo.isEmpty() || l.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .filter(l -> autorId == null || l.getAutor().getIdAutor().equals(autorId))
                .filter(l -> categoriaId == null || l.getCategoria().getIdCategoria().equals(categoriaId))
                .toList();

        model.addAttribute("resultados", filtrados);
        model.addAttribute("listaAutores", autorService.getAll());
        model.addAttribute("listaCategorias", categoriaService.getAll());

        return "consulta/consulta";
    }
}
