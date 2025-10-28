package org.cibertec.proyecto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "autor")
@Getter
@Setter
public class AutorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_autor")
    private Integer idAutor;

    @Column(name = "codigo_autor", nullable = false, unique = true, length = 10)
    private String codigoAutor;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;
}

