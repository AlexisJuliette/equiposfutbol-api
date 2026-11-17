package com.unicartagena.equiposfutbol.application.service.dto.queries;

import com.unicartagena.equiposfutbol.domain.exceptions.InvalidEquipoDataException;
import com.unicartagena.equiposfutbol.domain.valueobjects.EquipoId;

public record GetEquipoQuery(EquipoId equipoId) {

    public GetEquipoQuery {
        if (equipoId == null) {
            throw new InvalidEquipoDataException("El id del equipo es obligatorio para consultarlo");
        }
    }
}
