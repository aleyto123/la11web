package com.hospital.citas.controller;

import com.hospital.citas.entity.Atencion;
import com.hospital.citas.repository.AtencionRepository;
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
@RequestMapping("/api/atenciones")
public class AtencionController {
    private final AtencionRepository repository;
    private final CitaService citaService;

    public AtencionController(AtencionRepository repository, CitaService citaService) {
        this.repository = repository;
        this.citaService = citaService;
    }

    @GetMapping
    public List<Atencion> listar() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Atencion> crear(@Valid @RequestBody Atencion atencion) {
        atencion.setCita(citaService.obtenerCita(atencion.getCita().getIdCita()));
        Atencion creada = repository.save(atencion);
        return ResponseEntity.created(URI.create("/api/atenciones/" + creada.getIdAtencion())).body(creada);
    }

    @PutMapping("/{id}")
    public Atencion actualizar(@PathVariable Long id, @Valid @RequestBody Atencion datos) {
        Atencion atencion = repository.findById(id).orElseThrow();
        atencion.setCita(citaService.obtenerCita(datos.getCita().getIdCita()));
        atencion.setDiagnostico(datos.getDiagnostico());
        atencion.setTratamiento(datos.getTratamiento());
        atencion.setObservaciones(datos.getObservaciones());
        return repository.save(atencion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
