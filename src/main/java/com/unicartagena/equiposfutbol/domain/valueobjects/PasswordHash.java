package com.unicartagena.equiposfutbol.domain.valueobjects;

import com.unicartagena.equiposfutbol.domain.exceptions.InvalidUserDataException;

public record PasswordHash(String value) {

    public PasswordHash {
        if (value == null || value.isBlank()) {
            throw new InvalidUserDataException("El hash de la contrasena es obligatorio");
        }
        value = value.trim();
    }

    public static PasswordHash of(String value) {
        return new PasswordHash(value);
    }
}
