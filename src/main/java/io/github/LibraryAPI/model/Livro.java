package io.github.LibraryAPI.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "livro")
@Data
public class Livro {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "isbn", length = 20, nullable = false) // Nenhum desses parâmetros seriam obrigatórios, mas como exercício é bom colocar
    private String isbn;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "data_lancamento", nullable = false)
    private LocalDate data_lancamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 30, nullable = false)
    private GeneroLivro genero;

    @Column(name = "preco", nullable = false, precision = 18, scale = 2)
    private BigDecimal preco; // Para melhor precisão com cálculos, poderia codar também como: private BigDecimal preco;

    @ManyToOne(
//            cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "id_autor")
    private Autor autor;
}
