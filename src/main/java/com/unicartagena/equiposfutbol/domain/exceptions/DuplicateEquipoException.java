package com.unicartagena.equiposfutbol.domain.exceptions;

public class DuplicateEquipoException extends RuntimeException {

    public DuplicateEquipoException(String message) {
        super("Ya existe un equipo con ese nombre");
    }
    
}
