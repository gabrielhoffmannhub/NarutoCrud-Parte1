package com.naruto.application.service;

import com.naruto.domain.model.Jutsu;
import com.naruto.domain.model.Personagem;
import com.naruto.domain.model.TipoNinja;
import com.naruto.dto.NinjaDTO;
import com.naruto.ports.output.PersonagemRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PersonagemServiceTest {

    @Mock
    private PersonagemRepositoryPort personagemRepository;

    @InjectMocks
    private PersonagemService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = Mockito.spy(new PersonagemService(personagemRepository));
    }

    @Test
    void criarNinja_deveRetornarPersonagemComNomeETipo() {
        NinjaDTO dto = new NinjaDTO();
        dto.setNome("Naruto");
        dto.setTipoNinja("NINJUTSU");

        Personagem p = service.criarNinja(dto);

        assertEquals("Naruto", p.getNome());
        assertEquals(TipoNinja.NINJUTSU, p.getTipoNinja());
    }

    @Test
    void listarTodos_deveRetornarListaDoRepositorio() {
        List<Personagem> lista = Arrays.asList(new Personagem(), new Personagem());
        when(personagemRepository.listarTodos()).thenReturn(lista);

        List<Personagem> resultado = service.listarTodos();

        assertEquals(lista, resultado);
        verify(personagemRepository).listarTodos();
    }

    @Test
    void buscarPorNome_deveRetornarPersonagem() {
        Personagem p = new Personagem();
        when(personagemRepository.buscarPorNome("Naruto")).thenReturn(p);

        Personagem resultado = service.buscarPorNome("Naruto");

        assertEquals(p, resultado);
        verify(personagemRepository).buscarPorNome("Naruto");
    }

    @Test
    void salvar_deveChamarRepositorio() {
        Personagem p = new Personagem();
        when(personagemRepository.salvar(p)).thenReturn(p);

        Personagem resultado = service.salvar(p);

        assertEquals(p, resultado);
        verify(personagemRepository).salvar(p);
    }

    @Test
    void deletar_deveChamarRepositorio() {
        when(personagemRepository.buscarPorNome("Naruto")).thenReturn(new Personagem());
        doNothing().when(personagemRepository).deletar("Naruto");

        service.deletar("Naruto");

        verify(personagemRepository).deletar("Naruto");
    }

    @Test
    void atualizar_deveChamarRepositorio() {
        Personagem p = new Personagem();
        when(personagemRepository.buscarPorNome("Naruto")).thenReturn(p);
        when(personagemRepository.atualizar("Naruto", p)).thenReturn(p);

        Personagem resultado = service.atualizar("Naruto", p);

        assertEquals(p, resultado);
        verify(personagemRepository).atualizar("Naruto", p);
    }

    @Test
    void usarJutsu_deveRetornarMensagem() {
        NinjaDTO dto = new NinjaDTO();
        dto.setNome("Naruto");

        String msg = service.usarJutsu(dto);

        assertTrue(msg.contains("Naruto está pronto para usar um jutsu"));
    }

    @Test
    void desviar_deveRetornarMensagem() {
        NinjaDTO dto = new NinjaDTO();
        dto.setNome("Naruto");

        String msg = service.desviar(dto);

        assertTrue(msg.contains("Naruto está tentando desviar"));
    }

    @Test
    void adicionarJutsu_deveAdicionarQuandoPersonagemExiste() {
        Personagem p = Mockito.mock(Personagem.class);
        when(personagemRepository.buscarPorNome("Naruto")).thenReturn(p);
        when(personagemRepository.atualizar(eq("Naruto"), any())).thenReturn(p);

        Jutsu jutsu = new Jutsu();
        String msg = service.adicionarJutsu("Naruto", "Rasengan", jutsu);

        verify(p).adicionarJutsu("Rasengan", jutsu);
        verify(personagemRepository).atualizar("Naruto", p);
        assertEquals("Jutsu Rasengan adicionado ao personagem Naruto", msg);
    }

    @Test
    void adicionarJutsu_deveRetornarMensagemSePersonagemNaoExiste() {
        when(personagemRepository.buscarPorNome("Naruto")).thenReturn(null);

        String msg = service.adicionarJutsu("Naruto", "Rasengan", new Jutsu());

        assertEquals("Personagem não encontrado.", msg);
    }

    @Test
    void atacar_deveRetornarMensagemSeAtacanteOuAlvoNaoEncontrado() {
        when(personagemRepository.buscarPorNome("Naruto")).thenReturn(null);

        String msg = service.atacar("Naruto", "Sasuke", "Rasengan");

        assertEquals("Atacante ou alvo não encontrado.", msg);
    }

    @Test
    void atacar_deveRetornarMensagemSeAtacanteNaoTemJutsu() {
        Personagem atacante = mock(Personagem.class);
        Personagem alvo = mock(Personagem.class);
        when(personagemRepository.buscarPorNome("Naruto")).thenReturn(atacante);
        when(personagemRepository.buscarPorNome("Sasuke")).thenReturn(alvo);
        when(atacante.getJutsus()).thenReturn(new HashMap<>());

        String msg = service.atacar("Naruto", "Sasuke", "Rasengan");

        assertEquals("O atacante não possui esse jutsu.", msg);
    }

    @Test
    void atacar_deveRetornarMensagemSeChakraInsuficiente() {
        Personagem atacante = mock(Personagem.class);
        Personagem alvo = mock(Personagem.class);
        Jutsu jutsu = new Jutsu();
        jutsu.setConsumoDeChakra(100);
        jutsu.setDano(50);

        Map<String, Jutsu> jutsus = new HashMap<>();
        jutsus.put("Rasengan", jutsu);

        when(personagemRepository.buscarPorNome("Naruto")).thenReturn(atacante);
        when(personagemRepository.buscarPorNome("Sasuke")).thenReturn(alvo);
        when(atacante.getJutsus()).thenReturn(jutsus);
        when(atacante.getChakra()).thenReturn(50);

        String msg = service.atacar("Naruto", "Sasuke", "Rasengan");

        assertEquals("Chakra insuficiente para usar esse jutsu.", msg);
    }

    @Test
    void atacar_deveRetornarMensagemDeDesvio() {
        Personagem atacante = mock(Personagem.class);
        Personagem alvo = mock(Personagem.class);
        Jutsu jutsu = new Jutsu();
        jutsu.setConsumoDeChakra(10);
        jutsu.setDano(20);

        Map<String, Jutsu> jutsus = new HashMap<>();
        jutsus.put("Rasengan", jutsu);

        when(personagemRepository.buscarPorNome("Naruto")).thenReturn(atacante);
        when(personagemRepository.buscarPorNome("Sasuke")).thenReturn(alvo);
        when(atacante.getJutsus()).thenReturn(jutsus);
        when(atacante.getChakra()).thenReturn(100);
        doNothing().when(atacante).reduzirChakra(10);
        doReturn(true).when(service).tentarDesviar();

        when(alvo.getNome()).thenReturn("Sasuke");

        String msg = service.atacar("Naruto", "Sasuke", "Rasengan");

        assertEquals("Sasuke conseguiu desviar do jutsu Rasengan!", msg);
    }

    @Test
    void atacar_deveRetornarMensagemDeDerrota() {
        Personagem atacante = mock(Personagem.class);
        Personagem alvo = mock(Personagem.class);
        Jutsu jutsu = new Jutsu();
        jutsu.setConsumoDeChakra(10);
        jutsu.setDano(100);

        Map<String, Jutsu> jutsus = new HashMap<>();
        jutsus.put("Rasengan", jutsu);

        when(personagemRepository.buscarPorNome("Naruto")).thenReturn(atacante);
        when(personagemRepository.buscarPorNome("Sasuke")).thenReturn(alvo);
        when(atacante.getJutsus()).thenReturn(jutsus);
        when(atacante.getChakra()).thenReturn(100);
        doNothing().when(atacante).reduzirChakra(10);
        doReturn(false).when(service).tentarDesviar();

        when(alvo.getNome()).thenReturn("Sasuke");
        doAnswer(invocation -> {
            when(alvo.getVida()).thenReturn(0);
            return null;
        }).when(alvo).reduzirVida(100);
        when(atacante.getNome()).thenReturn("Naruto");

        String msg = service.atacar("Naruto", "Sasuke", "Rasengan");

        assertEquals("Naruto usou Rasengan e derrotou Sasuke!", msg);
    }

    @Test
    void atacar_deveRetornarMensagemDeDano() {
        Personagem atacante = mock(Personagem.class);
        Personagem alvo = mock(Personagem.class);
        Jutsu jutsu = new Jutsu();
        jutsu.setConsumoDeChakra(10);
        jutsu.setDano(20);

        Map<String, Jutsu> jutsus = new HashMap<>();
        jutsus.put("Rasengan", jutsu);

        when(personagemRepository.buscarPorNome("Naruto")).thenReturn(atacante);
        when(personagemRepository.buscarPorNome("Sasuke")).thenReturn(alvo);
        when(atacante.getJutsus()).thenReturn(jutsus);
        when(atacante.getChakra()).thenReturn(100);
        doNothing().when(atacante).reduzirChakra(10);
        doReturn(false).when(service).tentarDesviar();

        when(alvo.getNome()).thenReturn("Sasuke");
        doAnswer(invocation -> {
            when(alvo.getVida()).thenReturn(50);
            return null;
        }).when(alvo).reduzirVida(20);
        when(atacante.getNome()).thenReturn("Naruto");
        when(personagemRepository.atualizar("Sasuke", alvo)).thenReturn(alvo);

        String msg = service.atacar("Naruto", "Sasuke", "Rasengan");

        assertEquals("Naruto usou Rasengan e causou 20 de dano em Sasuke", msg);
    }

    @Test
    void desviarComDano_deveRetornarMensagemDeDesvio() {
        Personagem p = mock(Personagem.class);
        when(personagemRepository.buscarPorNome("Naruto")).thenReturn(p);
        when(p.getNome()).thenReturn("Naruto");
        doReturn(true).when(service).tentarDesviar();

        String msg = service.desviar("Naruto", 30);

        assertEquals("Naruto conseguiu desviar do ataque!", msg);
    }

    @Test
    void desviarComDano_deveRetornarMensagemDeDano() {
        Personagem p = mock(Personagem.class);
        when(personagemRepository.buscarPorNome("Naruto")).thenReturn(p);
        when(p.getNome()).thenReturn("Naruto");
        doReturn(false).when(service).tentarDesviar();
        doAnswer(invocation -> {
            when(p.getVida()).thenReturn(70);
            return null;
        }).when(p).reduzirVida(30);
        when(personagemRepository.atualizar("Naruto", p)).thenReturn(p);

        String msg = service.desviar("Naruto", 30);

        assertEquals("Naruto falhou ao desviar e perdeu 30 de vida.", msg);
    }

    @Test
    void desviarComDano_deveRetornarMensagemSePersonagemNaoExiste() {
        when(personagemRepository.buscarPorNome("Naruto")).thenReturn(null);

        String msg = service.desviar("Naruto", 30);

        assertEquals("Personagem não encontrado.", msg);
    }
}