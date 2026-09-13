package io.github.LibraryAPI.repository;

import io.github.LibraryAPI.model.Autor;
import io.github.LibraryAPI.model.GeneroLivro;
import io.github.LibraryAPI.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Query Method
    // select * from livro where id_autor = id;
    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);

    List<Livro> findByIsbn(String isbn);

    // select * from livro where titulo = ? and preco = ?
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    // select * from livro where titulo = ? or isbn = ?
    List<Livro> findByTituloOrIsbn(String titulo, String isbn);

    List<Livro> findByTituloContainingIgnoreCase(String titulo);

    // JPQL -> referencia as entidades e as propriedades
    @Query(" select l from Livro as l order by l.titulo, l.preco ") // select l.* from livro as l order by l.titulo, l.preco
    List<Livro> listarTodosOrdenadosPorTituloEPreco();

    @Query(" select a from Livro as l join l.autor as a ")
    List<Autor> listarTodosAutoresComLivros();

    @Query(" select distinct l.titulo from Livro as l ")
    List<String> listarNomesDeDiferentesLivros();

    @Query("""
        select distinct l.genero 
        from Livro as l 
        join l.autor as a 
        where a.nacionalidade = 'Brasileira' 
        order by l.genero
   """)
    List<GeneroLivro> listarGenerosBrasileiros();
}
