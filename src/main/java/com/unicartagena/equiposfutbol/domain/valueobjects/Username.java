package com.unicartagena.equiposfutbol.domain.valueobjects;

import com.unicartagena.equiposfutbol.domain.exceptions.InvalidUserDataException;
import java.util.regex.Pattern;

public record Username(String value) {

    private static final Pattern VALIDO = Pattern.compile("^[a-z0-9._-]{3,40}$");

    public Username {
        if (value == null || value.isBlank()) {
            throw new InvalidUserDataException("El username es obligatorio");
        }
        value = value.trim().toLowerCase();
        if (!VALIDO.matcher(value).matches()) {
            throw new InvalidUserDataException(
                    "El username debe tener entre 3 y 40 caracteres y solo puede contener letras, numeros, puntos, guiones o guiones bajos");
        }
    }

    public static Username of(String value) {
        return new Username(value);
    }
}
