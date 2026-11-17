package com.unicartagena.equiposfutbol.adapter.persistence.jpa.repository;

import com.unicartagena.equiposfutbol.adapter.persistence.jpa.entity.EquipoJpaEntity;
import com.unicartagena.equiposfutbol.domain.enums.CategoriaEquipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EquipoJpaRepository extends JpaRepository<EquipoJpaEntity, UUID> {

    Optional<EquipoJpaEntity> findByNombreNormalizado(String nombreNormalizado);

    List<EquipoJpaEntity> findByPaisIgnoreCase(String pais);

    List<EquipoJpaEntity> findByCategoria(CategoriaEquipo categoria);

    List<EquipoJpaEntity> findByGolesGreaterThanEqual(int goles);
}
