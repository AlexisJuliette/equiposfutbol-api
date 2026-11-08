package com.unicartagena.equiposfutbol.domain.exceptions;

public final class InvalidEquipoDataException extends RuntimeException {

    public InvalidEquipoDataException(String message) {
        super(message);
    }
}