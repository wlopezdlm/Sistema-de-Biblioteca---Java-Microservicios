package pe.biblioteca.prestamos.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.biblioteca.prestamos.client.LibrosClient;
import pe.biblioteca.prestamos.client.NotificacionesClient;
import pe.biblioteca.prestamos.dto.EjemplarResponse;
import pe.biblioteca.prestamos.dto.PrestamoRequest;
import pe.biblioteca.prestamos.dto.PrestamoResponse;
import pe.biblioteca.prestamos.dto.SocioResponse;
import pe.biblioteca.prestamos.entity.Prestamo;
import pe.biblioteca.prestamos.repository.PrestamoRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrestamoServiceTest {

    @Mock private PrestamoRepository prestamoRepository;
    @Mock private LibrosClient librosClient;
    @Mock private NotificacionesClient notificacionesClient;

    @InjectMocks private PrestamoService prestamoService;

    private PrestamoRequest crearRequest(String codigoEjemplar, String codigoSocio) {
        PrestamoRequest req = new PrestamoRequest();
        req.setCodigoEjemplar(codigoEjemplar);
        req.setCodigoSocio(codigoSocio);
        req.setDiasPrestamo(7);
        return req;
    }

    @Test
    void registrar_exitoso_retornaREGISTRADA() {
        SocioResponse socio = new SocioResponse();
        socio.setCodigoSocio("S001"); socio.setNombre("Ana"); socio.setEmail("ana@email.com"); socio.setActivo(true);

        EjemplarResponse ejemplar = new EjemplarResponse();
        ejemplar.setCodigoEjemplar("BIB-0001"); ejemplar.setDisponible(true);

        Prestamo guardado = new Prestamo(1L, "BIB-0001", "S001", LocalDateTime.now(), null, null, "REGISTRADA", null, null, null, null);

        when(librosClient.consultarSocio("S001")).thenReturn(socio);
        when(librosClient.consultarEjemplar("BIB-0001")).thenReturn(ejemplar);
        when(prestamoRepository.save(any())).thenReturn(guardado);

        PrestamoResponse resp = prestamoService.registrar(crearRequest("BIB-0001", "S001"));

        assertThat(resp.getEstado()).isEqualTo("REGISTRADA");
        verify(librosClient).cambiarDisponibilidad("BIB-0001", false);
        verify(notificacionesClient).notificar(anyString(), anyString());
    }

    @Test
    void registrar_socioInactivo_retornaRECHAZADA() {
        SocioResponse socio = new SocioResponse();
        socio.setActivo(false);
        when(librosClient.consultarSocio("S002")).thenReturn(socio);
        when(prestamoRepository.save(any())).thenReturn(new Prestamo(2L, "BIB-0001", "S002", LocalDateTime.now(), null, null, "RECHAZADA", "Socio inactivo", null, null, null));

        PrestamoResponse resp = prestamoService.registrar(crearRequest("BIB-0001", "S002"));

        assertThat(resp.getEstado()).isEqualTo("RECHAZADA");
        assertThat(resp.getMotivoRechazo()).isEqualTo("Socio inactivo");
        verify(notificacionesClient, never()).notificar(anyString(), anyString());
    }

    @Test
    void registrar_ejemplarNoDisponible_retornaRECHAZADA() {
        SocioResponse socio = new SocioResponse();
        socio.setActivo(true); socio.setNombre("Ana");
        EjemplarResponse ejemplar = new EjemplarResponse();
        ejemplar.setDisponible(false);

        when(librosClient.consultarSocio("S001")).thenReturn(socio);
        when(librosClient.consultarEjemplar("BIB-0003")).thenReturn(ejemplar);
        when(prestamoRepository.save(any())).thenReturn(new Prestamo(3L, "BIB-0003", "S001", LocalDateTime.now(), null, null, "RECHAZADA", "No disponible", null, null, null));

        PrestamoResponse resp = prestamoService.registrar(crearRequest("BIB-0003", "S001"));

        assertThat(resp.getEstado()).isEqualTo("RECHAZADA");
        assertThat(resp.getMotivoRechazo()).isEqualTo("No disponible");
    }

    @Test
    void registrar_ejemplarInexistente_retornaRECHAZADA() {
        SocioResponse socio = new SocioResponse();
        socio.setActivo(true); socio.setNombre("Ana");
        when(librosClient.consultarSocio("S001")).thenReturn(socio);
        when(librosClient.consultarEjemplar("NOEXI")).thenReturn(null);
        when(prestamoRepository.save(any())).thenReturn(new Prestamo(4L, "NOEXI", "S001", LocalDateTime.now(), null, null, "RECHAZADA", "Ejemplar no existe", null, null, null));

        PrestamoResponse resp = prestamoService.registrar(crearRequest("NOEXI", "S001"));

        assertThat(resp.getEstado()).isEqualTo("RECHAZADA");
        assertThat(resp.getMotivoRechazo()).isEqualTo("Ejemplar no existe");
    }

    @Test
    void devolucion_exitosa_retornaDEVUELTO() {
        Prestamo prestamo = new Prestamo(1L, "BIB-0001", "S001", LocalDateTime.now(), null, null, "REGISTRADA", null, null, null, null);
        Prestamo devuelto = new Prestamo(1L, "BIB-0001", "S001", LocalDateTime.now(), null, LocalDateTime.now(), "DEVUELTO", null, null, null, null);

        when(prestamoRepository.findById(1L)).thenReturn(Optional.of(prestamo));
        when(prestamoRepository.save(any())).thenReturn(devuelto);

        PrestamoResponse resp = prestamoService.registrarDevolucion(1L);

        assertThat(resp.getEstado()).isEqualTo("DEVUELTO");
        verify(librosClient).cambiarDisponibilidad("BIB-0001", true);
    }

    @Test
    void devolucion_duplicada_lanzaConflict() {
        Prestamo prestamo = new Prestamo(1L, "BIB-0001", "S001", LocalDateTime.now(), null, LocalDateTime.now(), "DEVUELTO", null, null, null, null);
        when(prestamoRepository.findById(1L)).thenReturn(Optional.of(prestamo));

        assertThatThrownBy(() -> prestamoService.registrarDevolucion(1L))
                .hasMessageContaining("ya fue devuelto");
    }
}
