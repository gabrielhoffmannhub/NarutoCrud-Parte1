package com.naruto.application.service;

import com.naruto.domain.model.Jutsu;
import com.naruto.domain.model.Personagem;
import com.naruto.domain.model.TipoNinja;
import com.naruto.dto.NinjaDTO;
import com.naruto.ports.input.PersonagemServicePort;
import com.naruto.ports.output.PersonagemRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
public class PersonagemService implements PersonagemServicePort {

    private final PersonagemRepositoryPort personagemRepository;

    @Autowired
    public PersonagemService(PersonagemRepositoryPort personagemRepository) {
        this.personagemRepository = personagemRepository;
    }

    public Personagem criarNinja(NinjaDTO ninjaDTO) {
        TipoNinja tipoNinja = TipoNinja.valueOf(ninjaDTO.getTipoNinja().toUpperCase());
        return new Personagem(ninjaDTO.getNome(), tipoNinja);
    }

    @Override
    public List<Personagem> listarTodos() {
        List<Personagem> lista = personagemRepository.listarTodos();
        if (lista == null || lista.isEmpty()) {
            throw new RuntimeException("Nenhum personagem encontrado");
        }
        return lista;
    }

    @Override
    public Personagem buscarPorNome(String nome) {
        Personagem personagem = personagemRepository.buscarPorNome(nome);
        if (personagem == null) {
            throw new RuntimeException("Personagem com o nome " + nome + " não encontrado");
        }
        return personagem;
    }

    @Override
    public Personagem salvar(Personagem personagem) {
        Personagem existente = personagemRepository.buscarPorNome(personagem.getNome());
        if (existente != null) {
            throw new RuntimeException("Personagem já existe");
        }
        return personagemRepository.salvar(personagem);
    }

    @Override
    public void deletar(String nome) {
        Personagem personagem = personagemRepository.buscarPorNome(nome);
        if (personagem == null) {
            throw new RuntimeException("Personagem com o nome " + nome + " não encontrado");
        }
        personagemRepository.deletar(nome);
    }

    @Override
    public Personagem atualizar(String nome, Personagem personagem) {
        Personagem existente = personagemRepository.buscarPorNome(nome);
        if (existente == null) {
            throw new RuntimeException("Personagem com o nome " + nome + " não encontrado");
        }
        return personagemRepository.atualizar(nome, personagem);
    }

    @Override
    public String usarJutsu(NinjaDTO ninjaDTO) {
        return ninjaDTO.getNome() + " está pronto para usar um jutsu. Use o método atacar para executar.";
    }

    @Override
    public String desviar(NinjaDTO ninjaDTO) {
        return ninjaDTO.getNome() + " está tentando desviar. Use o método desviar(dano) para simular.";
    }

    public String adicionarJutsu(String nomePersonagem, String nomeJutsu, Jutsu jutsu) {
        Personagem personagem = personagemRepository.buscarPorNome(nomePersonagem);
        if (personagem == null) {
            throw new RuntimeException("Personagem com o nome " + nomePersonagem + " não encontrado");
        }
        personagem.adicionarJutsu(nomeJutsu, jutsu);
        personagemRepository.atualizar(nomePersonagem, personagem);
        return "Jutsu " + nomeJutsu + " adicionado ao personagem " + nomePersonagem;
    }

    public String atacar(String nomeAtacante, String nomeAlvo, String nomeJutsu) {
        Personagem atacante = personagemRepository.buscarPorNome(nomeAtacante);
        Personagem alvo = personagemRepository.buscarPorNome(nomeAlvo);

        if (atacante == null || alvo == null) {
            throw new RuntimeException("Atacante ou alvo não encontrado.");
        }

        if (!atacante.getJutsus().containsKey(nomeJutsu)) {
            throw new RuntimeException("O atacante não possui esse jutsu.");
        }

        Jutsu jutsu = atacante.getJutsus().get(nomeJutsu);

        if (atacante.getChakra() < jutsu.getConsumoDeChakra()) {
            throw new RuntimeException("Chakra insuficiente para usar esse jutsu.");
        }

        atacante.reduzirChakra(jutsu.getConsumoDeChakra());

        boolean desviou = tentarDesviar();
        if (desviou) {
            return alvo.getNome() + " conseguiu desviar do jutsu " + nomeJutsu + "!";
        } else {
            alvo.reduzirVida(jutsu.getDano());
            if (alvo.getVida() <= 0) {
                return atacante.getNome() + " usou " + nomeJutsu + " e derrotou " + alvo.getNome() + "!";
            }
            personagemRepository.atualizar(nomeAlvo, alvo);
            return atacante.getNome() + " usou " + nomeJutsu + " e causou " + jutsu.getDano() + " de dano em " + alvo.getNome();
        }
    }

    public String desviar(String nomePersonagem, int dano) {
        Personagem personagem = personagemRepository.buscarPorNome(nomePersonagem);
        if (personagem == null) {
            throw new RuntimeException("Personagem com o nome " + nomePersonagem + " não encontrado");
        }

        boolean desviou = tentarDesviar();
        if (desviou) {
            return personagem.getNome() + " conseguiu desviar do ataque!";
        } else {
            personagem.reduzirVida(dano);
            personagemRepository.atualizar(nomePersonagem, personagem);
            return personagem.getNome() + " falhou ao desviar e perdeu " + dano + " de vida.";
        }
    }

    protected boolean tentarDesviar() {
        Random random = new Random();
        return random.nextBoolean();
    }
}