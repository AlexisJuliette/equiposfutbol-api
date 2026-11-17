package com.unicartagena.equiposfutbol.application.service.dto.queries;

import com.unicartagena.equiposfutbol.domain.exceptions.InvalidEquipoDataException;

public record ReportePorPaisQuery(String pais, String categoria) {

    public ReportePorPaisQuery {
        if (pais == null || pais.isBlank()) {
            throw new InvalidEquipoDataException("El pais es obligatorio para este reporte");
        }
        pais = pais.trim();
        categoria = (categoria == null || categoria.isBlank()) ? null : categoria.trim();
    }
}
