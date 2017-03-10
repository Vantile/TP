package P1JuegoDeVida;

/**
 * Vacia el mundo.
 */
public class Vaciar extends Comando {

	@Override
	public void ejecuta(Mundo mundo) {
		mundo.vaciar();
		System.out.println("Mundo vaciado correctamente.");
	}

	@Override
	public Comando parsea(String[] cadenaComando) {
		if(cadenaComando.length==1)
		{
			if(cadenaComando[0].equals("VACIAR"))
				return new Vaciar();
			else
				return null;
		}
		else
			return null;
	}

	@Override
	public String textoAyuda() {
		return "VACIAR: Crea un mundo vacio.";
	}

}
