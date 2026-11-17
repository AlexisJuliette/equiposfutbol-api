package com.unicartagena.equiposfutbol.application.port.in;

import com.unicartagena.equiposfutbol.application.service.dto.commands.DeleteEquipoCommand;

public interface DeleteEquipoUseCase {

    void deleteEquipo(DeleteEquipoCommand command);
}
