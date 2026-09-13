package io.github.LibraryAPI.repository;

import io.github.LibraryAPI.model.Autor;
import io.github.LibraryAPI.model.GeneroLivro;
import io.github.LibraryAPI.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest() {
        Autor autor = new Autor();
        autor.setNome("BugDaSilva");
        autor.setNacionalidade("Brasileira");
        autor.setData_nascimento(LocalDate.of(1999, 12, 31));

        var autorSalvo = repository.save(autor);

        if (autorSalvo == autor) {
            System.out.println("Autor salvo com sucesso!");
            System.out.println("Autor salvo: " + autorSalvo);
        }
    }

    @Test
    public void atualizarTest() {
        UUID id = UUID.fromString("5205d584-653f-49bd-8383-b522adf3d2f9");

        if (repository.findById(id).isPresent()) {
            Autor autorEncontrado = repository.findById(id).get();

            System.out.println("Dados do autor: "+ autorEncontrado);

            autorEncontrado.setData_nascimento(LocalDate.of(1960, 1, 1));

            repository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest() {
        List<Autor> autores = repository.findAll();
        autores.forEach(System.out::println);
    }

    @Test
    public void countTest() {
        System.out.println("Contagem de autores: "+ repository.count());
    }

    @Test
    public void deleteTest() {
        var id = UUID.fromString("68dafe05-6a39-4232-a3f1-62428b4cfe8a");

        String opcao = "PorObjeto"; // "PorID" ou "PorObjeto"

        if (repository.findById(id).isEmpty()) {
            System.out.println("Autor não encontrado!");
            return;
        }

        Autor autor = repository.findById(id).get();
        if (!livroRepository.findByAutor(autor).isEmpty()) {
            List<Livro> livrosList = livroRepository.findByAutor(autor);
            livroRepository.deleteAll(livrosList);
        }

        if(opcao.equals("PorID")) {
            repository.deleteById(id);
        } else if (opcao.equals("PorObjeto")) {
            repository.delete(autor);
        }
    }

    @Test
    public void salvarAutorComLivroTest() {
        Autor autor = new Autor();
        autor.setNome("Marieta");
        autor.setNacionalidade("Brasileira");
        autor.setData_nascimento(LocalDate.of(1979, 1, 1));

        Livro livro = new Livro();
        livro.setIsbn("786778-58746");
        livro.setTitulo("Matrizes para Platelmintos");
        livro.setPreco(BigDecimal.valueOf(75.59));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setData_lancamento(LocalDate.now());
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("745778-58746");
        livro2.setTitulo("Matemática Básica para Platelmintos");
        livro2.setPreco(BigDecimal.valueOf(75.59));
        livro2.setGenero(GeneroLivro.CIENCIA);
        livro2.setData_lancamento(LocalDate.of(2005, 1, 31));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());

        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);
        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    @Transient
    public void listarLivrosDoAutorTest() {
        UUID autorId =  UUID.fromString("d740c9cc-b672-41f9-a733-37dc0c3223ad");

        var autor = repository.findById(autorId);

        if (autor.isEmpty()) {
            System.out.println("Autor não encontrado!");
            return;
        }

        List<Livro> livrosList = livroRepository.findByAutor(autor.get());

        livrosList.forEach(System.out::println);
    }
}
