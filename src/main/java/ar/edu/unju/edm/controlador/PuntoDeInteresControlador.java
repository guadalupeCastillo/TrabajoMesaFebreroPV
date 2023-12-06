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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Base64;


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
        return "puntoDeInteres";
    }

    @GetMapping("/poi/nuevo")
    public String formularioNuevoPoi(Model model) {
        model.addAttribute("puntoDeInteres", new PuntoDeInteres());
        return "formularios/puntoDeInteresNuevo";
    }

    @PostMapping("/poi/nuevo")
    public String procesarNuevoPoi(@Valid PuntoDeInteres puntoDeInteres, BindingResult result, @RequestParam(value = "file", required = false) MultipartFile[] file, Model model) {
        if (result.hasErrors()) {
            return "formularios/puntoDeInteresNuevo";
        }

        puntoDeInteres.setCreador(usuarioServicio.obtenerUsuarioActual().orElseThrow());

        if (file != null && file.length > 0 && file[0].getSize() > 0) {
            try {
                byte[] contenido = file[0].getBytes();
                String base64 = Base64.getEncoder().encodeToString(contenido);
                puntoDeInteres.setImagen("data:" + file[0].getContentType() + ";base64," + base64);
            } catch (Exception e) {
                model.addAttribute("fileErrorMessage", e.getMessage());
                return "formularios/puntoDeInteresNuevo";
            }
        } else {
            puntoDeInteres.setImagen(null);
        }

        puntoDeInteresServicio.agregarPoi(puntoDeInteres);

        logger.info(puntoDeInteres.toString());
        return "redirect:/poi/" + puntoDeInteres.getId();
    }

    @GetMapping("/poi/{id}/editar")
    public String editarPoi(@PathVariable Long id, Model model) {
        var poi = puntoDeInteresServicio.buscarPorId(id).orElseThrow();
        assert poi.getCreador().getId().equals(usuarioServicio.obtenerUsuarioActual().orElseThrow().getId());
        model.addAttribute("puntoDeInteres", poi);
        model.addAttribute("editar", "Editar");
        return "formularios/puntoDeInteresNuevo";
    }

    @PostMapping("/poi/{id}/editar")
    public String procesarEditarPoi(@PathVariable Long id, @Valid PuntoDeInteres puntoDeInteres, BindingResult result, @RequestParam(value = "file", required = false) MultipartFile[] file, Model model) {
        assert puntoDeInteresServicio.buscarPorId(id).orElseThrow().getCreador().getId().equals(usuarioServicio.obtenerUsuarioActual().orElseThrow().getId());
        if (result.hasErrors()) {
            return "formularios/puntoDeInteresNuevo";
        }

        puntoDeInteres.setId(id);
        puntoDeInteres.setCreador(usuarioServicio.obtenerUsuarioActual().orElseThrow());

        if (file != null && file.length > 0 && file[0].getSize() > 0) {
            try {
                byte[] contenido = file[0].getBytes();
                String base64 = Base64.getEncoder().encodeToString(contenido);
                puntoDeInteres.setImagen("data:" + file[0].getContentType() + ";base64," + base64);
            } catch (Exception e) {
                model.addAttribute("fileErrorMessage", e.getMessage());
                return "formularios/puntoDeInteresNuevo";
            }
        }

        puntoDeInteresServicio.agregarPoi(puntoDeInteres);

        logger.info(puntoDeInteres.toString());
        return "redirect:/poi/" + puntoDeInteres.getId();
    }

    @GetMapping("/poi/{id}/eliminar")
    public String eliminarPoi(@PathVariable Long id) {
        var poi = puntoDeInteresServicio.buscarPorId(id).orElseThrow();
        assert poi.getCreador().getId().equals(usuarioServicio.obtenerUsuarioActual().orElseThrow().getId());
        puntoDeInteresServicio.eliminar(poi);

        return "redirect:/";
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
