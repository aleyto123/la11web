package com.hospital.citas.controller;

import com.hospital.citas.dto.CitaDetalleDto;
import com.hospital.citas.entity.Cita;
import com.hospital.citas.service.CitaService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/citas")
public class CitaController {
    private final CitaService service;

    public CitaController(CitaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Cita> listar() { return service.listarCitas(); }

    @GetMapping("/{id}")
    public Cita obtener(@PathVariable("id") Long id) { return service.obtenerCita(id); }

    @GetMapping("/{id}/detalle")
    public CitaDetalleDto detalle(@PathVariable("id") Long id) { return service.obtenerDetalle(id); }

    @PostMapping
    public ResponseEntity<Cita> crear(@Valid @RequestBody Cita cita) {
        Cita creada = service.crearCita(cita);
        return ResponseEntity.created(URI.create("/api/citas/" + creada.getIdCita())).body(creada);
    }

    @PutMapping("/{id}")
    public Cita actualizar(@PathVariable("id") Long id, @Valid @RequestBody Cita cita) {
        return service.actualizarCita(id, cita);
    }

    @PatchMapping("/{id}/cancelar")
    public Cita cancelar(@PathVariable("id") Long id) {
        return service.cancelarCita(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        service.eliminarCita(id);
        return ResponseEntity.noContent().build();
    }
}
