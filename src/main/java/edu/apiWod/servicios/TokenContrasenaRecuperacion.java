package edu.apiWod.servicios;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.apiWod.modelos.TokenRecuperacionContrasena;
import edu.apiWod.modelos.UsuarioModelo;
import edu.apiWod.repositorios.TokenRecuperacionContrasenaRepositorio;
import edu.apiWod.repositorios.UsuarioRepositorio;
import excepciones.UsuarioNoEncontradoExcepcion;

@Service
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
	    
		tokenRepo.deleteByUsuario(usuario);
			
		String token = UUID.randomUUID().toString();
		TokenRecuperacionContrasena tokenNuevo = new TokenRecuperacionContrasena();
		tokenNuevo.setUsuario(usuario);
		tokenNuevo.setToken(token);
		tokenNuevo.setExpiracionToken(LocalDateTime.now().plusHours(1));
	    
		tokenRepo.save(tokenNuevo);
	
	    return token;
	    
	 }
	 
	 /**
	  * Comprueba que el token existe y no ha expirado.
	  * msm - 290425
	  * @throws IllegalArgumentException si no existe o está expirado.
	  */
	    public void validarToken(String token) {
	      TokenRecuperacionContrasena respuestaValidacion = tokenRepo.findByToken(token);
	        
	      if (respuestaValidacion == null) {
	          throw new IllegalArgumentException("Token inválido");
	      }
	        
	      if (respuestaValidacion.getExpiracionToken().isBefore(LocalDateTime.now())) {
	          throw new IllegalArgumentException("Token expirado");
	      }
	      // Token válido -> nada más que hacer
	  }
}
