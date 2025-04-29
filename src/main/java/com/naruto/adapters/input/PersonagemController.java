package com.naruto.controller;

import com.naruto.domain.model.Personagem;
import com.naruto.dto.NinjaDTO;
import com.naruto.ports.in.PersonagemServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/personagens")
public class PersonagemController {

    private final PersonagemServicePort personagemService;

    @Autowired
    public PersonagemController(PersonagemServicePort personagemService) {
        this.personagemService = personagemService;
    }

    @GetMapping
    public List<Personagem> listarTodos() {
        return personagemService.listarTodos();
    }

    @GetMapping("/buscar/{nome}")
    public Personagem buscarPorNome(@PathVariable String nome) {
        return personagemService.buscarPorNome(nome);
    }

    @PostMapping
    public Personagem criar(@RequestBody Personagem personagem) {
        return personagemService.salvar(personagem);
    }

    @DeleteMapping("/{nome}")
    public void deletar(@PathVariable String nome) {
        personagemService.deletar(nome);
    }

    @PutMapping("/{nome}")
    public Personagem atualizar(@PathVariable String nome, @RequestBody Personagem personagem) {
        return personagemService.atualizar(nome, personagem);
    }

    @PostMapping("/usar-jutsu")
    public String usarJutsu(@RequestBody NinjaDTO ninjaDTO) {
        return personagemService.usarJutsu(ninjaDTO);
    }

    @PostMapping("/desviar")
    public String desviar(@RequestBody NinjaDTO ninjaDTO) {
        return personagemService.desviar(ninjaDTO);
    }
}
