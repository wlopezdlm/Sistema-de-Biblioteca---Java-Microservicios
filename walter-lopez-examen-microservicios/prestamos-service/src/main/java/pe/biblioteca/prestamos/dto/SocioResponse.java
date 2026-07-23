package pe.biblioteca.prestamos.dto;

import lombok.Data;

@Data
public class SocioResponse {
    private String codigoSocio;
    private String nombre;
    private String email;
    private boolean activo;
}
