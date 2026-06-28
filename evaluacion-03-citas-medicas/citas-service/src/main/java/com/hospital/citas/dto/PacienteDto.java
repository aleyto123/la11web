package com.hospital.citas.dto;

public record PacienteDto(Long idPaciente, String nombres, String apellidos, String dni) {
    public String nombreCompleto() {
        return nombres + " " + apellidos;
    }
}
