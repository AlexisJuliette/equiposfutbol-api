package com.unicartagena.equiposfutbol.domain.exceptions;

public final class DuplicateUserException extends RuntimeException {

    public DuplicateUserException() {
        super("Ya existe un usuario con ese username o correo");
    }

}
