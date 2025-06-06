package com.naruto.adapters.input;

import com.naruto.domain.model.Jutsu;
import com.naruto.domain.model.Personagem;
import com.naruto.dto.NinjaDTO;
import com.naruto.ports.input.PersonagemServicePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonagemControllerTest {

    @Mock
    private PersonagemServicePort personagemService;

    @InjectMocks
    private PersonagemController controller;

    @Test
    void deveListarTodos() {
        List<Personagem> lista = Arrays.asList(new Personagem(), new Personagem());
        when(personagemService.listarTodos()).thenReturn(lista);

        List<Personagem> resultado = controller.listarTodos();

        assertEquals(lista, resultado);
        verify(personagemService).listarTodos();
    }

    @Test
    void deveBuscarPorNome() {
        String nome = "Naruto";
        Personagem personagem = new Personagem();
        when(personagemService.buscarPorNome(nome)).thenReturn(personagem);

        Personagem resultado = controller.buscarPorNome(nome);

        assertEquals(personagem, resultado);
        verify(personagemService).buscarPorNome(nome);
    }

    @Test
    void deveCriarPersonagem() {
        NinjaDTO ninjaDTO = new NinjaDTO();
        Personagem personagemCriado = new Personagem();

        when(personagemService.criarNinja(ninjaDTO)).thenReturn(personagemCriado);
        when(personagemService.salvar(personagemCriado)).thenReturn(personagemCriado);

        Personagem resultado = controller.criar(ninjaDTO);

        assertEquals(personagemCriado, resultado);
        verify(personagemService).criarNinja(ninjaDTO);
        verify(personagemService).salvar(personagemCriado);
    }

    @Test
    void deveDeletarPersonagem() {
        String nome = "Naruto";
        doNothing().when(personagemService).deletar(nome);

        controller.deletar(nome);

        verify(personagemService).deletar(nome);
    }

    @Test
    void deveAtualizarPersonagem() {
        String nome = "Naruto";
        Personagem personagem = new Personagem();
        Personagem atualizado = new Personagem();

        when(personagemService.atualizar(nome, personagem)).thenReturn(atualizado);

        Personagem resultado = controller.atualizar(nome, personagem);

        assertEquals(atualizado, resultado);
        verify(personagemService).atualizar(nome, personagem);
    }

    @Test
    void deveAdicionarJutsu() {
        String nome = "Naruto";
        String nomeJutsu = "Rasengan";
        Jutsu jutsu = new Jutsu();
        String resposta = "Jutsu adicionado";

        when(personagemService.adicionarJutsu(nome, nomeJutsu, jutsu)).thenReturn(resposta);

        String resultado = controller.adicionarJutsu(nome, nomeJutsu, jutsu);

        assertEquals(resposta, resultado);
        verify(personagemService).adicionarJutsu(nome, nomeJutsu, jutsu);
    }

    @Test
    void deveAtacar() {
        String nomeAtacante = "Naruto";
        String nomeAlvo = "Sasuke";
        String nomeJutsu = "Rasengan";
        String resposta = "Ataque realizado";

        when(personagemService.atacar(nomeAtacante, nomeAlvo, nomeJutsu)).thenReturn(resposta);

        String resultado = controller.atacar(nomeAtacante, nomeAlvo, nomeJutsu);

        assertEquals(resposta, resultado);
        verify(personagemService).atacar(nomeAtacante, nomeAlvo, nomeJutsu);
    }

    @Test
    void deveUsarJutsu() {
        NinjaDTO ninjaDTO = new NinjaDTO();
        String resposta = "Jutsu usado";

        when(personagemService.usarJutsu(ninjaDTO)).thenReturn(resposta);

        String resultado = controller.usarJutsu(ninjaDTO);

        assertEquals(resposta, resultado);
        verify(personagemService).usarJutsu(ninjaDTO);
    }

    @Test
    void deveDesviar() {
        NinjaDTO ninjaDTO = new NinjaDTO();
        String resposta = "Desviou";

        when(personagemService.desviar(ninjaDTO)).thenReturn(resposta);

        String resultado = controller.desviar(ninjaDTO);

        assertEquals(resposta, resultado);
        verify(personagemService).desviar(ninjaDTO);
    }

    @Test
    void deveDesviarComDano() {
        String nome = "Naruto";
        int dano = 50;
        String resposta = "Desviou com dano";

        when(personagemService.desviar(nome, dano)).thenReturn(resposta);

        String resultado = controller.desviarComDano(nome, dano);

        assertEquals(resposta, resultado);
        verify(personagemService).desviar(nome, dano);
    }
}

