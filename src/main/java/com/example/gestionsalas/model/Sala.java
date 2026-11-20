package com.example.gestionsalas.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "salas", uniqueConstraints = @UniqueConstraint(name = "uk_sala_codigo", columnNames = "codigo"))
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Se guarda siempre en mayúsculas y sin espacios, así "c505" y "C505" son la misma sala
    @Column(nullable = false, length = 20)
    private String codigo;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Area area;

    @Column(nullable = false)
    private int capacidad;

    @Column(nullable = false)
    private boolean activa = true;

    @Column(length = 500)
    private String imagenUri;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Equipamiento> equipamiento = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public String getImagenUri() {
        return imagenUri;
    }

    public void setImagenUri(String imagenUri) {
        this.imagenUri = imagenUri;
    }

    public List<Equipamiento> getEquipamiento() {
        return equipamiento;
    }

    public void agregarEquipo(Equipamiento equipo) {
        equipo.setSala(this);
        equipamiento.add(equipo);
    }

    public void limpiarEquipos() {
        equipamiento.clear();
    }
}
