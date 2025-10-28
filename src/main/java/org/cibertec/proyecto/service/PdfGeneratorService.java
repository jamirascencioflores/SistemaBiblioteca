package org.cibertec.proyecto.service;

import org.cibertec.proyecto.entity.TicketPrestamoEntity;
import java.io.OutputStream;

public interface PdfGeneratorService {
    void generarTicketPDF(TicketPrestamoEntity ticket, OutputStream out) throws Exception;
}