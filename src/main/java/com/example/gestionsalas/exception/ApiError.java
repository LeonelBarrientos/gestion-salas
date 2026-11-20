package com.example.gestionsalas.exception;

import java.util.Map;

public record ApiError(int status, String mensaje, Map<String, String> detalles) {

    public static ApiError de(int status, String mensaje) {
        return new ApiError(status, mensaje, Map.of());
    }
}
