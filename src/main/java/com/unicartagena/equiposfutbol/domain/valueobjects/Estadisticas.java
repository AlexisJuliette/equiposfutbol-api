package com.unicartagena.equiposfutbol.domain.valueobjects;

import com.unicartagena.equiposfutbol.domain.exceptions.InvalidEquipoDataException;

public record Estadisticas(
        int goles,
        int partidosJugados,
        int partidosGanados,
        int empates,
        int campeonatos,
        int expulsiones) {

    public Estadisticas {
        requireNonNegative(goles, partidosJugados, partidosGanados, empates, campeonatos, expulsiones);
        if (partidosGanados + empates > partidosJugados) {
            throw new InvalidEquipoDataException(
                    "Los partidos ganados mas los empates no pueden superar los partidos jugados");
        }
    }

    public static Estadisticas of(
            Integer goles,
            Integer partidosJugados,
            Integer partidosGanados,
            Integer empates,
            Integer campeonatos,
            Integer expulsiones) {
        return new Estadisticas(
                defaultToZero(goles),
                defaultToZero(partidosJugados),
                defaultToZero(partidosGanados),
                defaultToZero(empates),
                defaultToZero(campeonatos),
                defaultToZero(expulsiones));
    }

    public int partidosPerdidos() {
        return partidosJugados - partidosGanados - empates;
    }

    public double promedioGoles() {
        return partidosJugados == 0 ? 0d : (double) goles / partidosJugados;
    }

    private static int defaultToZero(Integer value) {
        return value == null ? 0 : value;
    }

    private static void requireNonNegative(int... values) {
        for (int value : values) {
            if (value < 0) {
                throw new InvalidEquipoDataException(
                        "Las estadisticas del equipo no pueden ser negativas");
            }
        }
    }
}
