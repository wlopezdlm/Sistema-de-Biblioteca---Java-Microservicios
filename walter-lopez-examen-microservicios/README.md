# Sistema de Biblioteca — Microservicios

Sistema de gestión de biblioteca - microservicios usando Spring Boot y Spring Cloud.

Walter Lopez.

---

## Servicios

| Servicio | Puerto | Descripción |
|---|---|---|
| `eureka-server` | 8761 | Registro y descubrimiento de servicios |
| `config-server` | 8888 | Configuración centralizada (Git) |
| `api-gateway` | 8080 | Puerta de entrada única, valida JWT |
| `auth-service` | dinámico | Emite tokens JWT (login) |
| `libros-service` | 8081 | CRUD de libros (ejemplares) y socios |
| `prestamos-service` | 8082 | Gestión de préstamos |
| `notificaciones-service` | 8083 | Registro de notificaciones |

---

## Requisitos Previos

- Java 21
- Maven 3.9
- PostgreSQL

---

## Rutas de la API (via Gateway :8080)

### Autenticación

| Método | Ruta | Descripción |
|---|---|---|
| POST | `/api/v1/auth/login` | Obtener token JWT |

```json
// Body
{ "usuario": "admin", "clave": "admin123" }

// Response
{ "token": "eyJhbGci..." }
```

### Libros (Ejemplares)

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/v1/libros` | Listar ejemplares |
| GET | `/api/v1/libros/{id}` | Obtener ejemplar |
| POST | `/api/v1/libros` | Crear ejemplar |
| PUT | `/api/v1/libros/{id}` | Actualizar ejemplar |
| DELETE | `/api/v1/libros/{id}` | Eliminar ejemplar |

### Socios

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/v1/socios` | Listar socios |
| GET | `/api/v1/socios/{id}` | Obtener socio |
| POST | `/api/v1/socios` | Crear socio |
| PUT | `/api/v1/socios/{id}` | Actualizar socio |
| DELETE | `/api/v1/socios/{id}` | Eliminar socio |

### Préstamos

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/v1/prestamos` | Listar préstamos |
| POST | `/api/v1/prestamos` | Registrar préstamo |

### Notificaciones

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/v1/notificaciones` | Listar notificaciones |


---

## Calidad de Código — SonarCloud

El proyecto usa GitHub Actions para análisis automático con SonarCloud en cada push a `main`.

**Proyectos analizados:**
- `wlopezdlm_libros-service`
- `wlopezdlm_prestamos-service`
- `wlopezdlm_notificaciones-service`
- `wlopezdlm_auth-service`

Para habilitar el análisis, agrega el secret `SONAR_TOKEN` en:
**GitHub → Settings → Secrets and variables → Actions**

---
.

