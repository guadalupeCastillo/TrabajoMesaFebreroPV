package ar.edu.unju.edm.servicio.impl;

import ar.edu.unju.edm.modelo.Valoracion;
import ar.edu.unju.edm.repositorio.ValoracionRepositorio;
import ar.edu.unju.edm.servicio.ValoracionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ValoracionServicioImpl implements ValoracionServicio {
    @Autowired
    ValoracionRepositorio valoracionRepositorio;

    @Override
    public void agregarValoracion(Valoracion valoracion) {
        valoracionRepositorio.save(valoracion);
    }

    @Override
    public Optional<Valoracion> buscarValoracionPorId(Long id) {
        return valoracionRepositorio.findById(id);
    }

    @Override
    public List<Valoracion> buscarTodos() {
        return valoracionRepositorio.findByEstadoTrue();
    }

    @Override
    public void eliminar(Valoracion valoracion) {
        valoracion.setEstado(false);
        valoracionRepositorio.save(valoracion);
    }
}
