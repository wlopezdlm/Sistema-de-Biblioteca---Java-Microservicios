package pe.biblioteca.notificaciones.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.biblioteca.notificaciones.dto.NotificacionRequest;
import pe.biblioteca.notificaciones.entity.Notificacion;
import pe.biblioteca.notificaciones.repository.NotificacionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public Notificacion enviar(NotificacionRequest req) {
        String canal = req.getCanal() != null ? req.getCanal() : "EMAIL";
        log.info("[NOTIFICACION SIMULADA][{}] -> {} : {}", canal, req.getDestino(), req.getMensaje());

        Notificacion n = new Notificacion(
                null,
                req.getDestino(),
                req.getMensaje(),
                canal,
                "ENVIADO",
                LocalDateTime.now(),
                null,
                null
        );
        return notificacionRepository.save(n);
    }

    public List<Notificacion> listar() {
        return notificacionRepository.findAll();
    }
}
