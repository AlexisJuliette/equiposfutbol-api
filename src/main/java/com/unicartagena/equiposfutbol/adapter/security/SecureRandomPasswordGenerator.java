package com.unicartagena.equiposfutbol.adapter.security;

import java.security.SecureRandom;

import com.unicartagena.equiposfutbol.domain.ports.out.PasswordGeneratorPort;
import com.unicartagena.equiposfutbol.domain.valueobjects.RawPassword;

public class SecureRandomPasswordGenerator implements PasswordGeneratorPort {

    private static final String ALFABETO =
            "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final int LONGITUD = 12;

    private final SecureRandom random = new SecureRandom();

    @Override
    public RawPassword generate() {
        StringBuilder clave = new StringBuilder(LONGITUD);
        for (int i = 0; i < LONGITUD; i++) {
            clave.append(ALFABETO.charAt(random.nextInt(ALFABETO.length())));
        }
        return new RawPassword(clave.toString());
    }
}
