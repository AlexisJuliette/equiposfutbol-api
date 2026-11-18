package com.unicartagena.equiposfutbol.domain.valueobjects;

import com.unicartagena.equiposfutbol.domain.exceptions.InvalidUserDataException;
import java.util.regex.Pattern;

public record EmailAddress(String value) {

    private static final Pattern VALIDO = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]{2,}$");

    public EmailAddress {
        if (value == null || value.isBlank()) {
            throw new InvalidUserDataException("El correo es obligatorio");
        }
        value = value.trim().toLowerCase();
        if (!VALIDO.matcher(value).matches()) {
            throw new InvalidUserDataException("El correo no tiene un formato valido");
        }
    }

    public static EmailAddress of(String value) {
        return new EmailAddress(value);
    }
}
