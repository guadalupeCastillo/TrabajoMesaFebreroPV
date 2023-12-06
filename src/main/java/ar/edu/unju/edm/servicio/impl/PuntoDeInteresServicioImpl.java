package ar.edu.unju.edm.servicio.impl;

import ar.edu.unju.edm.modelo.PuntoDeInteres;
import ar.edu.unju.edm.repositorio.PuntoDeInteresRepositorio;
import ar.edu.unju.edm.servicio.PuntoDeInteresServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PuntoDeInteresServicioImpl implements PuntoDeInteresServicio {
    @Autowired
    private PuntoDeInteresRepositorio puntoDeInteresRepositorio;

    @Override
    public void agregarPoi(PuntoDeInteres poi) {
        puntoDeInteresRepositorio.save(poi);
    }

    @Override
    public List<PuntoDeInteres> buscarTodos() {
        return puntoDeInteresRepositorio.findByEstadoTrue();
    }

    @Override
    public Optional<PuntoDeInteres> buscarPorId(Long id) {
        return puntoDeInteresRepositorio.findById(id);
    }

    @Override
    public void eliminar(PuntoDeInteres poi) {
        poi.setEstado(false);
        puntoDeInteresRepositorio.save(poi);
    }
}
