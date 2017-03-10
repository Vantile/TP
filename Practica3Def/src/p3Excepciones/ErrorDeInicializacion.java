package p3Excepciones;

/**
 * Excepcion que comunica un error de inicializacion en el mundo.
 */
@SuppressWarnings("serial")
public class ErrorDeInicializacion extends Exception {

	public ErrorDeInicializacion(){}
	public ErrorDeInicializacion(String error)
	{
		super(error);
	}
}
