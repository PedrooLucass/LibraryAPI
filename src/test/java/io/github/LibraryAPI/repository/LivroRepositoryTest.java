package io.github.LibraryAPI.repository;

import io.github.LibraryAPI.model.Autor;
import io.github.LibraryAPI.model.GeneroLivro;
import io.github.LibraryAPI.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    private LivroRepository repository;

    @Autowired
    private AutorRepository autorRepository;

    @Test
    void salvarTest() {
        Livro livro = new Livro();

        UUID autorId = UUID.fromString("68dafe05-6a39-4232-a3f1-62428b4cfe8a");
        // Lucas: 5205d584-653f-49bd-8383-b522adf3d2f9  Maria: f91d2c42-18b7-4ea1-b56b-d86909647ee0

        if (autorRepository.findById(autorId).isEmpty()) {
            System.out.println("Autor não encontrado");
            return;
        }

        livro.setIsbn("12378-69271");
        livro.setTitulo("Usuarios Burros para Platelmintos");
        livro.setPreco(BigDecimal.valueOf(5.99));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setData_lancamento(LocalDate.now());

        Autor autor = autorRepository.findById(autorId).get();

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void atualizarAutorDoLivroTest() {
        UUID id = UUID.fromString("913c29ba-0570-4910-af76-3b7e167106eb");
        UUID autorId = UUID.fromString("f91d2c42-18b7-4ea1-b56b-d86909647ee0");

        var livroParaAtualizar = repository.findById(id);
        var autor = autorRepository.findById(autorId);

        if (livroParaAtualizar.isEmpty()) {
            System.out.println("Livro não encontrado");
            return;
        }
        if (autor.isEmpty()) {
            System.out.println("Autor não encontrado");
            return;
        }

        livroParaAtualizar.get().setAutor(autor.get());

        repository.save(livroParaAtualizar.get());
    }

    @Test
    void deletarTest() {
        UUID id = UUID.fromString("59a84673-06c3-47b9-a32a-4fedc0f33aab");

        if (repository.findById(id).isEmpty()) {
            System.out.println("Livro não encontrado");
            return;
        }

        repository.deleteById(id);
    }

    @Test
    void buscarLivroTest() {
        UUID id = UUID.fromString("f6e02b0b-5382-413f-a814-a4ed58c24c0f");

        if (repository.findById(id).isEmpty()) {
            System.out.println("Livro não encontrado");
            return;
        }

        Livro livro = repository.findById(id).get();

        System.out.println("Livro: " + livro.getTitulo());
        System.out.println("Autor(a):" + livro.getAutor());
    }

    @Test
    void pesquisaPorIsbnTest() {
        List<Livro> livroList = repository.findByIsbn("64654-58746");
        livroList.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloTest() {
        List<Livro> livroList = repository.findByTitulo("Platelmintos");
        livroList.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloEPrecoTest() {
        BigDecimal preco = BigDecimal.valueOf(75.59);
        String titulo = "Física Quântica para Platelmintos";

        List<Livro> livroList = repository.findByTituloAndPreco(titulo, preco);
        livroList.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloContendoTest() {
        String titulo = "Platelmintos";

        List<Livro> livroList = repository.findByTituloContainingIgnoreCase(titulo);
        livroList.forEach(System.out::println);
    }

    @Test
    void listarLivrosComQueryTest() {
        List<Livro> livroList = repository.listarTodosOrdenadosPorTituloEPreco();
        livroList.forEach(System.out::println);
    }

    @Test
    void listarAutoresComLivrosTest() {
        List<Autor> autorList = repository.listarTodosAutoresComLivros();
        autorList.forEach(System.out::println);
    }

    @Test
    void listarTitulosSemRepetirTest() {
        List<String> livroList = repository.listarNomesDeDiferentesLivros();
        livroList.forEach(System.out::println);
    }

    @Test
    void listarGenerosBrasileirosTest() {
        List<GeneroLivro> livroList = repository.listarGenerosBrasileiros();
        livroList.forEach(System.out::println);
    }

    @Test
    void listarGeneroComQueryParamTest() {
        var livroList = repository.findByGenero(GeneroLivro.CIENCIA,  Sort.by("titulo").ascending());
        livroList.forEach(System.out::println);
    }

    @Test
    void listarGeneroComQueryPositionalParamTest() {
        var livroList = repository.findByGeneroPositionalParam(GeneroLivro.CIENCIA, Sort.by("data_lancamento").ascending());
        livroList.forEach(System.out::println);
    }

    @Test
    void deletarPorGeneroTest() {
        repository.deleteByGenero(GeneroLivro.MISTERIO);
    }

    @Test
    void uptadeDataLancamentoTest() {
        UUID id = UUID.fromString("2b09ebe8-55a6-470e-9cd9-5e9c8decb75e");

        repository.updateDataDePublicacao(id, LocalDate.of(2777, 1, 1));
    }
}