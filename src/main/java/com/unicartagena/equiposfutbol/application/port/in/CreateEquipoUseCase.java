package com.unicartagena.equiposfutbol.application.port.in;

import com.unicartagena.equiposfutbol.application.service.dto.commands.CreateEquipoCommand;
import com.unicartagena.equiposfutbol.application.service.dto.response.EquipoResult;

public interface CreateEquipoUseCase {

    EquipoResult createEquipo(CreateEquipoCommand command);
}
