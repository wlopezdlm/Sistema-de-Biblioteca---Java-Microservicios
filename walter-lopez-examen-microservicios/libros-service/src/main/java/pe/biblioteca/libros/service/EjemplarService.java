package pe.biblioteca.libros.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.biblioteca.libros.entity.Ejemplar;
import pe.biblioteca.libros.exception.EjemplarNoEncontradoException;
import pe.biblioteca.libros.repository.EjemplarRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EjemplarService {

    private final EjemplarRepository ejemplarRepository;

    public List<Ejemplar> listar() {
        return ejemplarRepository.findAll();
    }

    public Ejemplar buscarPorCodigo(String codigo) {
        return ejemplarRepository.findById(codigo)
                .orElseThrow(() -> new EjemplarNoEncontradoException(codigo));
    }

    public Ejemplar crear(Ejemplar ejemplar) {
        return ejemplarRepository.save(ejemplar);
    }

    public Ejemplar editar(String codigo, Ejemplar datos) {
        Ejemplar existente = buscarPorCodigo(codigo);
        existente.setTitulo(datos.getTitulo());
        existente.setAutor(datos.getAutor());
        existente.setIsbn(datos.getIsbn());
        existente.setAnioPublicacion(datos.getAnioPublicacion());
        existente.setDisponible(datos.isDisponible());
        return ejemplarRepository.save(existente);
    }

    public void eliminar(String codigo) {
        buscarPorCodigo(codigo);
        ejemplarRepository.deleteById(codigo);
    }

    public Ejemplar cambiarDisponibilidad(String codigo, boolean disponible) {
        Ejemplar ejemplar = buscarPorCodigo(codigo);
        ejemplar.setDisponible(disponible);
        return ejemplarRepository.save(ejemplar);
    }
}
