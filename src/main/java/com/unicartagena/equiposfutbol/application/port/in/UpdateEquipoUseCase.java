package com.unicartagena.equiposfutbol.application.port.in;

import com.unicartagena.equiposfutbol.application.service.dto.commands.UpdateEquipoCommand;
import com.unicartagena.equiposfutbol.application.service.dto.response.EquipoResult;

public interface UpdateEquipoUseCase {

    EquipoResult updateEquipo(UpdateEquipoCommand command);
}
