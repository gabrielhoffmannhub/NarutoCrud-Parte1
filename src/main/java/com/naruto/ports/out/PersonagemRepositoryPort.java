package com.naruto.ports.out;

import com.naruto.domain.model.Personagem;

import java.util.List;

public interface PersonagemRepositoryPort {
    List<Personagem> listarTodos();
    Personagem buscarPorNome(String nome);
    Personagem salvar(Personagem personagem);
    void deletar(String nome);
    Personagem atualizar(String nome, Personagem personagemAtualizado);
}
