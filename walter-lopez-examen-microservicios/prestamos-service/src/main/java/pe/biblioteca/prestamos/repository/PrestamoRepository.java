package pe.biblioteca.prestamos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.biblioteca.prestamos.entity.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
}
