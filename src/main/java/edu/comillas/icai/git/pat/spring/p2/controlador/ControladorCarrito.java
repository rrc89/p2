package edu.comillas.icai.git.pat.spring.p2.controlador;

import edu.comillas.icai.git.pat.spring.p2.entity.Carrito;
import edu.comillas.icai.git.pat.spring.p2.entity.LineaCarrito;
import edu.comillas.icai.git.pat.spring.p2.entity.ModeloCarrito;
import edu.comillas.icai.git.pat.spring.p2.servicio.ServicioCarrito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/carritos")
public class ControladorCarrito {

    private final ServicioCarrito servicioCarrito;
    public ControladorCarrito(ServicioCarrito servicioCarrito) {
        this.servicioCarrito = servicioCarrito;
    }

    // endpoints basicos

    @GetMapping
    public List<Carrito> listarTodos() {
        return servicioCarrito.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrito> obtenerPorId(@PathVariable Long id) {
        return servicioCarrito.obtenerPorId(id)
                .map(carrito -> ResponseEntity.ok(carrito))
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Carrito> crear(@RequestBody Carrito carrito) {
        Carrito nuevo = servicioCarrito.crearCarrito(carrito);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Carrito> actualizar(@PathVariable Long id, @RequestBody Carrito carrito) {
         try{
             Carrito actualizado = servicioCarrito.actualizarCarrito(id, carrito);
             return ResponseEntity.ok(actualizado);
         } catch (RuntimeException e){
             return ResponseEntity.notFound().build();
         }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (servicioCarrito.eliminarCarrito(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // endpoints linea carrito
    @PostMapping("/{idCarrito}/lineas")
    public ResponseEntity<Carrito> anadirLinea(@PathVariable Long idCarrito, @RequestBody LineaCarrito linea){
        try{
            Carrito carritoActualizado = servicioCarrito.anadirLinea(idCarrito, linea);
            return ResponseEntity.ok(carritoActualizado);
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idCarrito}/lineas/{idArticulo}")
    public ResponseEntity<Carrito> borrarLinea(@PathVariable Long idCarrito, @PathVariable Long idArticulo){
        try{
            Carrito carritoActualizado = servicioCarrito.borrarLinea(idCarrito, idArticulo);
            return ResponseEntity.ok(carritoActualizado);
        } catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
}





