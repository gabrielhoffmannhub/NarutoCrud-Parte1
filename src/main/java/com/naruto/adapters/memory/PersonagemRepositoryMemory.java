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
        if (personagens.isEmpty()) {
            throw new RuntimeException("Nenhum personagem encontrado");
        }
        return personagens;
    }

    @Override
    public Personagem buscarPorNome(String nome) {
        Personagem personagem = personagens.stream()
                .filter(p -> p.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
        if (personagem == null) {
            throw new RuntimeException("Personagem com o nome '" + nome + "' não encontrado");
        }
        return personagem;
    }


    @Override
    public Personagem salvar(Personagem personagem) {
        for (Personagem p : personagens) {
            if (p.getNome().equalsIgnoreCase(personagem.getNome())) {
                throw new RuntimeException("Personagem já existe");
            }
        }
        personagens.add(personagem);
        return personagem;
    }


    @Override
    public void deletar(String nome) {
        Personagem personagem = buscarPorNome(nome);
        if (personagem != null) {
            personagens.remove(personagem);
        } else {
            throw new RuntimeException("Personagem não encontrado");
        }
    }


    @Override
    public Personagem atualizar(String nome, Personagem personagemAtualizado) {
        Personagem personagemExistente = buscarPorNome(nome);
        if (personagemExistente != null) {
            personagemExistente.setNome(personagemAtualizado.getNome());
            personagemExistente.setIdade(personagemAtualizado.getIdade());
            personagemExistente.setAldeia(personagemAtualizado.getAldeia());
            personagemExistente.setTipoNinja(personagemAtualizado.getTipoNinja());

            return personagemExistente;
        } else {
            throw new RuntimeException("Personagem não encontrado para atualização!");
        }
    }
}



