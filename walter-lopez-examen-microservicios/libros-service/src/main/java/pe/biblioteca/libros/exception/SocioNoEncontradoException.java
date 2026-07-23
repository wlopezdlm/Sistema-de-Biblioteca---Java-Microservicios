package pe.biblioteca.libros.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SocioNoEncontradoException extends RuntimeException {
    public SocioNoEncontradoException(String codigo) {
        super("Socio no encontrado: " + codigo);
    }
}
