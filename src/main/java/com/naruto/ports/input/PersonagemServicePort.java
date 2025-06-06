package com.naruto.ports.input;

import com.naruto.domain.model.Jutsu;
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

    Personagem criarNinja(NinjaDTO ninjaDTO);

    String adicionarJutsu(String nomePersonagem, String nomeJutsu, Jutsu jutsu);

    String atacar(String nomeAtacante, String nomeAlvo, String nomeJutsu);

    String desviar(String nomePersonagem, int dano);
}
