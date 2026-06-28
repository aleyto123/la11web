package com.hospital.medicos.repository;

import com.hospital.medicos.entity.HorarioMedico;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HorarioMedicoRepository extends JpaRepository<HorarioMedico, Long> {
    List<HorarioMedico> findByMedicoIdMedicoAndDiaSemanaIgnoreCase(Long idMedico, String diaSemana);
}
