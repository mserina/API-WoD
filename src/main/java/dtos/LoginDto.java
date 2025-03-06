package dtos;

/**
 * Contiene los campos del login
 * 
 * msm - 060325
 */
public class LoginDto {
	
	  private String correoElectronico;
	  private String contrasena;

	    // Getters y Setters
	    public String getCorreoElectronico() {
	        return correoElectronico;
	    }

	    public void setCorreoElectronico(String email) {
	        this.correoElectronico = email;
	    }

	    public String getContrasena() {
	        return contrasena;
	    }

	    public void setContrasena(String contrasena) {
	        this.contrasena = contrasena;
	    }
	    
}
