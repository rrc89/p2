package edu.comillas.icai.git.pat.spring.p2.servicio;


import edu.comillas.icai.git.pat.spring.p2.entity.Carrito;
import edu.comillas.icai.git.pat.spring.p2.entity.LineaCarrito;
import edu.comillas.icai.git.pat.spring.p2.repositorio.RepoCarrito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioCarrito {

    @Autowired
    private RepoCarrito repoCarrito;

    public List<Carrito> obtenerTodos(){
        List<Carrito> carritos = new ArrayList<>();
        repoCarrito.findAll().forEach(carritos::add);
        return carritos;
    }

    public Optional<Carrito> obtenerPorId(Long id){
        return repoCarrito.findById(id);
    }

    public Carrito actualizarCarrito(Long id, Carrito carritoActualizado){
        return repoCarrito.findById(id)
                .map(carrito -> {
                    // actualizo datos del carrito, no las lineas
                    carrito.setIdUsuario(carritoActualizado.getIdUsuario());
                    carrito.setCorreo(carritoActualizado.getCorreo());
                    return repoCarrito.save(carrito);
                })
                .orElseThrow(() -> new RuntimeException("no se ha encontrado el carrito"));
    }

    public boolean eliminarCarrito(Long id){
        if(repoCarrito.existsById(id)){
            repoCarrito.deleteById(id);
            return true;
        }
        return false;
    }


    //metodo auxiliar para crear un carrito (esto es para probar)
    public Carrito crearCarrito(Carrito carrito){
        return repoCarrito.save(carrito);
    }

    @Transactional
    public Carrito anadirLinea(Long idCarrito, LineaCarrito linea){
        Carrito carrito = repoCarrito.findById(idCarrito)
                .orElseThrow(() -> new RuntimeException("No se ha encontrado el carrito"));
        linea.calcularCoste(); // con esto calculamos el coste (precio * uds)
        linea.setCarrito(carrito); // para establecer realcion bi-direccional

        carrito.getLineas().add(linea);
        carrito.calcularTotal(); // recalculo el coste total del carrito

        // como he puesto cascadetype.all, al guardar el carrito, se guarda la linea automaticamente
        return repoCarrito.save(carrito);
    }

    @Transactional
    public Carrito borrarLinea(Long idCarrito, Long idArticulo){
        Carrito carrito = repoCarrito.findById(idCarrito)
                .orElseThrow(() -> new RuntimeException("no se ha encontrado el carrito"));

        // busco la linea que coincide con el id de articulo especificado y la elimino
        boolean eliminada = carrito.getLineas().removeIf(l -> l.getIdArticulo().equals(idArticulo));

        if(eliminada){
            carrito.calcularTotal(); // recalculo el total del carrito despues de haber borrado la linea
            return repoCarrito.save(carrito); // guardo el carrito actualizado
        }
        return carrito;
    }

}