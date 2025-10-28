package org.cibertec.proyecto.controller;

import org.cibertec.proyecto.entity.TicketPrestamoEntity;
import org.cibertec.proyecto.service.TicketPrestamoService;
import org.cibertec.proyecto.service.impl.PdfGeneratorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.OutputStream;

import java.util.List;

@Controller
@RequestMapping("/ticket")
public class TicketPrestamoController {

    @Autowired
    private TicketPrestamoService ticketService;

    @Autowired
    private PdfGeneratorServiceImpl pdfGeneratorService;


    @GetMapping("/buscar")
    public String buscarTicketPorId(@RequestParam("id") Integer id, Model model) {
        TicketPrestamoEntity ticket = ticketService.getById(id);

        if (ticket == null) {
            model.addAttribute("error", "❌ No se encontró ningún ticket con ID: " + id);
            model.addAttribute("tickets", ticketService.getAll());  // Mostrar la lista completa también
            return "ticket/lista";
        }

        model.addAttribute("ticketEncontrado", ticket);
        model.addAttribute("tickets", ticketService.getAll());
        return "ticket/lista";
    }


    @GetMapping("/lista")
    public String listarTickets(Model model) {
        List<TicketPrestamoEntity> tickets = ticketService.getAll();
        model.addAttribute("tickets", tickets);
        return "ticket/lista";
    }

    @GetMapping("/ver/{id}")
    public String verDetalleTicket(@PathVariable("id") Integer id, Model model) {
        TicketPrestamoEntity ticket = ticketService.getById(id);
        if (ticket == null) {
            return "redirect:/ticket/lista";
        }
        model.addAttribute("ticket", ticket);
        return "ticket/detalle";
    }

    @GetMapping("/ver-pdf/{id}")
    public void verTicketPdf(@PathVariable("id") Integer id, HttpServletResponse response) {
        TicketPrestamoEntity ticket = ticketService.getById(id);
        if (ticket != null) {
            try {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline; filename=ticket_" + id + ".pdf");

                OutputStream out = response.getOutputStream();
                pdfGeneratorService.generarTicketPDF(ticket, out);
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
