package pe.biblioteca.libros.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.biblioteca.libros.entity.Socio;
import pe.biblioteca.libros.service.SocioService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/socios")
@RequiredArgsConstructor
public class SocioController {

    private final SocioService socioService;

    @PostMapping
    public ResponseEntity<Socio> crear(@RequestBody Socio socio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(socioService.crear(socio));
    }

    @GetMapping
    public List<Socio> listar() {
        return socioService.listar();
    }

    @GetMapping("/{codigoSocio}")
    public ResponseEntity<Socio> buscar(@PathVariable String codigoSocio) {
        return ResponseEntity.ok(socioService.buscarPorCodigo(codigoSocio));
    }

    @PutMapping("/{codigoSocio}")
    public ResponseEntity<Socio> editar(@PathVariable String codigoSocio, @RequestBody Socio datos) {
        return ResponseEntity.ok(socioService.editar(codigoSocio, datos));
    }

    @DeleteMapping("/{codigoSocio}")
    public ResponseEntity<Void> eliminar(@PathVariable String codigoSocio) {
        socioService.eliminar(codigoSocio);
        return ResponseEntity.noContent().build();
    }
}
