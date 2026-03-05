package edu.comillas.icai.git.pat.spring.p2.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.comillas.icai.git.pat.spring.p2.entity.Carrito;
import jakarta.persistence.*;

@Entity
public class LineaCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLinea; //esta es la clave privada de la linea

    // contamos con una relacion n a 1 con carrito
    @ManyToOne
    @JoinColumn(name = "idCarrito_fk")
    @JsonIgnore // esto evita bucles infinitos
    private Carrito carrito;

    private Long idArticulo;
    private Double precioUnitario;
    private Integer numeroUnidades;
    private Double costeLinea;

    // constructor vacio - para jpa
    public LineaCarrito(){}

    // esta operacion calcula el coste directamente al asignar precio o unidades
    public void calcularCoste(){
        if(this.precioUnitario != null && this.numeroUnidades != null){
            this.costeLinea = this.precioUnitario * this.numeroUnidades;
        }
    }

    // constructor con parametros
    public LineaCarrito(Long idArticulo, Double precioUnitario, Integer numeroUnidades) {
        this.idArticulo = idArticulo;
        this.precioUnitario = precioUnitario;
        this.numeroUnidades = numeroUnidades;
        this.calcularCoste(); // calculo coste incial
    }

    // getters + setters
    public Long getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(Long idLinea) {
        this.idLinea = idLinea;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public Long getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Long idArticulo) {
        this.idArticulo = idArticulo;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
        this.calcularCoste(); // recalcula si precio cambia
    }

    public Integer getNumeroUnidades() {
        return numeroUnidades;
    }

    public void setNumeroUnidades(Integer numeroUnidades) {
        this.numeroUnidades = numeroUnidades;
        this.calcularCoste(); // recalcula si unidades cambian
    }

    public Double getCosteLinea() {
        return costeLinea;
    }

    public void setCosteLinea(Double costeLinea) {
        this.costeLinea = costeLinea;
    }

}


