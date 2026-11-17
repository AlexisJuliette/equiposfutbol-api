package com.unicartagena.equiposfutbol.application.service.dto.commands;

import com.unicartagena.equiposfutbol.domain.enums.CategoriaEquipo;
import com.unicartagena.equiposfutbol.domain.exceptions.InvalidEquipoDataException;
import com.unicartagena.equiposfutbol.domain.valueobjects.Estadisticas;
import com.unicartagena.equiposfutbol.domain.valueobjects.NombreEquipo;
import com.unicartagena.equiposfutbol.domain.valueobjects.TextoCorto;
import com.unicartagena.equiposfutbol.domain.valueobjects.Ubicacion;

public record CreateEquipoCommand(
        NombreEquipo nombre,
        TextoCorto eslogan,
        TextoCorto tecnico,
        Ubicacion ubicacion,
        CategoriaEquipo categoria,
        Estadisticas estadisticas) {

    public CreateEquipoCommand {
        if (nombre == null || eslogan == null || tecnico == null
                || ubicacion == null || categoria == null || estadisticas == null) {
            throw new InvalidEquipoDataException("Faltan datos para crear el equipo");
        }
    }
}
