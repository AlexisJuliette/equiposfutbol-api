package com.unicartagena.equiposfutbol.application.port.in;

import com.unicartagena.equiposfutbol.application.service.dto.queries.GetEquipoQuery;
import com.unicartagena.equiposfutbol.application.service.dto.response.EquipoResult;

public interface GetEquipoUseCase {

    EquipoResult getEquipo(GetEquipoQuery query);
}
