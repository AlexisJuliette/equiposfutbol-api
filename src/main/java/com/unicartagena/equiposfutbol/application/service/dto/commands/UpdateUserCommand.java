package com.unicartagena.equiposfutbol.application.service.dto.commands;

import com.unicartagena.equiposfutbol.domain.exceptions.InvalidUserDataException;
import com.unicartagena.equiposfutbol.domain.valueobjects.EmailAddress;
import com.unicartagena.equiposfutbol.domain.valueobjects.TextoCorto;

public record UpdateUserCommand(TextoCorto nombre, EmailAddress email) {

    public UpdateUserCommand {
        if (nombre == null || email == null) {
            throw new InvalidUserDataException("Faltan datos para actualizar el usuario");
        }
    }
}
