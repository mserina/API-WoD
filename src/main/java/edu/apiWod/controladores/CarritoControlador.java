package edu.apiWod.controladores;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.apiWod.modelos.CarritoModelo;
import edu.apiWod.servicios.CarritoServicio;

/**
 * Clase que se encarga de las operaciones con el carrito de compra
 * msm - 230525
 */
@RestController
@RequestMapping("/carrito")
public class CarritoControlador {

    @Autowired
    private CarritoServicio carritoServicio;

    
    /**
     * Agrega un articulo al carrito de un usuario
     * msm - 230525
     * @param peticionCarrito los datos para agregar articulos al carrito 
     * @return devuelve el articulo creado
     */
    @PostMapping("/crearArticulos")
    public ResponseEntity<?> agregarAlCarrito(@RequestBody CarritoModelo peticionCarrito) {
        try {
	    	CarritoModelo carritoArticuloAnadido = carritoServicio
	            .agregarAlCarrito(peticionCarrito.getUsuarioId(),
	            		peticionCarrito.getArticuloId(),
	            		peticionCarrito.getCantidad());
	    	 return ResponseEntity.ok(carritoArticuloAnadido);
	        
        }
        catch (IllegalArgumentException e) {
        	
            // Por ejemplo: cantidad inválida
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
            
        } catch (NoSuchElementException e) {
        	
            // Usuario o artículo no encontrados
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
            
        } catch (Exception e) {
        	
            // Cualquier otro error inesperado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Ocurrió un error inesperado"));
        }
    }
    
    
    /**
     * Lista todo los articulos del carrtio de un usuario
     * msm - 230525
     * @param usuarioId id
     * @return Devuelve la lista de articulos del carrito
     */
    @GetMapping("/{usuarioId}")
    public ResponseEntity<?> verCarrito(@PathVariable Long usuarioId) {
        try {
            List<CarritoModelo> articulos = carritoServicio.obtenerCarritoDeUsuario(usuarioId);
            return ResponseEntity.ok(articulos);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Error al obtener el carrito del usuario"));
        }
    }
    
    
    /**
     * 	Actualiza la cantidad de un articulo concreto
     * msm - 250525
     * @param articuloCarritoId el id del articulo en un carrito concreto
     * @param cantidad Cantidad del articulo
     * @return devuelve el carrito con los cambios
     */
    @PostMapping("/actualizar")
    public ResponseEntity<?> actualizarCantidad(@RequestParam Long articuloCarritoId, @RequestParam Long articuloId, @RequestParam Integer cantidad) {
        try {
            CarritoModelo carritoActualizado = carritoServicio.actualizarCantidad(articuloCarritoId, articuloId, cantidad);
            return ResponseEntity.ok(carritoActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Error al actualizar la cantidad del artículo"));
        }
    }

    

    /**
     * Elimina un articulo del carrito
     * @param articuloCarritoId el id del articulo en un carrito concreto
     * msm - 250525
     */
    @PostMapping("/eliminar")
    public ResponseEntity<?> eliminarItem(@RequestParam Long articuloCarritoId) {
        try {
            carritoServicio.eliminarItem(articuloCarritoId);
            return ResponseEntity.ok(Map.of("mensaje", "Se eliminó correctamente el artículo del carrito"));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Error al eliminar el artículo del carrito"));
        }
    }
    
}
