package com.unicartagena.equiposfutbol.adapter.persistence.jpa;

import com.unicartagena.equiposfutbol.domain.models.Usuario;
import com.unicartagena.equiposfutbol.domain.valueobjects.EmailAddress;
import com.unicartagena.equiposfutbol.domain.valueobjects.PasswordHash;
import com.unicartagena.equiposfutbol.domain.valueobjects.TextoCorto;
import com.unicartagena.equiposfutbol.domain.valueobjects.UserId;
import com.unicartagena.equiposfutbol.domain.valueobjects.Username;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class UsuarioPersistenceMapper {

    public UsuarioJpaEntity toEntity(Usuario usuario) {
        return new UsuarioJpaEntity(
                usuario.getId().value(),
                usuario.getUsername().value(),
                usuario.getPasswordHash().value(),
                usuario.getNombre().value(),
                usuario.getEmail().value(),
                usuario.getCreatedAt(),
                usuario.getUpdatedAt());
    }

    public Usuario toDomain(UsuarioJpaEntity entity) {
        return Usuario.rehydrate(
                new UserId(entity.getId()),
                new Username(entity.getUsername()),
                new PasswordHash(entity.getPasswordHash()),
                new TextoCorto(entity.getNombre()),
                new EmailAddress(entity.getEmail()),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public List<Usuario> toDomainList(List<UsuarioJpaEntity> entities) {
        return entities.stream().map(this::toDomain).toList();
    }
}