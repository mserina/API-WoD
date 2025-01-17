package edu.apiWod.modelos;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario",  schema = "administracion_usuarios")  // Especifica el nombre de la tabla en la base de datos
public class UsuarioModelo {

	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private Long id;

	    @Column(name = "nombre_completo", nullable = false, length = 50)
	    private String nombreCompleto;

	    @Column(name = "movil", nullable = false, length = 15)
	    private String movil;

	    @Column(name = "correo_electronico", nullable = false, unique = true, length = 50)
	    private String correoElectronico;

	    @Column(name = "tipo_usuario", nullable = false, length = 10)
	    private String tipoUsuario;

	    @Column(name = "contrasena", nullable = false, length = 255)
	    private String contrasena;

	    @Column(name = "foto", nullable = false, length = 255)
	    private String foto;

	    @Column(name = "token", nullable = false, length = 255)
	    private String token;

		@Column(name = "creacion_token", nullable = false)
	    private LocalDateTime creacionToken; 
	    
	    @Column(name = "expiracion_token", nullable = false)
	    private LocalDateTime expiracionToken; 
	    

	    
	    // Getters y Setters
	    
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getNombreCompleto() {
	        return nombreCompleto;
	    }

	    public void setNombreCompleto(String nombreCompleto) {
	        this.nombreCompleto = nombreCompleto;
	    }

	    public String getMovil() {
	        return movil;
	    }

	    public void setMovil(String movil) {
	        this.movil = movil;
	    }

	    public String getCorreoElectronico() {
	        return correoElectronico;
	    }

	    public void setCorreoElectronico(String correoElectronico) {
	        this.correoElectronico = correoElectronico;
	    }

	    public String getTipoUsuario() {
	        return tipoUsuario;
	    }

	    public void setTipoUsuario(String tipoUsuario) {
	        this.tipoUsuario = tipoUsuario;
	    }

	    public String getContrasena() {
	        return contrasena;
	    }

	    public void setContrasena(String contrasena) {
	        this.contrasena = contrasena;
	    }

	    public String getFoto() {
	        return foto;
	    }

	    public void setFoto(String foto) {
	        this.foto = foto;
	    }
	    
	    public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public LocalDateTime getCreacionToken() {
			return creacionToken;
		}

		public void setCreacionToken(LocalDateTime creacionToken) {
			this.creacionToken = creacionToken;
		}

		public LocalDateTime getExpiracionToken() {
			return expiracionToken;
		}

		public void setExpiracionToken(LocalDateTime expiracionToken) {
			this.expiracionToken = expiracionToken;
		}

	   
	    @Override
	    public String toString() {
	        return "UsuarioModelo{" +
	               "id=" + id +
	               ", nombreCompleto='" + nombreCompleto + '\'' +
	               ", movil='" + movil + '\'' +
	               ", correoElectronico='" + correoElectronico + '\'' +
	               ", tipoUsuario='" + tipoUsuario + '\'' +
	               '}';
	    }
}

