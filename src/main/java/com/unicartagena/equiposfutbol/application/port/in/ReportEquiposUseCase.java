package com.unicartagena.equiposfutbol.application.port.in;

import com.unicartagena.equiposfutbol.application.service.dto.queries.ReportePorPaisQuery;
import com.unicartagena.equiposfutbol.application.service.dto.queries.ReportePorRendimientoQuery;
import com.unicartagena.equiposfutbol.application.service.dto.response.EquipoResult;

import java.util.List;

public interface ReportEquiposUseCase {

    List<EquipoResult> reportePorPais(ReportePorPaisQuery query);

    List<EquipoResult> reportePorRendimiento(ReportePorRendimientoQuery query);
}
