package io.github.LibraryAPI.repository;

import io.github.LibraryAPI.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Test
    public void salvarTest() {
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileira");
        autor.setData_nascimento(LocalDate.of(1970, 1, 1));

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
        var id = UUID.fromString("982bb754-96dd-4f8b-b15b-d4bd521e1782");

        String opcao = "PorObjeto"; // "PorID" ou "PorObjeto"

        if (repository.findById(id).isEmpty()) {
            System.out.println("Autor não encontrado!");
            return;
        }

        if(opcao.equals("PorID")) {
            repository.deleteById(id);
        } else if (opcao.equals("PorObjeto")) {
            var objeto = repository.findById(id).get();
            repository.delete(objeto);
        }
    }
}
