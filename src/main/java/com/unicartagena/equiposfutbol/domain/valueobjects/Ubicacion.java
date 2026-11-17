package com.unicartagena.equiposfutbol.domain.valueobjects;

public record Ubicacion(TextoCorto pais, TextoCorto ciudad) {

    public static Ubicacion of(String pais, String ciudad) {
        return new Ubicacion(TextoCorto.of(pais), TextoCorto.of(ciudad));
    }

    public String paisValue() {
        return pais.value();
    }

    public String ciudadValue() {
        return ciudad.value();
    }
}
