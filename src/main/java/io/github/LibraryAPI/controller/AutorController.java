package io.github.LibraryAPI.controller;

import io.github.LibraryAPI.controller.dto.AutorDTO;
import io.github.LibraryAPI.controller.dto.ErroResposta;
import io.github.LibraryAPI.exceptions.OperacaoNaoPermitidaException;
import io.github.LibraryAPI.exceptions.RegistroDuplicadoException;
import io.github.LibraryAPI.model.Autor;
import io.github.LibraryAPI.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
@RequiredArgsConstructor
public class AutorController {

    public final AutorService autorService;

    @PostMapping  // @PostMapping = @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO autor) {
        try {
            var autorEntidade = autor.mapearParaAutor();
            autorService.salvar(autorEntidade);

            // http://localhost:8080/autores/{id}
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autorEntidade.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (RegistroDuplicadoException ex) {
            var erroDTO = ErroResposta.conflito(ex.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> obterDetalhesAutor(@PathVariable("id") String id) {
        var autorOptional = autorService
                .obterPorId(UUID.fromString(id));

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Autor autor = autorOptional.get();
        AutorDTO dto = new AutorDTO(
                autor.getId(),
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade());

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {
        try {
            var autorOptional = autorService
                    .obterPorId(UUID.fromString(id));

            if (autorOptional.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            autorService.deletar(autorOptional.get());

            return ResponseEntity.noContent().build();

        } catch (OperacaoNaoPermitidaException e) {
            var erroDTO = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {

        List<Autor> autorList = autorService.pesquisar(nome, nacionalidade);

        if (autorList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<AutorDTO> autorDTOList = autorList
                .stream()
                .map(autor ->
                        new AutorDTO(
                                autor.getId(),
                                autor.getNome(),
                                autor.getDataNascimento(),
                                autor.getNacionalidade()
                        ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(autorDTOList);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(
            @PathVariable("id") String id,
            @RequestBody AutorDTO autorDTO) {

        try {
            UUID idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Autor autor = autorOptional.get();

            autor.setNome(autorDTO.nome());
            autor.setDataNascimento(autorDTO.dataNascimento());
            autor.setNacionalidade(autorDTO.nacionalidade());

            autorService.atualizar(autor);

            return ResponseEntity.noContent().build();
        } catch (RegistroDuplicadoException ex) {
            var erroDTO = ErroResposta.conflito(ex.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
}
