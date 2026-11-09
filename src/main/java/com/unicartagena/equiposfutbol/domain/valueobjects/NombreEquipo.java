package com.unicartagena.equiposfutbol.domain.valueobjects;

import com.unicartagena.equiposfutbol.domain.exceptions.InvalidEquipoDataException;

public record NombreEquipo(String value) {
    private static final int MAX_LENGTH = 120;

    public NombreEquipo {
        if (value == null || value.isBlank()) {
            throw new InvalidEquipoDataException("El nombre del equipo es obligatorio");
        }
        value = value.trim().replaceAll("\\s+", " ");
        if (value.length() > MAX_LENGTH) {
            throw new InvalidEquipoDataException(
                    "El nombre del equipo no puede superar los 120 caracteres");
        }
    }

    public static NombreEquipo of(String value) {
        return new NombreEquipo(value);
    }

    public String normalized() {
        return value.toLowerCase();
    }
}
