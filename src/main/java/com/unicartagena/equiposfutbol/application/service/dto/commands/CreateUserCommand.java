package com.unicartagena.equiposfutbol.application.service.dto.commands;

import com.unicartagena.equiposfutbol.domain.exceptions.InvalidUserDataException;
import com.unicartagena.equiposfutbol.domain.valueobjects.EmailAddress;
import com.unicartagena.equiposfutbol.domain.valueobjects.RawPassword;
import com.unicartagena.equiposfutbol.domain.valueobjects.TextoCorto;
import com.unicartagena.equiposfutbol.domain.valueobjects.Username;

public record CreateUserCommand(
        Username username,
        RawPassword password,
        TextoCorto nombre,
        EmailAddress email) {

    public CreateUserCommand {
        if (username == null || password == null || nombre == null || email == null) {
            throw new InvalidUserDataException("Faltan datos para crear el usuario");
        }
    }  
}
