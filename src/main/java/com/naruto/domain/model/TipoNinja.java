package com.naruto.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoNinja {
    TAIJUTSU,
    NINJUTSU;

    @JsonCreator
    public static TipoNinja fromString(String value) {
        return TipoNinja.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
