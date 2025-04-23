package edu.apiWod.servicios;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.apiWod.modelos.UsuarioModelo;
import edu.apiWod.repositorios.UsuarioRepositorio;

/**
 * Contiene la logica de las funciones del usuario hacia la base de datos
 * msm - 060325
 */
@Service
public class UsuarioServicio {

  //Esta línea crea un logger utilizando la clase LoggerFactory.
  //El logger se usa para registrar eventos y mensajes de depuración.
	private static final Logger logger = LoggerFactory.getLogger(UsuarioServicio.class);
	
	
	 @Autowired
	    private PasswordEncoder cifradoContraseña;
	 
	 @Autowired // Inyección de dependencias para el repositorio de usuarios
	 private UsuarioRepositorio usuarioRepositorio;
		/*
		 * @Autowired private PasswordEncoder cifradoContraseña;
		 */
	 
 
	 /**
	  * Método para agregar un nuevo usuario a la base de datos
	  * msm - 060325
	  * @param usuario recoge los datos del usuario nuevo
	  */
	    public void agregarUsuario(UsuarioModelo usuario) {
	        usuarioRepositorio.save(usuario);  // Guarda el usuario utilizando el repositorio
	    }
	   
	   
	 /**
	  * Obtener todos los usuarios
	  * msm - 060325
	  * @return una lista con todo los usuarios
	  */
	    public List<UsuarioModelo > obtenerTodosUsuarios() {
	        return usuarioRepositorio.findAll();
	    }
	    
	     
	    /**
	     * Método para obtener un usuario específico de la base de datos dado su ID 
	     * @param idUsuario ID del usuario que se desea buscar.
	     * @return Optional<UsuarioModelo> que contiene el usuario si existe, o vacío si no se encuentra.
	     */
	    public Optional<UsuarioModelo> mostrarUnUsuario(Long idUsuario) {
	        return usuarioRepositorio.findById(idUsuario);
	    }

	    
	    
	    
	   
	  /**
	   * Método para borrar un usuario de la base de datos dado su ID
	   * msm - 060325
	   * @param idUsuario 
	   */
	    public void borrarUsuario(Long idUsuario) {
	        usuarioRepositorio.deleteById(idUsuario);  // Elimina el club utilizando su ID
	       
	    }

	    	
	    /**
	     * Metodo que autentifica usuarios
	     * msm - 190225
	     * @param email
	     * @param contrasena
	     * @return Un boolean que indica si inicio sesion o no
	     */
	    public UsuarioModelo login(String email, String contrasena) {
	       
	    	UsuarioModelo usuarioEncontrado = new UsuarioModelo();


	        // Obtener la lista de usuarios desde la API o base de datos
	        List<UsuarioModelo> usuarios = obtenerTodosUsuarios(); 
	        
			
			  // Buscar el usuario por email 
	        for (UsuarioModelo usuario : usuarios) { 
	        	if (usuario.getCorreoElectronico().equals(email)) { 
	        	// Comparar contraseñas 
	        		if (cifradoContraseña.matches(contrasena, usuario.getContrasena())) {
	        			usuarioEncontrado = usuario; 
	        		}
			  
			  break; 
			  } 
	        }
			 
	       
	        return usuarioEncontrado = null;

	    }
	    
	    
	    /**
	     * Metodo que modifica un usuario en base al correo
	     * msm - 060325
	     * @param correoElectronico
	     * @param campo
	     * @param nuevoValor
	     * @return Optional<UsuarioModelo> que contiene el usuario si existe, o vacío si no se encuentra.
	     */
	    public Optional<UsuarioModelo> modificarUsuario(String correoElectronico, String campo, String nuevoValor) {
	    	// Buscar al usuario por su correo electrónico
	        Optional<UsuarioModelo > usuarioBD = usuarioRepositorio.findAll().stream()
	                .peek(u -> logger.info("Comparando con usuario: {}", u.getCorreoElectronico()))
	                .peek(u -> logger.info("Correo recibido por parámetro: {}", correoElectronico))
	                .filter(u -> u.getCorreoElectronico().equals(correoElectronico))
	                .findFirst();

	        // Si se encuentra el usuario, se actualiza el campo
	        if (usuarioBD.isPresent()) {
	        	UsuarioModelo u = usuarioBD.get();

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
	                case "verificado":
	                	if(nuevoValor.equals("true")) {
	                		u.setVerificado(true);
	                	}
	                    break;
	                default:
	                    return Optional.empty(); // Si el campo no es válido, devolvemos un Optional vacío
	            
	            }

	            usuarioRepositorio.save(u);  // Guardar el usuario modificado
	            return Optional.of(u);  // Devolver el usuario actualizado
	        }

	        return Optional.empty();  // Si no se encuentra el usuario, devolver un Optional vacío
	    }
	    
}
