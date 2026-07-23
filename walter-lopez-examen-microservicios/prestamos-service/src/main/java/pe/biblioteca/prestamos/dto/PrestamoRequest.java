package pe.biblioteca.prestamos.dto;

import lombok.Data;

@Data
public class PrestamoRequest {
    private String codigoEjemplar;
    private String codigoSocio;
    private Integer diasPrestamo;
}
