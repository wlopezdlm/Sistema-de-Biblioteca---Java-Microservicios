package pe.biblioteca.libros.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.biblioteca.libros.entity.Socio;
import pe.biblioteca.libros.exception.SocioNoEncontradoException;
import pe.biblioteca.libros.repository.SocioRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocioService {

    private final SocioRepository socioRepository;

    public List<Socio> listar() {
        return socioRepository.findAll();
    }

    public Socio buscarPorCodigo(String codigo) {
        return socioRepository.findById(codigo)
                .orElseThrow(() -> new SocioNoEncontradoException(codigo));
    }

    public Socio crear(Socio socio) {
        return socioRepository.save(socio);
    }

    public Socio editar(String codigo, Socio datos) {
        Socio existente = buscarPorCodigo(codigo);
        existente.setNombre(datos.getNombre());
        existente.setEmail(datos.getEmail());
        existente.setTelefono(datos.getTelefono());
        existente.setActivo(datos.isActivo());
        return socioRepository.save(existente);
    }

    public void eliminar(String codigo) {
        buscarPorCodigo(codigo);
        socioRepository.deleteById(codigo);
    }
}
