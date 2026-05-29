# MS-06: Afectados y Alertas Municipales

> **CONAF — Coordinación de Emergencias por Incendios Forestales**  
> Microservicio encargado de registrar alertas municipales, 
> infraestructura comprometida y personas evacuadas durante 
> emergencias por incendios forestales.

---

## Contexto

La alcaldesa del municipio afectado necesita ingresar una alerta 
digital con la comuna, sector, nivel de urgencia y cantidad de 
familias evacuadas, para solicitar apoyo formal a CONAF regional 
y dejar registro de los daños.

---

## Stack Tecnológico

| Lenguaje | Java 21 |
| Framework | Spring Boot 3.4.5 |
| Base de datos | PostgreSQL (NeonTech) |
| ORM | Spring Data JPA / Hibernate |
| Validaciones | Bean Validation (@NotBlank, @Min) |
| Puerto | 8086 |

---

## Entidades

- **AlertaMunicipal** — alerta con comuna, sector, nivel de urgencia y estado
- **RegistroEvacuacion** — cantidad de personas y familias evacuadas asociadas a una alerta

---

### Funcionalidades Core:
* **Reporte de Focos**: Permite a las alcaldías reportar predios bajo amenaza inminente con georreferenciación exacta y catastro de familias evacuadas.
* **Trazabilidad de Recursos**: Facilita a las autoridades locales visualizar en tiempo real si el centro regional asignó brigadas o aeronaves a su territorio comunal.
* **Cierre de Incidentes**: Generación y descarga del expediente completo de daños para la coordinación de seguros y planes de reconstrucción comunal.

## Endpoint Base
http://localhost:8086/api/v1/alertas

---

## Roadmap de Endpoints

### 1. Crear alerta municipal
**POST** `/api/v1/alertas`

```json
{
  "comuna": "Constitución",
  "sector": "Sector Cordillerano Norte",
  "nivelUrgencia": "URGENTE",
  "descripcion": "Incendio avanza hacia sector residencial",
  "infraestructuraRiesgo": "30 viviendas",
  "cantidadFamilias": 30,
  "cantidadPersonas": 87,
  "puntoReunion": "Plaza de Armas Constitución",
  "observaciones": "Familias requieren alojamiento de emergencia"
}
```

Respuesta exitosa `201 Created`:
```json
{
  "id": 1,
  "comuna": "Constitución",
  "sector": "Sector Cordillerano Norte",
  "nivelUrgencia": "URGENTE",
  "descripcion": "Incendio avanza hacia sector residencial",
  "infraestructuraRiesgo": "30 viviendas",
  "fechaAlerta": "2026-05-28T10:30:00",
  "estado": "ACTIVA",
  "evacuaciones": [
    {
      "id": 1,
      "cantidadFamilias": 30,
      "cantidadPersonas": 87,
      "puntoReunion": "Plaza de Armas Constitución",
      "observaciones": "Familias requieren alojamiento de emergencia"
    }
  ]
}
```

---

### 2. Obtener todas las alertas
**GET** `/api/v1/alertas`

Respuesta exitosa `200 OK`:
```json
[
  {
    "id": 1,
    "comuna": "Constitución",
    "sector": "Sector Cordillerano Norte",
    "nivelUrgencia": "URGENTE",
    "estado": "ACTIVA",
    "fechaAlerta": "2026-05-28T10:30:00"
  }
]
```

---

### 3. Obtener alerta por ID
**GET** `/api/v1/alertas/{id}`

Ejemplo: `GET /api/v1/alertas/1`

Respuesta exitosa `200 OK`:
```json
{
  "id": 1,
  "comuna": "Constitución",
  "sector": "Sector Cordillerano Norte",
  "nivelUrgencia": "URGENTE",
  "estado": "ACTIVA",
  "fechaAlerta": "2026-05-28T10:30:00"
}
```

Error `404 Not Found`:
```json
{
  "timestamp": "2026-05-28T10:30:00",
  "status": 404,
  "error": "Not Found"
}
```

---

### 4. Actualizar estado de alerta
**PATCH** `/api/v1/alertas/{id}/estado?estado=RESUELTA`

Ejemplo: `PATCH /api/v1/alertas/1/estado?estado=RESUELTA`

Respuesta exitosa `200 OK`:
```json
{
  "id": 1,
  "comuna": "Constitución",
  "estado": "RESUELTA"
}
```

---

### 5. Eliminar alerta
**DELETE** `/api/v1/alertas/{id}`

Ejemplo: `DELETE /api/v1/alertas/1`

Respuesta exitosa `204 No Content` — sin cuerpo.

---

### 6. Validación — campos obligatorios vacíos
**POST** `/api/v1/alertas` sin comuna:

```json
{
  "comuna": "",
  "sector": "Sector Norte",
  "nivelUrgencia": "URGENTE"
}
```

Error `400 Bad Request`:
```json
{
  "timestamp": "2026-05-28T10:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "La comuna es obligatoria"
}
```

---