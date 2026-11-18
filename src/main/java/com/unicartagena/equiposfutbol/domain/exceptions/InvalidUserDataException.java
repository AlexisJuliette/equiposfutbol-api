package com.unicartagena.equiposfutbol.domain.exceptions;

public final class InvalidUserDataException extends RuntimeException {

    public InvalidUserDataException(String message) {
        super(message);
    }
}
