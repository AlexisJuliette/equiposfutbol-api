package com.unicartagena.equiposfutbol.application.service.mapper;

import java.util.List;

import com.unicartagena.equiposfutbol.application.service.dto.response.UserResult;
import com.unicartagena.equiposfutbol.domain.models.Usuario;

public final class UserDomainMapper {
    public UserResult toResult(Usuario usuario) {
        return new UserResult(
                usuario.getId().value(),
                usuario.getUsername().value(),
                usuario.getNombre().value(),
                usuario.getEmail().value(),
                usuario.getCreatedAt(),
                usuario.getUpdatedAt());
    }

    public List<UserResult> toResults(List<Usuario> usuarios) {
        return usuarios.stream().map(this::toResult).toList();
    }    
}
