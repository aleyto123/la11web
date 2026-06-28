package com.hospital.pacientes.service;

import com.hospital.pacientes.entity.Paciente;
import com.hospital.pacientes.exception.BusinessException;
import com.hospital.pacientes.exception.ResourceNotFoundException;
import com.hospital.pacientes.repository.PacienteRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {
    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    public List<Paciente> listar() {
        return repository.findAll();
    }

    public Paciente obtener(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado: " + id));
    }

    public Paciente crear(Paciente paciente) {
        if (repository.existsByDni(paciente.getDni())) {
            throw new BusinessException("Ya existe un paciente con DNI " + paciente.getDni());
        }
        return repository.save(paciente);
    }

    public Paciente actualizar(Long id, Paciente datos) {
        Paciente paciente = obtener(id);
        repository.findByDni(datos.getDni())
                .filter(existente -> !existente.getIdPaciente().equals(id))
                .ifPresent(existente -> {
                    throw new BusinessException("Ya existe un paciente con DNI " + datos.getDni());
                });
        paciente.setNombres(datos.getNombres());
        paciente.setApellidos(datos.getApellidos());
        paciente.setDni(datos.getDni());
        paciente.setFechaNacimiento(datos.getFechaNacimiento());
        paciente.setSexo(datos.getSexo());
        paciente.setTelefono(datos.getTelefono());
        paciente.setDireccion(datos.getDireccion());
        paciente.setCorreo(datos.getCorreo());
        paciente.setEstado(datos.getEstado());
        return repository.save(paciente);
    }

    public void eliminar(Long id) {
        Paciente paciente = obtener(id);
        repository.delete(paciente);
    }
}
