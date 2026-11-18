package com.unicartagena.equiposfutbol.entrypoint.rest.v1;

import com.unicartagena.equiposfutbol.application.port.in.ManageUsersUseCase;
import com.unicartagena.equiposfutbol.entrypoint.rest.v1.dto.request.CreateUserRequest;
import com.unicartagena.equiposfutbol.entrypoint.rest.v1.dto.request.UpdateUserRequest;
import com.unicartagena.equiposfutbol.entrypoint.rest.v1.dto.response.UserResponse;
import com.unicartagena.equiposfutbol.entrypoint.rest.v1.mappers.UserRestMapper;
import com.unicartagena.equiposfutbol.domain.valueobjects.UserId;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final ManageUsersUseCase manageUsers;
    private final UserRestMapper mapper;

    public UserController(ManageUsersUseCase manageUsers, UserRestMapper mapper) {
        this.manageUsers = manageUsers;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<UserResponse> crear(@Valid @RequestBody CreateUserRequest request) {
        UserResponse response = mapper.toResponse(manageUsers.createUser(mapper.toCommand(request)));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> consultar(@PathVariable UUID userId) {
        return ResponseEntity.ok(mapper.toResponse(manageUsers.getUser(new UserId(userId))));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> listar() {
        return ResponseEntity.ok(mapper.toResponses(manageUsers.listUsers()));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> actualizar(
            @PathVariable UUID userId,
            @Valid @RequestBody UpdateUserRequest request) {
        UserResponse response = mapper.toResponse(
                manageUsers.updateUser(new UserId(userId), mapper.toCommand(request)));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID userId) {
        manageUsers.deleteUser(new UserId(userId));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reportes/por-nombre")
    public ResponseEntity<List<UserResponse>> reportePorNombre(
            @RequestParam String fragmento) {
        return ResponseEntity.ok(mapper.toResponses(manageUsers.reportePorNombre(fragmento)));
    }

    @GetMapping("/reportes/por-fecha")
    public ResponseEntity<List<UserResponse>> reportePorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant hasta) {
        return ResponseEntity.ok(mapper.toResponses(manageUsers.reportePorFechaRegistro(desde, hasta)));
    }
}