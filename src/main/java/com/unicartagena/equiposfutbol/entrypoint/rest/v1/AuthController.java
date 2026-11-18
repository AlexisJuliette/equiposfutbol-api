package com.unicartagena.equiposfutbol.entrypoint.rest.v1;

import com.unicartagena.equiposfutbol.application.port.in.AuthUseCase;
import com.unicartagena.equiposfutbol.entrypoint.rest.v1.dto.request.LoginRequest;
import com.unicartagena.equiposfutbol.entrypoint.rest.v1.dto.request.RecoverPasswordRequest;
import com.unicartagena.equiposfutbol.entrypoint.rest.v1.dto.response.UserResponse;
import com.unicartagena.equiposfutbol.entrypoint.rest.v1.mappers.UserRestMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthUseCase auth;
    private final UserRestMapper mapper;

    public AuthController(AuthUseCase auth, UserRestMapper mapper) {
        this.auth = auth;
        this.mapper = mapper;
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(mapper.toResponse(auth.login(mapper.toLoginQuery(request))));
    }

    @PostMapping("/recuperar-clave")
    public ResponseEntity<Void> recuperarClave(@Valid @RequestBody RecoverPasswordRequest request) {
        auth.recuperarPassword(mapper.toRecoverCommand(request));
        return ResponseEntity.noContent().build();
    }
}
