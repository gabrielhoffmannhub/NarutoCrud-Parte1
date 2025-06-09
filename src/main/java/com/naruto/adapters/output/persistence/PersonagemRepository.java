package com.naruto.adapters.output.persistence;

import com.naruto.domain.model.Personagem;
import com.naruto.ports.output.PersonagemRepositoryPort;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonagemRepository implements PersonagemRepositoryPort {

    private final List<Personagem> personagens = new ArrayList<>();

    @Override
    public List<Personagem> listarTodos() {
        return new ArrayList<>(personagens);
    }

    @Override
    public Personagem buscarPorNome(String nome) {
        return personagens.stream()
                .filter(p -> p.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Personagem salvar(Personagem personagem) {
        personagens.add(personagem);
        return personagem;
    }

    @Override
    public void deletar(String nome) {
        Personagem personagem = buscarPorNome(nome);
        if (personagem != null) {
            personagens.remove(personagem);
        }
    }

    @Override
    public Personagem atualizar(String nome, Personagem personagemAtualizado) {
        Personagem personagemExistente = buscarPorNome(nome);
        if (personagemExistente != null) {
            personagemExistente.setNome(personagemAtualizado.getNome());
            personagemExistente.setTipoNinja(personagemAtualizado.getTipoNinja());
            personagemExistente.setVida(personagemAtualizado.getVida());
            personagemExistente.setChakra(personagemAtualizado.getChakra());
            personagemExistente.setJutsus(personagemAtualizado.getJutsus());
        }
        return personagemExistente;
    }
}