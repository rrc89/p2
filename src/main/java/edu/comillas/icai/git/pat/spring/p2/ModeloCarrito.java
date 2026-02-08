package edu.comillas.icai.git.pat.spring.p2;

import org.jspecify.annotations.NonNull;

//package com.example.practica2.repository;

//import com.example.practica2.model.Carrito;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ModeloCarrito {
    private final Map<Long, Carrito> storage = new HashMap<>();
    private Long idSequence = 1L;

    public List<Carrito> findAll() {
        return new ArrayList<>(storage.values());
    }

    public Optional<Carrito> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public Carrito save(Carrito carrito) {
        if (carrito.getIdCarrito() == null) {
            carrito.setIdCarrito(idSequence++);
        }
        storage.put(carrito.getIdCarrito(), carrito);
        return carrito;
    }

    public boolean deleteById(Long id) {
        return storage.remove(id) != null;
    }
}
