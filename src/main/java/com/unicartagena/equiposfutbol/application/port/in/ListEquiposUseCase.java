package com.unicartagena.equiposfutbol.application.port.in;

import com.unicartagena.equiposfutbol.application.service.dto.response.EquipoResult;

import java.util.List;

public interface ListEquiposUseCase {

    List<EquipoResult> listEquipos();
}
