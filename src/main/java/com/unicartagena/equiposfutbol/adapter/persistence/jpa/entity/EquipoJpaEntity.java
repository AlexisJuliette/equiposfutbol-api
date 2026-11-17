package com.unicartagena.equiposfutbol.adapter.persistence.jpa.entity;

import com.unicartagena.equiposfutbol.domain.enums.CategoriaEquipo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "equipos_futbol",
        indexes = {
                @Index(name = "idx_equipo_nombre_normalizado", columnList = "nombre_normalizado", unique = true),
                @Index(name = "idx_equipo_pais", columnList = "pais"),
                @Index(name = "idx_equipo_categoria", columnList = "categoria")
        })
public class EquipoJpaEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    @Column(name = "nombre_normalizado", nullable = false, length = 120)
    private String nombreNormalizado;

    @Column(name = "eslogan", nullable = false, length = 150)
    private String eslogan;

    @Column(name = "tecnico", nullable = false, length = 150)
    private String tecnico;

    @Column(name = "pais", nullable = false, length = 150)
    private String pais;

    @Column(name = "ciudad", nullable = false, length = 150)
    private String ciudad;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false, length = 30)
    private CategoriaEquipo categoria;

    @Column(name = "goles", nullable = false)
    private int goles;

    @Column(name = "partidos_jugados", nullable = false)
    private int partidosJugados;

    @Column(name = "partidos_ganados", nullable = false)
    private int partidosGanados;

    @Column(name = "empates", nullable = false)
    private int empates;

    @Column(name = "campeonatos", nullable = false)
    private int campeonatos;

    @Column(name = "expulsiones", nullable = false)
    private int expulsiones;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected EquipoJpaEntity() {
    }

    public EquipoJpaEntity(
            UUID id, String nombre, String nombreNormalizado, String eslogan, String tecnico,
            String pais, String ciudad, CategoriaEquipo categoria, int goles, int partidosJugados,
            int partidosGanados, int empates, int campeonatos, int expulsiones,
            Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.nombre = nombre;
        this.nombreNormalizado = nombreNormalizado;
        this.eslogan = eslogan;
        this.tecnico = tecnico;
        this.pais = pais;
        this.ciudad = ciudad;
        this.categoria = categoria;
        this.goles = goles;
        this.partidosJugados = partidosJugados;
        this.partidosGanados = partidosGanados;
        this.empates = empates;
        this.campeonatos = campeonatos;
        this.expulsiones = expulsiones;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() { return id; }
    public String getNombre() { return nombre; }
    public String getNombreNormalizado() { return nombreNormalizado; }
    public String getEslogan() { return eslogan; }
    public String getTecnico() { return tecnico; }
    public String getPais() { return pais; }
    public String getCiudad() { return ciudad; }
    public CategoriaEquipo getCategoria() { return categoria; }
    public int getGoles() { return goles; }
    public int getPartidosJugados() { return partidosJugados; }
    public int getPartidosGanados() { return partidosGanados; }
    public int getEmpates() { return empates; }
    public int getCampeonatos() { return campeonatos; }
    public int getExpulsiones() { return expulsiones; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
