package edu.apiWod.controladores;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.apiWod.modelos.UsuarioModelo;
import edu.apiWod.servicios.UsuarioServicio;
	
// Anotación que define este controlador como un controlador REST
@RestController
@RequestMapping("/usuario") // Define la ruta base para las solicitudes relacionadas con usuarios
public class UsuarioControlador {

	private static final Logger logger = LoggerFactory.getLogger(UsuarioServicio.class);
	
 //Inyección de la dependencia del servicio UsuarioServicio
    @Autowired
    private UsuarioServicio usuarioServicios;

    
    
    
    
    /*---------------------------------METODOS---------------------------------*/
    
 //Para agregar un usuario nuevo
    @PostMapping
    public String agregarUsuario(@RequestBody UsuarioModelo usuario) {
        usuarioServicios.agregarUsuario(usuario);
        return "Usuario añadido con éxito, ID: " + usuario.getId();
    }
    
     
   
    
    
    
 //Probar conexion WEB - API
    @GetMapping("/ping")
    public ResponseEntity<String> pingApi() {
    	
        // Devuelve un mensaje simple para indicar que la API está viva
        return ResponseEntity.ok("Pong");
    }

    
    
    
    
    
  //Saca todo los usuarios de la base de datos  
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/todos")
    public ResponseEntity<List<UsuarioModelo>> obtenerTodosUsuarios() {
        
    	// Registro del inicio de la solicitud
        logger.info("Solicitud recibida para obtener todos los usuarios.");
        
        
        // Llamada al servicio para obtener los usuarios
        List<UsuarioModelo> usuarios = usuarioServicios.obtenerTodosUsuarios();

       
        // Registro de la cantidad de usuarios recuperados
        if (!usuarios.isEmpty()) {
            logger.info("Se encontraron {} usuarios en la base de datos.", usuarios.size());
            logger.debug("Usuarios recuperados: {}", usuarios);
        
        } 
        
        else {
            logger.warn("No se encontraron usuarios en la base de datos.");
        }

        // Devolver la respuesta con los usuarios
        return ResponseEntity.ok(usuarios);
    }
    
    
    
    
    
 // Endpoint para obtener un usuario por su ID
    @GetMapping("/mostrarUsuario/{id}")
    public ResponseEntity<?> mostrarUnUsuario(@PathVariable Long id) {
        Optional<UsuarioModelo> usuario = usuarioServicios.mostrarUnUsuario(id);

        // Verificar si el usuario existe
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get()); // 200 OK con el usuario encontrado
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Map.of("mensaje", "Usuario no encontrado")); // 404 Not Found con mensaje
        }
    }

    
    
    
    
  //Borra un usuario por id
    @DeleteMapping("/borrar/{id}")
    public String borrarUsuario(@PathVariable Long id) {
    	
        usuarioServicios.borrarUsuario(id);
        return "Usuario borrado con éxito"; // Devuelve un mensaje de éxito
    }

    
    
    
    
    
    
    
    @PutMapping("/modificar")
    public ResponseEntity<?> modificarUsuario(@RequestParam String correoElectronico, @RequestParam String campo, @RequestParam String nuevoValor) {
        
    	// Llamar al servicio para modificar el usuario
        Optional<UsuarioModelo> usuario = usuarioServicios.modificarUsuario(correoElectronico, campo, nuevoValor);

        // Si el usuario fue encontrado y modificado
        if (usuario.isPresent()) {
            return ResponseEntity.ok(Map.of(
                "mensaje", "Usuario modificado exitosamente",
                "usuario", usuario.get()
            ));
        } else {
            // Si no se encuentra el usuario o el campo no es válido
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(Map.of("mensaje", "Error: No se pudo modificar el usuario"));
        }
    }
    
    




}
    
    


    
   
