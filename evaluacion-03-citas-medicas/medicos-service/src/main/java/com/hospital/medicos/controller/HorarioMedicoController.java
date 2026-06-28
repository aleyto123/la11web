package com.hospital.medicos.controller;

import com.hospital.medicos.entity.HorarioMedico;
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
@RequestMapping("/api/horarios")
public class HorarioMedicoController {
    private final MedicoService service;

    public HorarioMedicoController(MedicoService service) {
        this.service = service;
    }

    @GetMapping
    public List<HorarioMedico> listar() { return service.listarHorarios(); }

    @GetMapping("/{id}")
    public HorarioMedico obtener(@PathVariable("id") Long id) { return service.obtenerHorario(id); }

    @PostMapping
    public ResponseEntity<HorarioMedico> crear(@Valid @RequestBody HorarioMedico horario) {
        HorarioMedico creado = service.guardarHorario(horario);
        return ResponseEntity.created(URI.create("/api/horarios/" + creado.getIdHorario())).body(creado);
    }

    @PutMapping("/{id}")
    public HorarioMedico actualizar(@PathVariable("id") Long id, @Valid @RequestBody HorarioMedico horario) {
        return service.actualizarHorario(id, horario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        service.eliminarHorario(id);
        return ResponseEntity.noContent().build();
    }
}
