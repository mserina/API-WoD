package edu.apiWod.repositorios; 

import org.springframework.data.jpa.repository.JpaRepository; // Importa la interfaz JpaRepository de Spring Data JPA

import edu.apiWod.modelos.UsuarioModelo;

public interface UsuarioRepositorio extends JpaRepository<UsuarioModelo , Long> {
  
}
