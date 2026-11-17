package com.unicartagena.equiposfutbol.domain.valueobjects;

import com.unicartagena.equiposfutbol.domain.exceptions.InvalidEquipoDataException;

public record TextoCorto(String value) {

    private static final int MAX_LENGTH = 150;

    public TextoCorto {
        if (value == null || value.isBlank()) {
            throw new InvalidEquipoDataException("El texto es obligatorio");
        }
        value = value.trim().replaceAll("\\s+", " ");
        if (value.length() > MAX_LENGTH) {
            throw new InvalidEquipoDataException(
                    "El texto no puede superar los 150 caracteres");
        }
    }

    public static TextoCorto of(String value) {
        return new TextoCorto(value);
    }
}
