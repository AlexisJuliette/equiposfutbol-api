package com.unicartagena.equiposfutbol.application.service.dto.response;

import java.time.Instant;
import java.util.UUID;

public record UserResult(
        UUID id,
        String username,
        String nombre,
        String email,
        Instant createdAt,
        Instant updatedAt) {
    
}
  