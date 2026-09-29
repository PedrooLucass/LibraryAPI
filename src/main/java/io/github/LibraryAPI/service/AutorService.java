package io.github.LibraryAPI.service;

import io.github.LibraryAPI.exceptions.OperacaoNaoPermitidaException;
import io.github.LibraryAPI.model.Autor;
import io.github.LibraryAPI.repository.AutorRepository;
import io.github.LibraryAPI.repository.LivroRepository;
import io.github.LibraryAPI.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator autorValidator;
    private final LivroRepository livroRepository;

    public Autor salvar(Autor autor) {
        autorValidator.validar(autor);
        return autorRepository.save(autor);
    }

    public void atualizar(Autor autor) {
        if (autor.getId() == null) {
            throw new IllegalArgumentException("Para atualizar, é necessário que o autor já esteja salvo na base de dados.");
        }
        autorValidator.validar(autor);
        autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id) {
        return autorRepository.findById(id);
    }

    public void deletar(Autor autor) {
        if (possuiLivro(autor)) {
            throw new OperacaoNaoPermitidaException("Não é permitido excluir um Autor que possui livros cadastrados.");
        }
        autorRepository.delete(autor);
    }

    public List<Autor> pesquisar(String nome, String nacionalidade) {

        if (nacionalidade == null) {
            return autorRepository.findByNome(nome);
        }
        if (nome == null) {
            return autorRepository.findByNacionalidade(nacionalidade);
        }

        return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
    }

    public List<Autor> pesquisarByExample(String nome, String nacionalidade) {
        Autor autor  = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> example = Example.of(autor, matcher);
        return autorRepository.findAll(example);
    }

    public boolean possuiLivro(Autor autor) {
        return livroRepository.existsByAutor(autor);
    }
}
