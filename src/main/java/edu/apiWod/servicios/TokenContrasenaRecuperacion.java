package edu.apiWod.servicios;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.apiWod.modelos.TokenRecuperacionContrasena;
import edu.apiWod.modelos.UsuarioModelo;
import edu.apiWod.repositorios.TokenRecuperacionContrasenaRepositorio;
import edu.apiWod.repositorios.UsuarioRepositorio;
import excepciones.UsuarioNoEncontradoExcepcion;


public class TokenContrasenaRecuperacion {

	 @Autowired private TokenRecuperacionContrasenaRepositorio tokenRepo;
	 @Autowired private UsuarioRepositorio usuarioRepo;
	 @Autowired private PasswordEncoder cifradoContrasena;
	 
	 public String creacionTokenRecuperacion(String email) {
		//Busca al usuario por correo y borra el token anterior (en el caso de que exisitiera token)	 
		UsuarioModelo usuario = usuarioRepo.findByCorreoElectronico(email);
		if(usuario == null) {
	        throw new UsuarioNoEncontradoExcepcion("No existe usuario con ese correo: " + email);
		}
	    
		tokenRepo.deleteByUser(usuario);

		String token = UUID.randomUUID().toString();
		TokenRecuperacionContrasena tokenNuevo = new TokenRecuperacionContrasena();
		tokenNuevo.setUsuario(usuario);
		tokenNuevo.setToken(token);
		tokenNuevo.setExpiracion_token(LocalDateTime.now().plusHours(1));
	    
		tokenRepo.save(tokenNuevo);
	
	    return token;
	    
	 }
}
