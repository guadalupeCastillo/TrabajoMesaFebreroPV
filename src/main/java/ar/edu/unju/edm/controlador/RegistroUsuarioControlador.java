package ar.edu.unju.edm.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ar.edu.unju.edm.controlador.dto.UsuarioRegistroDTO;
import ar.edu.unju.edm.modelo.Rol;
import ar.edu.unju.edm.servicio.UsuarioServicio;

@Controller
@RequestMapping("/registro")
public class RegistroUsuarioControlador {

	private UsuarioServicio usuarioServicio;

	public RegistroUsuarioControlador(UsuarioServicio usuarioServicio) {
		super();
		this.usuarioServicio = usuarioServicio;
	}
	
	@ModelAttribute("usuario")
	public UsuarioRegistroDTO retornarNuevoUsuarioRegistroDTO() {
		return new UsuarioRegistroDTO();
	}

	@ModelAttribute("rol") 
	public Rol retornarNuevoUsuarioRegistroRolDTO() {
		return new Rol();
	}

	@GetMapping
	public String mostrarFormularioDeRegistro() {
		return "registro";
	}
	
	@PostMapping
	public String registrarCuentaDeUsuario(@ModelAttribute("rol") Rol rol, @ModelAttribute("usuario") UsuarioRegistroDTO registroDTO) {
		System.out.println(rol.getNombre());
		usuarioServicio.guardar(registroDTO);
		return "redirect:/registro?exito";
	}
}
