package pe.biblioteca.prestamos.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import pe.biblioteca.prestamos.dto.EjemplarResponse;
import pe.biblioteca.prestamos.dto.SocioResponse;

import java.util.Map;

@Slf4j
@Component
public class LibrosClient {

    private final RestClient restClient;

    public LibrosClient(RestClient.Builder builder) {
        this.restClient = builder.clone()
                .baseUrl("http://libros-service")
                .build();
    }

    public SocioResponse consultarSocio(String codigoSocio) {
        try {
            return restClient.get()
                    .uri("/api/v1/socios/{codigo}", codigoSocio)
                    .retrieve()
                    .body(SocioResponse.class);
        } catch (Exception e) {
            log.warn("No se pudo consultar socio {}: {}", codigoSocio, e.getMessage());
            return null;
        }
    }

    public EjemplarResponse consultarEjemplar(String codigoEjemplar) {
        try {
            return restClient.get()
                    .uri("/api/v1/libros/{codigo}", codigoEjemplar)
                    .retrieve()
                    .body(EjemplarResponse.class);
        } catch (Exception e) {
            log.warn("No se pudo consultar ejemplar {}: {}", codigoEjemplar, e.getMessage());
            return null;
        }
    }

    public void cambiarDisponibilidad(String codigoEjemplar, boolean disponible) {
        try {
            restClient.patch()
                    .uri("/api/v1/libros/{codigo}/disponibilidad", codigoEjemplar)
                    .body(Map.of("disponible", disponible))
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception e) {
            log.error("Error al cambiar disponibilidad del ejemplar {}: {}", codigoEjemplar, e.getMessage());
        }
    }
}
