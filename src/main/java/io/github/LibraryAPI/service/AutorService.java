package io.github.LibraryAPI.service;

import io.github.LibraryAPI.controller.dto.AutorDTO;
import io.github.LibraryAPI.model.Autor;
import io.github.LibraryAPI.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor salvar(Autor autor) {
        return autorRepository.save(autor);
    }

    public void atualizar(Autor autor) {
        if (autor.getId() == null) {
            throw new IllegalArgumentException("Para atualizar, é necessário que o autor já esteja salvo na base de dados.");
        }
        autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id) {
        return autorRepository.findById(id);
    }

    public void deletar(Autor autor) {
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
}
