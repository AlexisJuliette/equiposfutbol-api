package com.unicartagena.equiposfutbol.adapter.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.unicartagena.equiposfutbol.domain.ports.out.PasswordEncoderPort;
import com.unicartagena.equiposfutbol.domain.valueobjects.PasswordHash;
import com.unicartagena.equiposfutbol.domain.valueobjects.RawPassword;

public class BCryptPasswordEncoderAdapter implements PasswordEncoderPort {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public PasswordHash encode(RawPassword rawPassword) {
        return new PasswordHash(encoder.encode(rawPassword.value()));
    }

    @Override
    public boolean matches(RawPassword rawPassword, PasswordHash passwordHash) {
        return encoder.matches(rawPassword.value(), passwordHash.value());
    }
}
