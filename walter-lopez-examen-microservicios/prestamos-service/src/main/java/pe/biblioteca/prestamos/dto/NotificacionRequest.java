package pe.biblioteca.prestamos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionRequest {
    private String destino;
    private String mensaje;
    private String canal;
}
