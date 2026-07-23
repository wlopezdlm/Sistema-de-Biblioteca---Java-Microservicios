package pe.biblioteca.libros;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pe.biblioteca.libros.entity.Ejemplar;
import pe.biblioteca.libros.entity.Socio;
import pe.biblioteca.libros.repository.EjemplarRepository;
import pe.biblioteca.libros.repository.SocioRepository;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class CargaInicial implements CommandLineRunner {

    private final EjemplarRepository ejemplarRepository;
    private final SocioRepository socioRepository;

    @Override
    public void run(String... args) {
        if (ejemplarRepository.count() == 0) {
            ejemplarRepository.save(new Ejemplar("BIB-0001", "Cien años de soledad", "Gabriel García Márquez", "978-0060883287", 1967, true, null, null));
            ejemplarRepository.save(new Ejemplar("BIB-0002", "El señor de los anillos", "J.R.R. Tolkien", "978-0261102385", 1954, true, null, null));
            ejemplarRepository.save(new Ejemplar("BIB-0003", "1984", "George Orwell", "978-0451524935", 1949, false, null, null));
            log.info("Ejemplares iniciales creados (3).");
        }
        if (socioRepository.count() == 0) {
            socioRepository.save(new Socio("S001", "Ana Torres", "ana.torres@email.com", "999111222", LocalDate.of(2024, 1, 10), true, null, null));
            socioRepository.save(new Socio("S002", "Carlos Ruiz", "carlos.ruiz@email.com", "999333444", LocalDate.of(2024, 3, 5), false, null, null));
            log.info("Socios iniciales creados (2).");
        }
    }
}
