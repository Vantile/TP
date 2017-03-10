package p3Excepciones;

/**
 * Excepcion que salta cuando una posicion esta ocupada.
 */
@SuppressWarnings("serial")
public class PosicionOcupada extends Exception {

	public PosicionOcupada(){}
	public PosicionOcupada(String error)
	{
		super(error);
	}
}
