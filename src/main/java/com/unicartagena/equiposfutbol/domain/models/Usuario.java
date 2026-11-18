package com.unicartagena.equiposfutbol.domain.models;

import java.time.Instant;
import com.unicartagena.equiposfutbol.domain.exceptions.InvalidUserDataException;
import com.unicartagena.equiposfutbol.domain.valueobjects.EmailAddress;
import com.unicartagena.equiposfutbol.domain.valueobjects.PasswordHash;
import com.unicartagena.equiposfutbol.domain.valueobjects.TextoCorto;
import com.unicartagena.equiposfutbol.domain.valueobjects.UserId;
import com.unicartagena.equiposfutbol.domain.valueobjects.Username;

public final class Usuario {
    private final UserId id;
    private final Username username;
    private final PasswordHash passwordHash;
    private final TextoCorto nombre;
    private final EmailAddress email;
    private final Instant createdAt;
    private final Instant updatedAt;

    private Usuario(
            UserId id,
            Username username,
            PasswordHash passwordHash,
            TextoCorto nombre,
            EmailAddress email,
            Instant createdAt,
            Instant updatedAt) {
        if (id == null || username == null || passwordHash == null
                || nombre == null || email == null || createdAt == null || updatedAt == null) {
            throw new InvalidUserDataException("Todos los datos del usuario son obligatorios");
        }
        if (updatedAt.isBefore(createdAt)) {
            throw new InvalidUserDataException(
                    "La fecha de actualizacion no puede ser anterior a la de creacion");
        }
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.nombre = nombre;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Usuario create(
            UserId id,
            Username username,
            PasswordHash passwordHash,
            TextoCorto nombre,
            EmailAddress email,
            Instant creationTime) {
        return new Usuario(id, username, passwordHash, nombre, email, creationTime, creationTime);
    }

    public static Usuario rehydrate(
            UserId id,
            Username username,
            PasswordHash passwordHash,
            TextoCorto nombre,
            EmailAddress email,
            Instant createdAt,
            Instant updatedAt) {
        return new Usuario(id, username, passwordHash, nombre, email, createdAt, updatedAt);
    }

    public Usuario update(TextoCorto nuevoNombre, EmailAddress nuevoEmail, Instant updateTime) {
        return new Usuario(id, username, passwordHash, nuevoNombre, nuevoEmail, createdAt, updateTime);
    }

    public Usuario changePassword(PasswordHash nuevoHash, Instant updateTime) {
        return new Usuario(id, username, nuevoHash, nombre, email, createdAt, updateTime);
    }

    public UserId getId() {
        return id;
    }

    public Username getUsername() {
        return username;
    }

    public PasswordHash getPasswordHash() {
        return passwordHash;
    }

    public TextoCorto getNombre() {
        return nombre;
    }

    public EmailAddress getEmail() {
        return email;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }    
}
