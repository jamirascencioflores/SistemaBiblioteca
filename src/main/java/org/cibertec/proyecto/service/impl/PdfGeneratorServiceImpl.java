package org.cibertec.proyecto.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.cibertec.proyecto.entity.TicketPrestamoEntity;
import org.cibertec.proyecto.service.PdfGeneratorService;
import org.springframework.stereotype.Service;

import java.io.OutputStream;

@Service
public class PdfGeneratorServiceImpl implements PdfGeneratorService {

    @Override
    public void generarTicketPDF(TicketPrestamoEntity ticket, OutputStream out) throws Exception {
        Document documento = new Document();
        PdfWriter.getInstance(documento, out);
        documento.open();

        documento.add(new Paragraph("📚 Ticket de Préstamo", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
        documento.add(new Paragraph(" "));
        documento.add(new Paragraph("Número de ticket: " + ticket.getIdTicket()));
        documento.add(new Paragraph("Resumen: " + ticket.getResumen()));
        documento.add(new Paragraph("Fecha generado: " + ticket.getFechaGenerado()));
        documento.add(new Paragraph(" "));
        documento.add(new Paragraph("Gracias por usar el sistema de biblioteca."));

        documento.close();
    }
}