package com.unicartagena.equiposfutbol.domain.models;

import com.unicartagena.equiposfutbol.domain.enums.CategoriaEquipo;
import com.unicartagena.equiposfutbol.domain.exceptions.InvalidEquipoDataException;
import com.unicartagena.equiposfutbol.domain.valueobjects.EquipoId;
import com.unicartagena.equiposfutbol.domain.valueobjects.Estadisticas;
import com.unicartagena.equiposfutbol.domain.valueobjects.NombreEquipo;
import com.unicartagena.equiposfutbol.domain.valueobjects.TextoCorto;
import com.unicartagena.equiposfutbol.domain.valueobjects.Ubicacion;

import java.time.Instant;

public final class EquipoFutbol {

    private final EquipoId id;
    private final NombreEquipo nombre;
    private final TextoCorto eslogan;
    private final TextoCorto tecnico;
    private final Ubicacion ubicacion;
    private final CategoriaEquipo categoria;
    private final Estadisticas estadisticas;
    private final Instant createdAt;
    private final Instant updatedAt;

    private EquipoFutbol(
            EquipoId id,
            NombreEquipo nombre,
            TextoCorto eslogan,
            TextoCorto tecnico,
            Ubicacion ubicacion,
            CategoriaEquipo categoria,
            Estadisticas estadisticas,
            Instant createdAt,
            Instant updatedAt) {
        if (id == null || nombre == null || eslogan == null || tecnico == null
                || ubicacion == null || categoria == null || estadisticas == null
                || createdAt == null || updatedAt == null) {
            throw new InvalidEquipoDataException("Todos los datos del equipo son obligatorios");
        }
        if (updatedAt.isBefore(createdAt)) {
            throw new InvalidEquipoDataException(
                    "La fecha de actualizacion no puede ser anterior a la de creacion");
        }
        this.id = id;
        this.nombre = nombre;
        this.eslogan = eslogan;
        this.tecnico = tecnico;
        this.ubicacion = ubicacion;
        this.categoria = categoria;
        this.estadisticas = estadisticas;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static EquipoFutbol create(
            EquipoId id,
            NombreEquipo nombre,
            TextoCorto eslogan,
            TextoCorto tecnico,
            Ubicacion ubicacion,
            CategoriaEquipo categoria,
            Estadisticas estadisticas,
            Instant creationTime) {
        return new EquipoFutbol(id, nombre, eslogan, tecnico, ubicacion,
                categoria, estadisticas, creationTime, creationTime);
    }

    public static EquipoFutbol rehydrate(
            EquipoId id,
            NombreEquipo nombre,
            TextoCorto eslogan,
            TextoCorto tecnico,
            Ubicacion ubicacion,
            CategoriaEquipo categoria,
            Estadisticas estadisticas,
            Instant createdAt,
            Instant updatedAt) {
        return new EquipoFutbol(id, nombre, eslogan, tecnico, ubicacion,
                categoria, estadisticas, createdAt, updatedAt);
    }

    public EquipoFutbol update(
            NombreEquipo nuevoNombre,
            TextoCorto nuevoEslogan,
            TextoCorto nuevoTecnico,
            Ubicacion nuevaUbicacion,
            CategoriaEquipo nuevaCategoria,
            Estadisticas nuevasEstadisticas,
            Instant updateTime) {
        return new EquipoFutbol(id, nuevoNombre, nuevoEslogan, nuevoTecnico,
                nuevaUbicacion, nuevaCategoria, nuevasEstadisticas, createdAt, updateTime);
    }

    public EquipoId getId() {
        return id;
    }

    public NombreEquipo getNombre() {
        return nombre;
    }

    public TextoCorto getEslogan() {
        return eslogan;
    }

    public TextoCorto getTecnico() {
        return tecnico;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public CategoriaEquipo getCategoria() {
        return categoria;
    }

    public Estadisticas getEstadisticas() {
        return estadisticas;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
