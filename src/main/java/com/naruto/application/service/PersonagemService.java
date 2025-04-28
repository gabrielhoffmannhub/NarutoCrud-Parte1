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

    @Override
    public List<Personagem> listarTodos() {
        return personagemRepository.listarTodos();
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
        Ninja ninja = criarNinja(ninjaDTO);
        ninja.usarJutsu();
        return ninjaDTO.getNome() + " usou um jutsu de " + ninjaDTO.getTipoDePoder() + "!";
    }

    @Override
    public String desviar(NinjaDTO ninjaDTO) {
        Ninja ninja = criarNinja(ninjaDTO);
        ninja.desviar();
        return ninjaDTO.getNome() + " desviou de um ataque usando " + ninjaDTO.getTipoDePoder() + "!";
    }

    private Ninja criarNinja(NinjaDTO ninjaDTO) {
        String tipoDePoder = ninjaDTO.getTipoDePoder().toLowerCase();

        if (tipoDePoder.equals("ninjutsu")) {
            return new NinjaDeNinjutsu(ninjaDTO.getNome(), ninjaDTO.getIdade(), ninjaDTO.getAldeia());
        } else if (tipoDePoder.equals("genjutsu")) {
            return new NinjaDeGenjutsu(ninjaDTO.getNome(), ninjaDTO.getIdade(), ninjaDTO.getAldeia());
        } else if (tipoDePoder.equals("taijutsu")) {
            return new NinjaDeTaijutsu(ninjaDTO.getNome(), ninjaDTO.getIdade(), ninjaDTO.getAldeia());
        } else {
            throw new IllegalArgumentException("Tipo de poder inválido: " + ninjaDTO.getTipoDePoder());
        }
    }
}

