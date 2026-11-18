package com.unicartagena.equiposfutbol.application.service.dto.commands;

import com.unicartagena.equiposfutbol.domain.exceptions.InvalidUserDataException;
import com.unicartagena.equiposfutbol.domain.valueobjects.EmailAddress;

public record RecoverPasswordCommand(EmailAddress email) {

    public RecoverPasswordCommand {
        if (email == null) {
            throw new InvalidUserDataException("El correo es obligatorio para recuperar la contrasena");
        }
    }

}
