package edu.apiWod.modelos;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
/**
 * Contiene los campos usuario que se usaran para la base de datos
 * msm - 060325
 */
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

	    @Lob
	    @Column(name = "foto", columnDefinition = "bytea")
	    private byte[] foto;
	    
	    @Column(name = "tokenRecuperacion", length = 255)
	    private String tokenRecuperacion;
	    
	    @Column(name = "verificado")
	    private boolean verificado;
	
	    @OneToOne(mappedBy = "usuario", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
	    private TokenRecuperacionContrasena resetToken;
	   
	    

	    
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

	    public byte[] getFoto() {
	        return foto;
	    }

	    public void setFoto(byte[] foto) {
	        this.foto = foto;
	    }
	    
	    public String getTokenRecuperacion() {
	        return tokenRecuperacion;
	    }

	    public void setTokenRecuperacion(String tokenRecuperacion) {
	        this.tokenRecuperacion = tokenRecuperacion;
	    }
	    
	    public boolean getVerificado() {
	        return verificado;
	    }
	    
	    public void setVerificado(boolean verificado) {
	        this.verificado = verificado;
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

