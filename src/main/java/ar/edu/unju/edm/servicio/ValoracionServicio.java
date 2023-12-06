package ar.edu.unju.edm.servicio;

import ar.edu.unju.edm.modelo.Valoracion;

import java.util.Optional;

public interface ValoracionServicio {
    void agregarValoracion(Valoracion valoracion);

    Optional<Valoracion> buscarValoracionPorId(Long id);
}
