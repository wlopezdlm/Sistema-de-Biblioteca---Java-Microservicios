package pe.biblioteca.libros.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.biblioteca.libros.entity.Ejemplar;
import pe.biblioteca.libros.exception.EjemplarNoEncontradoException;
import pe.biblioteca.libros.repository.EjemplarRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EjemplarServiceTest {

    @Mock
    private EjemplarRepository ejemplarRepository;

    @InjectMocks
    private EjemplarService ejemplarService;

    @Test
    void buscarPorCodigo_existente_retornaEjemplar() {
        Ejemplar ejemplar = new Ejemplar("BIB-0001", "Cien años de soledad", "García Márquez", "isbn", 1967, true, null, null);
        when(ejemplarRepository.findById("BIB-0001")).thenReturn(Optional.of(ejemplar));

        Ejemplar resultado = ejemplarService.buscarPorCodigo("BIB-0001");

        assertThat(resultado.getCodigoEjemplar()).isEqualTo("BIB-0001");
        assertThat(resultado.getTitulo()).isEqualTo("Cien años de soledad");
    }

    @Test
    void buscarPorCodigo_inexistente_lanzaExcepcion() {
        when(ejemplarRepository.findById("NOEXI")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ejemplarService.buscarPorCodigo("NOEXI"))
                .isInstanceOf(EjemplarNoEncontradoException.class)
                .hasMessageContaining("NOEXI");
    }
}
