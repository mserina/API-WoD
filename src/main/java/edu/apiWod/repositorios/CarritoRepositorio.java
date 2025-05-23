package edu.apiWod.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import edu.apiWod.modelos.CarritoModelo;

/**
 * Clase
 * msm - 230525
 */
public interface CarritoRepositorio extends JpaRepository<CarritoModelo, Long> {
    // Buscar todos los items de un usuario
    List<CarritoModelo> findByUsuarioId(Long usuarioId);
}
