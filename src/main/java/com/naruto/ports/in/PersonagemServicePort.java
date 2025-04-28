package com.naruto.ports.in;

import com.naruto.domain.model.Personagem;
import com.naruto.dto.NinjaDTO;

import java.util.List;

public interface PersonagemServicePort {

    List<Personagem> listarTodos();

    Personagem salvar(Personagem personagem);

    Personagem atualizar(String id, Personagem personagem);

    void deletar(String id);

    String usarJutsu(NinjaDTO ninjaDTO);
    String desviar(NinjaDTO ninjaDTO);
}
