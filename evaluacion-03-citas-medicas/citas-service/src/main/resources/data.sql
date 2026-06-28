INSERT INTO estados_cita (id_estado, descripcion) VALUES (1, 'PROGRAMADA');
INSERT INTO estados_cita (id_estado, descripcion) VALUES (2, 'CONFIRMADA');
INSERT INTO estados_cita (id_estado, descripcion) VALUES (3, 'ATENDIDA');
INSERT INTO estados_cita (id_estado, descripcion) VALUES (4, 'CANCELADA');

INSERT INTO cita (id_cita, fecha, hora, paciente_id, medico_id, motivo, estado_id)
VALUES (1, '2026-07-02', '10:30:00', 1, 1, 'Control general', 1);
