package pe.biblioteca.notificaciones.dto;

import lombok.Data;

@Data
public class NotificacionRequest {
    private String destino;
    private String mensaje;
    private String canal;
}
