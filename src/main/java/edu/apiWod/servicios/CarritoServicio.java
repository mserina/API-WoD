package edu.apiWod.servicios;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.apiWod.modelos.ArticuloModelo;
import edu.apiWod.modelos.CarritoModelo;
import edu.apiWod.modelos.UsuarioModelo;
import edu.apiWod.repositorios.ArticuloRepositorio;
import edu.apiWod.repositorios.CarritoRepositorio;
import edu.apiWod.repositorios.UsuarioRepositorio;

@Service
public class CarritoServicio {

    @Autowired
    private CarritoRepositorio carritoRepositorio;
    @Autowired
    private ArticuloRepositorio articuloRepositorio;
    @Autowired 
	private UsuarioRepositorio usuarioRepositorio;

    /**
     * 
     * Agrega articulos al carrito
     * msm - 230525
     * @param usuarioId
     * @param articuloId
     * @param cantidad
     * @return Devuelve la lista de articulos del carrito
     */
    public CarritoModelo agregarAlCarrito(Long usuarioId, Long articuloId, Integer cantidad) {

        //  Validar existencia del usuario
        Optional<UsuarioModelo> usuarioEncontrado = usuarioRepositorio.findById(usuarioId);
        if (usuarioEncontrado.isEmpty()) {
            throw new NoSuchElementException("Usuario no encontrado con ID: " + usuarioId);
        }

        //  Validar existencia del artículo 
        Optional<ArticuloModelo> articuloEncontrado = articuloRepositorio.findById(articuloId);
        if (articuloEncontrado.isEmpty()) {
            throw new NoSuchElementException("Artículo no encontrado con ID: " + articuloId);
        }

        //  Crear el objeto del carrito
        CarritoModelo item = new CarritoModelo();
        item.setUsuarioId(usuarioId);
        item.setArticuloId(articuloId);
        item.setCantidad(cantidad);

        // 5) Guardar en la base de datos
        return carritoRepositorio.save(item);
    }


    /**
     * Muestra la lista de articulos del carrito
     * msm - 230525
     * @param usuarioId
     * @return Devuelve la lista de articulos del carrito
     */
    public List<CarritoModelo> obtenerCarritoDeUsuario(Long usuarioId) {
        if (!usuarioRepositorio.existsById(usuarioId)) {
            throw new NoSuchElementException("Usuario no encontrado con ID: " + usuarioId);
        }

        return carritoRepositorio.findByUsuarioId(usuarioId);
    }
    
    /**
     * Actualizar la cantidad de un producto concreto
     * msm - 250525
     * @param carritoItemId
     * @param nuevaCantidad
     * @return Devuelve la lista de articulo
     */
    public CarritoModelo actualizarCantidad(Long articuloCarritoId, Long articuloId, Integer nuevaCantidad) {
    	if (nuevaCantidad == null || nuevaCantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
    	
    	ArticuloModelo articulo = articuloRepositorio.findById(articuloId)
    	        .orElseThrow(() -> new NoSuchElementException("Artículo no encontrado con ID: " + articuloId));
    	
    	int stockDisponible = articulo.getStock();
    	
    	if (nuevaCantidad > stockDisponible) {
    	    throw new IllegalArgumentException("Solo hay " + stockDisponible + " unidades disponibles en stock");
    	}


        CarritoModelo carritoModificado = carritoRepositorio.findById(articuloCarritoId)
                .orElseThrow(() -> new NoSuchElementException("fila de carrito no encontrado con ID: " + articuloCarritoId));

        carritoModificado.setCantidad(nuevaCantidad);
        return carritoRepositorio.save(carritoModificado);
    }
    
    
    
    public void eliminarItem(Long carritoItemId) {
        if (!carritoRepositorio.existsById(carritoItemId)) {
            throw new NoSuchElementException("No existe un item de carrito con ID: " + carritoItemId);
        }
        carritoRepositorio.deleteById(carritoItemId);
    }
}


