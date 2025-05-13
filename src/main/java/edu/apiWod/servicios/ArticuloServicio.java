package edu.apiWod.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.apiWod.modelos.ArticuloModelo;
import edu.apiWod.repositorios.ArticuloRepositorio;

@Service
public class ArticuloServicio {

	@Autowired
	ArticuloRepositorio articuloRepositorio;
	
	  /**
	  * Método para agregar un nuevo articulo a la base de datos
	  * msm - 130525
	  * @param usuario recoge los datos del articulo nuevo
	  */
	    public ArticuloModelo agregarArticulo(ArticuloModelo articuloNuevo) {
	        articuloRepositorio.save(articuloNuevo);  // Guarda el articulo utilizando el repositorio
	        return articuloNuevo;
	    }
	    
	   /**
	   * Obtener todos los articulo
	   * msm - 060325
	   * @return una lista con todo los articulo
	   */
		 public List<ArticuloModelo> obtenerTodosUsuarios() {
		     return articuloRepositorio.findAll();
		 }
		 
		 /**
		  * Busca un articulo por id
		  * msm - 130525 
		  * @param id 
		  * @return Devuelve el articulo por id
		  */
		public Optional<ArticuloModelo> buscarArticuloPorId(Long id) {
			Optional<ArticuloModelo> articuloEncontrado = articuloRepositorio.findById(id);;
			return articuloEncontrado;
		}
}
