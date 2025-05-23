package edu.apiWod.controladores;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CarritoModelo> agregarAlCarrito(@RequestBody CarritoModelo peticionCarrito) {
        CarritoModelo creado = carritoServicio
            .agregarAlCarrito(peticionCarrito.getUsuarioId(),
            		peticionCarrito.getArticuloId(),
            		peticionCarrito.getCantidad());
        return ResponseEntity.ok(creado);
    }
    
    /**
     * Lista todo los articulos del carrtio de un usuario
     * msm - 230525
     * @param usuarioId id
     * @return Devuelve la lista de articulos del carrito
     */
    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<CarritoModelo>> verCarrito(@PathVariable Long usuarioId) {
        List<CarritoModelo> articulos = carritoServicio.obtenerCarritoDeUsuario(usuarioId);
        return ResponseEntity.ok(articulos);
    }
}
