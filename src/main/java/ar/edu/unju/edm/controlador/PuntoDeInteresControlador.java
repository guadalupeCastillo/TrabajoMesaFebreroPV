package ar.edu.unju.edm.controlador;

import ar.edu.unju.edm.modelo.PuntoDeInteres;
import ar.edu.unju.edm.servicio.PuntoDeInteresServicio;
import ar.edu.unju.edm.servicio.UsuarioServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@RestController
public class PuntoDeInteresControlador {
    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private PuntoDeInteresServicio puntoDeInteresServicio;

    private final Logger logger = LoggerFactory.getLogger(PuntoDeInteresControlador.class);

    @GetMapping("/")
    public ModelAndView mostrarPuntosDeInteres() {
        var model = new ModelAndView("index");
        model.addObject("pois", puntoDeInteresServicio.buscarTodos());
        return model;
    }

    @PostMapping("/poi")
//    @ResponseBody
    public PuntoDeInteres agregarPoi(@Valid @RequestBody PuntoDeInteres poi, BindingResult result) {
        poi.setCreador(usuarioServicio.obtenerUsuarioActual().orElseThrow());
        logger.info(poi.toString());

        if (result.hasErrors()) {
            logger.error(result.getAllErrors().toString());
            return null;
        }

        puntoDeInteresServicio.agregarPoi(poi);

        return poi;
    }
}
