package pe.codigo.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pe.codigo.authservice.dto.LoginRequest;
import pe.codigo.authservice.service.JwtService;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwt;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest req) {
        if (!("admin".equals(req.usuario()) && "admin123".equals(req.clave()))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales invalidas");
        }
        return Map.of("token", jwt.generar(req.usuario()));
    }
}
