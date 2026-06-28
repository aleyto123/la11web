package com.hospital.citas.service;

import com.hospital.citas.dto.CitaDetalleDto;
import com.hospital.citas.dto.DisponibilidadDto;
import com.hospital.citas.dto.MedicoDetalleDto;
import com.hospital.citas.dto.PacienteDto;
import com.hospital.citas.entity.Cita;
import com.hospital.citas.entity.EstadoCita;
import com.hospital.citas.exception.BusinessException;
import com.hospital.citas.exception.ResourceNotFoundException;
import com.hospital.citas.repository.CitaRepository;
import com.hospital.citas.repository.EstadoCitaRepository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CitaService {
    private final CitaRepository citas;
    private final EstadoCitaRepository estados;
    private final RestTemplate restTemplate;

    public CitaService(CitaRepository citas, EstadoCitaRepository estados, RestTemplate restTemplate) {
        this.citas = citas;
        this.estados = estados;
        this.restTemplate = restTemplate;
    }

    public List<Cita> listarCitas() {
        return citas.findAll();
    }

    public List<EstadoCita> listarEstados() {
        return estados.findAll();
    }

    public Cita obtenerCita(Long id) {
        return citas.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada: " + id));
    }

    public EstadoCita obtenerEstado(Long id) {
        return estados.findById(id).orElseThrow(() -> new ResourceNotFoundException("Estado de cita no encontrado: " + id));
    }

    public EstadoCita guardarEstado(EstadoCita estado) {
        estado.setDescripcion(estado.getDescripcion().toUpperCase());
        return estados.save(estado);
    }

    public Cita crearCita(Cita cita) {
        cita.setEstado(resolverEstado(cita.getEstado()));
        validarReferencias(cita.getPacienteId(), cita.getMedicoId());
        validarHorarioDisponible(cita.getMedicoId(), cita.getFecha(), cita.getHora());
        if (citas.existsByMedicoIdAndFechaAndHora(cita.getMedicoId(), cita.getFecha(), cita.getHora())) {
            throw new BusinessException("El medico ya tiene una cita registrada en esa fecha y hora");
        }
        return citas.save(cita);
    }

    public Cita actualizarCita(Long id, Cita datos) {
        Cita cita = obtenerCita(id);
        cita.setFecha(datos.getFecha());
        cita.setHora(datos.getHora());
        cita.setPacienteId(datos.getPacienteId());
        cita.setMedicoId(datos.getMedicoId());
        cita.setMotivo(datos.getMotivo());
        cita.setEstado(resolverEstado(datos.getEstado()));
        validarReferencias(cita.getPacienteId(), cita.getMedicoId());
        validarHorarioDisponible(cita.getMedicoId(), cita.getFecha(), cita.getHora());
        if (citas.existsByMedicoIdAndFechaAndHoraAndIdCitaNot(
                cita.getMedicoId(), cita.getFecha(), cita.getHora(), cita.getIdCita())) {
            throw new BusinessException("El medico ya tiene otra cita en esa fecha y hora");
        }
        return citas.save(cita);
    }

    public Cita cancelarCita(Long id) {
        Cita cita = obtenerCita(id);
        EstadoCita cancelada = estados.findByDescripcionIgnoreCase("CANCELADA")
                .orElseThrow(() -> new ResourceNotFoundException("No existe el estado CANCELADA"));
        cita.setEstado(cancelada);
        return citas.save(cita);
    }

    public void eliminarCita(Long id) {
        citas.delete(obtenerCita(id));
    }

    public EstadoCita actualizarEstado(Long id, EstadoCita datos) {
        EstadoCita estado = obtenerEstado(id);
        estado.setDescripcion(datos.getDescripcion().toUpperCase());
        return estados.save(estado);
    }

    public void eliminarEstado(Long id) {
        estados.delete(obtenerEstado(id));
    }

    public CitaDetalleDto obtenerDetalle(Long id) {
        Cita cita = obtenerCita(id);
        PacienteDto paciente = obtenerPaciente(cita.getPacienteId());
        MedicoDetalleDto medico = obtenerMedico(cita.getMedicoId());
        return new CitaDetalleDto(
                cita.getIdCita(),
                cita.getFecha(),
                cita.getHora(),
                cita.getEstado().getDescripcion(),
                Map.of("nombre", paciente.nombreCompleto(), "dni", paciente.dni()),
                Map.of("nombre", medico.nombre(), "especialidad", medico.especialidad()),
                medico.consultorio());
    }

    private EstadoCita resolverEstado(EstadoCita estado) {
        if (estado == null || estado.getIdEstado() == null) {
            return estados.findByDescripcionIgnoreCase("PROGRAMADA")
                    .orElseThrow(() -> new ResourceNotFoundException("No existe el estado PROGRAMADA"));
        }
        return obtenerEstado(estado.getIdEstado());
    }

    private void validarReferencias(Long pacienteId, Long medicoId) {
        obtenerPaciente(pacienteId);
        obtenerMedico(medicoId);
    }

    private PacienteDto obtenerPaciente(Long pacienteId) {
        return restTemplate.getForObject("http://PACIENTES-SERVICE/api/pacientes/{id}", PacienteDto.class, pacienteId);
    }

    private MedicoDetalleDto obtenerMedico(Long medicoId) {
        return restTemplate.getForObject("http://MEDICOS-SERVICE/api/medicos/{id}/detalle", MedicoDetalleDto.class, medicoId);
    }

    private void validarHorarioDisponible(Long medicoId, LocalDate fecha, LocalTime hora) {
        String diaSemana = diaSemanaEnEspanol(fecha.getDayOfWeek());
        DisponibilidadDto disponibilidad = restTemplate.getForObject(
                "http://MEDICOS-SERVICE/api/medicos/{id}/disponible?diaSemana={diaSemana}&hora={hora}",
                DisponibilidadDto.class,
                medicoId,
                diaSemana,
                hora);
        if (disponibilidad == null || !Boolean.TRUE.equals(disponibilidad.disponible())) {
            throw new BusinessException("El medico no tiene horario disponible para la fecha y hora indicada");
        }
    }

    private String diaSemanaEnEspanol(DayOfWeek day) {
        return switch (day) {
            case MONDAY -> "LUNES";
            case TUESDAY -> "MARTES";
            case WEDNESDAY -> "MIERCOLES";
            case THURSDAY -> "JUEVES";
            case FRIDAY -> "VIERNES";
            case SATURDAY -> "SABADO";
            case SUNDAY -> "DOMINGO";
        };
    }
}
