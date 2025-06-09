package com.naruto.adapters.input;

import com.naruto.domain.model.Jutsu;
import com.naruto.domain.model.Personagem;
import com.naruto.dto.NinjaDTO;
import com.naruto.ports.input.PersonagemServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/{nome}")
    public Personagem buscarPorNome(@PathVariable String nome) {
        return personagemService.buscarPorNome(nome);
    }

    @PostMapping
    public Personagem criar(@RequestBody NinjaDTO ninjaDTO) {
        Personagem personagem = personagemService.criarNinja(ninjaDTO);
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

    @PostMapping("/{nome}/adicionar-jutsu")
    public String adicionarJutsu(@PathVariable String nome, @RequestParam String nomeJutsu, @RequestBody Jutsu jutsu) {
        return personagemService.adicionarJutsu(nome, nomeJutsu, jutsu);
    }

    @PostMapping("/atacar")
    public String atacar(@RequestParam String nomeAtacante, @RequestParam String nomeAlvo, @RequestParam String nomeJutsu) {
        return personagemService.atacar(nomeAtacante, nomeAlvo, nomeJutsu);
    }

    @PostMapping("/usar-jutsu")
    public String usarJutsu(@RequestBody NinjaDTO ninjaDTO) {
        return personagemService.usarJutsu(ninjaDTO);
    }

    @PostMapping("/desviar")
    public String desviar(@RequestBody NinjaDTO ninjaDTO) {
        return personagemService.desviar(ninjaDTO);
    }

    @PostMapping("/{nome}/desviar")
    public String desviarComDano(@PathVariable String nome, @RequestParam int dano) {
        return personagemService.desviar(nome, dano);
    }
}