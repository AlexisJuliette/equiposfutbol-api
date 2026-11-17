package com.unicartagena.equiposfutbol.application.service.dto.commands;

import com.unicartagena.equiposfutbol.domain.exceptions.InvalidEquipoDataException;
import com.unicartagena.equiposfutbol.domain.valueobjects.EquipoId;

public record DeleteEquipoCommand(EquipoId equipoId) {

    public DeleteEquipoCommand {
        if (equipoId == null) {
            throw new InvalidEquipoDataException("El id del equipo es obligatorio para eliminarlo");
        }
    }
}
