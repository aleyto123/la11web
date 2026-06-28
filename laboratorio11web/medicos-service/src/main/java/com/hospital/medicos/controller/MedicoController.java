package com.hospital.medicos.controller;

import com.hospital.medicos.entity.Medico;
import com.hospital.medicos.service.MedicoService;
import jakarta.validation.Valid;
import java.net.URI;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {
    private final MedicoService service;

    public MedicoController(MedicoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Medico> listar() { return service.listarMedicos(); }

    @GetMapping("/{id}")
    public Medico obtener(@PathVariable("id") Long id) { return service.obtenerMedico(id); }

    @GetMapping("/{id}/detalle")
    public Map<String, Object> detalle(@PathVariable("id") Long id) { return service.detalleMedico(id); }

    @GetMapping("/{id}/disponible")
    public Map<String, Boolean> disponible(
            @PathVariable("id") Long id,
            @RequestParam("diaSemana") String diaSemana,
            @RequestParam("hora") LocalTime hora) {
        return Map.of("disponible", service.estaDisponible(id, diaSemana, hora));
    }

    @PostMapping
    public ResponseEntity<Medico> crear(@Valid @RequestBody Medico medico) {
        Medico creado = service.guardarMedico(medico);
        return ResponseEntity.created(URI.create("/api/medicos/" + creado.getIdMedico())).body(creado);
    }

    @PutMapping("/{id}")
    public Medico actualizar(@PathVariable("id") Long id, @Valid @RequestBody Medico medico) {
        return service.actualizarMedico(id, medico);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        service.eliminarMedico(id);
        return ResponseEntity.noContent().build();
    }
}
