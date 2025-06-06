package com.naruto.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Personagem {
    private String nome;
    private TipoNinja tipoNinja;
    private int vida = 100;
    private int chakra = 100;
    private Map<String, Jutsu> jutsus = new HashMap<>();

    public Personagem(String nome, TipoNinja tipoNinja) {
        this.nome = nome;
        this.tipoNinja = tipoNinja;
        this.vida = 100;
        this.chakra = 100;
        this.jutsus = new HashMap<>();
    }

    public void adicionarJutsu(String nomeJutsu, Jutsu jutsu) {
        if (jutsus == null) {
            jutsus = new HashMap<>();
        }
        if (nomeJutsu == null || nomeJutsu.isEmpty()) {
            throw new IllegalArgumentException("O nome do jutsu não pode ser vazio ou nulo.");
        }
        if (jutsu == null || jutsu.getDano() <= 0 || jutsu.getConsumoDeChakra() <= 0) {
            throw new IllegalArgumentException("Jutsu inválido: dano e consumo de chakra devem ser maiores que zero.");
        }
        jutsus.put(nomeJutsu, jutsu);
    }

    public Map<String, Jutsu> getJutsus() {
        if (jutsus == null) {
            return new HashMap<>();
        }
        return jutsus;
    }

    public void reduzirChakra(int quantidade) {
        this.chakra = Math.max(0, this.chakra - quantidade);
    }

    public void reduzirVida(int dano) {
        this.vida = Math.max(0, this.vida - dano);
    }
}
