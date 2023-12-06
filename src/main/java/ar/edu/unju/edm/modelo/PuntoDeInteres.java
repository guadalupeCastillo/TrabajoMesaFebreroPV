package ar.edu.unju.edm.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.OptionalDouble;

@Entity
public class PuntoDeInteres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poi_id")
    private Long id;

    @NotBlank
    private String nombre;

    @Column(columnDefinition = "TEXT")
    @Lob
    private String descripcion;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String imagen;

    @ManyToOne
    private Usuario creador;

    @JsonIgnore
    @OneToMany(mappedBy = "poi", cascade = CascadeType.REMOVE)
    private List<Valoracion> valoraciones;

    private boolean estado = true;

    public OptionalDouble getValoracion() {
        return valoraciones.stream().filter(Valoracion::isEstado).mapToDouble(v -> (double) v.getValor()).average();
    }

    public PuntoDeInteres() {
    }

    public PuntoDeInteres(Long id, String nombre, String descripcion, String imagen, Usuario creador, List<Valoracion> valoraciones) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.creador = creador;
        this.valoraciones = valoraciones;
    }

    public PuntoDeInteres(String nombre, String descripcion, String imagen, Usuario creador, List<Valoracion> valoraciones) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.creador = creador;
        this.valoraciones = valoraciones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public List<Valoracion> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(List<Valoracion> valoraciones) {
        this.valoraciones = valoraciones;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "PuntoDeInteres{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", imagen='" + imagen + '\'' +
                ", creador=" + creador +
                '}';
    }
}
