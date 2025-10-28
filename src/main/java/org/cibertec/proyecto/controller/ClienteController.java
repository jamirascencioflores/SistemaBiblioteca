package org.cibertec.proyecto.controller;

import org.cibertec.proyecto.entity.ClienteEntity;
import org.cibertec.proyecto.entity.PrestamoEntity;
import org.cibertec.proyecto.entity.TicketPrestamoEntity;
import org.cibertec.proyecto.service.ClienteService;
import org.cibertec.proyecto.service.LibroService;
import org.cibertec.proyecto.service.PrestamoService;
import org.cibertec.proyecto.service.TicketPrestamoService;
import org.cibertec.proyecto.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private LibroService libroService;

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private TicketPrestamoService ticketPrestamoService;

    @Autowired
    private StockService stockService;

    @GetMapping("/lista")
    public String listaClientes(Model model) {
        model.addAttribute("clientes", clienteService.getAll());
        return "cliente/lista";
    }

    @GetMapping("/registrar")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("cliente", new ClienteEntity());
        return "cliente/registrar";
    }

    @PostMapping("/registrar")
    public String registrarCliente(@ModelAttribute ClienteEntity cliente) {
        boolean existeDni = clienteService.getAll().stream()
                .anyMatch(c -> c.getDni().equals(cliente.getDni()));

        if (existeDni) {
            return "redirect:/cliente/registrar?error=true";
        }

        clienteService.create(cliente);
        return "redirect:/cliente/lista";
    }


    @GetMapping("/prestamo")
    public String mostrarFormularioPrestamo(Model model) {
        model.addAttribute("clientes", clienteService.getAll());
        model.addAttribute("libros", libroService.getAll());
        model.addAttribute("prestamo", new PrestamoEntity());
        return "cliente/prestamo";
    }

    @PostMapping("/prestamo")
    public String registrarPrestamo(@ModelAttribute PrestamoEntity prestamo, Model model) {
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamoService.create(prestamo);

        TicketPrestamoEntity ticket = new TicketPrestamoEntity();
        ticket.setPrestamo(prestamo);
        ticket.setResumen("Préstamo del libro: " + prestamo.getLibro().getTitulo());
        ticket.setFechaGenerado(LocalDateTime.now());
        ticketPrestamoService.create(ticket);

        stockService.actualizarStockAlPrestar(prestamo.getLibro());

        return "redirect:/cliente/prestamo";
    }
}