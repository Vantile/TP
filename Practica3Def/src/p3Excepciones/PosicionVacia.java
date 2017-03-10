package p3Excepciones;

/**
 * Excepcion que indica que una posicion esta vacía.
 */
@SuppressWarnings("serial")
public class PosicionVacia extends Exception {

	public PosicionVacia(){}
	public PosicionVacia(String error)
	{
		super(error);
	}
}
