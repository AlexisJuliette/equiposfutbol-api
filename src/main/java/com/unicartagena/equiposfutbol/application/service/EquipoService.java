package com.unicartagena.equiposfutbol.application.service;

import com.unicartagena.equiposfutbol.application.port.in.CreateEquipoUseCase;
import com.unicartagena.equiposfutbol.application.port.in.DeleteEquipoUseCase;
import com.unicartagena.equiposfutbol.application.port.in.GetEquipoUseCase;
import com.unicartagena.equiposfutbol.application.port.in.ListEquiposUseCase;
import com.unicartagena.equiposfutbol.application.port.in.ReportEquiposUseCase;
import com.unicartagena.equiposfutbol.application.port.in.UpdateEquipoUseCase;
import com.unicartagena.equiposfutbol.application.service.dto.commands.CreateEquipoCommand;
import com.unicartagena.equiposfutbol.application.service.dto.commands.DeleteEquipoCommand;
import com.unicartagena.equiposfutbol.application.service.dto.commands.UpdateEquipoCommand;
import com.unicartagena.equiposfutbol.application.service.dto.queries.GetEquipoQuery;
import com.unicartagena.equiposfutbol.application.service.dto.queries.ReportePorPaisQuery;
import com.unicartagena.equiposfutbol.application.service.dto.queries.ReportePorRendimientoQuery;
import com.unicartagena.equiposfutbol.application.service.dto.response.EquipoResult;
import com.unicartagena.equiposfutbol.application.service.mapper.EquipoDomainMapper;
import com.unicartagena.equiposfutbol.domain.enums.CategoriaEquipo;
import com.unicartagena.equiposfutbol.domain.exceptions.DuplicateEquipoException;
import com.unicartagena.equiposfutbol.domain.exceptions.EquipoNotFoundException;
import com.unicartagena.equiposfutbol.domain.models.EquipoFutbol;
import com.unicartagena.equiposfutbol.domain.ports.out.EquipoRepositoryPort;
import com.unicartagena.equiposfutbol.domain.valueobjects.EquipoId;
import com.unicartagena.equiposfutbol.domain.valueobjects.NombreEquipo;

import java.time.Clock;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class EquipoService implements
        CreateEquipoUseCase,
        GetEquipoUseCase,
        ListEquiposUseCase,
        UpdateEquipoUseCase,
        DeleteEquipoUseCase,
        ReportEquiposUseCase {

    private final EquipoRepositoryPort equipoRepository;
    private final Clock clock;
    private final EquipoDomainMapper equipoMapper;

    public EquipoService(
            EquipoRepositoryPort equipoRepository,
            Clock clock,
            EquipoDomainMapper equipoMapper) {
        this.equipoRepository = equipoRepository;
        this.clock = clock;
        this.equipoMapper = equipoMapper;
    }

    @Override
    public EquipoResult createEquipo(CreateEquipoCommand command) {
        ensureNombreDisponible(command.nombre(), Optional.empty());
        Instant ahora = Instant.now(clock);
        EquipoFutbol equipo = EquipoFutbol.create(
                EquipoId.random(),
                command.nombre(),
                command.eslogan(),
                command.tecnico(),
                command.ubicacion(),
                command.categoria(),
                command.estadisticas(),
                ahora);
        equipoRepository.save(equipo);
        return equipoMapper.toResult(equipo);
    }

    @Override
    public EquipoResult getEquipo(GetEquipoQuery query) {
        return equipoMapper.toResult(buscarEquipo(query.equipoId()));
    }

    @Override
    public List<EquipoResult> listEquipos() {
        return ordenarPorNombre(equipoRepository.findAll());
    }

    @Override
    public EquipoResult updateEquipo(UpdateEquipoCommand command) {
        EquipoFutbol actual = buscarEquipo(command.equipoId());
        ensureNombreDisponible(command.nombre(), Optional.of(actual.getId()));
        EquipoFutbol actualizado = actual.update(
                command.nombre(),
                command.eslogan(),
                command.tecnico(),
                command.ubicacion(),
                command.categoria(),
                command.estadisticas(),
                Instant.now(clock));
        equipoRepository.save(actualizado);
        return equipoMapper.toResult(actualizado);
    }

    @Override
    public void deleteEquipo(DeleteEquipoCommand command) {
        buscarEquipo(command.equipoId());
        equipoRepository.deleteById(command.equipoId());
    }

    @Override
    public List<EquipoResult> reportePorPais(ReportePorPaisQuery query) {
        List<EquipoFutbol> equipos = equipoRepository.findByPais(query.pais());
        if (query.categoria() != null) {
            CategoriaEquipo categoria = CategoriaEquipo.from(query.categoria());
            equipos = equipos.stream()
                    .filter(equipo -> equipo.getCategoria() == categoria)
                    .toList();
        }
        return ordenarPorNombre(equipos);
    }

    @Override
    public List<EquipoResult> reportePorRendimiento(ReportePorRendimientoQuery query) {
        List<EquipoFutbol> equipos =
                equipoRepository.findByGolesGreaterThanOrEqual(query.minimoGoles());
        if (query.minimoCampeonatos() != null) {
            equipos = equipos.stream()
                    .filter(equipo -> equipo.getEstadisticas().campeonatos() >= query.minimoCampeonatos())
                    .toList();
        }
        return equipos.stream()
                .map(equipoMapper::toResult)
                .sorted(Comparator.comparingInt(EquipoResult::goles).reversed()
                        .thenComparing(EquipoResult::nombre))
                .toList();
    }

    private List<EquipoResult> ordenarPorNombre(List<EquipoFutbol> equipos) {
        return equipos.stream()
                .map(equipoMapper::toResult)
                .sorted(Comparator.comparing(EquipoResult::nombre, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    private EquipoFutbol buscarEquipo(EquipoId equipoId) {
        return equipoRepository.findById(equipoId).orElseThrow(EquipoNotFoundException::new);
    }

    private void ensureNombreDisponible(NombreEquipo nombre, Optional<EquipoId> idActual) {
        equipoRepository.findByNombre(nombre)
                .filter(equipo -> idActual
                        .map(id -> !Objects.equals(equipo.getId(), id))
                        .orElse(true))
                .ifPresent(equipo -> {
                    throw new DuplicateEquipoException();
                });
    }
}
