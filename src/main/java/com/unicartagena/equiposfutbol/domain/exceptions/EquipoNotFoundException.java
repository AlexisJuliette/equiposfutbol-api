package com.unicartagena.equiposfutbol.domain.exceptions;

public final class EquipoNotFoundException extends RuntimeException {

    public EquipoNotFoundException() {
        super("El equipo solicitado no existe");
    }
}