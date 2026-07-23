package pe.biblioteca.notificaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.biblioteca.notificaciones.entity.Notificacion;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
}
