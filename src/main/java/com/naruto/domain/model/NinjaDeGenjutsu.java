package com.naruto.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NinjaDeGenjutsu extends Personagem implements Ninja {

    public NinjaDeGenjutsu(String nome, int idade, String aldeia) {
        super(nome, idade, aldeia);
    }

        @Override
    public void usarJutsu() {
        System.out.println(getNome() + " está usando um Genjutsu!");
    }

    @Override
    public void desviar() {
        System.out.println(getNome() + " está desviando de um ataque usando Taijutsu!");
    }
}
