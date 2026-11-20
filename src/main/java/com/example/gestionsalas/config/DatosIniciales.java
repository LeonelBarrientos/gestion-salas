package com.example.gestionsalas.config;

import com.example.gestionsalas.model.Area;
import com.example.gestionsalas.model.Equipamiento;
import com.example.gestionsalas.model.Sala;
import com.example.gestionsalas.repository.SalaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@ConditionalOnProperty(name = "app.seed", havingValue = "true", matchIfMissing = true)
public class DatosIniciales implements CommandLineRunner {

    private final SalaRepository repositorio;

    public DatosIniciales(SalaRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (repositorio.count() > 0) {
            return;
        }

        crear("C204", "Lab. Multimedia", Area.INFORMATICA, 30,
                List.of("Proyector", "Audio", "Pizarra"), List.of("Computadores"));
        crear("C205", "Lab. Desarrollo", Area.INFORMATICA, 24,
                List.of("Proyector", "Pizarra"), List.of("Computadores"));
        crear("F101", "Estudio Fotográfico", Area.FOTOGRAFIA, 20,
                List.of("Audio"), List.of());
        crear("D302", "Taller de Diseño", Area.DISENO, 32,
                List.of("Proyector", "Audio", "Pizarra"), List.of());
        crear("A105", "Sala de Conferencias", Area.COMUNICACIONES, 45,
                List.of("Audio", "Videoconferencia"), List.of("Proyector"));
        crear("S210", "Sala Clínica", Area.SALUD, 30,
                List.of("Pizarra"), List.of());
        crear("G104", "Aula Gastronómica", Area.GASTRONOMIA, 36,
                List.of("Proyector", "Audio"), List.of());
        crear("T305", "Sala Turismo", Area.TURISMO, 18,
                List.of("Pizarra"), List.of());
    }

    private void crear(String codigo, String nombre, Area area, int capacidad,
                       List<String> disponibles, List<String> ocupados) {
        Sala sala = new Sala();
        sala.setCodigo(codigo);
        sala.setNombre(nombre);
        sala.setArea(area);
        sala.setCapacidad(capacidad);
        disponibles.forEach(e -> sala.agregarEquipo(new Equipamiento(e, false)));
        ocupados.forEach(e -> sala.agregarEquipo(new Equipamiento(e, true)));
        repositorio.save(sala);
    }
}
