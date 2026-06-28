package com.hospital.medicos.controller;

import com.hospital.medicos.entity.Consultorio;
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
@RequestMapping("/api/consultorios")
public class ConsultorioController {
    private final MedicoService service;

    public ConsultorioController(MedicoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Consultorio> listar() { return service.listarConsultorios(); }

    @GetMapping("/{id}")
    public Consultorio obtener(@PathVariable("id") Long id) { return service.obtenerConsultorio(id); }

    @PostMapping
    public ResponseEntity<Consultorio> crear(@Valid @RequestBody Consultorio consultorio) {
        Consultorio creado = service.guardarConsultorio(consultorio);
        return ResponseEntity.created(URI.create("/api/consultorios/" + creado.getIdConsultorio())).body(creado);
    }

    @PutMapping("/{id}")
    public Consultorio actualizar(@PathVariable("id") Long id, @Valid @RequestBody Consultorio consultorio) {
        return service.actualizarConsultorio(id, consultorio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        service.eliminarConsultorio(id);
        return ResponseEntity.noContent().build();
    }
}
