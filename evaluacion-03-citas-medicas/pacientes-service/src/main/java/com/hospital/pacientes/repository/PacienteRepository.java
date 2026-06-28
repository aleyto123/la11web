package com.hospital.pacientes.repository;

import com.hospital.pacientes.entity.Paciente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    boolean existsByDni(String dni);

    Optional<Paciente> findByDni(String dni);
}
