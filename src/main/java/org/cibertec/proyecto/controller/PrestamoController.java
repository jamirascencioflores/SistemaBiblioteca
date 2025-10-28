package org.cibertec.proyecto.controller;

import com.itextpdf.text.*;
import org.cibertec.proyecto.entity.ClienteEntity;
import org.cibertec.proyecto.entity.LibroEntity;
import org.cibertec.proyecto.entity.PrestamoEntity;
import org.cibertec.proyecto.entity.TicketPrestamoEntity;
import org.cibertec.proyecto.service.ClienteService;
import org.cibertec.proyecto.service.LibroService;
import org.cibertec.proyecto.service.PrestamoService;
import org.cibertec.proyecto.service.StockService;
import org.cibertec.proyecto.service.TicketPrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;



@Controller
@RequestMapping("/prestamo")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private TicketPrestamoService ticketService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private LibroService libroService;

    @Autowired
    private StockService stockService;

    @GetMapping("/lista")
    public String listaPrestamos(Model model) {
        model.addAttribute("prestamos", prestamoService.getAll());
        return "prestamo/lista";
    }

    @GetMapping("/registrar")
    public String formularioSeleccion(Model model,
                                      @RequestParam(required = false) Integer clienteId,
                                      @RequestParam(required = false) Integer libroIdNuevo,
                                      @RequestParam(required = false) List<Integer> libroIds) {

        model.addAttribute("clientes", clienteService.getAll());
        model.addAttribute("libros", libroService.getAll());
        model.addAttribute("clienteSeleccionado", clienteId);

        if (libroIds == null) {
            libroIds = new ArrayList<>();
        }

        if (libroIdNuevo != null && !libroIds.contains(libroIdNuevo)) {
            libroIds.add(libroIdNuevo);
        }

        model.addAttribute("librosSeleccionados", libroIds);

        final List<Integer> libroIdsFinal = new ArrayList<>(libroIds);

        if (!libroIdsFinal.isEmpty()) {
            List<LibroEntity> librosFiltrados = libroService.getAll().stream()
                    .filter(libro -> libroIdsFinal.contains(libro.getIdLibro()))
                    .collect(Collectors.toList());
            model.addAttribute("librosFiltrados", librosFiltrados);
        }

        return "prestamo/registrar";
    }


    @PostMapping("/guardar")
    public String guardarPrestamo(@RequestParam("clienteId") Integer clienteId,
                                  @RequestParam("libroIds") List<Integer> libroIds,
                                  @RequestParam("cantidades") List<Integer> cantidades,
                                  Model model) {

        ClienteEntity cliente = clienteService.getById(clienteId);
        List<String> erroresStock = new ArrayList<>();

        for (int i = 0; i < libroIds.size(); i++) {
            LibroEntity libro = libroService.getById(libroIds.get(i));
            int cantidadSolicitada = cantidades.get(i);

            if (stockService.getByLibro(libro).getDisponible() < cantidadSolicitada) {
                erroresStock.add("No hay stock suficiente para el libro: " + libro.getTitulo());
            }
        }

        if (!erroresStock.isEmpty()) {
            model.addAttribute("clientes", clienteService.getAll());
            model.addAttribute("libros", libroService.getAll());
            model.addAttribute("clienteSeleccionado", clienteId);
            model.addAttribute("librosSeleccionados", libroIds);
            model.addAttribute("librosFiltrados", libroService.getAll().stream()
                    .filter(lib -> libroIds.contains(lib.getIdLibro()))
                    .collect(Collectors.toList()));
            model.addAttribute("errorStock", String.join(" | ", erroresStock));
            return "prestamo/registrar";
        }

        for (int i = 0; i < libroIds.size(); i++) {
            LibroEntity libro = libroService.getById(libroIds.get(i));
            int cantidad = cantidades.get(i);

            PrestamoEntity prestamo = new PrestamoEntity();
            prestamo.setCliente(cliente);
            prestamo.setLibro(libro);
            prestamo.setFechaPrestamo(LocalDate.now());
            prestamoService.create(prestamo);

            // Registrar ticket
            TicketPrestamoEntity ticket = new TicketPrestamoEntity();
            ticket.setPrestamo(prestamo);
            ticket.setResumen("Cliente: " + cliente.getNombreCompleto() + ", Libro: " + libro.getTitulo() + ", Cantidad: " + cantidad + ", Fecha: " + LocalDate.now());
            ticket.setFechaGenerado(LocalDateTime.now());
            ticketService.create(ticket);

            stockService.actualizarStockAlPrestar(libro, cantidad);
        }

        return "redirect:/prestamo/lista";
    }

    @GetMapping("/devolver/{id}")
    public String devolverPrestamo(@PathVariable("id") Integer id) {
        PrestamoEntity prestamo = prestamoService.getById(id);
        if (prestamo != null && prestamo.getFechaDevolucion() == null) {
            prestamo.setFechaDevolucion(LocalDate.now());
            prestamoService.modify(prestamo);
            stockService.actualizarStockAlDevolver(prestamo.getLibro());
        }
        return "redirect:/prestamo/lista";
    }

    @GetMapping("/reporte")
    public void generarReportePrestamos(HttpServletResponse response) throws Exception {
        List<PrestamoEntity> listaPrestamos = prestamoService.getAll();

        Document document = new Document();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=prestamos.pdf");

        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE);
        Paragraph titulo = new Paragraph("📚 Reporte de Préstamos", tituloFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20f);
        document.add(titulo);

        PdfPTable tabla = new PdfPTable(5);
        tabla.setWidthPercentage(100);
        tabla.addCell("ID");
        tabla.addCell("Cliente");
        tabla.addCell("Libro");
        tabla.addCell("Fecha Préstamo");
        tabla.addCell("Fecha Devolución");

        for (PrestamoEntity prestamo : listaPrestamos) {
            tabla.addCell(String.valueOf(prestamo.getIdPrestamo()));
            tabla.addCell(prestamo.getCliente().getNombreCompleto());
            tabla.addCell(prestamo.getLibro().getTitulo());
            tabla.addCell(String.valueOf(prestamo.getFechaPrestamo()));
            tabla.addCell(prestamo.getFechaDevolucion() != null ? String.valueOf(prestamo.getFechaDevolucion()) : "Pendiente");
        }

        document.add(tabla);
        document.close();
    }



}
