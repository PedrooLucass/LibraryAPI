package io.github.LibraryAPI.service;

import io.github.LibraryAPI.controller.dto.AutorDTO;
import io.github.LibraryAPI.exceptions.OperacaoNaoPermitidaException;
import io.github.LibraryAPI.model.Autor;
import io.github.LibraryAPI.repository.AutorRepository;
import io.github.LibraryAPI.repository.LivroRepository;
import io.github.LibraryAPI.validator.AutorValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator autorValidator;
    private final LivroRepository livroRepository;

    public AutorService(AutorRepository autorRepository, AutorValidator autorValidator, LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.autorValidator = autorValidator;
        this.livroRepository = livroRepository;
    }

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
            System.out.println("Chegou aqui");
            return autorRepository.findByNome(nome);
        }
        if (nome == null) {
            System.out.println("Chegou aqui");
            return autorRepository.findByNacionalidade(nacionalidade);
        }

        return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
    }

    public boolean possuiLivro(Autor autor) {
        return livroRepository.existsByAutor(autor);
    }
}
