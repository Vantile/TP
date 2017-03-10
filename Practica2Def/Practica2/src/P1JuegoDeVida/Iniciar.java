package P1JuegoDeVida;

/**
 * Comando que inicia el mundo.
 */
public class Iniciar extends Comando {

	@Override
	public void ejecuta(Mundo mundo) {
		mundo.vaciar();
		mundo.iniciar();
		System.out.println("Mundo iniciado correctamente.");
	}

	@Override
	public Comando parsea(String[] cadenaComando) {
		if(cadenaComando.length==1)
		{
			if(cadenaComando[0].equals("INICIAR"))
				return new Iniciar();
			else
				return null;
		}
		else
			return null;
	}

	@Override
	public String textoAyuda() {
		return "INICIAR: Inicia la simulacion.";
	}

}
