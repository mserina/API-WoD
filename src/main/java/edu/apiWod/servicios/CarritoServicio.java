package edu.apiWod.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.apiWod.modelos.CarritoModelo;
import edu.apiWod.repositorios.CarritoRepositorio;

@Service
public class CarritoServicio {

    @Autowired
    private CarritoRepositorio carritoRepo;
    
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
        // 1) Validar cantidad mínima
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }

        // 2) Crear y guardar
        CarritoModelo objetoCarrito = new CarritoModelo();
        objetoCarrito.setUsuarioId(usuarioId);
        objetoCarrito.setArticuloId(articuloId);
        objetoCarrito.setCantidad(cantidad);
        return carritoRepo.save(objetoCarrito);
    }

    /**
     * Muestra la lista de articulos del carrito
     * msm - 230525
     * @param usuarioId
     * @return Devuelve la lista de articulos del carrito
     */
    public List<CarritoModelo> obtenerCarritoDeUsuario(Long usuarioId) {
        return carritoRepo.findByUsuarioId(usuarioId);
    }
}


