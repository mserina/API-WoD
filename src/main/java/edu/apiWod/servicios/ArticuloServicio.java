package edu.apiWod.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.apiWod.modelos.ArticuloModelo;
import edu.apiWod.modelos.UsuarioModelo;
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
		 public List<ArticuloModelo> obtenerTodosArticulos() {
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
		
		/**
		* Método para borrar un articulo de la base de datos dado su ID
		* msm - 130525
		* @param idArticulo 
		*/
		 public void borrarArticulo(Long idArticulo) {
		        articuloRepositorio.deleteById(idArticulo);  // Elimina el articulo utilizando su ID
		       
		 }
		 
		 /**
		 * Metodo que modifica un articulo en base al nombre
		 * msm - 130525
		 * @param nombre
		 * @param campo
		 * @param nuevoValor
		 * @return Optional<ArticuloModelo> que contiene el articulo si existe, o vacío si no se encuentra.
		 */
		    public Optional<ArticuloModelo> modificarArticulo(String nombre, String campo, String nuevoValor) {
		    	// Buscar al articulo por su correo electrónico
		        Optional<ArticuloModelo > articuloBD = articuloRepositorio.findAll().stream()
		                .filter(a -> a.getNombre().equals(nombre))
		                .findFirst();

		        // Si se encuentra el usuario, se actualiza el campo
		        if (articuloBD.isPresent()) {
		        	ArticuloModelo a = articuloBD.get();

		            switch (campo.toLowerCase()) {
		                case "nombre":
		                    a.setNombre(nuevoValor);
		                    break;
		                
		                case "descripcion":
		                    a.setDescripcion(nuevoValor);
		                    break;
		                
		                case "precio":
		                	try {
		                        int precio = Integer.parseInt(nuevoValor);
		                        a.setPrecio(precio);
		                    } catch (NumberFormatException e) {
		                        // Manejo del error si el valor no es un número válido
		                        System.out.println("Error: el valor '" + nuevoValor + "' no es un número válido para el precio.");
		                    }
		                    break;
		                
		                case "stock":	
		                	try {
		                        int stock = Integer.parseInt(nuevoValor);
		                        a.setStock(stock);
		                    } catch (NumberFormatException e) {
		                        // Manejo del error si el valor no es un número válido
		                        System.out.println("Error: el valor '" + nuevoValor + "' no es un número válido para el stock.");
		                    }
		                    break;
		                
		                case "tipo_articulo":
		                    a.setTipoArticulo(nuevoValor);
		                    break;
						
		                default:
		                    return Optional.empty(); // Si el campo no es válido, devolvemos un Optional vacío
		            
		            }

		            articuloRepositorio.save(a);  // Guardar el articulo modificado
		            return Optional.of(a);  // Devolver el articulo actualizado
		        }

		        return Optional.empty();  // Si no se encuentra el articulo, devolver un Optional vacío
		    }	  
		    
		    
}
