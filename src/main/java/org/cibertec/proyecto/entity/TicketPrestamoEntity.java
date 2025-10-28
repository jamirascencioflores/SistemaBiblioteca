package org.cibertec.proyecto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_prestamo")
@Getter
@Setter
public class TicketPrestamoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket")
    private Integer idTicket;

    @OneToOne
    @JoinColumn(name = "id_prestamo", nullable = false)
    private PrestamoEntity prestamo;

    @Column(name = "resumen", nullable = false, columnDefinition = "TEXT")
    private String resumen;

    @Column(name = "fecha_generado", nullable = false)
    private LocalDateTime fechaGenerado;
}
