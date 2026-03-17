# 🛒 Productos API — Spring Boot CRUD

API REST para gestión de productos construida con **Spring Boot 3.5**, **PostgreSQL** y buenas prácticas de arquitectura por capas.

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.11-brightgreen?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue?logo=postgresql)
![License](https://img.shields.io/badge/License-MIT-yellow)

## Características

- CRUD completo con respuestas estructuradas
- Arquitectura por capas (Controller → Service → Repository)
- DTOs para separar la entidad de la API pública
- Validaciones con Bean Validation (`@Valid`)
- Paginación, ordenamiento y filtros por query params
- Manejo global de excepciones
- Documentación interactiva con Swagger / OpenAPI 3

## Arquitectura

```
┌─────────────────────────────────────────────────────────┐
│  Controller        Recibe HTTP, retorna JSON            │
│  ↓                                                      │
│  Service (interfaz + impl)   Lógica de negocio          │
│  ↓                                                      │
│  Repository        Acceso a datos (Spring Data JPA)     │
│  ↓                                                      │
│  PostgreSQL                                             │
└─────────────────────────────────────────────────────────┘
```

```
src/main/java/com/task/crud/
├── config/              → Configuración (OpenAPI)
├── controller/          → Endpoints REST
├── dto/                 → Objetos de transferencia (Request/Response)
├── entity/              → Entidades JPA
├── exception/           → Manejo global de errores
├── repository/          → Interfaces de acceso a datos
└── service/             → Lógica de negocio
```

## Requisitos previos

- Java 17+
- Maven 3.9+
- PostgreSQL (corriendo en localhost)

## Instalación

**1. Clonar el repositorio**

```bash
git clone https://github.com/JesusGabrielCastro/test-spring.git
cd test-spring
```

**2. Crear la base de datos**

```sql
CREATE DATABASE spring;
```

**3. Configurar la conexión**

Edita `src/main/resources/application.yml` con tus credenciales:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/spring
    username: tu_usuario
    password: tu_password
```

**4. Ejecutar**

```bash
mvn spring-boot:run
```

La API estará disponible en `http://localhost:8080`

## Endpoints

### Productos

| Método   | Endpoint                    | Descripción               |
|----------|-----------------------------|---------------------------|
| `GET`    | `/api/productos`            | Listar todos (paginado)   |
| `GET`    | `/api/productos/{id}`       | Obtener por ID            |
| `GET`    | `/api/productos/filtrar`    | Filtrar con query params  |
| `POST`   | `/api/productos`            | Crear producto            |
| `PUT`    | `/api/productos/{id}`       | Actualizar producto       |
| `DELETE` | `/api/productos/{id}`       | Eliminar producto         |

### Parámetros de paginación y ordenamiento

```
GET /api/productos?page=1&size=10&sort=precio&direction=desc
```

| Parámetro   | Default | Descripción                          |
|-------------|---------|--------------------------------------|
| `page`      | 1       | Número de página                     |
| `size`      | 10      | Elementos por página                 |
| `sort`      | id      | Campo de ordenamiento                |
| `direction` | asc     | Dirección: `asc` o `desc`           |

### Parámetros de filtrado

```
GET /api/productos/filtrar?nombre=laptop&precioMin=100000&precioMax=5000000
```

| Parámetro   | Tipo     | Descripción                              |
|-------------|----------|------------------------------------------|
| `nombre`    | String   | Búsqueda parcial, case insensitive       |
| `precioMin` | Double   | Precio mínimo                            |
| `precioMax` | Double   | Precio máximo                            |

Todos los filtros son opcionales y combinables entre sí.

## Ejemplos de uso

### Crear un producto

```bash
curl -X POST http://localhost:8080/api/productos \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Laptop Lenovo ThinkPad",
    "precio": 2500000,
    "descripcion": "Laptop para desarrollo de software"
  }'
```

**Respuesta** `201 Created`:

```json
{
  "id": 1,
  "nombre": "Laptop Lenovo ThinkPad",
  "precio": 2500000.0,
  "descripcion": "Laptop para desarrollo de software",
  "createdAt": "2026-03-17T00:15:30",
  "updatedAt": "2026-03-17T00:15:30"
}
```

### Error de validación

```bash
curl -X POST http://localhost:8080/api/productos \
  -H "Content-Type: application/json" \
  -d '{"precio": -500}'
```

**Respuesta** `400 Bad Request`:

```json
{
  "timestamp": "2026-03-17T00:16:00",
  "status": 400,
  "error": "Validation Failed",
  "errors": {
    "nombre": "El nombre es obligatorio",
    "precio": "El precio debe ser mayor a 0"
  }
}
```

### Respuesta paginada

```json
{
  "content": [ ... ],
  "page": 1,
  "size": 10,
  "totalElements": 25,
  "totalPages": 3,
  "first": true,
  "last": false
}
```

## Documentación interactiva

Con la aplicación corriendo, accede a:

| URL                              | Descripción                    |
|----------------------------------|--------------------------------|
| `http://localhost:8080/docs`     | Swagger UI interactivo         |
| `http://localhost:8080/api-docs` | OpenAPI 3 JSON (importar en Postman) |

## Validaciones

| Campo        | Reglas                                          |
|--------------|-------------------------------------------------|
| `nombre`     | Obligatorio, entre 2 y 100 caracteres           |
| `precio`     | Obligatorio, mayor a 0                          |
| `descripcion`| Opcional, máximo 500 caracteres                 |

## Stack tecnológico

- **Java 17** — lenguaje
- **Spring Boot 3.5** — framework
- **Spring Data JPA** — acceso a datos
- **PostgreSQL** — base de datos
- **Bean Validation** — validaciones
- **SpringDoc OpenAPI** — documentación Swagger
- **Maven** — gestión de dependencias
