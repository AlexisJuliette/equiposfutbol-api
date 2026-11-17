package com.unicartagena.equiposfutbol.domain.exceptions;

public final class DuplicateEquipoException extends RuntimeException {

    public DuplicateEquipoException() {
        super("Ya existe un equipo con ese nombre");
    }
}
