package ar.edu.unju.edm.modelo;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Valoracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "valoracion_id")
    private Long id;

    @ManyToOne
//    @NotNull
    private Usuario creador;

    @ManyToOne
//    @NotNull
    private PuntoDeInteres poi;

    @NotNull
    @Positive
    @Max(value = 5)
    private Integer valoracion;

    @Column(columnDefinition = "TEXT")
    @Lob
    private String descripcion;

    boolean estado = true;

    public Valoracion() {
    }

    public Valoracion(Long id, Usuario creador, PuntoDeInteres poi, Integer valoracion, String descripcion) {
        this.id = id;
        this.creador = creador;
        this.poi = poi;
        this.valoracion = valoracion;
        this.descripcion = descripcion;
    }

    public Valoracion(Usuario creador, PuntoDeInteres poi, Integer valoracion, String descripcion) {
        this.creador = creador;
        this.poi = poi;
        this.valoracion = valoracion;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public PuntoDeInteres getPoi() {
        return poi;
    }

    public void setPoi(PuntoDeInteres poi) {
        this.poi = poi;
    }

    public Integer getValoracion() {
        return valoracion;
    }

    public void setValoracion(Integer valoracion) {
        this.valoracion = valoracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Valoracion{" +
                "id=" + id +
                ", creador=" + creador +
                ", poi=" + poi +
                ", valoracion=" + valoracion +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
