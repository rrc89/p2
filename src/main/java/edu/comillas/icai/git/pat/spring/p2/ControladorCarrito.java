package edu.comillas.icai.git.pat.spring.p2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carritos")
public class ControladorCarrito {

    private final ModeloCarrito repository;
    public ControladorCarrito(ModeloCarrito repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Carrito> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrito> obtenerPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(carrito -> ResponseEntity.ok(carrito))
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Carrito> crear(@RequestBody Carrito carrito) {
        Carrito nuevo = repository.save(carrito);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Carrito> actualizar(@PathVariable Long id, @RequestBody Carrito carrito) {
        return repository.findById(id)
                .map(existente -> {
                    carrito.setIdCarrito(id); // Aseguramos que el ID sea el de la URL
                    return ResponseEntity.ok(repository.save(carrito));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (repository.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}





