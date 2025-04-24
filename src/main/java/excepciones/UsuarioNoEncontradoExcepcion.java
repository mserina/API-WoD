package excepciones;

public class UsuarioNoEncontradoExcepcion extends RuntimeException{
	
	public UsuarioNoEncontradoExcepcion(String msg) {
        super(msg);
    }
}
