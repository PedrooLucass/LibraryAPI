package io.github.LibraryAPI.controller;

import io.github.LibraryAPI.controller.dto.AutorDTO;
import io.github.LibraryAPI.model.Autor;
import io.github.LibraryAPI.service.AutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("autores")
public class AutorController {

    public final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping  // @PostMapping = @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO autor) {
        var autorEntidade = autor.mapearParaAutor();
        autorService.salvarAutor(autorEntidade);

        // http://localhost:8080/autores/{id}
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorEntidade.getId())
                .toUri();

        return ResponseEntity.created(location).build();
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
    public ResponseEntity<Void> deletarAutor(@PathVariable("id") String id) {
        var autorOptional = autorService
                .obterPorId(UUID.fromString(id));

        if (autorOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        autorService.deletarAutor(autorOptional.get());

        return ResponseEntity.noContent().build();
    }
}
