package edu.apiWod.controladores;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import dtos.LoginDto;
import edu.apiWod.modelos.UsuarioModelo;
import edu.apiWod.repositorios.UsuarioRepositorio;
import edu.apiWod.servicios.TokenContrasenaRecuperacionServicio;
import edu.apiWod.servicios.UsuarioServicio;
import excepciones.UsuarioNoEncontradoExcepcion;

/**
 * Controlador para gestionar las operaciones relacionadas con los usuarios.
 * Proporciona endpoints para autenticación y CRUD de usuarios.
 * 
 * msm - 06/03/25
 */
@RestController
@RequestMapping("/usuario") // Define la ruta base para las solicitudes relacionadas con usuarios
public class UsuarioControlador {

  
    /** Servicio encargado de la lógica de negocio de los usuarios */
    @Autowired
    private UsuarioServicio usuarioServicios;
    @Autowired
    private TokenContrasenaRecuperacionServicio tokenServicio;
    @Autowired
    private UsuarioRepositorio repoUsu;
    
    

    
    /**
     * Endpoint para autenticar un usuario en la aplicación.
     *
     * @param datos Objeto con las credenciales del usuario (email y contraseña).
     * @return El modelo de usuario si la autenticación es exitosa.
     */
    @PostMapping("/login")
    public UsuarioModelo login(@RequestBody LoginDto datos) {
        UsuarioModelo usuarioEncontrado = usuarioServicios.login(datos.getCorreoElectronico(), datos.getContrasena());
        return usuarioEncontrado;
    }

    /*--------------------------------- CRUD DE USUARIOS ---------------------------------*/

    
    /**
     * Crea y agrega un nuevo usuario a la base de datos.
     *
     * @param usuario Datos del usuario a registrar.
     * @return El usuario recién agregado.
     */
    @PostMapping(path = "/crear", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UsuarioModelo> agregarUsuarios(@RequestParam("nombreCompleto") String nombreCompleto, @RequestParam("movil") String movil, @RequestParam("correoElectronico") String correoElectronico, @RequestParam("tipoUsuario") String tipoUsuario, @RequestParam("contrasena") String contrasena, @RequestParam("foto") MultipartFile foto) throws IOException {
    	 UsuarioModelo usuario = new UsuarioModelo();
    	    usuario.setNombreCompleto(nombreCompleto);
    	    usuario.setMovil(movil);
    	    usuario.setCorreoElectronico(correoElectronico);
    	    usuario.setTipoUsuario(tipoUsuario);
    	    usuario.setContrasena(contrasena);
    	    usuario.setFoto(foto.getBytes());
    	    UsuarioModelo guardado = usuarioServicios.agregarUsuario(usuario);
    	    return ResponseEntity.ok(guardado);
    }

  
    /**
     * Carga la foto de la base de datos
     * msm - 130525
     * @param id el id del usuario
     * @return la imagen
     */
    @GetMapping(path = "/foto/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> servirFoto(@PathVariable Long id) {
        // 1) buscar el usuario
    	Optional<UsuarioModelo> usuarioOpt = repoUsu.findById(id);

    	 // 2) verificar que exista el usuario
        if (!usuarioOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        UsuarioModelo usuario = usuarioOpt.get();
        
        // 3) extraer el byte[]
        byte[] foto = usuario.getFoto();

        
        // 4) verificar que exista
        if (foto == null || foto.length == 0) {
            // 204 No Content si no hay foto
            return ResponseEntity.noContent().build();
        }

        // 5) devolver la foto en un ResponseEntity
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)  // o PNG si guardas otro formato
                .body(foto);
    }
    
    
    /**
     * Obtiene y devuelve la lista de todos los usuarios almacenados en la base de datos.
     *
     * @return Lista de usuarios en formato ResponseEntity.
     */
    @GetMapping("/mostrarUsuarios")
    public ResponseEntity<List<UsuarioModelo>> obtenerTodosUsuarios() {
        List<UsuarioModelo> usuarios = usuarioServicios.obtenerTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Obtiene los datos de un usuario específico por su ID.
     *
     * @param id Identificador único del usuario.
     * @return ResponseEntity con el usuario si se encuentra o un mensaje de error si no.
     */
    @GetMapping("/mostrarUsuario/{id}")
    public ResponseEntity<?> mostrarUnUsuario(@PathVariable Long id) {
        Optional<UsuarioModelo> usuario = usuarioServicios.mostrarUnUsuario(id);

        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get()); // 200 OK con los datos del usuario
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Usuario no encontrado")); // 404 Not Found con mensaje
        }
    }

    /**
     * Borra un usuario de la base de datos utilizando su ID.
     *
     * @param id Identificador único del usuario.
     * @return ResponseEntity con mensaje de éxito o error si el usuario no existe.
     */
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> borrarUsuario(@PathVariable Long id) {
        Optional<UsuarioModelo> usuario = usuarioServicios.mostrarUnUsuario(id);

        if (usuario.isPresent()) {
            usuarioServicios.borrarUsuario(id); // Llamar al servicio para eliminar el usuario
            return ResponseEntity.ok(Map.of("mensaje", "Usuario " + usuario.get().getNombreCompleto() + " ha sido eliminado"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Usuario no encontrado"));
        }
    }

    /**
     * Modifica un campo específico de un usuario identificado por su correo electrónico.
     *
     * @param correoElectronico Email del usuario a modificar.
     * @param campo Nombre del campo a actualizar.
     * @param nuevoValor Nuevo valor a asignar en el campo.
     * @return El usuario actualizado en un Optional o vacío si no se encontró.
     */
    @PutMapping("/modificarUsuarios")
    public Optional<UsuarioModelo> modificarUsuario(@RequestParam String correoElectronico, @RequestParam String campo, @RequestParam String nuevoValor) {
        return usuarioServicios.modificarUsuario(correoElectronico, campo, nuevoValor);
    }
    
  
	
    
    //--------------METODOS PARA TOKEN RECUPERACION CONTRASEÑA -----------------//
    
    /**
     * Revisa que el usuario existe y le asigna un token
     * msm - 010525
     * @param cuerpoPeticion email ingresado por el usuario
     * @return Devuelve una respuesta con el nuevo token del usuario
     */
    @PostMapping("/peticionIntrucciones")
    public ResponseEntity<?> peticionReinicioContrasena(@RequestBody Map<String, String> cuerpoPeticion) {
        String email = cuerpoPeticion.get("email");
        try {
            String token = tokenServicio.creacionTokenRecuperacion(email);
            return ResponseEntity.ok(Map.of("token", token));
            
        } catch (UsuarioNoEncontradoExcepcion ex) {
            // Si no existe el usuario, devolvemos 404 con mensaje de error
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Map.of("error", ex.getMessage()));
        } catch (Exception e) {
            // Para cualquier otro error, devolvemos 500 con mensaje genérico
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Error al generar el token de recuperación"));
        }
    }
    
    /**
     * Comprueba que el token es correcto y que no a expirado
     * msm - 290425
     * @param token token extraido del link del email mandado al usuario
     */
    @GetMapping("/validarToken")
    public void validateToken(@RequestParam String token) {
        try {
            tokenServicio.validarToken(token);
            
        } catch (IllegalArgumentException ex) {
            // 404 si no existe, 400 si expiró
        	
            if ("Token inválido".equals(ex.getMessage())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
            }
        }
    }
    
    /**
     * Recibe la nueva contraseña y el token del usuario
     * msm - 020525
     * @param cuerpoPeticion, sacamos el token y la nueva contraseña del cuerpo de la peticion
     * @return devuelve un ResponseEntity, true o false
     */
    @PostMapping("/reiniciarContrasena")
    public ResponseEntity<Void> resetPassword(@RequestBody Map<String, String> cuerpoPeticion) {
        String token = cuerpoPeticion.get("token");
        String contrasenaNueva = cuerpoPeticion.get("contrasenaNueva");
        tokenServicio.cambiarContrasena(token, contrasenaNueva);
        return ResponseEntity.ok().build();
    }

    
    
}
