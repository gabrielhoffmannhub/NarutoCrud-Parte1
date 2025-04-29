package com.naruto.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Personagem {
    private String nome;
    private int idade;
    private String aldeia;
    private int chakra;
    private TipoNinja tipoNinja;
}
