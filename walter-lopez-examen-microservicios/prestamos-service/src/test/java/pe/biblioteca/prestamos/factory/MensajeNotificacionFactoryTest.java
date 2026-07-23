package pe.biblioteca.prestamos.factory;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MensajeNotificacionFactoryTest {

    @Test
    void crearMensaje_estadoRegistrada_contieneNombreYEjemplar() {
        String msg = MensajeNotificacionFactory.crearMensaje("REGISTRADA", "BIB-0001", "Ana");
        assertThat(msg).contains("Ana").contains("BIB-0001").contains("registrado");
    }

    @Test
    void crearMensaje_estadoRechazada_contieneMensajeNegativo() {
        String msg = MensajeNotificacionFactory.crearMensaje("RECHAZADA", "BIB-0002", "Carlos");
        assertThat(msg).contains("Carlos").contains("BIB-0002").contains("no se pudo");
    }

    @Test
    void crearMensaje_estadoDevuelto_contieneGracias() {
        String msg = MensajeNotificacionFactory.crearMensaje("DEVUELTO", "BIB-0003", "María");
        assertThat(msg).contains("María").contains("BIB-0003").contains("devolución");
    }
}
