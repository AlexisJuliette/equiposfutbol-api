package com.unicartagena.equiposfutbol.application.service.dto.queries;

import com.unicartagena.equiposfutbol.domain.exceptions.InvalidEquipoDataException;

public record ReportePorRendimientoQuery(int minimoGoles, Integer minimoCampeonatos) {

    public ReportePorRendimientoQuery {
        if (minimoGoles < 0 || (minimoCampeonatos != null && minimoCampeonatos < 0)) {
            throw new InvalidEquipoDataException("Los umbrales del reporte no pueden ser negativos");
        }
    }
}
