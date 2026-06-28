# Laboratorio: Sistema de Gestion de Citas Medicas

## Resumen de la solucion
# Alumno: Bellido Rony
Se implemento una arquitectura de microservicios con Spring Boot para la clinica Salud Integral. La solucion separa el dominio en servicios independientes para pacientes, medicos y citas, ademas de Eureka Server para descubrimiento y API Gateway para centralizar el acceso.

## Microservicios desarrollados

- `pacientes-service`: permite registrar, listar, buscar, actualizar y eliminar pacientes.
- `medicos-service`: permite registrar, listar, buscar, actualizar y eliminar medicos. Tambien administra especialidades, consultorios y horarios medicos.
- `citas-service`: permite registrar, listar, buscar, actualizar, cancelar citas y registrar atenciones.
- `eureka-server`: registra los microservicios disponibles.
- `api-gateway`: expone una entrada unica en el puerto `8080`.

## Tablas cubiertas

- `paciente`: DNI, nombres, apellidos, fecha de nacimiento, sexo, telefono, direccion, correo y estado.
- `especialidades`: nombre y descripcion de la especialidad.
- `medico`: CMP, nombres, apellidos, telefono, correo, especialidad, consultorio y estado.
- `horarios_medicos`: dia de semana y rango horario del medico.
- `cita`: fecha, hora, paciente, medico, motivo y estado.
- `atencion`: diagnostico, tratamiento, observaciones y fecha de registro.

Importar la coleccion:

`postman/Sistema-Citas-Medicas.postman_collection.json`

Orden recomendado:

1. `GET http://localhost:8080/api/pacientes`
2. `POST http://localhost:8080/api/pacientes`
3. `GET http://localhost:8080/api/medicos`
4. `GET http://localhost:8080/api/medicos/1/disponible?diaSemana=JUEVES&hora=10:30`
5. `POST http://localhost:8080/api/citas`
6. `GET http://localhost:8080/api/citas/1/detalle`
7. `PATCH http://localhost:8080/api/citas/1/cancelar`
8. `GET http://localhost:8080/api/atenciones`

## Ejecucion

Desde la raiz:

```bash
mvn clean test
```
```bash
cd eureka-server
mvn spring-boot:run

cd ../pacientes-service
mvn spring-boot:run

cd ../medicos-service
mvn spring-boot:run

cd ../citas-service
mvn spring-boot:run

cd ../api-gateway
mvn spring-boot:run
```
