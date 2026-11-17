package com.unicartagena.equiposfutbol.adapter.persistence.jpa;

import com.unicartagena.equiposfutbol.adapter.persistence.jpa.mapper.EquipoPersistenceMapper;
import com.unicartagena.equiposfutbol.adapter.persistence.jpa.repository.EquipoJpaRepository;
import com.unicartagena.equiposfutbol.domain.enums.CategoriaEquipo;
import com.unicartagena.equiposfutbol.domain.models.EquipoFutbol;
import com.unicartagena.equiposfutbol.domain.ports.out.EquipoRepositoryPort;
import com.unicartagena.equiposfutbol.domain.valueobjects.EquipoId;
import com.unicartagena.equiposfutbol.domain.valueobjects.NombreEquipo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class EquipoJpaAdapter implements EquipoRepositoryPort {

    private final EquipoJpaRepository equipoJpaRepository;
    private final EquipoPersistenceMapper equipoMapper;

    public EquipoJpaAdapter(
            EquipoJpaRepository equipoJpaRepository,
            EquipoPersistenceMapper equipoMapper) {
        this.equipoJpaRepository = equipoJpaRepository;
        this.equipoMapper = equipoMapper;
    }

    @Override
    @Transactional
    public void save(EquipoFutbol equipo) {
        equipoJpaRepository.save(equipoMapper.toEntity(equipo));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EquipoFutbol> findById(EquipoId equipoId) {
        return equipoJpaRepository.findById(equipoId.value()).map(equipoMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EquipoFutbol> findByNombre(NombreEquipo nombre) {
        return equipoJpaRepository.findByNombreNormalizado(nombre.normalized())
                .map(equipoMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipoFutbol> findAll() {
        return equipoJpaRepository.findAll().stream().map(equipoMapper::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipoFutbol> findByPais(String pais) {
        return equipoJpaRepository.findByPaisIgnoreCase(pais).stream()
                .map(equipoMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipoFutbol> findByCategoria(CategoriaEquipo categoria) {
        return equipoJpaRepository.findByCategoria(categoria).stream()
                .map(equipoMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipoFutbol> findByGolesGreaterThanOrEqual(int minimoGoles) {
        return equipoJpaRepository.findByGolesGreaterThanEqual(minimoGoles).stream()
                .map(equipoMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(EquipoId equipoId) {
        equipoJpaRepository.deleteById(equipoId.value());
    }
}
