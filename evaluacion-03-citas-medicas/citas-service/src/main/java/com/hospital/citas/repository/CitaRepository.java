package com.hospital.citas.repository;

import com.hospital.citas.entity.Cita;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    boolean existsByMedicoIdAndFechaAndHora(Long medicoId, LocalDate fecha, LocalTime hora);

    boolean existsByMedicoIdAndFechaAndHoraAndIdCitaNot(Long medicoId, LocalDate fecha, LocalTime hora, Long idCita);
}
