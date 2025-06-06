package com.naruto.adapters.output.persistence;

import com.naruto.domain.model.Personagem;
import com.naruto.domain.model.TipoNinja;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PersonagemRepositoryTest {

    private PersonagemRepository repository;

    @BeforeEach
    void setUp() {
        repository = new PersonagemRepository();
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
    void listarTodos_deveRetornarListaQuandoNaoVazia() {
        Personagem p = novoPersonagem("Naruto");
        repository.salvar(p);

        List<Personagem> lista = repository.listarTodos();

        assertEquals(1, lista.size());
        assertEquals("Naruto", lista.get(0).getNome());
    }

    @Test
    void listarTodos_deveLancarExcecaoQuandoVazia() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> repository.listarTodos());
        assertEquals("Nenhum personagem encontrado", ex.getMessage());
    }

    @Test
    void buscarPorNome_deveRetornarPersonagemExistente() {
        Personagem p = novoPersonagem("Sasuke");
        repository.salvar(p);

        Personagem encontrado = repository.buscarPorNome("Sasuke");

        assertNotNull(encontrado);
        assertEquals("Sasuke", encontrado.getNome());
    }

    @Test
    void buscarPorNome_deveLancarExcecaoSeNaoEncontrar() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> repository.buscarPorNome("Inexistente"));
        assertTrue(ex.getMessage().contains("Personagem com o nome Inexistente não encontrado"));
    }

    @Test
    void salvar_deveAdicionarPersonagemNovo() {
        Personagem p = novoPersonagem("Kakashi");
        Personagem salvo = repository.salvar(p);

        assertEquals(p, salvo);
        assertEquals("Kakashi", repository.buscarPorNome("Kakashi").getNome());
    }

    @Test
    void salvar_deveLancarExcecaoSePersonagemJaExiste() {
        Personagem p = novoPersonagem("Naruto");
        repository.salvar(p);

        Personagem duplicado = novoPersonagem("Naruto");
        RuntimeException ex = assertThrows(RuntimeException.class, () -> repository.salvar(duplicado));
        assertEquals("Personagem já existe", ex.getMessage());
    }

    @Test
    void deletar_deveRemoverPersonagemExistente() {
        Personagem p = novoPersonagem("Sakura");
        repository.salvar(p);

        repository.deletar("Sakura");

        assertThrows(RuntimeException.class, () -> repository.buscarPorNome("Sakura"));
    }

    @Test
    void deletar_deveLancarExcecaoSeNaoEncontrar() {
        assertThrows(RuntimeException.class, () -> repository.deletar("Inexistente"));
    }

    @Test
    void atualizar_deveAlterarDadosDoPersonagem() {
        Personagem p = novoPersonagem("Shikamaru");
        repository.salvar(p);

        Personagem atualizado = novoPersonagem("Shikamaru");
        atualizado.setTipoNinja(TipoNinja.NINJUTSU); 
        atualizado.setVida(80);
        atualizado.setChakra(90);

        Personagem resultado = repository.atualizar("Shikamaru", atualizado);

        assertEquals(TipoNinja.NINJUTSU, resultado.getTipoNinja());
        assertEquals(80, resultado.getVida());
        assertEquals(90, resultado.getChakra());
    }

    @Test
    void atualizar_deveLancarExcecaoSeNaoEncontrar() {
        Personagem atualizado = novoPersonagem("NaoExiste");
        assertThrows(RuntimeException.class, () -> repository.atualizar("NaoExiste", atualizado));
    }
}