package com.example.gestionsalas.dto;

import com.example.gestionsalas.model.Area;

import java.util.List;

public record SalaResponse(
        Long id,
        String codigo,
        String nombre,
        Area area,
        int capacidad,
        boolean activa,
        String imagenUri,
        List<EquipamientoDto> equipamiento
) {
}
