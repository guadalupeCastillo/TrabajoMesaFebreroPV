package ar.edu.unju.edm.repositorio;

import ar.edu.unju.edm.modelo.PuntoDeInteres;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PuntoDeInteresRepositorio extends CrudRepository<PuntoDeInteres, Long> {
    List<PuntoDeInteres> findByEstadoTrue();
}
