# Laboratorio: Sistema de Gestion de Citas Medicas

## Resumen de la solucion

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

## Pruebas sugeridas en Postman

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

Luego ejecutar en este orden:

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

## Conclusiones

1. La arquitectura de microservicios permite separar responsabilidades y mantener cada modulo del sistema de citas de forma independiente.
2. Eureka facilita el descubrimiento de servicios, evitando depender directamente de direcciones fijas entre microservicios.
3. El API Gateway simplifica las pruebas porque centraliza las rutas en un solo puerto.
4. Spring Data JPA reduce el codigo necesario para implementar operaciones CRUD y mantiene la persistencia ordenada.
5. Las validaciones y el manejo global de excepciones ayudan a devolver respuestas claras cuando faltan datos o se incumplen reglas de negocio.
