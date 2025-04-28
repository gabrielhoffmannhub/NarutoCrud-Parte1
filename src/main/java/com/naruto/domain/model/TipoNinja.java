package com.naruto.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TipoNinja {
    TAIJUTSU,
    NINJUTSU,
    GENJUTSU;

    @JsonCreator
    public static TipoNinja fromString(String value) {
        return TipoNinja.valueOf(value.toUpperCase());
    }
}
