package io.github.LibraryAPI.validator;

import io.github.LibraryAPI.exceptions.RegistroDuplicadoException;
import io.github.LibraryAPI.model.Autor;
import io.github.LibraryAPI.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    private final AutorRepository autorRepository;

    public AutorValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public void validar(Autor autor) {
        if(existeAutor(autor)) {
            throw new RegistroDuplicadoException("Autor já cadastrado");
        }
    }

    private boolean existeAutor(Autor autor) {
        Optional<Autor> autorOptional = autorRepository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade()
        );

        if(autor.getId() == null) {
            return autorOptional.isPresent();
        }

        return !autor.getId()
                .equals(autorOptional.get().getId())
                && autorOptional.isPresent();
    }
}
