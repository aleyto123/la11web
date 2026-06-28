package com.hospital.citas.repository;

import com.hospital.citas.entity.EstadoCita;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoCitaRepository extends JpaRepository<EstadoCita, Long> {
    Optional<EstadoCita> findByDescripcionIgnoreCase(String descripcion);
}
