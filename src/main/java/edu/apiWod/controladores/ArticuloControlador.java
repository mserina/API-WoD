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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.apiWod.modelos.ArticuloModelo;
import edu.apiWod.repositorios.ArticuloRepositorio;
import edu.apiWod.servicios.ArticuloServicio;

/**
 * Controlador para gestionar las operaciones relacionadas con los articulos.
 * Proporciona endpoints para autenticación y CRUD de articulos.
 * 
 * msm - 130525
 */
@RestController
@RequestMapping("/articulo") // Define la ruta base para las solicitudes relacionadas con articulos
public class ArticuloControlador {

	/** Servicio encargado de la lógica de negocio de los articulos */
    @Autowired
    private ArticuloServicio articuloServicio;
    @Autowired
    private ArticuloRepositorio articuloRepo;
    
    
    /**
     * Crea y agrega un nuevo articulo a la base de datos.
     *msm - 130525
     * @param usuario Datos del articulo a registrar.
     * @return El articulo recién agregado.
     */
    @PostMapping(path = "/crearArticulos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ArticuloModelo> agregarArticulo(@RequestParam("nombre") String nombreArticulo, @RequestParam("descripcion") String descripcion, @RequestParam("precio") Integer precio,@RequestParam("stock") Integer stock, @RequestParam("tipoArticulo") String tipoArticulo, @RequestParam("fotoArticulo") MultipartFile fotoArticulo) throws IOException {
    	 ArticuloModelo articuloNuevo = new ArticuloModelo();
    	 articuloNuevo.setNombre(nombreArticulo);
    	 articuloNuevo.setDescripcion(descripcion);
    	 articuloNuevo.setPrecio(precio);
    	 articuloNuevo.setStock(stock);
    	 articuloNuevo.setTipoArticulo(tipoArticulo);
    	 articuloNuevo.setFotoArticulo(fotoArticulo.getBytes());
    	    ArticuloModelo guardado = articuloServicio.agregarArticulo(articuloNuevo);
    	    return ResponseEntity.ok(guardado);
    }
    
    
    
    /**
     * Obtiene y devuelve la lista de todos los articulos almacenados en la base de datos.
     *
     * @return Lista de articulos en formato ResponseEntity.
     */
    @GetMapping("/mostrarArticulos")
    public ResponseEntity<List<ArticuloModelo>> obtenerTodosArticulos() {
        List<ArticuloModelo> articulos = articuloServicio.obtenerTodosArticulos();
        return ResponseEntity.ok(articulos);
    }
    
    
    @GetMapping("/mostrarArticuloId/{id}")
    public ResponseEntity <ArticuloModelo> obtenerArticuloId(@PathVariable Long id) {
        ArticuloModelo articulo = articuloServicio.buscarArticuloPorId(id);
        return ResponseEntity.ok(articulo);
    }
    
    
    @GetMapping("/mostrarArticulosPorTipo")
    public ResponseEntity<List<ArticuloModelo>> obtenerArticulosPorTipo(@RequestParam String tipo) {
        List<ArticuloModelo> articulos = articuloServicio.obtenerArticulosPorTipos(tipo);
        return ResponseEntity.ok(articulos);
    }

    
    
    /**
     * Carga la foto de la base de datos
     * msm - 130525
     * @param id el id del articulo
     * @return la imagen
     */
    @GetMapping(path = "/fotoArticulo/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> servirFoto(@PathVariable Long id) {
        // 1) buscar el articulo
    	Optional<ArticuloModelo> articuloOpt = articuloRepo.findById(id);

    	 // 2) verificar que exista el usuario
        if (!articuloOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ArticuloModelo articulo = articuloOpt.get();
        
        // 3) extraer el byte[]
        byte[] foto = articulo.getFotoArticulo();

        
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
     * Borra un articulo de la base de datos utilizando su ID.
     *
     * @param id Identificador único del articulo.
     * @return ResponseEntity con mensaje de éxito o error si el articulo no existe.
     */
    @DeleteMapping("/borrarArticulo/{id}")
    public ResponseEntity<?> borrarArticulo(@PathVariable Long id) {
        ArticuloModelo articulo = articuloServicio.buscarArticuloPorId(id);

        if (articulo != null) {
            articuloServicio.borrarArticulo(id); // Llamar al servicio para eliminar el articulo
            return ResponseEntity.ok(Map.of("mensaje", "Articulo " + articulo.getNombre() + " ha sido eliminado"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Articulo no encontrado"));
        }
    }
    
    
    /**
     * Modifica un campo específico de un articulo identificado por su nombre.
     *
     * @param nombre Nombre del articulo a modificar.
     * @param campo Nombre del campo a actualizar.
     * @param nuevoValor Nuevo valor a asignar en el campo.
     * @return El articulo actualizado en un Optional o vacío si no se encontró.
     */
    @PutMapping(path = "/modificarArticulo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Optional<ArticuloModelo> modificarArticulo(@RequestParam String nombre, @RequestParam String campo, @RequestParam(required = false) String nuevoValor, @RequestParam(required = false, name="foto") MultipartFile foto) {
    	Optional<ArticuloModelo> resultado = articuloServicio.modificarArticulo(nombre, campo, nuevoValor, foto);

    	    return resultado;
    }
    	        
}
