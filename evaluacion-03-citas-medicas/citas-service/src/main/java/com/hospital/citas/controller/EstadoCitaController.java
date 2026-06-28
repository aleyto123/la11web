package com.hospital.citas.controller;

import com.hospital.citas.entity.EstadoCita;
import com.hospital.citas.service.CitaService;
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
@RequestMapping("/api/estados-cita")
public class EstadoCitaController {
    private final CitaService service;

    public EstadoCitaController(CitaService service) {
        this.service = service;
    }

    @GetMapping
    public List<EstadoCita> listar() { return service.listarEstados(); }

    @GetMapping("/{id}")
    public EstadoCita obtener(@PathVariable("id") Long id) { return service.obtenerEstado(id); }

    @PostMapping
    public ResponseEntity<EstadoCita> crear(@Valid @RequestBody EstadoCita estado) {
        EstadoCita creado = service.guardarEstado(estado);
        return ResponseEntity.created(URI.create("/api/estados-cita/" + creado.getIdEstado())).body(creado);
    }

    @PutMapping("/{id}")
    public EstadoCita actualizar(@PathVariable("id") Long id, @Valid @RequestBody EstadoCita estado) {
        return service.actualizarEstado(id, estado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        service.eliminarEstado(id);
        return ResponseEntity.noContent().build();
    }
}
