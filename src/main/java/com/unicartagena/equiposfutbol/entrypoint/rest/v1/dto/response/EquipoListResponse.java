package com.unicartagena.equiposfutbol.entrypoint.rest.v1.dto.response;

import java.util.List;

public record EquipoListResponse(List<EquipoResponse> equipos, int total) {

    public static EquipoListResponse from(List<EquipoResponse> equipos) {
        List<EquipoResponse> inmutables = List.copyOf(equipos);
        return new EquipoListResponse(inmutables, inmutables.size());
    }  
}
