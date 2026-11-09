package com.unicartagena.equiposfutbol.domain.valueobjects;

import java.util.UUID;

import com.unicartagena.equiposfutbol.domain.exceptions.InvalidEquipoDataException;

public record EquipoId(UUID value) {
    public EquipoId {
        if (value == null) {
            throw new InvalidEquipoDataException("El id del equipo es obligatorio");
        }
    }

    public static EquipoId from(UUID value) {
        return new EquipoId(value);
    }

    public static EquipoId random() {
        return new EquipoId(UUID.randomUUID());
    }
}
