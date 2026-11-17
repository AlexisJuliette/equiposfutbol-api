package com.unicartagena.equiposfutbol.entrypoint.rest.v1.dto.response;

import java.time.Instant;
import java.util.UUID;

public record EquipoResponse(
        UUID id,
        String nombre,
        String eslogan,
        String tecnico,
        String pais,
        String ciudad,
        String categoria,
        int goles,
        int partidosJugados,
        int partidosGanados,
        int empates,
        int partidosPerdidos,
        int campeonatos,
        int expulsiones,
        double promedioGoles,
        Instant createdAt,
        Instant updatedAt) {
}
