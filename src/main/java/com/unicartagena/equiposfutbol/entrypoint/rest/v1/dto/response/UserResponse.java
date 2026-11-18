package com.unicartagena.equiposfutbol.entrypoint.rest.v1.dto.response;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String nombre,
        String email,
        Instant createdAt,
        Instant updatedAt) {
}
