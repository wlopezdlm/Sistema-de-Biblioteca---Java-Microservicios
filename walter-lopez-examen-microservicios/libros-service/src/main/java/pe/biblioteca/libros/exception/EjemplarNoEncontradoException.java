package pe.biblioteca.libros.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EjemplarNoEncontradoException extends RuntimeException {
    public EjemplarNoEncontradoException(String codigo) {
        super("Ejemplar no encontrado: " + codigo);
    }
}
