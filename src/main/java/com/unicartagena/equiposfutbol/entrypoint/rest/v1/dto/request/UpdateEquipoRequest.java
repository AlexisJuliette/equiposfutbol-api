package com.unicartagena.equiposfutbol.entrypoint.rest.v1.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateEquipoRequest(
        @NotBlank @Size(max = 120) String nombre,
        @NotBlank @Size(max = 150) String eslogan,
        @NotBlank @Size(max = 150) String tecnico,
        @NotBlank @Size(max = 150) String pais,
        @NotBlank @Size(max = 150) String ciudad,
        @NotBlank @Size(max = 30) String categoria,
        @Min(0) Integer goles,
        @Min(0) Integer partidosJugados,
        @Min(0) Integer partidosGanados,
        @Min(0) Integer empates,
        @Min(0) Integer campeonatos,
        @Min(0) Integer expulsiones) {
}
