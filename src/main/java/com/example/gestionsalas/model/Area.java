package com.example.gestionsalas.model;

public enum Area {
    INFORMATICA("Informática"),
    FOTOGRAFIA("Fotografía"),
    DISENO("Diseño"),
    COMUNICACIONES("Comunicaciones"),
    SALUD("Salud"),
    GASTRONOMIA("Gastronomía"),
    TURISMO("Turismo"),
    CONSTRUCCION("Construcción");

    private final String nombre;

    Area(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
