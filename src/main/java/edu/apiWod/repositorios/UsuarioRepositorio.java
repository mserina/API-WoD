package edu.apiWod.repositorios; 

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository; // Importa la interfaz JpaRepository de Spring Data JPA

import edu.apiWod.modelos.UsuarioModelo;

/**
 * Contiene los metodos que haran las query a la base de datos con la tabla de usuarios
 * msm - 060325
 */
public interface UsuarioRepositorio extends JpaRepository<UsuarioModelo , Long> {

	UsuarioModelo findByCorreoElectronico(String email);
  
}
