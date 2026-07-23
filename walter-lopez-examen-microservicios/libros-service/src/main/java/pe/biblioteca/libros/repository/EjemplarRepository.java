package pe.biblioteca.libros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.biblioteca.libros.entity.Ejemplar;

public interface EjemplarRepository extends JpaRepository<Ejemplar, String> {
}
