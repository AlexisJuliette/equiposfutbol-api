package com.unicartagena.equiposfutbol.domain.ports.out;

import com.unicartagena.equiposfutbol.domain.enums.CategoriaEquipo;
import com.unicartagena.equiposfutbol.domain.models.EquipoFutbol;
import com.unicartagena.equiposfutbol.domain.valueobjects.EquipoId;
import com.unicartagena.equiposfutbol.domain.valueobjects.NombreEquipo;

import java.util.List;
import java.util.Optional;

public interface EquipoRepositoryPort {

    void save(EquipoFutbol equipo);

    Optional<EquipoFutbol> findById(EquipoId equipoId);

    Optional<EquipoFutbol> findByNombre(NombreEquipo nombre);

    List<EquipoFutbol> findAll();

    List<EquipoFutbol> findByPais(String pais);

    List<EquipoFutbol> findByCategoria(CategoriaEquipo categoria);

    List<EquipoFutbol> findByGolesGreaterThanOrEqual(int minimoGoles);

    void deleteById(EquipoId equipoId);
}
