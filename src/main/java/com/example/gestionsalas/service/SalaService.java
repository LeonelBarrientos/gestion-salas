package com.example.gestionsalas.service;

import com.example.gestionsalas.dto.EquipamientoDto;
import com.example.gestionsalas.dto.SalaRequest;
import com.example.gestionsalas.dto.SalaResponse;
import com.example.gestionsalas.exception.ConflictoException;
import com.example.gestionsalas.exception.RecursoNoEncontradoException;
import com.example.gestionsalas.exception.ReglaNegocioException;
import com.example.gestionsalas.model.Equipamiento;
import com.example.gestionsalas.model.Sala;
import com.example.gestionsalas.repository.SalaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class SalaService {

    private final SalaRepository repositorio;

    public SalaService(SalaRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Transactional(readOnly = true)
    public List<SalaResponse> listar(String busqueda) {
        String filtro = busqueda == null ? "" : busqueda.trim();
        return repositorio.buscar(filtro).stream().map(this::aRespuesta).toList();
    }

    @Transactional(readOnly = true)
    public SalaResponse obtener(Long id) {
        return aRespuesta(buscarPorId(id));
    }

    @Transactional
    public SalaResponse crear(SalaRequest solicitud) {
        String codigo = normalizarCodigo(solicitud.codigo());
        if (repositorio.existsByCodigoIgnoreCase(codigo)) {
            throw new ConflictoException("Ya existe una sala con el código " + codigo);
        }

        Sala sala = new Sala();
        aplicarDatos(sala, solicitud, codigo);
        return aRespuesta(repositorio.saveAndFlush(sala));
    }

    @Transactional
    public SalaResponse actualizar(Long id, SalaRequest solicitud) {
        Sala sala = buscarPorId(id);
        String codigo = normalizarCodigo(solicitud.codigo());
        if (repositorio.existsByCodigoIgnoreCaseAndIdNot(codigo, id)) {
            throw new ConflictoException("Ya existe otra sala con el código " + codigo);
        }

        aplicarDatos(sala, solicitud, codigo);
        return aRespuesta(repositorio.saveAndFlush(sala));
    }

    @Transactional
    public SalaResponse cambiarEstado(Long id, boolean activa) {
        Sala sala = buscarPorId(id);
        sala.setActiva(activa);
        return aRespuesta(repositorio.save(sala));
    }

    @Transactional(readOnly = true)
    public boolean codigoDisponible(String codigo, Long excluirId) {
        String normalizado = normalizarCodigo(codigo);
        long excluir = excluirId == null ? 0L : excluirId;
        return !repositorio.existsByCodigoIgnoreCaseAndIdNot(normalizado, excluir);
    }

    //internos

    private Sala buscarPorId(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la sala con id " + id));
    }

    private String normalizarCodigo(String codigo) {
        return codigo == null ? "" : codigo.trim().toUpperCase(Locale.ROOT);
    }

    private void aplicarDatos(Sala sala, SalaRequest s, String codigo) {
        sala.setCodigo(codigo);
        sala.setNombre(s.nombre().trim());
        sala.setArea(s.area());
        sala.setCapacidad(s.capacidad());
        sala.setActiva(s.activa() == null || s.activa());
        sala.setImagenUri(s.imagenUri());

        // El equipamiento se reemplaza completo, igual que hace la app al guardar una sala
        Set<String> vistos = new HashSet<>();
        sala.limpiarEquipos();
        if (s.equipamiento() != null) {
            for (EquipamientoDto dto : s.equipamiento()) {
                String nombre = dto.nombre().trim();
                if (!vistos.add(nombre.toLowerCase(Locale.ROOT))) {
                    throw new ReglaNegocioException("El equipo \"" + nombre + "\" está repetido");
                }
                sala.agregarEquipo(new Equipamiento(nombre, Boolean.TRUE.equals(dto.ocupado())));
            }
        }
    }

    private SalaResponse aRespuesta(Sala sala) {
        List<EquipamientoDto> equipos = sala.getEquipamiento().stream()
                .map(e -> new EquipamientoDto(e.getId(), e.getNombre(), e.isOcupado()))
                .toList();

        return new SalaResponse(
                sala.getId(),
                sala.getCodigo(),
                sala.getNombre(),
                sala.getArea(),
                sala.getCapacidad(),
                sala.isActiva(),
                sala.getImagenUri(),
                equipos
        );
    }
}
