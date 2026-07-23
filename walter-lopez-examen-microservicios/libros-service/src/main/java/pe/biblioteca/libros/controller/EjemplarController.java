package pe.biblioteca.libros.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.biblioteca.libros.entity.Ejemplar;
import pe.biblioteca.libros.service.EjemplarService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/libros")
@RequiredArgsConstructor
public class EjemplarController {

    private final EjemplarService ejemplarService;

    @PostMapping
    public ResponseEntity<Ejemplar> crear(@RequestBody Ejemplar ejemplar) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ejemplarService.crear(ejemplar));
    }

    @GetMapping
    public List<Ejemplar> listar() {
        return ejemplarService.listar();
    }

    @GetMapping("/{codigoEjemplar}")
    public ResponseEntity<Ejemplar> buscar(@PathVariable String codigoEjemplar) {
        return ResponseEntity.ok(ejemplarService.buscarPorCodigo(codigoEjemplar));
    }

    @PutMapping("/{codigoEjemplar}")
    public ResponseEntity<Ejemplar> editar(@PathVariable String codigoEjemplar, @RequestBody Ejemplar datos) {
        return ResponseEntity.ok(ejemplarService.editar(codigoEjemplar, datos));
    }

    @DeleteMapping("/{codigoEjemplar}")
    public ResponseEntity<Void> eliminar(@PathVariable String codigoEjemplar) {
        ejemplarService.eliminar(codigoEjemplar);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{codigoEjemplar}/disponibilidad")
    public ResponseEntity<Ejemplar> cambiarDisponibilidad(
            @PathVariable String codigoEjemplar,
            @RequestBody Map<String, Boolean> body) {
        return ResponseEntity.ok(ejemplarService.cambiarDisponibilidad(codigoEjemplar, body.get("disponible")));
    }
}
