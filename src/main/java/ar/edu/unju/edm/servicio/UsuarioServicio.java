package ar.edu.unju.edm.servicio;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import ar.edu.unju.edm.controlador.dto.UsuarioRegistroDTO;
import ar.edu.unju.edm.modelo.Usuario;


public interface UsuarioServicio extends UserDetailsService{

	public Usuario guardar(UsuarioRegistroDTO registroDTO);
	
	public List<Usuario> listarUsuarios();
	
}
