package org.cibertec.proyecto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "libro",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_autor", "titulo"}))
@Getter
@Setter
public class LibroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private Integer idLibro;

    @Column(name = "codigo_libro", nullable = false, unique = true, length = 10)
    private String codigoLibro;

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    private AutorEntity autor;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaEntity categoria;

    @ManyToOne
    @JoinColumn(name = "id_editorial", nullable = false)
    private EditorialEntity editorial;

    @Column(name = "hash_registro", nullable = false, length = 256)
    private String hashRegistro;

    @OneToOne(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private StockEntity stock;


}

