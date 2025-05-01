package edu.apiWod.modelos;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
/**
 * Campos de la tabla de los token de los usuarios
 * msm - 020525
 */
@Entity
@Table(name = "token-recuperacion",  schema = "administracion_usuarios")  // Especifica el nombre de la tabla en la base de datos
public class TokenRecuperacionContrasena {

	
	  @Id 
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  @Column (name = "id")
	  private Long id;

	  @Column(name = "token", nullable = false, unique = true, length = 255)
	  private String token;

	  @OneToOne(fetch = FetchType.LAZY, optional = false)
	  @JoinColumn(name = "usuario_id", nullable = false, unique = true)
	  private UsuarioModelo usuario;

	  @Column(name="expiracion_token", nullable = false)
	  private LocalDateTime expiracionToken;

	  
	  	public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public UsuarioModelo getUsuario() {
			return usuario;
		}

		public void setUsuario(UsuarioModelo usuario) {
			this.usuario = usuario;
		}

		public LocalDateTime getExpiracionToken() {
			return expiracionToken;
		}

		public void setExpiracionToken(LocalDateTime expiracionToken) {
			this.expiracionToken = expiracionToken;
		}

}
