package pe.biblioteca.prestamos.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import pe.biblioteca.prestamos.dto.NotificacionRequest;

@Slf4j
@Component
public class NotificacionesClient {

    private final RestClient restClient;

    public NotificacionesClient(RestClient.Builder builder) {
        this.restClient = builder.clone()
                .baseUrl("http://notificaciones-service")
                .build();
    }

    public void notificar(String destino, String mensaje) {
        try {
            restClient.post()
                    .uri("/api/v1/notificaciones")
                    .body(new NotificacionRequest(destino, mensaje, "EMAIL"))
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception e) {
            log.warn("No se pudo enviar notificacion a {}: {}", destino, e.getMessage());
        }
    }
}
