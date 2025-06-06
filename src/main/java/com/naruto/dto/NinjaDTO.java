package com.naruto.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class NinjaDTO {
    private String nome;
    private String tipoNinja;
    private int vida;
    private int chakra;
    private Map<String, JutsuDTO> jutsus;
}