package ar.edu.unju.edm.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import ar.edu.unju.edm.controlador.dto.UsuarioRegistroDTO;
import ar.edu.unju.edm.modelo.Usuario;


public interface UsuarioServicio extends UserDetailsService{

	Usuario guardar(UsuarioRegistroDTO registroDTO);
	
	List<Usuario> listarUsuarios();

	Usuario buscarUsuario(String email);

	Optional<Usuario> obtenerUsuarioActual();
}
