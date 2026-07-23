package pe.biblioteca.prestamos.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.biblioteca.prestamos.dto.PrestamoRequest;
import pe.biblioteca.prestamos.dto.PrestamoResponse;
import pe.biblioteca.prestamos.entity.Prestamo;
import pe.biblioteca.prestamos.service.PrestamoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/prestamos")
@RequiredArgsConstructor
public class PrestamoController {

    private final PrestamoService prestamoService;

    @PostMapping
    public ResponseEntity<PrestamoResponse> registrar(@RequestBody PrestamoRequest req) {
        return ResponseEntity.ok(prestamoService.registrar(req));
    }

    @GetMapping
    public List<Prestamo> listar() {
        return prestamoService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.buscarPorId(id));
    }

    @PostMapping("/{id}/devolucion")
    public ResponseEntity<PrestamoResponse> devolucion(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.registrarDevolucion(id));
    }
}
