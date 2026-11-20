package com.example.gestionsalas.controller;

import com.example.gestionsalas.dto.SalaRequest;
import com.example.gestionsalas.dto.SalaResponse;
import com.example.gestionsalas.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/salas")
public class SalaController {

    private final SalaService servicio;

    public SalaController(SalaService servicio) {
        this.servicio = servicio;
    }

    // GET /api/salas?busqueda=c20
    @GetMapping
    public List<SalaResponse> listar(@RequestParam(defaultValue = "") String busqueda) {
        return servicio.listar(busqueda);
    }

    // GET /api/salas/3
    @GetMapping("/{id}")
    public SalaResponse obtener(@PathVariable Long id) {
        return servicio.obtener(id);
    }

    // GET /api/salas/codigo-disponible?codigo=C204&excluirId=0  ->  true / false
    @GetMapping("/codigo-disponible")
    public boolean codigoDisponible(@RequestParam String codigo,
                                    @RequestParam(defaultValue = "0") Long excluirId) {
        return servicio.codigoDisponible(codigo, excluirId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SalaResponse crear(@Valid @RequestBody SalaRequest solicitud) {
        return servicio.crear(solicitud);
    }

    @PutMapping("/{id}")
    public SalaResponse actualizar(@PathVariable Long id, @Valid @RequestBody SalaRequest solicitud) {
        return servicio.actualizar(id, solicitud);
    }

    // PATCH /api/salas/3/estado?activa=false
    @PatchMapping("/{id}/estado")
    public SalaResponse cambiarEstado(@PathVariable Long id, @RequestParam boolean activa) {
        return servicio.cambiarEstado(id, activa);
    }
}
