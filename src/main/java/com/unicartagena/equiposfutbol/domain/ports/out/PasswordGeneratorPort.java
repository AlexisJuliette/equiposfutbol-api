package com.unicartagena.equiposfutbol.domain.ports.out;

import com.unicartagena.equiposfutbol.domain.valueobjects.RawPassword;

public interface PasswordGeneratorPort {
    RawPassword generate();    
}
