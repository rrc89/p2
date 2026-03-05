package edu.comillas.icai.git.pat.spring.p2.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrito{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrito;

    private Long idUsuario;
    private String correo;
    private Double totalPrecio = 0.0;

    // tenemos una relacion 1 a n - un carrito tiene muchas lineas
    // cascadetype.all es porque si guardas o borras el carrito, se guardan o borran todas sus lineas (en cascada)

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineaCarrito> lineas = new ArrayList<>();

    // jpa requiere un constructor vacio
    public Carrito(){}

    public Carrito(Long idUsuario, String correoUsuario) {
        this.idUsuario = idUsuario;
        this.correo = correo;
        this.totalPrecio = 0.0;
        this.lineas = new ArrayList<>();
    }

    // getters y setters
    public Long getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(Long idCarrito) {
        this.idCarrito = idCarrito;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Double getTotalPrecio() {
        return totalPrecio;
    }

    public void setTotalPrecio(Double totalPrecio) {
        this.totalPrecio = totalPrecio;
    }

    public List<LineaCarrito> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaCarrito> lineas) {
        this.lineas = lineas;
    }

    public void calcularTotal(){
        this.totalPrecio = this.lineas.stream()
                .mapToDouble(LineaCarrito::getCosteLinea)
                .sum();
    }
}
