package pe.biblioteca.libros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.biblioteca.libros.entity.Socio;

public interface SocioRepository extends JpaRepository<Socio, String> {
}
