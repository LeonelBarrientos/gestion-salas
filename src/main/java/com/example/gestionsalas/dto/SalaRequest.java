package com.example.gestionsalas.dto;

import com.example.gestionsalas.model.Area;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record SalaRequest(
        @NotBlank(message = "El código es obligatorio")
        @Size(max = 20, message = "El código no puede superar los 20 caracteres")
        String codigo,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre,

        @NotNull(message = "El área es obligatoria")
        Area area,

        @NotNull(message = "La capacidad es obligatoria")
        @Min(value = 1, message = "La capacidad debe estar entre 1 y 200")
        @Max(value = 200, message = "La capacidad debe estar entre 1 y 200")
        Integer capacidad,

        // si no viene, la sala se crea activa
        Boolean activa,

        @Size(max = 500, message = "La ruta de la imagen es demasiado larga")
        String imagenUri,

        @Valid
        List<EquipamientoDto> equipamiento
) {
}
