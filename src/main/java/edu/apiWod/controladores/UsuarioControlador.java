package edu.apiWod.controladores;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import dtos.LoginDto;
import edu.apiWod.modelos.UsuarioModelo;
import edu.apiWod.servicios.TokenContrasenaRecuperacion;
import edu.apiWod.servicios.UsuarioServicio;

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
    private TokenContrasenaRecuperacion tokenServicio;

    
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
    @PostMapping("/crear")
    public UsuarioModelo agregarUsuario(@RequestBody UsuarioModelo usuario) {
        usuarioServicios.agregarUsuario(usuario);
        return usuario;
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
    public Optional<UsuarioModelo> modificarUsuario(
            @RequestParam String correoElectronico,
            @RequestParam String campo,
            @RequestParam String nuevoValor) {
        return usuarioServicios.modificarUsuario(correoElectronico, campo, nuevoValor);
    }
    
  
    //--------------METODOS PARA TOKEN RECUPERACION CONTRASEÑA -----------------//
    
    @PostMapping("/request")
    public ResponseEntity<?> peticionReinicioContrasena(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String token = tokenServicio.creacionTokenRecuperacion(email);
        return ResponseEntity.ok(Map.of("token", token));
    }

}
