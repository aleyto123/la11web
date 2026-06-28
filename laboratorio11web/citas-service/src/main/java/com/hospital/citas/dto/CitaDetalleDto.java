package com.hospital.citas.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public record CitaDetalleDto(
        Long idCita,
        LocalDate fecha,
        LocalTime hora,
        String estado,
        Map<String, String> paciente,
        Map<String, String> medico,
        ConsultorioDto consultorio) {
}
