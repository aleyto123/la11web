# Sistema de Gestion de Citas Medicas

Evaluacion 03 - Arquitectura de Microservicios con Spring Boot, Eureka Server, API Gateway y YAML.

## Estructura

- `eureka-server`: registro y descubrimiento de servicios.
- `api-gateway`: entrada centralizada a todos los servicios.
- `pacientes-service`: CRUD de pacientes con los campos de la tabla `paciente`.
- `medicos-service`: CRUD de medicos, especialidades, horarios y consultorios.
- `citas-service`: CRUD de citas, estados, atenciones, reglas de negocio e integracion REST.
- `postman`: coleccion para evidencias de pruebas.

## Tecnologias

- Java 17
- Spring Boot 3.3.6
- Spring Cloud 2023.0.4
- Spring Data JPA / Hibernate
- H2 Database
- Eureka Server / Eureka Client
- Spring Cloud Gateway
- Bean Validation

## Perfiles YAML

Cada microservicio define perfiles en `application.yml`:

- `dev`: ejecucion local con H2 en memoria.
- `test`: puertos alternos y base H2 de prueba.
- `prod`: configuracion preparada para H2 en archivo y menor salida SQL.

Para cambiar perfil:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=test
```

## Orden de ejecucion

Desde la raiz del proyecto:

```bash
mvn clean install
```

Luego iniciar en este orden:

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

## Puertos

- Eureka: `http://localhost:8761`
- API Gateway: `http://localhost:8080`
- Pacientes: `http://localhost:8081`
- Medicos: `http://localhost:8082`
- Citas: `http://localhost:8083`

## Endpoints principales via Gateway

- `GET /api/pacientes`
- `POST /api/pacientes`
- `GET /api/medicos`
- `GET /api/especialidades`
- `GET /api/consultorios`
- `GET /api/horarios`
- `POST /api/citas`
- `GET /api/citas/{id}/detalle`
- `PATCH /api/citas/{id}/cancelar`
- `GET /api/estados-cita`
- `GET /api/atenciones`

## Reglas implementadas

- No se registra una cita si el medico no tiene horario disponible.
- No se permiten dos citas para el mismo medico en la misma fecha y hora.
- Al crear o actualizar una cita se valida que existan paciente y medico mediante REST.
- Al consultar detalle de cita se integra informacion de paciente, medico, especialidad y consultorio.
- Se manejan errores globales con respuestas JSON claras.

## Ejemplo de cita integrada

Con los datos iniciales:

```http
GET http://localhost:8080/api/citas/1/detalle
```

Respuesta esperada:

```json
{
  "idCita": 1,
  "fecha": "2026-07-02",
  "hora": "10:30:00",
  "estado": "PROGRAMADA",
  "paciente": {
    "nombre": "Juan Perez",
    "dni": "74589632"
  },
  "medico": {
    "nombre": "Carlos Torres",
    "especialidad": "Cardiologia"
  },
  "consultorio": {
    "numero": "C-203",
    "piso": 2,
    "ubicacion": "Torre central"
  }
}
```

## Nota de ejecucion

Este proyecto usa Maven. Si el equipo no tiene `mvn` instalado, abrir la carpeta en IntelliJ IDEA, Eclipse STS o VS Code con extension Java/Maven permitirá importar los `pom.xml` y descargar dependencias.
