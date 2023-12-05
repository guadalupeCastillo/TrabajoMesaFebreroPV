package ar.edu.unju.edm.controlador;

import ar.edu.unju.edm.modelo.PuntoDeInteres;
import ar.edu.unju.edm.servicio.PuntoDeInteresServicio;
import ar.edu.unju.edm.servicio.UsuarioServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
public class PuntoDeInteresControlador {
    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private PuntoDeInteresServicio puntoDeInteresServicio;

    private final Logger logger = LoggerFactory.getLogger(PuntoDeInteresControlador.class);

    @GetMapping("/")
    public String mostrarPuntosDeInteres(Model model) {
        model.addAttribute("pois", puntoDeInteresServicio.buscarTodos());
        return "index";
    }

    @GetMapping("/poi/{id}")
    public String mostrarPuntoDeInteresPorId(@PathVariable Long id, Model model) {
        var poi = puntoDeInteresServicio.buscarPorId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("poi", poi);
        model.addAttribute("usuarioId", usuarioServicio.obtenerUsuarioActual().orElseThrow().getId());
        return "/puntoDeInteres";
    }


    @PostMapping("/poi")
    @ResponseBody
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
