package com.naruto.adapters.memory;

import com.naruto.domain.model.Personagem;
import com.naruto.ports.out.PersonagemRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonagemRepositoryMemory implements PersonagemRepositoryPort {

    private final List<Personagem> personagens = new ArrayList<>();

    @Override
    public List<Personagem> listarTodos() {
        return personagens;
    }

    @Override
    public Personagem buscarPorNome(String nome) {
        return personagens.stream()
                .filter(p -> p.getNome().equals(nome))
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
        personagens.removeIf(p -> p.getNome().equals(nome));
    }

    @Override
    public Personagem atualizar(String nome, Personagem personagemAtualizado) {
        for (int i = 0; i < personagens.size(); i++) {
            if (personagens.get(i).getNome().equals(nome)) {
                personagens.set(i, personagemAtualizado);
                return personagemAtualizado;
            }
        }
        return null;
    }
}