package ar.edu.unju.edm.servicio.impl;

import ar.edu.unju.edm.modelo.Valoracion;
import ar.edu.unju.edm.repositorio.ValoracionRepositorio;
import ar.edu.unju.edm.servicio.ValoracionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValoracionServicioImpl implements ValoracionServicio {
    @Autowired
    ValoracionRepositorio valoracionRepositorio;

    @Override
    public void agregarValoracion(Valoracion valoracion) {
        valoracionRepositorio.save(valoracion);
    }
}
