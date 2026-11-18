package com.unicartagena.equiposfutbol.application.service.dto.queries;

import com.unicartagena.equiposfutbol.domain.exceptions.InvalidUserDataException;
import com.unicartagena.equiposfutbol.domain.valueobjects.RawPassword;
import com.unicartagena.equiposfutbol.domain.valueobjects.Username;

public record LoginQuery(Username username, RawPassword password) {

    public LoginQuery {
        if (username == null || password == null) {
            throw new InvalidUserDataException("Usuario y contrasena son obligatorios");
        }
    }
}
