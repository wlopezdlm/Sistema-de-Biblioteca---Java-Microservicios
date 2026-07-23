package pe.biblioteca.prestamos.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class PrestamoResponse {

    private Long id;
    private String codigoEjemplar;
    private String codigoSocio;
    private String estado;
    private String motivoRechazo;
    private LocalDateTime fechaPrestamo;
    private LocalDate fechaDevolucionEsperada;
    private LocalDateTime fechaDevolucionReal;
    private String observaciones;

    private PrestamoResponse() {}


    public String getEstado() { return estado; }
    public String getMotivoRechazo() { return motivoRechazo; }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final PrestamoResponse response = new PrestamoResponse();

        public Builder id(Long id) { response.id = id; return this; }
        public Builder codigoEjemplar(String v) { response.codigoEjemplar = v; return this; }
        public Builder codigoSocio(String v) { response.codigoSocio = v; return this; }
        public Builder estado(String v) { response.estado = v; return this; }
        public Builder motivoRechazo(String v) { response.motivoRechazo = v; return this; }
        public Builder fechaPrestamo(LocalDateTime v) { response.fechaPrestamo = v; return this; }
        public Builder fechaDevolucionEsperada(LocalDate v) { response.fechaDevolucionEsperada = v; return this; }
        public Builder fechaDevolucionReal(LocalDateTime v) { response.fechaDevolucionReal = v; return this; }


        public PrestamoResponse build() { return response; }
    }
}
