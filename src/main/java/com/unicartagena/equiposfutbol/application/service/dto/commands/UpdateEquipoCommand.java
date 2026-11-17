package com.unicartagena.equiposfutbol.application.service.dto.commands;

import com.unicartagena.equiposfutbol.domain.enums.CategoriaEquipo;
import com.unicartagena.equiposfutbol.domain.exceptions.InvalidEquipoDataException;
import com.unicartagena.equiposfutbol.domain.valueobjects.EquipoId;
import com.unicartagena.equiposfutbol.domain.valueobjects.Estadisticas;
import com.unicartagena.equiposfutbol.domain.valueobjects.NombreEquipo;
import com.unicartagena.equiposfutbol.domain.valueobjects.TextoCorto;
import com.unicartagena.equiposfutbol.domain.valueobjects.Ubicacion;

public record UpdateEquipoCommand(
        EquipoId equipoId,
        NombreEquipo nombre,
        TextoCorto eslogan,
        TextoCorto tecnico,
        Ubicacion ubicacion,
        CategoriaEquipo categoria,
        Estadisticas estadisticas) {

    public UpdateEquipoCommand {
        if (equipoId == null || nombre == null || eslogan == null || tecnico == null
                || ubicacion == null || categoria == null || estadisticas == null) {
            throw new InvalidEquipoDataException("Faltan datos para actualizar el equipo");
        }
    }
}
