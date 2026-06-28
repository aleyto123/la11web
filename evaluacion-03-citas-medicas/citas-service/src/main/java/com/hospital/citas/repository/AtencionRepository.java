package com.hospital.citas.repository;

import com.hospital.citas.entity.Atencion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtencionRepository extends JpaRepository<Atencion, Long> {
}
