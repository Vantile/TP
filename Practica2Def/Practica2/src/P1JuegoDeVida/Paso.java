package P1JuegoDeVida;

/**
 * Avanza un paso en la evolución del mundo.
 */
public class Paso extends Comando {

	@Override
	public void ejecuta(Mundo mundo) {
		mundo.evoluciona();

	}

	@Override
	public Comando parsea(String[] cadenaComando) {
		if(cadenaComando.length==1)
		{
			if(cadenaComando[0].equals("PASO"))
				return new Paso();
			else
				return null;
		}
		else
			return null;
	}

	@Override
	public String textoAyuda() {
		return "PASO: Avanza la simulacion.";
	}

}
