package com.naruto.application.service;

import com.naruto.application.service.PersonagemService;
import com.naruto.domain.model.Personagem;
import com.naruto.domain.model.TipoNinja;
import com.naruto.dto.NinjaDTO;
import com.naruto.ports.out.PersonagemRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PersonagemServiceTest {

    @Mock
    private PersonagemRepositoryPort personagemRepositoryPort;

    private PersonagemService personagemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        personagemService = new PersonagemService(personagemRepositoryPort);
    }

    @Test
    void testCriarNinja() {
        NinjaDTO ninjaDTO = new NinjaDTO();
        ninjaDTO.setNome("Naruto");
        ninjaDTO.setIdade(16);
        ninjaDTO.setAldeia("Konoha");
        ninjaDTO.setChakra(100);
        ninjaDTO.setTipoNinja("Ninjutsu");

        Personagem personagem = personagemService.criarNinja(ninjaDTO);

        assertNotNull(personagem);
        assertEquals("Naruto", personagem.getNome());
        assertEquals(16, personagem.getIdade());
        assertEquals("Konoha", personagem.getAldeia());
        assertEquals(100, personagem.getChakra());
        assertEquals(TipoNinja.NINJUTSU, personagem.getTipoNinja());
    }

    @Test
    void testListarTodos() {
        List<Personagem> personagens = Arrays.asList(
                new Personagem("Naruto", 16, "Konoha", 1000, TipoNinja.NINJUTSU),
                new Personagem("Sasuke", 17, "Konoha", 100, TipoNinja.GENJUTSU)
        );

        when(personagemRepositoryPort.listarTodos()).thenReturn(personagens);

        List<Personagem> resultado = personagemService.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("Naruto", resultado.get(0).getNome());
        assertEquals("Sasuke", resultado.get(1).getNome());

        verify(personagemRepositoryPort, times(1)).listarTodos();
    }

    @Test
    void testSalvar() {
        Personagem personagem = new Personagem("Naruto", 16, "Konoha", 1000, TipoNinja.NINJUTSU);

        when(personagemRepositoryPort.salvar(personagem)).thenReturn(personagem);

        Personagem resultado = personagemService.salvar(personagem);

        assertNotNull(resultado);
        assertEquals("Naruto", resultado.getNome());

        verify(personagemRepositoryPort, times(1)).salvar(personagem);
    }

    @Test
    void testUsarJutsu() {
        NinjaDTO ninjaDTO = new NinjaDTO();
        ninjaDTO.setNome("Naruto");
        ninjaDTO.setIdade(16);
        ninjaDTO.setAldeia("Konoha");
        ninjaDTO.setChakra(1000);
        ninjaDTO.setTipoNinja("Ninjutsu");

        String resultado = personagemService.usarJutsu(ninjaDTO);

        assertEquals("Naruto usou um jutsu de Ninjutsu", resultado);

        verify(personagemRepositoryPort, times(0)).salvar(any());
    }

    @Test
    void testDesviar() {
        NinjaDTO ninjaDTO = new NinjaDTO();
        ninjaDTO.setNome("Naruto");
        ninjaDTO.setIdade(16);
        ninjaDTO.setAldeia("Konoha");
        ninjaDTO.setChakra(1000);
        ninjaDTO.setTipoNinja("Ninjutsu");

        String resultado = personagemService.desviar(ninjaDTO);

        assertEquals("Naruto desviou de um ataque usando Ninjutsu", resultado);

        verify(personagemRepositoryPort, times(0)).salvar(any());
    }

    @Test
    void testDeletar() {
        String nome = "Naruto";

        doNothing().when(personagemRepositoryPort).deletar(nome);

        personagemService.deletar(nome);

        verify(personagemRepositoryPort, times(1)).deletar(nome);
    }

    @Test
    void testAtualizar() {
        String nome = "Naruto";
        Personagem personagem = new Personagem("Naruto Adulto", 35, "Konoha", 10000, TipoNinja.NINJUTSU);

        when(personagemRepositoryPort.atualizar(nome, personagem)).thenReturn(personagem);

        Personagem resultado = personagemService.atualizar(nome, personagem);

        assertNotNull(resultado);
        assertEquals("Naruto Adulto", resultado.getNome());

        verify(personagemRepositoryPort, times(1)).atualizar(nome, personagem);
    }
}
