package com.unicartagena.equiposfutbol.entrypoint.rest.v1.mappers;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;
import com.unicartagena.equiposfutbol.application.service.dto.commands.CreateEquipoCommand;
import com.unicartagena.equiposfutbol.application.service.dto.commands.DeleteEquipoCommand;
import com.unicartagena.equiposfutbol.application.service.dto.commands.UpdateEquipoCommand;
import com.unicartagena.equiposfutbol.application.service.dto.queries.GetEquipoQuery;
import com.unicartagena.equiposfutbol.application.service.dto.queries.ReportePorPaisQuery;
import com.unicartagena.equiposfutbol.application.service.dto.queries.ReportePorRendimientoQuery;
import com.unicartagena.equiposfutbol.application.service.dto.response.EquipoResult;
import com.unicartagena.equiposfutbol.domain.enums.CategoriaEquipo;
import com.unicartagena.equiposfutbol.domain.valueobjects.EquipoId;
import com.unicartagena.equiposfutbol.domain.valueobjects.Estadisticas;
import com.unicartagena.equiposfutbol.domain.valueobjects.NombreEquipo;
import com.unicartagena.equiposfutbol.domain.valueobjects.TextoCorto;
import com.unicartagena.equiposfutbol.domain.valueobjects.Ubicacion;
import com.unicartagena.equiposfutbol.entrypoint.rest.v1.dto.request.CreateEquipoRequest;
import com.unicartagena.equiposfutbol.entrypoint.rest.v1.dto.request.UpdateEquipoRequest;
import com.unicartagena.equiposfutbol.entrypoint.rest.v1.dto.response.EquipoResponse;

@Component
public class EquipoRestMapper {
    public CreateEquipoCommand toCommand(CreateEquipoRequest request) {
        return new CreateEquipoCommand(
                NombreEquipo.of(request.nombre()),
                TextoCorto.of(request.eslogan()),
                TextoCorto.of(request.tecnico()),
                Ubicacion.of(request.pais(), request.ciudad()),
                CategoriaEquipo.from(request.categoria()),
                Estadisticas.of(
                        request.goles(),
                        request.partidosJugados(),
                        request.partidosGanados(),
                        request.empates(),
                        request.campeonatos(),
                        request.expulsiones()));
    }

    public UpdateEquipoCommand toCommand(UUID equipoId, UpdateEquipoRequest request) {
        return new UpdateEquipoCommand(
                EquipoId.from(equipoId),
                NombreEquipo.of(request.nombre()),
                TextoCorto.of(request.eslogan()),
                TextoCorto.of(request.tecnico()),
                Ubicacion.of(request.pais(), request.ciudad()),
                CategoriaEquipo.from(request.categoria()),
                Estadisticas.of(
                        request.goles(),
                        request.partidosJugados(),
                        request.partidosGanados(),
                        request.empates(),
                        request.campeonatos(),
                        request.expulsiones()));
    }

    public GetEquipoQuery toGetQuery(UUID equipoId) {
        return new GetEquipoQuery(EquipoId.from(equipoId));
    }

    public DeleteEquipoCommand toDeleteCommand(UUID equipoId) {
        return new DeleteEquipoCommand(EquipoId.from(equipoId));
    }

    public ReportePorPaisQuery toReportePorPaisQuery(String pais, String categoria) {
        return new ReportePorPaisQuery(pais, categoria);
    }

    public ReportePorRendimientoQuery toReportePorRendimientoQuery(
            int minimoGoles, Integer minimoCampeonatos) {
        return new ReportePorRendimientoQuery(minimoGoles, minimoCampeonatos);
    }

    public EquipoResponse toResponse(EquipoResult result) {
        return new EquipoResponse(
                result.id(),
                result.nombre(),
                result.eslogan(),
                result.tecnico(),
                result.pais(),
                result.ciudad(),
                result.categoria().name(),
                result.goles(),
                result.partidosJugados(),
                result.partidosGanados(),
                result.empates(),
                result.partidosPerdidos(),
                result.campeonatos(),
                result.expulsiones(),
                result.promedioGoles(),
                result.createdAt(),
                result.updatedAt());
    }

    public List<EquipoResponse> toResponses(List<EquipoResult> results) {
        return results.stream().map(this::toResponse).toList();
    }
}
