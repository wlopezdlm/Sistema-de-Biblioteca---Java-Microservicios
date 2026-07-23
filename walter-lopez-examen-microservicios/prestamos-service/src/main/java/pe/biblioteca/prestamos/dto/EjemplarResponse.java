package pe.biblioteca.prestamos.dto;

import lombok.Data;

@Data
public class EjemplarResponse {
    private String codigoEjemplar;
    private String titulo;
    private String autor;
    private boolean disponible;
}
