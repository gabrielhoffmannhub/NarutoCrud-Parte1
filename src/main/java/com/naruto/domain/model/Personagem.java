package com.naruto.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Personagem {
    private String nome;
    private int idade;
    private String aldeia;
    private List<String> jutsus;
    private int chakra;

    public Personagem(String nome, int idade, String aldeia) {
        this.nome = nome;
        this.idade = idade;
        this.aldeia = aldeia;
        this.jutsus = new ArrayList<>();
        this.chakra = 100;
    }

    public void adicionarJutsu(String jutsu) {
        jutsus.add(jutsu);
    }

    public void aumentarChakra(int quantidade) {
        chakra += quantidade;
    }

    public void exibirInformacoes() {
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Aldeia: " + aldeia);
        System.out.println("Jutsus: " + jutsus);
        System.out.println("Chakra: " + chakra);
    }
}