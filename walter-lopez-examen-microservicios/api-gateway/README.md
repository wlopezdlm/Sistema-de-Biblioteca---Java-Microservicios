# api-gateway (estado acumulado hasta la Clase 4)

La única puerta pública del sistema (**puerto 8080**). Este proyecto es el
resultado de aplicar, en orden, lo de las Clases 2, 3 y 4:

| Clase | Qué le agregó |
|---|---|
| 2 | Rutea por **nombre** (`lb://`) hacia `personas`, `solicitudes` y `notificaciones`, resolviendo direcciones vía Eureka. |
| 3 | Deja de tener config repetida: pide la suya (URL de Eureka, `jwt.secret`) al **Config Server**. |
| 4 | Valida un **JWT** en cada request con `JwtAuthFilter`; solo `/api/v1/auth/**` (el login de `auth-service`) queda público. |

## Para correrlo (orden de arranque)

```
1. config-server    (:8888)
2. eureka-server     (:8761)
3. auth-service       (:8084)
4. personas / solicitudes / notificaciones-service
5. api-gateway        (:8080)   <- este proyecto
```

```bash
mvn spring-boot:run
```

## Probarlo

```bash
# 1) Sin token -> 401
curl -i http://localhost:8080/api/v1/solicitudes

# 2) Login (via el gateway, hacia auth-service)
TOKEN=$(curl -s -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"usuario":"admin","clave":"admin123"}' | jq -r .token)

# 3) Con token -> 200
curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/api/v1/solicitudes
```

## El secreto compartido (jwt.secret)

`auth-service` FIRMA el token y este gateway lo VERIFICA — deben usar
**exactamente el mismo** `jwt.secret`. La forma correcta es que ambos lo
reciban del Config Server (`application.properties` del repo, una sola vez).
El valor por defecto embebido en `JwtAuthFilter` es solo un respaldo para que
el gateway no se caiga si el Config Server está abajo; en ese caso, asegúrate
de que `auth-service` tenga el MISMO default.

## Rutas configuradas

| Path | Destino |
|---|---|
| `/api/v1/auth/**` | `lb://auth-service` (público) |
| `/api/v1/personas/**` | `lb://personas-service` |
| `/api/v1/solicitudes/**` | `lb://solicitudes-service` |
| `/api/v1/notificaciones/**` | `lb://notificaciones-service` |
