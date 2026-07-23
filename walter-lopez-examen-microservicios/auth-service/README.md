# auth-service (:8084)

Emite los JWT que el `api-gateway` valida. Sin este servicio arriba y
registrado en Eureka, la ruta `/api/v1/auth/**` del gateway (`lb://auth-service`)
no tiene a quién enrutar.

## Orden de arranque

```
1. config-server   (:8888)
2. eureka-server    (:8761)
3. auth-service      (:8084)   <- este proyecto
4. personas / solicitudes / notificaciones-service
5. api-gateway       (:8080)
```

```bash
mvn spring-boot:run
```

## Probarlo directo (sin pasar por el gateway, para aislar problemas)

```bash
curl -X POST http://localhost:8084/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"usuario":"admin","clave":"admin123"}'
```

Debe devolver `{"token": "..."}`. Si esto funciona pero el login por el
gateway (`:8080/api/v1/auth/login`) no, el problema está en Eureka
(auth-service no se registró) o en el gateway, no aquí.

## El secreto (jwt.secret)

Debe ser **exactamente igual** al que tiene `JwtAuthFilter` en el
`api-gateway` — uno firma, el otro verifica. Ya viene puesto el mismo valor
que generamos antes. Si luego lo centralizas en el Config Server, bórralo de
aquí y del gateway, y déjalo solo en el repo compartido.
