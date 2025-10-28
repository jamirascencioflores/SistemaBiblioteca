package org.cibertec.proyecto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "stock")
@Getter
@Setter
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stock")
    private Integer idStock;

    @OneToOne
    @JoinColumn(name = "id_libro", nullable = false)
    private LibroEntity libro;

    @Column(name = "disponible", nullable = false)
    private Integer disponible;

    @Column(name = "prestado", nullable = false)
    private Integer prestado;

    @Column(name = "mantenimiento", nullable = false)
    private Integer mantenimiento;
}

