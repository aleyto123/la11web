package com.hospital.medicos.controller;

import com.hospital.medicos.entity.Especialidad;
import com.hospital.medicos.service.MedicoService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {
    private final MedicoService service;

    public EspecialidadController(MedicoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Especialidad> listar() { return service.listarEspecialidades(); }

    @GetMapping("/{id}")
    public Especialidad obtener(@PathVariable("id") Long id) { return service.obtenerEspecialidad(id); }

    @PostMapping
    public ResponseEntity<Especialidad> crear(@Valid @RequestBody Especialidad especialidad) {
        Especialidad creada = service.guardarEspecialidad(especialidad);
        return ResponseEntity.created(URI.create("/api/especialidades/" + creada.getIdEspecialidad())).body(creada);
    }

    @PutMapping("/{id}")
    public Especialidad actualizar(@PathVariable("id") Long id, @Valid @RequestBody Especialidad especialidad) {
        return service.actualizarEspecialidad(id, especialidad);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        service.eliminarEspecialidad(id);
        return ResponseEntity.noContent().build();
    }
}
