package com.unicartagena.equiposfutbol.domain.enums;

import com.unicartagena.equiposfutbol.domain.exceptions.InvalidEquipoDataException;

import java.util.Arrays;

public enum CategoriaEquipo {

    PRIMERA_A,
    PRIMERA_B,
    JUVENIL,
    INFANTIL,
    AFICIONADO;

    public static CategoriaEquipo from(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidEquipoDataException("La categoria del equipo es obligatoria");
        }
        String normalizado = value.trim().toUpperCase().replace(' ', '_');
        return Arrays.stream(values())
                .filter(categoria -> categoria.name().equals(normalizado))
                .findFirst()
                .orElseThrow(() -> new InvalidEquipoDataException(
                        "Categoria de equipo desconocida: " + value));
    }
}
