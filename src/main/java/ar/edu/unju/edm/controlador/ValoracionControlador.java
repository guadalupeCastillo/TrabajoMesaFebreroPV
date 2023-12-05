package ar.edu.unju.edm.controlador;

import ar.edu.unju.edm.modelo.Valoracion;
import ar.edu.unju.edm.servicio.PuntoDeInteresServicio;
import ar.edu.unju.edm.servicio.UsuarioServicio;
import ar.edu.unju.edm.servicio.ValoracionServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class ValoracionControlador {
    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    PuntoDeInteresServicio puntoDeInteresServicio;

    @Autowired
    ValoracionServicio valoracionServicio;

    private final Logger logger = LoggerFactory.getLogger(ValoracionControlador.class);

    @PostMapping("/valoracion")
    @ResponseBody
    public Valoracion agregarValoracion(
            @Valid @RequestBody Valoracion valoracion,
            BindingResult result,
            @RequestParam("poi_id") Long poiId) {
        valoracion.setCreador(usuarioServicio.obtenerUsuarioActual().orElseThrow());
        valoracion.setPoi(puntoDeInteresServicio.buscarPorId(poiId).orElseThrow());

        if (result.hasErrors()) {
            logger.error(result.getAllErrors().toString());
            return null;
        }

        valoracionServicio.agregarValoracion(valoracion);
        return valoracion;
    }
}
