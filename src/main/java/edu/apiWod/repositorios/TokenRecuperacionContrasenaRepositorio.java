package edu.apiWod.repositorios; 

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository; // Importa la interfaz JpaRepository de Spring Data JPA

import edu.apiWod.modelos.TokenRecuperacionContrasena;
import edu.apiWod.modelos.UsuarioModelo;

/**
 * Contiene los metodos que haran las query a la base de datos
 * msm - 060325
 */
public interface TokenRecuperacionContrasenaRepositorio extends JpaRepository<TokenRecuperacionContrasena , Long> {
  
	Optional<TokenRecuperacionContrasena> findByToken(String token);
	void deleteByUser(Optional<UsuarioModelo> usuario);
}
