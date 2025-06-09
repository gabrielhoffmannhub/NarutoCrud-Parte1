package com.naruto.adapters.output.persistence;

import com.naruto.domain.model.Personagem;
import com.naruto.domain.model.TipoNinja;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.HashMap;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class PersonagemRepositoryTest {

    private PersonagemRepository repository;

    @BeforeEach
    void setUp() {
        repository = new PersonagemRepository();
    }

    @AfterEach
    void tearDown() {

    }

    private Personagem novoPersonagem(String nome) {
        Personagem p = new Personagem();
        p.setNome(nome);
        p.setTipoNinja(TipoNinja.NINJUTSU);
        p.setVida(100);
        p.setChakra(100);
        p.setJutsus(new HashMap<>());
        return p;
    }

    @Test
    void salvar_e_buscarPorNome() {
        Personagem naruto = novoPersonagem("Naruto");
        repository.salvar(naruto);

        Personagem encontrado = repository.buscarPorNome("Naruto");
        assertEquals("Naruto", encontrado.getNome());
        assertEquals(TipoNinja.NINJUTSU, encontrado.getTipoNinja());
    }

    @Test
    void listarTodos() {
        Personagem naruto = novoPersonagem("Naruto");
        Personagem sasuke = novoPersonagem("Sasuke");
        repository.salvar(naruto);
        repository.salvar(sasuke);

        List<Personagem> lista = repository.listarTodos();
        assertEquals(2, lista.size());
    }

    @Test
    void buscarPorNome_retornaNullSeNaoEncontrar() {
        Personagem resultado = repository.buscarPorNome("Inexistente");
        assertNull(resultado);
    }

    @Test
    void deletar_removePersonagem() {
        Personagem sakura = novoPersonagem("Sakura");
        repository.salvar(sakura);

        repository.deletar("Sakura");
        assertNull(repository.buscarPorNome("Sakura"));
    }

    @Test
    void deletar_naoFazNadaSeNaoExistir() {
        repository.deletar("NaoExiste");

    }

    @Test
    void atualizar_alteraDadosDoPersonagem() {
        Personagem shikamaru = novoPersonagem("Shikamaru");
        repository.salvar(shikamaru);

        Personagem atualizado = novoPersonagem("Shikamaru");
        atualizado.setTipoNinja(TipoNinja.NINJUTSU);
        atualizado.setVida(80);
        atualizado.setChakra(50);

        Personagem resultado = repository.atualizar("Shikamaru", atualizado);

        assertEquals(TipoNinja.NINJUTSU, resultado.getTipoNinja());
        assertEquals(80, resultado.getVida());
        assertEquals(50, resultado.getChakra());
    }

    @Test
    void atualizar_retornaNullSeNaoEncontrar() {
        Personagem atualizado = novoPersonagem("NaoExiste");
        Personagem resultado = repository.atualizar("NaoExiste", atualizado);
        assertNull(resultado);
    }
}