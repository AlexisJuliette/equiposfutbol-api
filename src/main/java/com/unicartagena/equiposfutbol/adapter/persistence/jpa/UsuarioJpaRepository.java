package com.unicartagena.equiposfutbol.adapter.persistence.jpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioJpaEntity, UUID> {

    Optional<UsuarioJpaEntity> findByUsername(String username);

    Optional<UsuarioJpaEntity> findByEmail(String email);

    List<UsuarioJpaEntity> findByNombreContainingIgnoreCase(String fragmento);
}
