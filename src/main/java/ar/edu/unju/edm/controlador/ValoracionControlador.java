package ar.edu.unju.edm.controlador;

import ar.edu.unju.edm.modelo.Valoracion;
import ar.edu.unju.edm.servicio.PuntoDeInteresServicio;
import ar.edu.unju.edm.servicio.UsuarioServicio;
import ar.edu.unju.edm.servicio.ValoracionServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@Controller
public class ValoracionControlador {
    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    PuntoDeInteresServicio puntoDeInteresServicio;

    @Autowired
    ValoracionServicio valoracionServicio;

    @PersistenceContext
    private EntityManager entityManager;

    private final Logger logger = LoggerFactory.getLogger(ValoracionControlador.class);

    @GetMapping("/poi/{poiId}/valoracion/nueva")
    public String formularioNuevaValoracion(@PathVariable Long poiId, Model model) {
        assert puntoDeInteresServicio.buscarPorId(poiId).orElseThrow().isEstado();
        model.addAttribute("valoracion", new Valoracion());
        model.addAttribute("url", "/poi/2/valoracion/nueva");
        return "formularios/valoracionNueva";
    }

    @PostMapping("/poi/{poiId}/valoracion/nueva")
    public String procesarNuevaValoracion(@PathVariable Long poiId, @Valid Valoracion valoracion, BindingResult result) {
        logger.info(valoracion.toString());
        var poi =  puntoDeInteresServicio.buscarPorId(poiId).orElseThrow();
        assert poi.isEstado();
        if (result.hasErrors()) {
            return "puntoDeInteres";
        }

        valoracion.setCreador(usuarioServicio.obtenerUsuarioActual().orElseThrow());
        valoracion.setPoi(poi);

        valoracionServicio.agregarValoracion(new Valoracion(null, valoracion.getCreador(), valoracion.getPoi(), valoracion.getValor(), valoracion.getDescripcion()));

        return "redirect:/poi/" + poiId;
    }

    @GetMapping("/poi/{poiId}/valoracion/{id}/editar")
    public String editarValoracion(@PathVariable Long poiId, @PathVariable Long id, Model model) {
        var poi = puntoDeInteresServicio.buscarPorId(poiId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var valoracion = valoracionServicio.buscarValoracionPorId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        entityManager.detach(valoracion);
        model.addAttribute("poi", poi);
        model.addAttribute("valoracion", valoracion);
        model.addAttribute("usuarioId", usuarioServicio.obtenerUsuarioActual().orElseThrow().getId());
        model.addAttribute("editar", "Editar");
        return "puntoDeInteres";
    }

    @PostMapping("/poi/{poiId}/valoracion/{id}/editar")
    public String procesarEditarValoracion(@PathVariable Long poiId, @PathVariable Long id, @Valid Valoracion valoracion, BindingResult result) {
        var poi =  puntoDeInteresServicio.buscarPorId(poiId).orElseThrow();
        assert poi.isEstado();
        if (result.hasErrors()) {
            return "puntoDeInteres";
        }

        var realValoracion = valoracionServicio.buscarValoracionPorId(id).orElseThrow();
        realValoracion.setValor(valoracion.getValor());
        realValoracion.setDescripcion(valoracion.getDescripcion());

        valoracionServicio.agregarValoracion(realValoracion);

        return "redirect:/poi/" + poiId;
    }
    @GetMapping("/poi/{poiId}/valoracion/{id}/eliminar")
    public String eliminarValoracion(@PathVariable Long poiId, @PathVariable Long id) {
        var valoracion = valoracionServicio.buscarValoracionPorId(id).orElseThrow();
        assert valoracion.getCreador().getId().equals(usuarioServicio.obtenerUsuarioActual().orElseThrow().getId());
        valoracion.setEstado(false);
        valoracionServicio.agregarValoracion(valoracion);
        return "redirect:/poi/" + poiId;
    }

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
