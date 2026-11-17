package com.unicartagena.equiposfutbol.application.service.mapper;

import com.unicartagena.equiposfutbol.application.service.dto.response.EquipoResult;
import com.unicartagena.equiposfutbol.domain.models.EquipoFutbol;
import com.unicartagena.equiposfutbol.domain.valueobjects.Estadisticas;

public class EquipoDomainMapper {

    public EquipoResult toResult(EquipoFutbol equipo) {
        Estadisticas estadisticas = equipo.getEstadisticas();
        return new EquipoResult(
                equipo.getId().value(),
                equipo.getNombre().value(),
                equipo.getEslogan().value(),
                equipo.getTecnico().value(),
                equipo.getUbicacion().paisValue(),
                equipo.getUbicacion().ciudadValue(),
                equipo.getCategoria(),
                estadisticas.goles(),
                estadisticas.partidosJugados(),
                estadisticas.partidosGanados(),
                estadisticas.empates(),
                estadisticas.partidosPerdidos(),
                estadisticas.campeonatos(),
                estadisticas.expulsiones(),
                estadisticas.promedioGoles(),
                equipo.getCreatedAt(),
                equipo.getUpdatedAt());
    }
}
