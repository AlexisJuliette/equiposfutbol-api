package com.unicartagena.equiposfutbol.domain.valueobjects;

import java.util.UUID;
import com.unicartagena.equiposfutbol.domain.exceptions.InvalidUserDataException;

public record UserId(UUID value) {

    public UserId {
        if (value == null) {
            throw new InvalidUserDataException("El id del usuario es obligatorio");
        }
    }

    public static UserId from(UUID value) {
        return new UserId(value);
    }

    public static UserId random() {
        return new UserId(UUID.randomUUID());
    }
}
