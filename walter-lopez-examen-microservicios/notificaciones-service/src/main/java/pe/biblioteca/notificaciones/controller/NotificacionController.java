package pe.biblioteca.notificaciones.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.biblioteca.notificaciones.dto.NotificacionRequest;
import pe.biblioteca.notificaciones.entity.Notificacion;
import pe.biblioteca.notificaciones.service.NotificacionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;

    @PostMapping
    public ResponseEntity<Notificacion> enviar(@RequestBody NotificacionRequest req) {
        return ResponseEntity.ok(notificacionService.enviar(req));
    }

    @GetMapping
    public List<Notificacion> listar() {
        return notificacionService.listar();
    }
}
