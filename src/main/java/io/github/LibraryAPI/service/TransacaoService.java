package io.github.LibraryAPI.service;

import io.github.LibraryAPI.model.Autor;
import io.github.LibraryAPI.model.GeneroLivro;
import io.github.LibraryAPI.model.Livro;
import io.github.LibraryAPI.repository.AutorRepository;
import io.github.LibraryAPI.repository.LivroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    // PSEUDOCÓDIGO
    // livro (titulo, ..., nome_arquivo) -> id.png
    @Transactional
    public void salvarLivroComFoto() {
        // salva o livro
        // repository.save(livro);

        // var id = livro.getId();

        // salvar foto do livro -> bucket na nuvem
        // bucketService.salvar(livro.getFoto(), id + ".png");

        // atualiza o nome do arquivo que foi salvo
        // livro.setNomeArquivoFoto(id + ".png");
        // repository.save(livro);
    }

    @Transactional
    public void atualizacaoSemAtualizar() {
        var livro = livroRepository
                .findById(UUID.fromString("4743bdf1-083a-48a2-8e90-a83becea8b22"));

        if (livro.isEmpty()) {
            return;
        }

        livro.get().setData_lancamento(LocalDate.of(2019, 2, 28));
    }

    @Transactional
    public void executar() {
        Autor autor = new Autor();
        autor.setNome("Chico");
        autor.setNacionalidade("Brasileira");
        autor.setData_nascimento(LocalDate.of(1963, 10, 19));

        autorRepository.save(autor);


        Livro livro = new Livro();
        livro.setIsbn("786778-58746");
        livro.setTitulo("História para Platelmintos");
        livro.setPreco(BigDecimal.valueOf(45.59));
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setData_lancamento(LocalDate.of(1985, 10, 20));
        livro.setAutor(autor);

        autor.setLivros(List.of(livro));

        livroRepository.saveAll(autor.getLivros());

        if (autor.getNome().equals("Chicoa")) {
            throw new RuntimeException("Rollback!");
        }
    }
}
