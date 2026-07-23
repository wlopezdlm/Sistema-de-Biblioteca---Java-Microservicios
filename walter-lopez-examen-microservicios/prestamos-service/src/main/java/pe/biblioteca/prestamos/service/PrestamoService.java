package pe.biblioteca.prestamos.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pe.biblioteca.prestamos.client.LibrosClient;
import pe.biblioteca.prestamos.client.NotificacionesClient;
import pe.biblioteca.prestamos.dto.EjemplarResponse;
import pe.biblioteca.prestamos.dto.PrestamoRequest;
import pe.biblioteca.prestamos.dto.PrestamoResponse;
import pe.biblioteca.prestamos.dto.SocioResponse;
import pe.biblioteca.prestamos.entity.Prestamo;
import pe.biblioteca.prestamos.factory.MensajeNotificacionFactory;
import pe.biblioteca.prestamos.repository.PrestamoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final LibrosClient librosClient;
    private final NotificacionesClient notificacionesClient;

    public PrestamoResponse registrar(PrestamoRequest req) {
        int dias = req.getDiasPrestamo() != null ? req.getDiasPrestamo() : 7;

        // 1) Validar socio
        SocioResponse socio = librosClient.consultarSocio(req.getCodigoSocio());
        if (socio == null) {
            return rechazar(req, "Socio no existe");
        }
        if (!socio.isActivo()) {
            return rechazar(req, "Socio inactivo");
        }

        // 2) Validar ejemplar
        EjemplarResponse ejemplar = librosClient.consultarEjemplar(req.getCodigoEjemplar());
        if (ejemplar == null) {
            return rechazar(req, "Ejemplar no existe");
        }
        if (!ejemplar.isDisponible()) {
            return rechazar(req, "No disponible");
        }

        // 3) Marcar ejemplar como no disponible
        librosClient.cambiarDisponibilidad(req.getCodigoEjemplar(), false);

        // 4) Persistir préstamo
        Prestamo prestamo = new Prestamo(
                null,
                req.getCodigoEjemplar(),
                req.getCodigoSocio(),
                LocalDateTime.now(),
                LocalDate.now().plusDays(dias),
                null,
                "REGISTRADA",
                null,
                null,
                null,
                null
        );
        Prestamo guardado = prestamoRepository.save(prestamo);

        // 5) Notificar usando Factory Method para armar el mensaje
        String mensaje = MensajeNotificacionFactory.crearMensaje("REGISTRADA", req.getCodigoEjemplar(), socio.getNombre());
        notificacionesClient.notificar(socio.getEmail(), mensaje);

        // [Patrón: Builder] construye la respuesta para el camino exitoso
        return PrestamoResponse.builder()
                .id(guardado.getId())
                .codigoEjemplar(guardado.getCodigoEjemplar())
                .codigoSocio(guardado.getCodigoSocio())
                .estado(guardado.getEstado())
                .fechaPrestamo(guardado.getFechaPrestamo())
                .fechaDevolucionEsperada(guardado.getFechaDevolucionEsperada())
                .build();
    }

    public List<Prestamo> listar() {
        return prestamoRepository.findAll();
    }

    public Prestamo buscarPorId(Long id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Préstamo no encontrado: " + id));
    }

    public PrestamoResponse registrarDevolucion(Long id) {
        Prestamo prestamo = buscarPorId(id);

        if ("DEVUELTO".equals(prestamo.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El préstamo ya fue devuelto");
        }

        prestamo.setEstado("DEVUELTO");
        prestamo.setFechaDevolucionReal(LocalDateTime.now());
        Prestamo guardado = prestamoRepository.save(prestamo);

        librosClient.cambiarDisponibilidad(prestamo.getCodigoEjemplar(), true);

        // [Patrón: Builder] construye la respuesta para el camino de devolución
        return PrestamoResponse.builder()
                .id(guardado.getId())
                .codigoEjemplar(guardado.getCodigoEjemplar())
                .codigoSocio(guardado.getCodigoSocio())
                .estado(guardado.getEstado())
                .fechaPrestamo(guardado.getFechaPrestamo())
                .fechaDevolucionReal(guardado.getFechaDevolucionReal())
                .build();
    }

    private PrestamoResponse rechazar(PrestamoRequest req, String motivo) {
        log.warn("Préstamo RECHAZADO ejemplar={}, socio={}, motivo={}",
                req.getCodigoEjemplar(), req.getCodigoSocio(), motivo);

        Prestamo rechazado = new Prestamo(
                null,
                req.getCodigoEjemplar(),
                req.getCodigoSocio(),
                LocalDateTime.now(),
                null,
                null,
                "RECHAZADA",
                motivo,
                null,
                null,
                null
        );
        Prestamo guardado = prestamoRepository.save(rechazado);

        // [Patrón: Builder] construye la respuesta para el camino de rechazo
        return PrestamoResponse.builder()
                .id(guardado.getId())
                .codigoEjemplar(req.getCodigoEjemplar())
                .codigoSocio(req.getCodigoSocio())
                .estado("RECHAZADA")
                .motivoRechazo(motivo)
                .build();
    }
}
