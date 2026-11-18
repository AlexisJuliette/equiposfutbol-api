package com.unicartagena.equiposfutbol.domain.valueobjects;

import com.unicartagena.equiposfutbol.domain.exceptions.InvalidUserDataException;

public record RawPassword(String value) {

    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 72;

    public RawPassword {
        if (value == null || value.isBlank()) {
            throw new InvalidUserDataException("La contrasena es obligatoria");
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new InvalidUserDataException(
                    "La contrasena debe tener entre 6 y 72 caracteres");
        }
    }

    public static RawPassword of(String value) {
        return new RawPassword(value);
    }

    @Override
    public String toString() {
        return "RawPassword{****}";
    }
}
