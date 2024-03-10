package ar.edu.unju.edm.servicio;

import ar.edu.unju.edm.modelo.PuntoDeInteres;

import java.util.List;
import java.util.Optional;

public interface PuntoDeInteresServicio {
    void agregarPoi(PuntoDeInteres poi);

    List<PuntoDeInteres> buscarTodos();

    Optional<PuntoDeInteres> buscarPorId(Long id);

    void eliminar(PuntoDeInteres poi);
}
