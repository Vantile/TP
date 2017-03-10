package P1JuegoDeVida;

/**
 * Comando que elimina una c�lula en el mundo.
 */
public class EliminarCelula extends Comando {
	// Casilla en la que ejecutar el comando.
	private Casilla casilla;
	
	public EliminarCelula()
	{
		casilla = null;
	}
	
	public EliminarCelula(int f, int c)
	{
		casilla = new Casilla(f, c);
	}
	
	@Override
	public void ejecuta(Mundo mundo) {
		boolean eliminada = mundo.eliminarCelula(casilla.getFila(), casilla.getColumna());
		if(eliminada)
			System.out.println("C�lula eliminada en (" + casilla.getFila() + ", " + casilla.getColumna() + ").");
		else
			System.err.println("No se pudo eliminar la c�lula.");
	}

	@Override
	public Comando parsea(String[] cadenaComando) {
		if(cadenaComando.length==3)
		{
			if(cadenaComando[0].equals("ELIMINARCELULA"))
			{
				int f = Integer.parseInt(cadenaComando[1]);
				int c = Integer.parseInt(cadenaComando[2]);
				return new EliminarCelula(f, c);
			}
			else
				return null;
		}
		else
			return null;
	}

	@Override
	public String textoAyuda() {
		return "ELIMINARCELULA F C: Elimina la c�lula del punto (F, C).";
	}

}
