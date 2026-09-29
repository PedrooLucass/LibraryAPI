package io.github.LibraryAPI.controller.dto;

import io.github.LibraryAPI.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,

        @NotBlank(message = "campo obrigatório") String nome,

        @NotNull(message = "campo obrigatório") LocalDate dataNascimento,

        @NotBlank(message = "campo obrigatório") String nacionalidade) {

    public Autor mapearParaAutor() {
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setDataNascimento(dataNascimento);
        autor.setNacionalidade(nacionalidade);
        return autor;
    }
}
