package com.unicartagena.equiposfutbol.domain.exceptions;

public final class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Usuario o contrasena incorrectos");
    }

}
