package com.unicartagena.equiposfutbol.entrypoint.rest.v1;

import com.unicartagena.equiposfutbol.application.port.in.CreateEquipoUseCase;
import com.unicartagena.equiposfutbol.application.port.in.DeleteEquipoUseCase;
import com.unicartagena.equiposfutbol.application.port.in.GetEquipoUseCase;
import com.unicartagena.equiposfutbol.application.port.in.ListEquiposUseCase;
import com.unicartagena.equiposfutbol.application.port.in.ReportEquiposUseCase;
import com.unicartagena.equiposfutbol.application.port.in.UpdateEquipoUseCase;
import com.unicartagena.equiposfutbol.application.service.dto.response.EquipoResult;
import com.unicartagena.equiposfutbol.entrypoint.rest.v1.dto.request.CreateEquipoRequest;
import com.unicartagena.equiposfutbol.entrypoint.rest.v1.dto.request.UpdateEquipoRequest;
import com.unicartagena.equiposfutbol.entrypoint.rest.v1.dto.response.EquipoListResponse;
import com.unicartagena.equiposfutbol.entrypoint.rest.v1.dto.response.EquipoResponse;
import com.unicartagena.equiposfutbol.entrypoint.rest.v1.mappers.EquipoRestMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/equipos")
public class EquipoController {

    private final CreateEquipoUseCase createEquipoUseCase;
    private final GetEquipoUseCase getEquipoUseCase;
    private final ListEquiposUseCase listEquiposUseCase;
    private final UpdateEquipoUseCase updateEquipoUseCase;
    private final DeleteEquipoUseCase deleteEquipoUseCase;
    private final ReportEquiposUseCase reportEquiposUseCase;
    private final EquipoRestMapper equipoMapper;

    public EquipoController(
            CreateEquipoUseCase createEquipoUseCase,
            GetEquipoUseCase getEquipoUseCase,
            ListEquiposUseCase listEquiposUseCase,
            UpdateEquipoUseCase updateEquipoUseCase,
            DeleteEquipoUseCase deleteEquipoUseCase,
            ReportEquiposUseCase reportEquiposUseCase,
            EquipoRestMapper equipoMapper) {
        this.createEquipoUseCase = createEquipoUseCase;
        this.getEquipoUseCase = getEquipoUseCase;
        this.listEquiposUseCase = listEquiposUseCase;
        this.updateEquipoUseCase = updateEquipoUseCase;
        this.deleteEquipoUseCase = deleteEquipoUseCase;
        this.reportEquiposUseCase = reportEquiposUseCase;
        this.equipoMapper = equipoMapper;
    }

    @PostMapping
    public ResponseEntity<EquipoResponse> createEquipo(@Valid @RequestBody CreateEquipoRequest request) {
        EquipoResult result = createEquipoUseCase.createEquipo(equipoMapper.toCommand(request));
        EquipoResponse response = equipoMapper.toResponse(result);
        return ResponseEntity.created(URI.create("/api/v1/equipos/" + response.id())).body(response);
    }

    @GetMapping("/{equipoId}")
    public ResponseEntity<EquipoResponse> getEquipo(@PathVariable UUID equipoId) {
        EquipoResult result = getEquipoUseCase.getEquipo(equipoMapper.toGetQuery(equipoId));
        return ResponseEntity.ok(equipoMapper.toResponse(result));
    }

    @GetMapping
    public ResponseEntity<EquipoListResponse> listEquipos() {
        List<EquipoResponse> equipos = equipoMapper.toResponses(listEquiposUseCase.listEquipos());
        return ResponseEntity.ok(EquipoListResponse.from(equipos));
    }

    @PutMapping("/{equipoId}")
    public ResponseEntity<EquipoResponse> updateEquipo(
            @PathVariable UUID equipoId,
            @Valid @RequestBody UpdateEquipoRequest request) {
        EquipoResult result = updateEquipoUseCase.updateEquipo(equipoMapper.toCommand(equipoId, request));
        return ResponseEntity.ok(equipoMapper.toResponse(result));
    }

    @DeleteMapping("/{equipoId}")
    public ResponseEntity<Void> deleteEquipo(@PathVariable UUID equipoId) {
        deleteEquipoUseCase.deleteEquipo(equipoMapper.toDeleteCommand(equipoId));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reportes/por-pais")
    public ResponseEntity<EquipoListResponse> reportePorPais(
            @RequestParam String pais,
            @RequestParam(required = false) String categoria) {
        List<EquipoResponse> equipos = equipoMapper.toResponses(
                reportEquiposUseCase.reportePorPais(
                        equipoMapper.toReportePorPaisQuery(pais, categoria)));
        return ResponseEntity.ok(EquipoListResponse.from(equipos));
    }

    @GetMapping("/reportes/por-rendimiento")
    public ResponseEntity<EquipoListResponse> reportePorRendimiento(
            @RequestParam int minimoGoles,
            @RequestParam(required = false) Integer minimoCampeonatos) {
        List<EquipoResponse> equipos = equipoMapper.toResponses(
                reportEquiposUseCase.reportePorRendimiento(
                        equipoMapper.toReportePorRendimientoQuery(minimoGoles, minimoCampeonatos)));
        return ResponseEntity.ok(EquipoListResponse.from(equipos));
    }
}