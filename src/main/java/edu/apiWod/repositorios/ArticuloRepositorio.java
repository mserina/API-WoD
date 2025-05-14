package edu.apiWod.repositorios; 

import org.springframework.data.jpa.repository.JpaRepository; // Importa la interfaz JpaRepository de Spring Data JPA

import edu.apiWod.modelos.ArticuloModelo;

/**
 * Contiene los metodos que haran las query a la base de datos con la tabla de articulos
 * msm - 060325
 */
public interface ArticuloRepositorio extends JpaRepository<ArticuloModelo , Long> {

	ArticuloModelo getOneById(Long id);
}
