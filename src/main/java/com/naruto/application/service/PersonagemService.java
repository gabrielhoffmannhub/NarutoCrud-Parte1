package com.naruto.application.service;

import com.naruto.domain.model.*;
import com.naruto.dto.NinjaDTO;
import com.naruto.ports.in.PersonagemServicePort;
import com.naruto.ports.out.PersonagemRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonagemService implements PersonagemServicePort {

    private final PersonagemRepositoryPort personagemRepository;

    @Autowired
    public PersonagemService(PersonagemRepositoryPort personagemRepository) {
        this.personagemRepository = personagemRepository;
    }

    public Personagem criarNinja(NinjaDTO ninjaDTO) {
        TipoNinja tipoNinja = TipoNinja.valueOf(ninjaDTO.getTipoNinja().toUpperCase());
        return new Personagem(ninjaDTO.getNome(), ninjaDTO.getIdade(), ninjaDTO.getAldeia(),ninjaDTO.getChakra(), tipoNinja);
    }

    @Override
    public List<Personagem> listarTodos() {
        return personagemRepository.listarTodos();
    }

    @Override
    public Personagem buscarPorNome(String nome) {
        return personagemRepository.buscarPorNome(nome);
    }

    @Override
    public Personagem salvar(Personagem personagem) {
        return personagemRepository.salvar(personagem);
    }

    @Override
    public void deletar(String nome) {
        personagemRepository.deletar(nome);
    }

    @Override
    public Personagem atualizar(String nome, Personagem personagem) {
        return personagemRepository.atualizar(nome, personagem);
    }

    @Override
    public String usarJutsu(NinjaDTO ninjaDTO) {
        Personagem personagem = criarNinja(ninjaDTO);
        return ninjaDTO.getNome() + " usou um jutsu de " + ninjaDTO.getTipoNinja();
    }

    @Override
    public String desviar(NinjaDTO ninjaDTO) {
        Personagem personagem = criarNinja(ninjaDTO);
        return ninjaDTO.getNome() + " desviou de um ataque usando " + ninjaDTO.getTipoNinja();
    }
}
