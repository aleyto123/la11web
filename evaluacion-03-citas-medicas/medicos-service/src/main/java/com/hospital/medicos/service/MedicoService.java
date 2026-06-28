package com.hospital.medicos.service;

import com.hospital.medicos.entity.Consultorio;
import com.hospital.medicos.entity.Especialidad;
import com.hospital.medicos.entity.HorarioMedico;
import com.hospital.medicos.entity.Medico;
import com.hospital.medicos.exception.BusinessException;
import com.hospital.medicos.exception.ResourceNotFoundException;
import com.hospital.medicos.repository.ConsultorioRepository;
import com.hospital.medicos.repository.EspecialidadRepository;
import com.hospital.medicos.repository.HorarioMedicoRepository;
import com.hospital.medicos.repository.MedicoRepository;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {
    private final MedicoRepository medicos;
    private final EspecialidadRepository especialidades;
    private final ConsultorioRepository consultorios;
    private final HorarioMedicoRepository horarios;

    public MedicoService(
            MedicoRepository medicos,
            EspecialidadRepository especialidades,
            ConsultorioRepository consultorios,
            HorarioMedicoRepository horarios) {
        this.medicos = medicos;
        this.especialidades = especialidades;
        this.consultorios = consultorios;
        this.horarios = horarios;
    }

    public List<Medico> listarMedicos() { return medicos.findAll(); }
    public List<Especialidad> listarEspecialidades() { return especialidades.findAll(); }
    public List<Consultorio> listarConsultorios() { return consultorios.findAll(); }
    public List<HorarioMedico> listarHorarios() { return horarios.findAll(); }

    public Medico obtenerMedico(Long id) {
        return medicos.findById(id).orElseThrow(() -> new ResourceNotFoundException("Medico no encontrado: " + id));
    }

    public Especialidad obtenerEspecialidad(Long id) {
        return especialidades.findById(id).orElseThrow(() -> new ResourceNotFoundException("Especialidad no encontrada: " + id));
    }

    public Consultorio obtenerConsultorio(Long id) {
        return consultorios.findById(id).orElseThrow(() -> new ResourceNotFoundException("Consultorio no encontrado: " + id));
    }

    public HorarioMedico obtenerHorario(Long id) {
        return horarios.findById(id).orElseThrow(() -> new ResourceNotFoundException("Horario no encontrado: " + id));
    }

    public Medico guardarMedico(Medico medico) {
        medico.setEspecialidad(obtenerEspecialidad(medico.getEspecialidad().getIdEspecialidad()));
        medico.setConsultorio(obtenerConsultorio(medico.getConsultorio().getIdConsultorio()));
        return medicos.save(medico);
    }

    public Especialidad guardarEspecialidad(Especialidad especialidad) {
        return especialidades.save(especialidad);
    }

    public Consultorio guardarConsultorio(Consultorio consultorio) {
        return consultorios.save(consultorio);
    }

    public HorarioMedico guardarHorario(HorarioMedico horario) {
        if (!horario.getHoraInicio().isBefore(horario.getHoraFin())) {
            throw new BusinessException("La hora de inicio debe ser menor que la hora de fin");
        }
        horario.setMedico(obtenerMedico(horario.getMedico().getIdMedico()));
        return horarios.save(horario);
    }

    public Medico actualizarMedico(Long id, Medico datos) {
        Medico medico = obtenerMedico(id);
        medico.setCmp(datos.getCmp());
        medico.setNombres(datos.getNombres());
        medico.setApellidos(datos.getApellidos());
        medico.setTelefono(datos.getTelefono());
        medico.setCorreo(datos.getCorreo());
        medico.setEstado(datos.getEstado());
        medico.setEspecialidad(obtenerEspecialidad(datos.getEspecialidad().getIdEspecialidad()));
        medico.setConsultorio(obtenerConsultorio(datos.getConsultorio().getIdConsultorio()));
        return medicos.save(medico);
    }

    public Especialidad actualizarEspecialidad(Long id, Especialidad datos) {
        Especialidad especialidad = obtenerEspecialidad(id);
        especialidad.setNombreEspecialidad(datos.getNombreEspecialidad());
        especialidad.setDescripcion(datos.getDescripcion());
        return especialidades.save(especialidad);
    }

    public Consultorio actualizarConsultorio(Long id, Consultorio datos) {
        Consultorio consultorio = obtenerConsultorio(id);
        consultorio.setNumero(datos.getNumero());
        consultorio.setPiso(datos.getPiso());
        consultorio.setUbicacion(datos.getUbicacion());
        return consultorios.save(consultorio);
    }

    public HorarioMedico actualizarHorario(Long id, HorarioMedico datos) {
        HorarioMedico horario = obtenerHorario(id);
        horario.setMedico(obtenerMedico(datos.getMedico().getIdMedico()));
        horario.setDiaSemana(datos.getDiaSemana());
        horario.setHoraInicio(datos.getHoraInicio());
        horario.setHoraFin(datos.getHoraFin());
        return guardarHorario(horario);
    }

    public void eliminarMedico(Long id) { medicos.delete(obtenerMedico(id)); }
    public void eliminarEspecialidad(Long id) { especialidades.delete(obtenerEspecialidad(id)); }
    public void eliminarConsultorio(Long id) { consultorios.delete(obtenerConsultorio(id)); }
    public void eliminarHorario(Long id) { horarios.delete(obtenerHorario(id)); }

    public boolean estaDisponible(Long idMedico, String diaSemana, LocalTime hora) {
        obtenerMedico(idMedico);
        return horarios.findByMedicoIdMedicoAndDiaSemanaIgnoreCase(idMedico, diaSemana).stream()
                .anyMatch(h -> !hora.isBefore(h.getHoraInicio()) && hora.isBefore(h.getHoraFin()));
    }

    public Map<String, Object> detalleMedico(Long idMedico) {
        Medico medico = obtenerMedico(idMedico);
        return Map.of(
                "idMedico", medico.getIdMedico(),
                "nombre", medico.getNombres() + " " + medico.getApellidos(),
                "especialidad", medico.getEspecialidad().getNombreEspecialidad(),
                "consultorio", Map.of(
                        "numero", medico.getConsultorio().getNumero(),
                        "piso", medico.getConsultorio().getPiso(),
                        "ubicacion", medico.getConsultorio().getUbicacion()));
    }
}
