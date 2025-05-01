package edu.apiWod.repositorios; 


import org.springframework.data.jpa.repository.JpaRepository; // Importa la interfaz JpaRepository de Spring Data JPA
import org.springframework.data.jpa.repository.Modifying;

import edu.apiWod.modelos.TokenRecuperacionContrasena;
import edu.apiWod.modelos.UsuarioModelo;
import jakarta.transaction.Transactional;

/**
 * Contiene los metodos que haran las query a la base de datos con la tabla de token-recuperacion
 * msm - 060325
 */
public interface TokenRecuperacionContrasenaRepositorio extends JpaRepository<TokenRecuperacionContrasena , Long> {
  
	TokenRecuperacionContrasena findByToken(String token);
	
	@Modifying
    @Transactional
	void deleteByUsuario(UsuarioModelo usuario);
}
