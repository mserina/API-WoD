package edu.apiWod.servicios;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.apiWod.modelos.UsuarioModelo;
import edu.apiWod.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio {

  //Esta línea crea un logger utilizando la clase LoggerFactory.
  //El logger se usa para registrar eventos y mensajes de depuración.
	private static final Logger logger = LoggerFactory.getLogger(UsuarioServicio.class);
	
	 @Autowired // Inyección de dependencias para el repositorio de usuarios
	    private UsuarioRepositorio usuarioRepositorio;

	 
	 
	 
	 
	 
	    
	 //Método para agregar un nuevo usuario a la base de datos
	    public void agregarUsuario(UsuarioModelo  usuario) {
	        usuarioRepositorio.save(usuario);  // Guarda el usuario utilizando el repositorio
	    }
	    
	    
	    
	    
	    
	    
	    
	   
	 //Obtener todos los usuarios
	    public List<UsuarioModelo > obtenerTodosUsuarios() {
	        return usuarioRepositorio.findAll();
	    }
	    
	    
	    
	    
	    
	    
	    
	   
	  //Método para borrar un usuario de la base de datos dado su ID
	    public void borrarUsuario(Long idUsuario) {
	        usuarioRepositorio.deleteById(idUsuario);  // Elimina el club utilizando su ID
	    }

	    
	    
	    
	    
	    public Optional<UsuarioModelo > modificarUsuario(String correoElectronico, String campo, String nuevoValor) {
	    	// Buscar al usuario por su correo electrónico
	        Optional<UsuarioModelo > usuarioBD = usuarioRepositorio.findAll().stream()
	                .peek(u -> logger.info("Comparando con usuario: {}", u.getCorreoElectronico()))
	                .peek(u -> logger.info("Correo recibido por parámetro: {}", correoElectronico))
	                .filter(u -> u.getCorreoElectronico().equals(correoElectronico))
	                .findFirst();

	        // Si se encuentra el usuario, se actualiza el campo
	        if (usuarioBD.isPresent()) {
	        	UsuarioModelo  u = usuarioBD.get();

	            switch (campo.toLowerCase()) {
	                case "nombre_completo":
	                    u.setNombreCompleto(nuevoValor);
	                    break;
	                case "movil":
	                    u.setMovil(nuevoValor);
	                    break;
	                case "correo_electronico":
	                    u.setCorreoElectronico(nuevoValor);
	                    break;
	                case "tipo_usuario":
	                    u.setTipoUsuario(nuevoValor);
	                    break;
	                case "contrasena":
	                    u.setContrasena(nuevoValor);
	                    break;
	                case "foto":
	                    u.setFoto(nuevoValor);
	                    break;
	                default:
	                    return Optional.empty(); // Si el campo no es válido, devolvemos un Optional vacío
	            
	            }

	            // Guardar el usuario modificado
	            usuarioRepositorio.save(u);
	            return Optional.of(u);  // Devolver el usuario actualizado
	        }

	        return Optional.empty();  // Si no se encuentra el usuario, devolver un Optional vacío
	    }
	    
}
