package p3Excepciones;

/**
 * Excepcion que indica que se introdujo/ley� una palabra diferente a la esperada.
 */
@SuppressWarnings("serial")
public class PalabraIncorrecta extends Exception {

	public PalabraIncorrecta(){}
	public PalabraIncorrecta(String error)
	{
		super(error);
	}
}
