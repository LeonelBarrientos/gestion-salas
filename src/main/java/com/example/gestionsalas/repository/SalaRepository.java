package com.example.gestionsalas.repository;

import com.example.gestionsalas.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalaRepository extends JpaRepository<Sala, Long> {

    // Busca por código o nombre sin importar mayúsculas (igual que el buscador de la app)
    @Query("SELECT s FROM Sala s "
            + "WHERE LOWER(s.codigo) LIKE LOWER(CONCAT('%', :busqueda, '%')) "
            + "OR LOWER(s.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%')) "
            + "ORDER BY s.codigo")
    List<Sala> buscar(@Param("busqueda") String busqueda);

    boolean existsByCodigoIgnoreCase(String codigo);

    boolean existsByCodigoIgnoreCaseAndIdNot(String codigo, Long id);
}
