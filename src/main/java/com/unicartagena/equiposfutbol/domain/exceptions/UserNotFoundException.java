package com.unicartagena.equiposfutbol.domain.exceptions;

public final class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("El usuario solicitado no existe");
    }

}
