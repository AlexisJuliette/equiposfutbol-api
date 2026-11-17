package com.unicartagena.equiposfutbol.adapter.persistence.jpa.mapper;

import com.unicartagena.equiposfutbol.adapter.persistence.jpa.entity.EquipoJpaEntity;
import com.unicartagena.equiposfutbol.domain.models.EquipoFutbol;
import com.unicartagena.equiposfutbol.domain.valueobjects.EquipoId;
import com.unicartagena.equiposfutbol.domain.valueobjects.Estadisticas;
import com.unicartagena.equiposfutbol.domain.valueobjects.NombreEquipo;
import com.unicartagena.equiposfutbol.domain.valueobjects.TextoCorto;
import com.unicartagena.equiposfutbol.domain.valueobjects.Ubicacion;
import org.springframework.stereotype.Component;

@Component
public class EquipoPersistenceMapper {

    public EquipoJpaEntity toEntity(EquipoFutbol equipo) {
        Estadisticas estadisticas = equipo.getEstadisticas();
        return new EquipoJpaEntity(
                equipo.getId().value(),
                equipo.getNombre().value(),
                equipo.getNombre().normalized(),
                equipo.getEslogan().value(),
                equipo.getTecnico().value(),
                equipo.getUbicacion().paisValue(),
                equipo.getUbicacion().ciudadValue(),
                equipo.getCategoria(),
                estadisticas.goles(),
                estadisticas.partidosJugados(),
                estadisticas.partidosGanados(),
                estadisticas.empates(),
                estadisticas.campeonatos(),
                estadisticas.expulsiones(),
                equipo.getCreatedAt(),
                equipo.getUpdatedAt());
    }

    public EquipoFutbol toDomain(EquipoJpaEntity entity) {
        return EquipoFutbol.rehydrate(
                EquipoId.from(entity.getId()),
                NombreEquipo.of(entity.getNombre()),
                TextoCorto.of(entity.getEslogan()),
                TextoCorto.of(entity.getTecnico()),
                Ubicacion.of(entity.getPais(), entity.getCiudad()),
                entity.getCategoria(),
                new Estadisticas(
                        entity.getGoles(),
                        entity.getPartidosJugados(),
                        entity.getPartidosGanados(),
                        entity.getEmpates(),
                        entity.getCampeonatos(),
                        entity.getExpulsiones()),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}
