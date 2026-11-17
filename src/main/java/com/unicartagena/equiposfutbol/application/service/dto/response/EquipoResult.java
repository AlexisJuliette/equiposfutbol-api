package com.unicartagena.equiposfutbol.application.service.dto.response;

import com.unicartagena.equiposfutbol.domain.enums.CategoriaEquipo;

import java.time.Instant;
import java.util.UUID;

public record EquipoResult(
        UUID id,
        String nombre,
        String eslogan,
        String tecnico,
        String pais,
        String ciudad,
        CategoriaEquipo categoria,
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
