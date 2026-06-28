package com.hospital.citas.dto;

public record MedicoDetalleDto(Long idMedico, String nombre, String especialidad, ConsultorioDto consultorio) {
}
