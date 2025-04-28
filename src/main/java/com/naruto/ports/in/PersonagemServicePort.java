package com.naruto.ports.in;

import com.naruto.domain.model.Personagem;
import com.naruto.dto.NinjaDTO;

import java.util.List;

public interface PersonagemServicePort {

    List<Personagem> listarTodos();

    Personagem buscarPorNome(String nome);

    Personagem salvar(Personagem personagem);

    Personagem atualizar(String nome, Personagem personagem);

    void deletar(String nome);

    String usarJutsu(NinjaDTO ninjaDTO);
    String desviar(NinjaDTO ninjaDTO);
}
