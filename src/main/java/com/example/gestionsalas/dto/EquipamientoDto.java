package com.example.gestionsalas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EquipamientoDto(
        Long id,
        @NotBlank(message = "Escribe el nombre del equipo")
        @Size(max = 60, message = "El nombre del equipo no puede superar los 60 caracteres")
        String nombre,
        Boolean ocupado
) {
}
