package pe.biblioteca.prestamos.factory;

// [Patrón: Factory Method]
public class MensajeNotificacionFactory {

    public static String crearMensaje(String estado, String codigoEjemplar, String nombreSocio) {
        return switch (estado) {
            case "REGISTRADA" -> "Hola " + nombreSocio + ", tu préstamo del ejemplar "
                    + codigoEjemplar + " ha sido registrado exitosamente.";
            case "RECHAZADA" -> "Hola " + nombreSocio + ", lamentablemente no se pudo registrar "
                    + "el préstamo del ejemplar " + codigoEjemplar + ".";
            case "DEVUELTO" -> "Hola " + nombreSocio + ", la devolución del ejemplar "
                    + codigoEjemplar + " ha sido registrada. ¡Gracias!";
            default -> "Notificación sobre el ejemplar " + codigoEjemplar + ".";
        };
    }
}
