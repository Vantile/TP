package p3Excepciones;

/**
 * Excepcion que comunica que una funcion intenta acceder a posiciones no accesibles.
 */
@SuppressWarnings("serial")
public class IndicesFueraDeRango extends Exception {

	public IndicesFueraDeRango(){}
	public IndicesFueraDeRango(String error)
	{
		super(error);
	}
}
