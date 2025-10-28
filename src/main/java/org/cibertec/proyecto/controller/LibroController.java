package org.cibertec.proyecto.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.cibertec.proyecto.entity.LibroEntity;
import org.cibertec.proyecto.entity.StockEntity;
import org.cibertec.proyecto.service.*;
import org.cibertec.proyecto.util.HashUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/libro")
@RequiredArgsConstructor
public class LibroController {

    private final LibroService libroService;
    private final AutorService autorService;
    private final CategoriaService categoriaService;
    private final EditorialService editorialService;
    private final StockService stockService;

    @GetMapping("/registrar")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("libro", new LibroEntity());
        model.addAttribute("autores", autorService.getAll());
        model.addAttribute("categorias", categoriaService.getAll());
        model.addAttribute("editoriales", editorialService.getAll());
        return "libro/registro";
    }

    @GetMapping("/lista")
    public String listarLibros(Model model) {
        List<LibroEntity> libros = libroService.getAll();
        Map<Integer, StockEntity> stockMap = new HashMap<>();

        for (LibroEntity libro : libros) {
            StockEntity stock = stockService.getByLibro(libro);
            if (stock != null) {
                stockMap.put(libro.getIdLibro(), stock);
            }
        }

        model.addAttribute("libro", new LibroEntity());
        model.addAttribute("listaLibros", libros);
        model.addAttribute("stockMap", stockMap);
        model.addAttribute("autores", autorService.getAll());
        model.addAttribute("categorias", categoriaService.getAll());
        model.addAttribute("editoriales", editorialService.getAll());
        return "libro/lista";
    }

    @PostMapping("/guardar")
    public String guardarLibro(@ModelAttribute LibroEntity libro,
                               @RequestParam("disponible") int disponible,
                               @RequestParam("prestado") int prestado,
                               @RequestParam("mantenimiento") int mantenimiento) {

        String data = libro.getTitulo() + libro.getAutor().getIdAutor() + libro.getCodigoLibro();
        libro.setHashRegistro(HashUtil.Nuevo(data));

        if (libro.getIdLibro() == null) {
            libroService.create(libro);
        } else {
            libroService.modify(libro);
        }

        StockEntity stock = stockService.getByLibro(libro);
        if (stock == null) {
            stock = new StockEntity();
            stock.setLibro(libro);
        }

        stock.setDisponible(disponible);
        stock.setPrestado(prestado);
        stock.setMantenimiento(mantenimiento);
        stockService.create(stock);

        return "redirect:/libro/lista";
    }

    @GetMapping("/editar/{id}")
    public String editarLibro(@PathVariable Integer id, Model model) {
        LibroEntity libro = libroService.getById(id);
        model.addAttribute("libro", libro);
        model.addAttribute("autores", autorService.getAll());
        model.addAttribute("categorias", categoriaService.getAll());
        model.addAttribute("editoriales", editorialService.getAll());
        return "libro/registro";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarLibro(@PathVariable Integer id) {
        LibroEntity libro = libroService.getById(id);
        if (libro != null) {
            StockEntity stock = stockService.getByLibro(libro);
            if (stock != null) {
                stockService.remove(stock.getIdStock());
            }
            libroService.remove(id);
        }
        return "redirect:/libro/lista";
    }

    @GetMapping("/reporte-pdf")
    public void generarReportePdf(HttpServletResponse response) {
        try {
            List<LibroEntity> libros = libroService.getAll();
            Map<Integer, StockEntity> stockMap = libros.stream()
                    .collect(Collectors.toMap(LibroEntity::getIdLibro, libro -> stockService.getByLibro(libro)));

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=reporte_libros.pdf");

            OutputStream out = response.getOutputStream();

            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, out);
            document.open();

            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.BLUE);
            Paragraph titulo = new Paragraph("📚 Reporte de Libros", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);

            PdfPTable table = new PdfPTable(9);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1.2f, 3f, 2.5f, 2.5f, 2.5f, 1.5f, 1.5f, 1.8f, 1.5f});

            String[] headers = {"ID", "Título", "Autor", "Categoría", "Editorial", "Disponible", "Prestado", "Mantenimiento", "Stock"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            for (LibroEntity libro : libros) {
                StockEntity stock = stockMap.get(libro.getIdLibro());
                int disponible = stock != null ? stock.getDisponible() : 0;
                int prestado = stock != null ? stock.getPrestado() : 0;
                int mantenimiento = stock != null ? stock.getMantenimiento() : 0;
                int totalStock = disponible + prestado + mantenimiento;

                table.addCell(String.valueOf(libro.getIdLibro()));
                table.addCell(libro.getTitulo());
                table.addCell(libro.getAutor().getNombreCompleto());
                table.addCell(libro.getCategoria().getNombreCategoria());
                table.addCell(libro.getEditorial().getNombreEditorial());
                table.addCell(String.valueOf(disponible));
                table.addCell(String.valueOf(prestado));
                table.addCell(String.valueOf(mantenimiento));
                table.addCell(String.valueOf(totalStock));
            }

            document.add(table);
            document.close();
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
