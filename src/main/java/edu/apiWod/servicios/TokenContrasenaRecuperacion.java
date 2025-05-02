package edu.apiWod.servicios;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
	 @Autowired private UsuarioServicio usuarioServicio;
	 
	 /**
	  * Se busca al usuario y se le asigna un token
	  * msm - 020525
	  * @param email
	  * @return devuelve el nuevo token
	  */
	 @Transactional
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
	 @Transactional
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
	    
	    
	    /**
	     * Cambia la contraseña del usuario por la nueva
	     * msm - 300425
	     * @param token el token que usaremos para buscar al usuario
	     * @param contrasenaNueva 
	     */
	 @Transactional
	    public void cambiarContrasena(String token, String contrasenaNueva) {
		// 1) Buscar el token
		    TokenRecuperacionContrasena tokenUsuario = tokenRepo.findByToken(token);
		    if (tokenUsuario == null) {
		        // no existe: nada de usuario aquí
		        throw new IllegalArgumentException("Token inválido");
		    }

		// 2) Si ha expirado, lo borramos y lanzamos la excepción
		    if (tokenUsuario.getExpiracionToken().isBefore(LocalDateTime.now())) {
		    	tokenRepo.deleteByUsuario(tokenUsuario.getUsuario());
		        throw new IllegalArgumentException("Token expirado");
		    }
		    
		 // 3) Ya sabemos que tokenUsuario existe y no ha expirado: obtenemos el usuario
		    UsuarioModelo usuario = tokenUsuario.getUsuario();
		    if (usuario == null) {
		        // por si acaso, eliminamos el token huérfano
		    	tokenRepo.deleteByUsuario(tokenUsuario.getUsuario());
		        throw new UsuarioNoEncontradoExcepcion("Usuario asociado al token no encontrado");
		    }
		    
		 // 4) Ciframos y guardamos la nueva contraseña
		    usuario.setContrasena(cifradoContrasena.encode(contrasenaNueva));
		    usuarioRepo.save(usuario);

		 // 5) Borramos el token para que no pueda reutilizarse
		    tokenRepo.deleteByUsuario(usuario);
		    tokenRepo.flush();
	    }
	 

	  
}
