package io.github.LibraryAPI.repository;

import io.github.LibraryAPI.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransacoesTest {

    @Autowired
    TransacaoService transacaoService;

    /**
     * Commit -> confirmar as alterações
     * Rollback -> desfazer as alterações
     */
    @Test
    void transacaoSimplesTest(){
        transacaoService.executar();
    }

    @Test
    void transacaoEstadoManagedTest(){
        transacaoService.atualizacaoSemAtualizar();
    }
}
