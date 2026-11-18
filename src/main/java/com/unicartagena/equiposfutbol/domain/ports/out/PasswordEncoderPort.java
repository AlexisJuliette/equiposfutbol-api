package com.unicartagena.equiposfutbol.domain.ports.out;

import com.unicartagena.equiposfutbol.domain.valueobjects.PasswordHash;
import com.unicartagena.equiposfutbol.domain.valueobjects.RawPassword;

public interface PasswordEncoderPort {
    PasswordHash encode(RawPassword rawPassword);

    boolean matches(RawPassword rawPassword, PasswordHash passwordHash);   
}
